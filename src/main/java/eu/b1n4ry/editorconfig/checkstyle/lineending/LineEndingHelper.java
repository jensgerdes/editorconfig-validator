package eu.b1n4ry.editorconfig.checkstyle.lineending;

import eu.b1n4ry.editorconfig.checkstyle.CheckResult;

import java.nio.file.Path;

public interface LineEndingHelper {
	/**
	 * This method is being called by an instance of {@link eu.b1n4ry.editorconfig.checkstyle.LineEndingCheck}
	 * for each character of the file that is currently being validated.
	 *
	 * @param character The latest character.
	 */
	void readCharacter(char character);

	/**
	 * This method is being called by an instance of {@link eu.b1n4ry.editorconfig.checkstyle.LineEndingCheck}
	 * once to validate the current file with the given Code Style.
	 *
	 * @param fileToCheck The file to validate.
	 * @return An instance of {@link CheckResult}, never null.
	 */
	CheckResult validate(Path fileToCheck);
}