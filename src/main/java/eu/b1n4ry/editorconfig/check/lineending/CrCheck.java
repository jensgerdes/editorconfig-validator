package eu.b1n4ry.editorconfig.check.lineending;

import eu.b1n4ry.editorconfig.check.CheckResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

/**
 * Checks that a given Character stream contains only {@link eu.b1n4ry.editorconfig.style.LineEndingStyle#CR}
 * line endings.
 */
public class CrCheck implements LineEndingHelper {

	private static final Logger LOG = LoggerFactory.getLogger(CrCheck.class);
	private static final String ERROR = "File(%s) contains non-compliant line-endings (LF).";

	private static final char NON_COMPLIANT_CHARACTER = '\n';

	private boolean foundNonCompliantCharacter;

	@Override
	public void readCharacter(char character) {
		LOG.trace("Entering readCharacter({}).", character);

		if (NON_COMPLIANT_CHARACTER == character) {
			LOG.debug("Found an LF character.");
			foundNonCompliantCharacter = true;
		}
	}

	@Override
	public CheckResult validate(Path fileToCheck) {
		LOG.trace("Entering #validate({}).", fileToCheck);

		if (foundNonCompliantCharacter) {
			return CheckResult.withViolation(String.format(ERROR, fileToCheck));
		} else return CheckResult.SUCCESS;
	}
}
