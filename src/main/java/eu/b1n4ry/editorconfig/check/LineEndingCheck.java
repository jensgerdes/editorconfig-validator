package eu.b1n4ry.editorconfig.check;

import java.nio.file.Path;

import eu.b1n4ry.editorconfig.CodeStyle;
import eu.b1n4ry.editorconfig.check.lineending.CrCheck;
import eu.b1n4ry.editorconfig.check.lineending.CrLfCheck;
import eu.b1n4ry.editorconfig.check.lineending.LfCheck;
import eu.b1n4ry.editorconfig.check.lineending.LineEndingHelper;
import eu.b1n4ry.editorconfig.check.lineending.NoOpCheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Checks that a given file uses the defined line ending style (LF, CR or CRLF).
 * This class delegates the handling of different Line endings to its {@link LineEndingHelper}s.
 */
public class LineEndingCheck implements CharacterBasedCheck {

	private static final Logger LOG = LoggerFactory.getLogger(LineEndingCheck.class);

	private LineEndingHelper delegate;

	@Override
	public void readCharacter(char character) {
		LOG.trace("Entering readCharacter({}).", character);
		delegate.readCharacter(character);
	}

	@Override
	public void setCodeStyle(CodeStyle codeStyle) {
		LOG.trace("Entering #setCodeStyle({}).", codeStyle);

		switch (codeStyle.getLineEndingStyle()) {
			case LF:
				LOG.trace("Instantiating LfCheck.");
				this.delegate = new LfCheck();
				break;
			case CR:
				LOG.trace("Instantiating CrCheck.");
				this.delegate = new CrCheck();
				break;
			case CRLF:
				LOG.trace("Instantiating CrLfCheck.");
				this.delegate = new CrLfCheck();
				break;
			default:
				LOG.trace("Assigning NoOpCheck.");
				this.delegate = NoOpCheck.INSTANCE;
		}
	}

	@Override
	public CheckResult validate(Path fileToCheck) {
		LOG.trace("Entering #validate({}).", fileToCheck);
		return delegate.validate(fileToCheck);
	}
}
