package io.b1n4ry.editorconfig.check;

import java.nio.file.Path;

import io.b1n4ry.editorconfig.CodeStyle;
import io.b1n4ry.editorconfig.style.InsertFinalNewlineStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Checks that a given text file has a final new line if required.
 */
public class FinalNewLineCheck implements CharacterBasedCheck {

	private static final Logger LOG = LoggerFactory.getLogger(FinalNewLineCheck.class);
	private static final String ERROR_MESSAGE = "File(%s) is missing a final new line.";

	private char lastChar;
	private InsertFinalNewlineStyle finalNewlineStyle;

	@Override
	public void readCharacter(char character) {
		LOG.trace("Entering #readCharacter({}).", character);
		this.lastChar = character;
	}

	@Override
	public void setCodeStyle(CodeStyle codeStyle) {
		LOG.trace("Entering #setCodeStyle({}).", codeStyle);
		this.finalNewlineStyle = codeStyle.getInsertFinalNewlineStyle();
	}

	@Override
	public CheckResult validate(Path fileToCheck) {
		LOG.trace("Entering #validate({}).", fileToCheck);

		if (finalNewlineStyle != InsertFinalNewlineStyle.TRUE) {
			LOG.debug("Skipping check as final newline is not expected.");
			return CheckResult.SUCCESS;
		}

		if (Util.isLineEnding(lastChar)) {
			return CheckResult.SUCCESS;
		}

		LOG.debug("File({}) is missing a final new line.", fileToCheck);
		return CheckResult.withViolation(String.format(ERROR_MESSAGE, fileToCheck));
	}
}
