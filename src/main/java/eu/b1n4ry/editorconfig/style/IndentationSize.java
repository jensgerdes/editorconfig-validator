package eu.b1n4ry.editorconfig.style;

import java.util.Objects;
import java.util.OptionalInt;

/**
 * Defines the number of indents to be used per indentation level.
 */
public class IndentationSize {

	private static final IndentationSize UNDEFINED = new IndentationSize(-1);
	private static final IndentationSize DEFAULT = new IndentationSize(1);

	private static final String TAB_IDENTIFIER = "tab";
	private static final String ERROR_NO_VALID_VALUE = "`%s` is not a valid value for `%%s`. Valid values are (a positive number, tab).";

	private final int size;

	private IndentationSize(int size) {
		this.size = size;
	}

	/**
	 * Parses the given value as IndentationSize and returns an interpreted enum.
	 *
	 * @param indentation_size The config value to parse.
	 * @param tab_width        The width of a tab stop. indentation_size defaults to tab_width if given and indentation_size = tab.
	 * @return The corresponding IndentationSize.
	 * @throws IllegalArgumentException Throws an exception when neither null, 'tab' nor a number is given.
	 */
	public static IndentationSize parse(String indentation_size, String tab_width) {
		if (indentation_size == null) {
			return UNDEFINED;
		}

		if (TAB_IDENTIFIER.equals(indentation_size)) {
			return fromTabWidth(tab_width);
		}

		final OptionalInt sizeAsInt = parseInt(indentation_size);

		if (sizeAsInt.isPresent()) {
			return new IndentationSize(sizeAsInt.getAsInt());
		}

		throw new IllegalArgumentException(String.format(ERROR_NO_VALID_VALUE, indentation_size));
	}

	/**
	 * Creates an instance of IndentationSize with the value of "tab_width" or 1 if no data can be found.
	 *
	 * @param tab_width_string The value to parse.
	 * @return An instance of IndentationSize.
	 */
	private static IndentationSize fromTabWidth(String tab_width_string) {
		final OptionalInt tab_width = parseInt(tab_width_string);

		if (tab_width.isPresent()) {
			return new IndentationSize(tab_width.getAsInt());
		}

		return DEFAULT;
	}

	private static OptionalInt parseInt(String numeric_string) {
		try {
			if (numeric_string != null) {
				return OptionalInt.of(Math.abs(Integer.valueOf(numeric_string)));
			}
		} catch (NumberFormatException e) {
			// ignore
		}

		return OptionalInt.empty();
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
