package eu.b1n4ry.editorconfig.checkstyle.lineending;

import eu.b1n4ry.editorconfig.codingstyle.CharsetStyle;
import eu.b1n4ry.editorconfig.codingstyle.LineEndingStyle;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Determines the possible Byte sequences that represent a "Line ending" using a specific charset and line ending style.
 */
public class ByteSequenceDeterminator {

	private static final String LF = "\n";
	private static final String CR = "\r";
	private static final String CRLF = "\r\n";

	static final byte[] LF_ASCII = LF.getBytes(StandardCharsets.US_ASCII);
	static final byte[] LF_UTF16BE = LF.getBytes(StandardCharsets.UTF_16BE);
	static final byte[] LF_UTF16LE = LF.getBytes(StandardCharsets.UTF_16LE);

	static final byte[] CR_ASCII = CR.getBytes(StandardCharsets.US_ASCII);
	static final byte[] CR_UTF16BE = CR.getBytes(StandardCharsets.UTF_16BE);
	static final byte[] CR_UTF16LE = CR.getBytes(StandardCharsets.UTF_16LE);

	static final byte[] CRLF_ASCII = CRLF.getBytes(StandardCharsets.US_ASCII);
	static final byte[] CRLF_UTF16BE = CRLF.getBytes(StandardCharsets.UTF_16BE);
	static final byte[] CRLF_UTF16LE = CRLF.getBytes(StandardCharsets.UTF_16LE);

	static final Set<byte[]> LF_ENCODINGS = createLfEncodings();
	static final Set<byte[]> CR_ENCODINGS = createCrEncodings();
	static final Set<byte[]> CRLF_ENCODINGS = createCrLfEncodings();

	static final Set<byte[]> ASCII_ENCODINGS = createAsciiEncodings();
	static final Set<byte[]> UTF16BE_ENCODINGS = createUtf16beEncodings();
	static final Set<byte[]> UTF16LE_ENCODINGS = createUtf16leEncodings();

	static final Set<byte[]> ALL_ENCODINGS = createAllEncodings();

	public static Set<byte[]> determinePossibleByteSequences(CharsetStyle charsetStyle, LineEndingStyle lineEndingStyle) {
		final Set<byte[]> possibleByteSequences = consider(charsetStyle);
		possibleByteSequences.retainAll(consider(lineEndingStyle));

		return possibleByteSequences;
	}

	/**
	 * Considers the given {@link LineEndingStyle} and returns a set of byte sequences that may represent a line ending using
	 * this particular lineEndingStyle.
	 *
	 * @param lineEndingStyle The LineEndingStyle to consider. May not be null.
	 * @return A mutable set of byte sequences that consider the given lineEndingStyle.
	 */
	private static Set<byte[]> consider(LineEndingStyle lineEndingStyle) {
		final Set<byte[]> sequences;

		switch (lineEndingStyle) {
			case LF:
				sequences = new HashSet<>(LF_ENCODINGS);
				break;
			case CR:
				sequences = new HashSet<>(CR_ENCODINGS);
				break;
			case CRLF:
				sequences = new HashSet<>(CRLF_ENCODINGS);
				break;
			default:
				sequences = new HashSet<>(ALL_ENCODINGS);
		}

		return sequences;
	}

	/**
	 * Considers the given {@link CharsetStyle} and returns a set of byte sequences that may represent a line ending using
	 * this particular charset.
	 *
	 * @param charsetStyle The Charset to consider. May not be null.
	 * @return A mutable set of byte sequences that consider the given charset.
	 */
	private static Set<byte[]> consider(CharsetStyle charsetStyle) {
		final Set<byte[]> sequences;

		switch (charsetStyle) {
			case LATIN1:
			case UTF8:
			case UTF8BOM:
				sequences = new HashSet<>(ASCII_ENCODINGS);
				break;
			case UTF16BE:
				sequences = new HashSet<>(UTF16BE_ENCODINGS);
				break;
			case UTF16LE:
				sequences = new HashSet<>(UTF16LE_ENCODINGS);
				break;
			default:
				sequences = new HashSet<>(ALL_ENCODINGS);
		}

		return sequences;
	}


	/**
	 * @return Returns an unmodifiable set of byte sequences that any style of line ending in any supported charset.
	 */
	private static Set<byte[]> createAllEncodings() {
		return unmodifiableSetOf(
				LF_ASCII, LF_UTF16BE, LF_UTF16LE,
				CR_ASCII, CR_UTF16BE, CR_UTF16LE,
				CRLF_ASCII, CRLF_UTF16BE, CRLF_UTF16LE
		);
	}

	/**
	 * @return Returns an unmodifiable set of byte sequences that may represent a LineFeed, CarriageReturn or
	 * CarriageReturn-LineFeed using the UTF-16BE charset.
	 */
	private static Set<byte[]> createUtf16leEncodings() {
		return unmodifiableSetOf(LF_UTF16LE, CR_UTF16LE, CRLF_UTF16LE);
	}

	/**
	 * @return Returns an unmodifiable set of byte sequences that may represent a LineFeed, CarriageReturn or
	 * CarriageReturn-LineFeed using the UTF-16BE charset.
	 */
	private static Set<byte[]> createUtf16beEncodings() {
		return unmodifiableSetOf(LF_UTF16BE, CR_UTF16BE, CRLF_UTF16BE);
	}


	/**
	 * @return Returns an unmodifiable set of byte sequences that may represent a LineFeed, CarriageReturn or
	 * CarriageReturn-LineFeed using the ASCII charset.
	 */
	private static Set<byte[]> createAsciiEncodings() {
		return unmodifiableSetOf(LF_ASCII, CR_ASCII, CRLF_ASCII);
	}

	/**
	 * @return Returns an unmodifiable set of byte sequences that may represent a LineFeed in one of (ASCII, UTF-16BE, UTF-16LE).
	 */
	private static Set<byte[]> createLfEncodings() {
		return unmodifiableSetOf(LF_ASCII, LF_UTF16BE, LF_UTF16LE);
	}

	/**
	 * @return Returns an unmodifiable set of byte sequences that may represent a CarriageReturn in one of (ASCII, UTF-16BE, UTF-16LE).
	 */
	private static Set<byte[]> createCrEncodings() {
		return unmodifiableSetOf(CR_ASCII, CR_UTF16BE, CR_UTF16LE);
	}

	/**
	 * @return Returns an unmodifiable set of byte sequences that may represent a CarriageReturn-LineFeed
	 * in one of (ASCII, UTF-16BE, UTF-16LE).
	 */
	private static Set<byte[]> createCrLfEncodings() {
		return unmodifiableSetOf(CRLF_ASCII, CRLF_UTF16BE, CRLF_UTF16LE);
	}

	private static Set<byte[]> unmodifiableSetOf(byte[]... sequences) {
		final Set<byte[]> setOfByteSequences = new HashSet<>(Arrays.asList(sequences));
		return Collections.unmodifiableSet(setOfByteSequences);
	}
}
