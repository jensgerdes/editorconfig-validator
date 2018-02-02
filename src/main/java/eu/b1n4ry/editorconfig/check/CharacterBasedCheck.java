package eu.b1n4ry.editorconfig.check;

/**
 * Extension of the {@link Check} interface for all validation plugins that can handle parsing the file character by character.
 * This is the recommended and fastest way to check the content of a file.
 */
public interface CharacterBasedCheck extends Check {

	/**
	 * This method is being called by an instance of {@link eu.b1n4ry.editorconfig.FileValidator} for each character of the
	 * file that is currently being validated.
	 *
	 * @param character The character to read.
	 */
	void readCharacter(char character);
}
