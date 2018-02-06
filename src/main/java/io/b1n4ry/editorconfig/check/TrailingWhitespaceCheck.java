package io.b1n4ry.editorconfig.check;

import java.nio.file.Path;

import io.b1n4ry.editorconfig.CodeStyle;
import io.b1n4ry.editorconfig.style.TrimTrailingWhiteSpaceStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Checks that a file does not have trailing whitespace.
 */
public class TrailingWhitespaceCheck implements CharacterBasedCheck {

	private static final Logger LOG = LoggerFactory.getLogger(TrailingWhitespaceCheck.class);
	private static final String ERROR_MESSAGE = "File(%s) has unwanted trailing whitespaces.";

	private TrimTrailingWhiteSpaceStyle style;
	private boolean foundTrailingWhitespace = false;
	private boolean previousCharWasSpace = false;

	@Override
	public void readCharacter(char character) {
		LOG.trace("Entering #readCharacter({}).", character);

		if (Util.isLineEnding(character) && previousCharWasSpace) {
			LOG.debug("Found trailing whitespace.");
			foundTrailingWhitespace = true;
		}

		previousCharWasSpace = Util.isSpace(character);
	}

	@Override
	public void setCodeStyle(CodeStyle codeStyle) {
		LOG.trace("Entering #setCodeStyle({}).", codeStyle);
		this.style = codeStyle.getTrimTrailingWhiteSpaceStyle();
	}

	@Override
	public CheckResult validate(Path fileToCheck) {
		LOG.trace("Entering #validate({}).", fileToCheck);

		if (foundTrailingWhitespace && style == TrimTrailingWhiteSpaceStyle.TRUE) {
			LOG.debug("Found unwanted trailing whitespace.");
			return CheckResult.withViolation(String.format(ERROR_MESSAGE, fileToCheck));
		}

		return CheckResult.SUCCESS;
	}
}
