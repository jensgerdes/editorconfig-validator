package io.b1n4ry.editorconfig.check.indentation;

import java.nio.file.Path;

import io.b1n4ry.editorconfig.check.CheckResult;
import io.b1n4ry.editorconfig.style.IndentationSize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.b1n4ry.editorconfig.check.Util.isSpace;
import static io.b1n4ry.editorconfig.check.Util.isTab;

/**
 * Checks that a given file uses only spaces as indentation. If provided with the indentation size it checks for the correct number
 * of indents, as well.
 */
public class SpaceCheck extends AbstractIndentCheck {

	private static final Logger LOG = LoggerFactory.getLogger(SpaceCheck.class);
	private static final String ERROR_MESSAGE_TAB = "File(%s) is (partially) tab-indented although space-indentation is requested.";
	private static final String ERROR_MESSAGE_INDENT_SIZE = "File(%s) has an invalid indentation size.";

	private final IndentationSize indentationSize;
	private int indents = 0;
	private boolean hasValidIndentSize = true;

	public SpaceCheck(IndentationSize indentationSize) {
		this.indentationSize = indentationSize;
	}

	@Override
	public void readCharacter(char character) {
		LOG.trace("Entering #readCharacter({}).", character);

		if (followsLineEndOrIndent()) {
			checkAndSetIndentationSize(character);
		}

		updateLastChar(character);
	}

	private void checkAndSetIndentationSize(char character) {
		LOG.trace("Entering #checkAndSetIndentationSize({}).", character);

		if (isTab(character)) {
			LOG.debug("Found a TAB that follows an INDENT or LINE END.");
			flagAsInvalidIndentation();
		} else if (isSpace(character)) {
			indents++;
		} else {
			considerAndResetIndentationSize();
		}
	}

	private void considerAndResetIndentationSize() {
		LOG.trace("Entering #considerIndentationSize().");
		hasValidIndentSize &= indents % indentationSize.getSize() == 0;
		indents = 0;
	}

	private String createErrorMessage(String message, Path fileToCheck) {
		LOG.trace("Entering #createErrorMessage({}, {}).", message, fileToCheck);
		return String.format(message, fileToCheck);
	}

	@Override
	protected String createErrorMessage(Path fileToCheck) {
		LOG.trace("Entering #createErrorMessage({}).", fileToCheck);
		return createErrorMessage(ERROR_MESSAGE_TAB, fileToCheck);
	}

	@Override
	protected boolean isIndentChar(char c) {
		LOG.trace("Entering #isIndentChar({}).", c);
		return isSpace(c);
	}

	@Override
	public CheckResult validate(Path fileToCheck) {
		LOG.trace("Entering #validate({}).", fileToCheck);

		final CheckResult indentSizeResult = (hasValidIndentSize) ? CheckResult.SUCCESS :
				CheckResult.withViolation(createErrorMessage(ERROR_MESSAGE_INDENT_SIZE, fileToCheck));

		return CheckResult.merge(super.validate(fileToCheck), indentSizeResult);
	}
}
