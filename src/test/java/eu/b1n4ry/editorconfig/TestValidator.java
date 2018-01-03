package eu.b1n4ry.editorconfig;

import eu.b1n4ry.editorconfig.check.CharacterBasedCheck;
import eu.b1n4ry.editorconfig.check.Check;
import eu.b1n4ry.editorconfig.check.CheckResult;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Testing utility that sets all required sources for a given Check and if it is a {@link eu.b1n4ry.editorconfig.check.CharacterBasedCheck}
 * feeds the check with all characters from a given source.
 */
public class TestValidator {

	public static CheckResult applyGeneric(Check check, CodeStyle style, Path fileToCheck) {
		check.setCodeStyle(style);
		return check.validate(fileToCheck);
	}

	public static CheckResult apply(CharacterBasedCheck check, CodeStyle style, Path fileToCheck) throws IOException {
		check.setCodeStyle(style);

		int value;
		try (final Reader reader = Files.newBufferedReader(fileToCheck, style.getCharsetStyle().getCharset())) {
			while ((value = reader.read()) > -1) {
				check.readCharacter((char) value);
			}
		}

		return check.validate(fileToCheck);
	}
}
