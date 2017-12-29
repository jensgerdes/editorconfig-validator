package eu.b1n4ry.editorconfig.checkstyle.lineending;

import eu.b1n4ry.editorconfig.codingstyle.CharsetStyle;
import eu.b1n4ry.editorconfig.codingstyle.LineEndingStyle;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class ByteSequenceDeterminatorTest {

	@Test
	void whenNothingToConsiderThenReturnAllPossibleLineEndingSequences() {
		assertThatCharsetAndLineEndingStyleReturn(
				CharsetStyle.UNDEFINED,
				LineEndingStyle.UNDEFINED,
				ByteSequenceDeterminator.ALL_ENCODINGS
		);
	}

	@Test
	void whenLatin1GivenThenReturnOnlyAsciiLineEndings() {
		assertThatCharsetAndLineEndingStyleReturn(
				CharsetStyle.LATIN1,
				LineEndingStyle.UNDEFINED,
				ByteSequenceDeterminator.ASCII_ENCODINGS
		);
	}


	@Test
	void whenUTF8GivenThenReturnOnlyAsciiLineEndings() {
		assertThatCharsetAndLineEndingStyleReturn(
				CharsetStyle.UTF8,
				LineEndingStyle.UNDEFINED,
				ByteSequenceDeterminator.ASCII_ENCODINGS
		);
	}

	@Test
	void whenUTF8BOMGivenThenReturnOnlyAsciiLineEndings() {
		assertThatCharsetAndLineEndingStyleReturn(
				CharsetStyle.UTF8BOM,
				LineEndingStyle.UNDEFINED,
				ByteSequenceDeterminator.ASCII_ENCODINGS
		);
	}

	@Test
	void whenUTF16BEGivenThenReturnOnlyUTF16BELineEndings() {
		assertThatCharsetAndLineEndingStyleReturn(
				CharsetStyle.UTF16BE,
				LineEndingStyle.UNDEFINED,
				ByteSequenceDeterminator.UTF16BE_ENCODINGS
		);
	}

	@Test
	void whenUTF16LEGivenThenReturnOnlyUTF16BELineEndings() {
		assertThatCharsetAndLineEndingStyleReturn(
				CharsetStyle.UTF16LE,
				LineEndingStyle.UNDEFINED,
				ByteSequenceDeterminator.UTF16LE_ENCODINGS
		);
	}

	@Test
	void whenLFStyleGivenThenReturnOnlyLFEncodings() {
		assertThatCharsetAndLineEndingStyleReturn(
				CharsetStyle.UNDEFINED,
				LineEndingStyle.LF,
				ByteSequenceDeterminator.LF_ENCODINGS
		);
	}

	@Test
	void whenCRStyleGivenThenReturnOnlyCREncodings() {
		assertThatCharsetAndLineEndingStyleReturn(
				CharsetStyle.UNDEFINED,
				LineEndingStyle.CR,
				ByteSequenceDeterminator.CR_ENCODINGS
		);
	}

	@Test
	void whenCRLFStyleGivenThenReturnOnlyCRLFEncodings() {
		assertThatCharsetAndLineEndingStyleReturn(
				CharsetStyle.UNDEFINED,
				LineEndingStyle.CRLF,
				ByteSequenceDeterminator.CRLF_ENCODINGS
		);
	}

	@Test
	void whenLFStyleAndLatin1GivenThenReturnLFAscii() {
		assertThatCharsetAndLineEndingStyleReturn(
				CharsetStyle.LATIN1,
				LineEndingStyle.LF,
				Collections.singleton(ByteSequenceDeterminator.LF_ASCII)
		);
	}

	@Test
	void whenCRStyleAndLatin1GivenThenReturnCRAscii() {
		assertThatCharsetAndLineEndingStyleReturn(
				CharsetStyle.LATIN1,
				LineEndingStyle.CR,
				Collections.singleton(ByteSequenceDeterminator.CR_ASCII)
		);
	}

	@Test
	void whenCRLFStyleAndLatin1GivenThenReturnCRLFAscii() {
		assertThatCharsetAndLineEndingStyleReturn(
				CharsetStyle.LATIN1,
				LineEndingStyle.CRLF,
				Collections.singleton(ByteSequenceDeterminator.CRLF_ASCII)
		);
	}

	@Test
	void whenLFStyleAndUTF8GivenThenReturnLFAscii() {
		assertThatCharsetAndLineEndingStyleReturn(
				CharsetStyle.UTF8,
				LineEndingStyle.LF,
				Collections.singleton(ByteSequenceDeterminator.LF_ASCII)
		);
	}

	@Test
	void whenCRStyleAndUTF8GivenThenReturnCRAscii() {
		assertThatCharsetAndLineEndingStyleReturn(
				CharsetStyle.UTF8,
				LineEndingStyle.CR,
				Collections.singleton(ByteSequenceDeterminator.CR_ASCII)
		);
	}

	@Test
	void whenCRLFStyleAndUTF8GivenThenReturnCRLFAscii() {
		assertThatCharsetAndLineEndingStyleReturn(
				CharsetStyle.UTF8,
				LineEndingStyle.CRLF,
				Collections.singleton(ByteSequenceDeterminator.CRLF_ASCII)
		);
	}

	@Test
	void whenLFStyleAndUTF8BOMGivenThenReturnLFAscii() {
		assertThatCharsetAndLineEndingStyleReturn(
				CharsetStyle.UTF8BOM,
				LineEndingStyle.LF,
				Collections.singleton(ByteSequenceDeterminator.LF_ASCII)
		);
	}

	@Test
	void whenCRStyleAndUTF8BOMGivenThenReturnCRAscii() {
		assertThatCharsetAndLineEndingStyleReturn(
				CharsetStyle.UTF8BOM,
				LineEndingStyle.CR,
				Collections.singleton(ByteSequenceDeterminator.CR_ASCII)
		);
	}

	@Test
	void whenCRLFStyleAndUTF8BOMGivenThenReturnCRLFAscii() {
		assertThatCharsetAndLineEndingStyleReturn(
				CharsetStyle.UTF8BOM,
				LineEndingStyle.CRLF,
				Collections.singleton(ByteSequenceDeterminator.CRLF_ASCII)
		);
	}

	@Test
	void whenLFStyleAndUTF16BEGivenThenReturnLFUtf16be() {
		assertThatCharsetAndLineEndingStyleReturn(
				CharsetStyle.UTF16BE,
				LineEndingStyle.LF,
				Collections.singleton(ByteSequenceDeterminator.LF_UTF16BE)
		);
	}

	@Test
	void whenCRStyleAndUTF16BEGivenThenReturnCRUtf16be() {
		assertThatCharsetAndLineEndingStyleReturn(
				CharsetStyle.UTF16BE,
				LineEndingStyle.CR,
				Collections.singleton(ByteSequenceDeterminator.CR_UTF16BE)
		);
	}

	@Test
	void whenCRLFStyleAndUTF16BEGivenThenReturnCRLFUtf16be() {
		assertThatCharsetAndLineEndingStyleReturn(
				CharsetStyle.UTF16BE,
				LineEndingStyle.CRLF,
				Collections.singleton(ByteSequenceDeterminator.CRLF_UTF16BE)
		);
	}

	@Test
	void whenLFStyleAndUTF16LEGivenThenReturnLFUtf16le() {
		assertThatCharsetAndLineEndingStyleReturn(
				CharsetStyle.UTF16LE,
				LineEndingStyle.LF,
				Collections.singleton(ByteSequenceDeterminator.LF_UTF16LE)
		);
	}

	@Test
	void whenCRStyleAndUTF16LEGivenThenReturnCRUtf16le() {
		assertThatCharsetAndLineEndingStyleReturn(
				CharsetStyle.UTF16LE,
				LineEndingStyle.CR,
				Collections.singleton(ByteSequenceDeterminator.CR_UTF16LE)
		);
	}

	@Test
	void whenCRLFStyleAndUTF16lEGivenThenReturnCRLFUtf16le() {
		assertThatCharsetAndLineEndingStyleReturn(
				CharsetStyle.UTF16LE,
				LineEndingStyle.CRLF,
				Collections.singleton(ByteSequenceDeterminator.CRLF_UTF16LE)
		);
	}

	private void assertThatCharsetAndLineEndingStyleReturn(
			CharsetStyle charsetStyle,
			LineEndingStyle lineEndingStyle,
			Set<byte[]> expected) {
		final Set<byte[]> actualByteSequences = ByteSequenceDeterminator
				.determinePossibleByteSequences(charsetStyle, lineEndingStyle);

		assertThat(actualByteSequences.containsAll(expected), is(true));
	}
}
