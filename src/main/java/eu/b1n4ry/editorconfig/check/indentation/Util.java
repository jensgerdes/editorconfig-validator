package eu.b1n4ry.editorconfig.check.indentation;

/**
 * Utility class for {@link IndentationHelper} instances.
 */
class Util {

	private static final char CR = '\r';
	private static final char LF = '\n';
	private static final char TAB = '\t';

	static boolean isLineEnding(char c) {
		return c == CR || c == LF;
	}

	static boolean isTab(char c) {
		return c == TAB;
	}

	static boolean isSpace(char c) {
		return Character.isSpaceChar(c);
	}
}
