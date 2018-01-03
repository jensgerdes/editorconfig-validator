package eu.b1n4ry.editorconfig.style;

import java.util.Objects;

/**
 * Defines the number of indents to be used per indentation level.
 */
public class IndentationSize {

	private static final IndentationSize UNDEFINED = new IndentationSize(-1);
	private static final IndentationSize TAB = new IndentationSize(-2);

	private static final String TAB_IDENTIFIER = "tab";

	private final int size;

	private IndentationSize(int size) {
		this.size = size;
	}

	/**
	 * Parses the given value as IndentationSize and returns an interpreted enum.
	 *
	 * @param size The config value to parse.
	 * @return The corresponding IndentationSize.
	 * @throws IllegalArgumentException Throws an exception when neither null, 'tab' nor a number is given.
	 */
	public static IndentationSize parse(String size) {
		if (size == null) {
			return UNDEFINED;
		}

		if (TAB_IDENTIFIER.equals(size)) {
			return TAB;
		}

		try {
			final int sizeInt = Math.abs(Integer.parseInt(size));
			return new IndentationSize(sizeInt);
		} catch (NumberFormatException e) {
			final String error = String.format(
					"`%s` is not a valid value for `%%s`. Valid values are (a positive number, tab).",
					size
			);
			throw new IllegalArgumentException(error);
		}
	}

	/**
	 * Returns the indent_size;
	 *
	 * @return The indent_size.
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Checks if the indent_size is undefined.
	 *
	 * @return True if indent_size is not defined, false otherwise.
	 */
	public boolean isUndefined() {
		return UNDEFINED.equals(this);
	}

	/**
	 * Checks if the indent_size is set to 'tab'.
	 *
	 * @return True if indent_size is set to 'tab', false otherwise.
	 */
	public boolean isTab() {
		return TAB.equals(this);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final IndentationSize that = (IndentationSize) o;
		return size == that.size;
	}

	@Override
	public int hashCode() {
		return Objects.hash(size);
	}
}
