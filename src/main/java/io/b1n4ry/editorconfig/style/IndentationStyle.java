package io.b1n4ry.editorconfig.style;

/**
 * Defines the type of indentation to be used. Allowed values are (TAB, SPACE).
 */
public enum IndentationStyle {
	TAB,
	SPACE,
	UNDEFINED;

	/**
	 * Parses the given value as IndentationStyle and returns an interpreted enum.
	 *
	 * @param styleName The config value to parse.
	 * @return The corresponding IndentationStyle.
	 * @throws IllegalArgumentException Throws an exception when neither null, 'tab' nor 'space' is given.
	 */
	public static IndentationStyle parse(String styleName) {
		if (styleName == null) {
			return IndentationStyle.UNDEFINED;
		}

		switch (styleName) {
			case "space":
				return IndentationStyle.SPACE;
			case "tab":
				return IndentationStyle.TAB;
			default:
				throw new IllegalArgumentException(
						String.format("`%s` is not a valid value for `%%s`. Valid values are (space, tab).", styleName));
		}
	}
}
