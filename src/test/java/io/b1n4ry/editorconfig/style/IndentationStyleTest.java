package io.b1n4ry.editorconfig.style;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IndentationStyleTest {

	private static final String NON_EXISTING_STYLE_NAME = "abc";

	@Test
	void whenNullGivenThenDetermineStyleThrowsNullpointer() {
		final IndentationStyle style = IndentationStyle.parse(null);
		assertThat(style, is(IndentationStyle.UNDEFINED));
	}

	@Test
	void whenSpaceUpperCaseGivenThenReturnSPACE() {
		final IndentationStyle style = IndentationStyle.parse("space");
		assertThat(style, is(IndentationStyle.SPACE));
	}

	@Test
	void whenTabLowerCaseGivenThenReturnTAB() {
		final IndentationStyle style = IndentationStyle.parse("tab");
		assertThat(style, is(IndentationStyle.TAB));
	}

	@Test
	void whenNonExistingStyleGivenThenReturnIllegalArgumentException() {
		final Executable determineStyle = () -> IndentationStyle.parse(NON_EXISTING_STYLE_NAME);
		final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, determineStyle);

		assertThat(exception.getMessage(), is(equalTo(
				String.format("`%s` is not a valid value for `%%s`. Valid values are (space, tab).", NON_EXISTING_STYLE_NAME)
		)));
	}
}
