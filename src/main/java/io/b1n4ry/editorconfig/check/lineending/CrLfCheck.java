package io.b1n4ry.editorconfig.check.lineending;

import io.b1n4ry.editorconfig.check.CheckResult;
import io.b1n4ry.editorconfig.style.LineEndingStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

/**
 * Checks that a given Character stream contains only {@link LineEndingStyle#CRLF}
 * line endings.
 */
public class CrLfCheck implements LineEndingHelper {

	private static final Logger LOG = LoggerFactory.getLogger(CrLfCheck.class);
	private static final String ERROR = "File(%s) contains non-compliant line-endings.";

	private static final char LF = '\n';
	private static final char CR = '\r';

	private boolean expectLf;
	private boolean foundNonCompliantCharacter;

	@Override
	public void readCharacter(char character) {
		LOG.trace("Entering readCharacter({}).", character);

		if (expectLf) {
			if (LF != character) {
				LOG.debug("Found invalid char({}), expected \\n to follow recent \\r.", character);
				foundNonCompliantCharacter = true;
			}
			expectLf = false;
		} else {
			if (CR == character) {
				LOG.debug("Found \\r, expecting \\n to be the next char.");
				expectLf = true;
			} else if (LF == character) {
				LOG.debug("Found \\n, without preceding \\r.");
				foundNonCompliantCharacter = true;
			}
		}
	}

	@Override
	public CheckResult validate(Path fileToCheck) {
		LOG.trace("Entering #validate({}).", fileToCheck);

		if (expectLf || foundNonCompliantCharacter) {
			return CheckResult.withViolation(String.format(ERROR, fileToCheck));
		} else return CheckResult.SUCCESS;
	}
}
