package io.b1n4ry.editorconfig.style;

/**
 * Defines the desired line endings file format. Valid values are (lf, crlf, cr).
 */
public enum LineEndingStyle {
	LF,
	CRLF,
	CR,
	UNDEFINED;

	/**
	 * Parses the given value as LineEndingStyle and returns an interpreted enum.
	 *
	 * @param lineEnding The config value to parse.
	 * @return The corresponding LineEndingStyle.
	 * @throws IllegalArgumentException Throws an exception when none of (lf, crlf, cr, null) is given.
	 */
	public static LineEndingStyle parse(String lineEnding) {
		if (lineEnding == null) {
			return UNDEFINED;
		}

		switch (lineEnding) {
			case "lf":
				return LF;
			case "crlf":
				return CRLF;
			case "cr":
				return CR;
			default:
				throw new IllegalArgumentException(
						String.format("`%s` is not a valid value for `%%s`. Valid values are (lf, crlf, cr).", lineEnding)
				);
		}
	}
}
