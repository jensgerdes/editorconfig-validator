package io.b1n4ry.editorconfig.check;

import io.b1n4ry.editorconfig.check.indentation.IndentationHelper;

/**
 * Utility class for {@link IndentationHelper} instances.
 */
public class Util {

	private static final char CR = '\r';
	private static final char LF = '\n';
	private static final char TAB = '\t';

	/**
	 * Checks if the given character is a line ending character (a line feed or carriage return).
	 *
	 * @param c The character to check.
	 * @return True if the character is equal to '\r' or '\n'.
	 */
	public static boolean isLineEnding(char c) {
		return c == CR || c == LF;
	}

	/**
	 * Checks if the given character is a tab character.
	 *
	 * @param c The character to check.
	 * @return True if the character is equal to '\t', false otherwise.
	 */
	public static boolean isTab(char c) {
		return c == TAB;
	}

	/**
	 * Checks if the given character is a whitespace character.
	 *
	 * @param c The character to check.
	 * @return True if the character is a whitespace character, false otherwise.
	 */
	public static boolean isSpace(char c) {
		return Character.isSpaceChar(c);
	}
}
