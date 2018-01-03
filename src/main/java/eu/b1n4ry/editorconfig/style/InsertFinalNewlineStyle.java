package eu.b1n4ry.editorconfig.style;

/**
 * Defines whether the file should end with a newline or not.
 */
public enum InsertFinalNewlineStyle {
	TRUE,
	FALSE,
	UNDEFINED;

	/**
	 * Parses the given value as InsertFinalNewlineStyle and returns an interpreted enum.
	 *
	 * @param newLineAtEnd The config value to parse.
	 * @return The corresponding InsertFinalNewlineStyle.
	 * @throws IllegalArgumentException Throws an exception when neither null, 'true' nor false.
	 */
	public static InsertFinalNewlineStyle parse(String newLineAtEnd) {
		if (newLineAtEnd == null) {
			return UNDEFINED;
		}

		switch (newLineAtEnd) {
			case "true":
				return TRUE;
			case "false":
				return FALSE;
			default:
				throw new IllegalArgumentException(
						String.format(
								"`%s` is not a valid value for `%%s`. Valid values are (true, false).",
								newLineAtEnd
						)
				);
		}
	}
}
