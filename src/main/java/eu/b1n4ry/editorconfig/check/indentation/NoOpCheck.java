package eu.b1n4ry.editorconfig.check.indentation;

import java.nio.file.Path;

import eu.b1n4ry.editorconfig.check.CheckResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * No-operation implementation of {@link IndentationHelper} that does nothing and returns success.
 */
public class NoOpCheck implements IndentationHelper {

	public static final IndentationHelper INSTANCE = new NoOpCheck();
	private static final Logger LOG = LoggerFactory.getLogger(NoOpCheck.class);

	private NoOpCheck() {
	}

	@Override
	public void readCharacter(char character) {
		LOG.trace("Entering #readCharacter({}).", character);
	}

	@Override
	public CheckResult validate(Path fileToCheck) {
		LOG.trace("Entering #validate({}).", fileToCheck);
		return CheckResult.SUCCESS;
	}
}
