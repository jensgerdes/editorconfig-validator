package eu.b1n4ry.editorconfig.codingstyle;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.nio.charset.StandardCharsets;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CharsetStyleTest {

	private static final String NON_EXISTING_CHARSET = "NONSENSE";

	@Test
	void whenNullGivenThenReturnUNDEFINED() {
		final CharsetStyle style = CharsetStyle.parse(null);
		assertThat(style, is(CharsetStyle.UNDEFINED));
	}

	@Test
	void whenUTF8GivenThenReturnUTF8() {
		final CharsetStyle style = CharsetStyle.parse("utf-8");
		assertThat(style, is(CharsetStyle.UTF8));
		assertThat(style.getCharset(), is(StandardCharsets.UTF_8));
	}

	@Test
	void whenUTF16BEGivenThenReturnUTF16BE() {
		final CharsetStyle style = CharsetStyle.parse("utf-16be");
		assertThat(style, is(CharsetStyle.UTF16BE));
		assertThat(style.getCharset(), is(StandardCharsets.UTF_16BE));
	}

	@Test
	void whenUTF16LEGivenThenReturnUTF16LE() {
		final CharsetStyle style = CharsetStyle.parse("utf-16le");
		assertThat(style, is(CharsetStyle.UTF16LE));
		assertThat(style.getCharset(), is(StandardCharsets.UTF_16LE));
	}

	@Test
	void whenLatin1GivenThenReturnLATIN1() {
		final CharsetStyle style = CharsetStyle.parse("latin1");
		assertThat(style, is(CharsetStyle.LATIN1));
		assertThat(style.getCharset(), is(StandardCharsets.ISO_8859_1));
	}

	@Test
	void whenUtf8BomGivenThenReturnUTF8Bom() {
		final CharsetStyle style = CharsetStyle.parse("utf-8-bom");
		assertThat(style, is(CharsetStyle.UTF8BOM));
		assertThat(style.getCharset(), is(StandardCharsets.UTF_8));
	}

	@Test
	void whenInvalidValueGivenThenThrowsIllegalArgumentException() {
		final Executable parse = () -> CharsetStyle.parse(NON_EXISTING_CHARSET);
		final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, parse);
		assertThat(exception.getMessage(), is(equalTo(
				String.format(
						"`%s` is not a valid value for `%%s`. Valid values are (latin1, utf-8, utf-8-bom, utf-16be, utf-16le).",
						NON_EXISTING_CHARSET
				)
		)));

	}
}
