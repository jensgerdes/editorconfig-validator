package eu.b1n4ry.editorconfig.codingstyle;

/**
 * Defines whether whitespace is allowed at the end of lines or not.
 */
public enum TrimTrailingWhiteSpaceStyle {
	TRUE,
	FALSE,
	UNDEFINED;

	/**
	 * Parses the given value as TrimTrailingWhiteSpaceStyle and returns an interpreted enum.
	 *
	 * @param trimWhitespace The config value to parse.
	 * @return The corresponding TrimTrailingWhiteSpaceStyle.
	 * @throws IllegalArgumentException Throws an exception when neither null, 'true' nor false.
	 */
	public static TrimTrailingWhiteSpaceStyle parse(String trimWhitespace) {
		if (trimWhitespace == null) {
			return UNDEFINED;
		}

		switch (trimWhitespace) {
			case "true":
				return TRUE;
			case "false":
				return FALSE;
			default:
				throw new IllegalArgumentException(
						String.format(
								"`%s` is not a valid value for `%%s`. Valid values are (true, false).",
								trimWhitespace
						)
				);
		}
	}
}
