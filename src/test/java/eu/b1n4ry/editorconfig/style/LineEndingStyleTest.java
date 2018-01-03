package eu.b1n4ry.editorconfig.style;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LineEndingStyleTest {

	private static final String NON_EXISTING_LINE_ENDING_STYLE = "LFCR";

	@Test
	void whenNullIsGivenThenUNDEFINEDIsReturned() {
		final LineEndingStyle style = LineEndingStyle.parse(null);
		assertThat(style, is(LineEndingStyle.UNDEFINED));
	}

	@Test
	void whenCRIsGivenThenCRIsReturned() {
		final LineEndingStyle style = LineEndingStyle.parse("cr");
		assertThat(style, is(LineEndingStyle.CR));
	}

	@Test
	void whenLFIsGivenThenLFIsReturned() {
		final LineEndingStyle style = LineEndingStyle.parse("lf");
		assertThat(style, is(LineEndingStyle.LF));
	}

	@Test
	void whenCRLFIsGivenThenCRLFIsReturned() {
		final LineEndingStyle style = LineEndingStyle.parse("crlf");
		assertThat(style, is(LineEndingStyle.CRLF));
	}

	@Test
	void whenInvalidValueIsGivenThenIllegalArgumentExceptionIsThrown() {
		final Executable parse = () -> LineEndingStyle.parse(NON_EXISTING_LINE_ENDING_STYLE);
		final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, parse);

		assertThat(exception.getMessage(), is(equalTo(
				String.format("`%s` is not a valid value for `%%s`. Valid values are (lf, crlf, cr).", NON_EXISTING_LINE_ENDING_STYLE)
		)));
	}
}
