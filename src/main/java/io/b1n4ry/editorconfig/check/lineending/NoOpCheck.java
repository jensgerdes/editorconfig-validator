package io.b1n4ry.editorconfig.check.lineending;

import io.b1n4ry.editorconfig.check.CheckResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

/**
 * No-operation implementation of {@link LineEndingHelper} that does nothing and returns success.
 */
public class NoOpCheck implements LineEndingHelper {

	public static final LineEndingHelper INSTANCE = new NoOpCheck();
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
