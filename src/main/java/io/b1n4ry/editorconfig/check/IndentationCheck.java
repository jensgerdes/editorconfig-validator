package io.b1n4ry.editorconfig.check;

import java.nio.file.Path;

import io.b1n4ry.editorconfig.CodeStyle;
import io.b1n4ry.editorconfig.check.indentation.IndentationHelper;
import io.b1n4ry.editorconfig.check.indentation.NoOpCheck;
import io.b1n4ry.editorconfig.check.indentation.SpaceCheck;
import io.b1n4ry.editorconfig.check.indentation.TabCheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Checks that a given file uses the defined indentation style (SPACE, TAB) and size.
 * This class delegates the handling of different styles to its {@link IndentationHelper}s.
 */
public class IndentationCheck implements CharacterBasedCheck {

	private static final Logger LOG = LoggerFactory.getLogger(LineEndingCheck.class);

	private IndentationHelper delegate;

	@Override
	public void readCharacter(char character) {
		LOG.trace("Entering readCharacter({}).", character);
		delegate.readCharacter(character);
	}

	@Override
	public void setCodeStyle(CodeStyle codeStyle) {
		LOG.trace("Entering #setCodeStyle({}).", codeStyle);

		switch (codeStyle.getIndentationStyle()) {
			case TAB:
				LOG.trace("Instantiating TabCheck.");
				this.delegate = new TabCheck();
				break;
			case SPACE:
				LOG.trace("Instantiating SpaceCheck.");
				this.delegate = new SpaceCheck(codeStyle.getIndentationSize());
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
