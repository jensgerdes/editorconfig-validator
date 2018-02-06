package io.b1n4ry.editorconfig.check;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class UtilTest {

	@Test
	void testIsLineEnding() {
		assertThat(Util.isLineEnding('a'), is(false));
		assertThat(Util.isLineEnding(' '), is(false));
		assertThat(Util.isLineEnding('\r'), is(true));
		assertThat(Util.isLineEnding('\n'), is(true));
	}

	@Test
	void testIsSpace() {
		assertThat(Util.isSpace('\n'), is(false));
		assertThat(Util.isSpace('\r'), is(false));
		assertThat(Util.isSpace('1'), is(false));
		assertThat(Util.isSpace('\0'), is(false));
		assertThat(Util.isSpace('\t'), is(false));
		assertThat(Util.isSpace(' '), is(true));
	}

	@Test
	void testIsTab() {
		assertThat(Util.isTab('\n'), is(false));
		assertThat(Util.isTab('1'), is(false));
		assertThat(Util.isTab('\0'), is(false));
		assertThat(Util.isTab('\t'), is(true));
		assertThat(Util.isTab(' '), is(false));
	}
}
