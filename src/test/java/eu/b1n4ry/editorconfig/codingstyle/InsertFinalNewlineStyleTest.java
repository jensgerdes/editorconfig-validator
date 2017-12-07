package eu.b1n4ry.editorconfig.codingstyle;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InsertFinalNewlineStyleTest {

	private static final String INVALID_VALUE = "nonsense";

	@Test
	void whenNullGivenThenReturnUNDEFINED() {
		final InsertFinalNewlineStyle style = InsertFinalNewlineStyle.parse(null);
		assertThat(style, is(InsertFinalNewlineStyle.UNDEFINED));
	}

	@Test
	void whenTrueGivenThenReturnTRUE() {
		final InsertFinalNewlineStyle style = InsertFinalNewlineStyle.parse("true");
		assertThat(style, is(InsertFinalNewlineStyle.TRUE));
	}

	@Test
	void whenFalseGivenThenReturnFALSE() {
		final InsertFinalNewlineStyle style = InsertFinalNewlineStyle.parse("false");
		assertThat(style, is(InsertFinalNewlineStyle.FALSE));
	}

	@Test
	void whenInvalidValueGivenThenThrowsIllegalArgumentException() {
		final Executable parse = () -> InsertFinalNewlineStyle.parse(INVALID_VALUE);
		final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, parse);
		assertThat(exception.getMessage(), is(equalTo(
				String.format("`%s` is not a valid value for `%%s`. Valid values are (true, false).", INVALID_VALUE)
		)));
	}
}
