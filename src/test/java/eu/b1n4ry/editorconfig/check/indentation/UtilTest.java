package eu.b1n4ry.editorconfig.check.indentation;

import org.junit.jupiter.api.Test;

import static eu.b1n4ry.editorconfig.check.indentation.Util.isLineEnding;
import static eu.b1n4ry.editorconfig.check.indentation.Util.isSpace;
import static eu.b1n4ry.editorconfig.check.indentation.Util.isTab;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

class UtilTest {

	@Test
	void testIsLineEnding() {
		assertThat(isLineEnding('a'), is(false));
		assertThat(isLineEnding(' '), is(false));
		assertThat(isLineEnding('\r'), is(true));
		assertThat(isLineEnding('\n'), is(true));
	}

	@Test
	void testIsSpace() {
		assertThat(isSpace('\n'), is(false));
		assertThat(isSpace('1'), is(false));
		assertThat(isSpace('\0'), is(false));
		assertThat(isSpace('\t'), is(false));
		assertThat(isSpace(' '), is(true));
	}

	@Test
	void testIsTab() {
		assertThat(isTab('\n'), is(false));
		assertThat(isTab('1'), is(false));
		assertThat(isTab('\0'), is(false));
		assertThat(isTab('\t'), is(true));
		assertThat(isTab(' '), is(false));
	}
}
