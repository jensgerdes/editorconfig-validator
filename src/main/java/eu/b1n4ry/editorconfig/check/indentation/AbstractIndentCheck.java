package eu.b1n4ry.editorconfig.check.indentation;

import java.nio.file.Path;

import eu.b1n4ry.editorconfig.check.CheckResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static eu.b1n4ry.editorconfig.check.Util.isLineEnding;

abstract class AbstractIndentCheck implements IndentationHelper {

	private static final Logger LOG = LoggerFactory.getLogger(AbstractIndentCheck.class);

	private CharType lastChar = CharType.LINE_END;
	private boolean hasInvalidIndentation;

	@Override
	public CheckResult validate(Path fileToCheck) {
		LOG.trace("Entering #validate({}).", fileToCheck);
		return (hasInvalidIndentation) ? CheckResult.withViolation(createErrorMessage(fileToCheck)) : CheckResult.SUCCESS;
	}

	protected abstract String createErrorMessage(Path fileToCheck);

	protected abstract boolean isIndentChar(char c);

	protected enum CharType {
		LINE_END, INDENT, OTHER
	}

	/**
	 * Interprets the given char in the context of the previous character.
	 * A line ending character is always a line ending. However, an indent character is only an indentation if
	 * it follows a line ending or another indent. All other characters are defined as "other".
	 *
	 * @param c The character to interpret.
	 * @return The corresponding CharType.
	 */
	private CharType toCharType(char c) {
		LOG.trace("Entering #toCharType({}).", c);

		if (isLineEnding(c)) {
			return CharType.LINE_END;
		}

		if (isIndentChar(c) && followsLineEndOrIndent()) {
			return CharType.INDENT;
		}

		return CharType.OTHER;
	}

	protected boolean followsLineEndOrIndent() {
		LOG.trace("Entering #followsLineEndOrIndent().");
		return lastChar == CharType.INDENT || lastChar == CharType.LINE_END;
	}

	protected void flagAsInvalidIndentation() {
		LOG.trace("Entering #flagAsInvalidIndentation().");
		this.hasInvalidIndentation = true;
	}

	protected void updateLastChar(char character) {
		LOG.trace("#updateLastChar({}).", character);
		lastChar = toCharType(character);
	}
}
