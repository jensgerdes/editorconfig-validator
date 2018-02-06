package eu.b1n4ry.editorconfig.check.indentation;

import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static eu.b1n4ry.editorconfig.check.Util.isSpace;
import static eu.b1n4ry.editorconfig.check.Util.isTab;

/**
 * Checks that a given file uses only tab characters as indentation.
 */
public class TabCheck extends AbstractIndentCheck {

	private static final Logger LOG = LoggerFactory.getLogger(TabCheck.class);
	private static final String ERROR_MESSAGE = "File(%s) is (partially) space-indented although tab-indentation is requested.";

	@Override
	public void readCharacter(char character) {
		LOG.trace("Entering #readCharacter({}).", character);

		if (followsLineEndOrIndent() && isSpace(character)) {
			LOG.debug("Found invalid space indentation.");
			flagAsInvalidIndentation();
		}

		updateLastChar(character);
	}

	@Override
	protected String createErrorMessage(Path fileToCheck) {
		LOG.trace("Entering #createErrorMessage({}).", fileToCheck);
		return String.format(ERROR_MESSAGE, fileToCheck);
	}

	@Override
	protected boolean isIndentChar(char c) {
		LOG.trace("Entering #isIndentChar({}).", c);
		return isTab(c);
	}
}
