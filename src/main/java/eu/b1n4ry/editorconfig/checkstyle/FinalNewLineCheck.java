package eu.b1n4ry.editorconfig.checkstyle;

import eu.b1n4ry.editorconfig.CodeStyle;
import eu.b1n4ry.editorconfig.codingstyle.LineEndingStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

/**
 * Checks that a given text file has a final new line if required.
 */
public class FinalNewLineCheck implements CharacterBasedCheck {

	private static final Logger LOG = LoggerFactory.getLogger(FinalNewLineCheck.class);
	private static final String ERROR_MESSAGE = "File(%s) is missing a final new line.";

	private static final char CR = '\r';
	private static final char LF = '\n';

	private char lastChar;
	private LineEndingStyle style;
	private Path fileToCheck;

	@Override
	public void readCharacter(char character) {
		LOG.trace("Entering #readCharacter({}).", character);
		this.lastChar = character;
	}

	@Override
	public void setCodeStyle(CodeStyle codeStyle) {
		LOG.trace("Entering #setCodeStyle({}).", codeStyle);
		this.style = codeStyle.getLineEndingStyle();
	}

	@Override
	public CheckResult validate(Path fileToCheck) {
		LOG.trace("Entering #validate({}).", fileToCheck);

		this.fileToCheck = fileToCheck;

		final CheckResult result;

		switch (style) {
			case LF:
			case CRLF:
				// do lf
				result = expectLastCharToBe(LF);
				break;
			case CR:
				// do cr
				result = expectLastCharToBe(CR);
				break;
			default:
				result = CheckResult.SUCCESS;
		}

		return result;
	}

	private CheckResult expectLastCharToBe(char expectedLastChar) {
		if (expectedLastChar == lastChar) {
			return CheckResult.SUCCESS;
		}

		return CheckResult.withViolation(String.format(ERROR_MESSAGE, fileToCheck));
	}
}
