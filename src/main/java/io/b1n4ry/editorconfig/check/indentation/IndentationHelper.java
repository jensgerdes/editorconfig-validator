package io.b1n4ry.editorconfig.check.indentation;

import java.nio.file.Path;

import io.b1n4ry.editorconfig.check.CheckResult;
import io.b1n4ry.editorconfig.check.IndentationCheck;

public interface IndentationHelper {

	/**
	 * This method is being called by an instance of {@link IndentationCheck}
	 * for each character of the file that is currently being validated.
	 *
	 * @param character The latest character.
	 */
	void readCharacter(char character);

	/**
	 * This method is being called by an instance of {@link IndentationCheck}
	 * once to validate the current file with the given Code Style.
	 *
	 * @param fileToCheck The file to validate.
	 * @return An instance of {@link CheckResult}, never null.
	 */
	CheckResult validate(Path fileToCheck);
}
