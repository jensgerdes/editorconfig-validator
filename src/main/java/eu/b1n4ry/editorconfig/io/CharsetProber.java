package eu.b1n4ry.editorconfig.io;

import eu.b1n4ry.editorconfig.style.CharsetStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * Utility to check if a file can be read with a given {@link CharsetStyle}.
 */
public class CharsetProber {

	private static final Logger LOG = LoggerFactory.getLogger(CharsetProber.class);
	private static final int BUFFER_SIZE = 4096;
	private static final double THRESHOLD_IGNORABLE_CHARACTERS = 0.01;

	/**
	 * Checks if a file can be read with a given CharsetStyle.
	 *
	 * @param file The file to check.
	 * @param charsetStyle The charset style to use.
	 * @return True if the file can be read with this Charset, false otherwise.
	 */
	public static boolean canReadFile(Path file, CharsetStyle charsetStyle) {
		LOG.trace("Entering #canReadFile({}, {}).", file, charsetStyle);

		final Charset charset = charsetStyle.getCharset();
		final CharsetDecoder decoder = charset.newDecoder();
		final CharsetEncoder encoder = charset.newEncoder();
		boolean result = false;

		try (SeekableByteChannel channel = Files.newByteChannel(file,StandardOpenOption.READ)) {
			final ByteBuffer buffer = ByteBuffer.allocateDirect(BUFFER_SIZE);

			int totalCharacters = 0;
			int ignorableCharacters = 0;

			while (channel.read(buffer) > 0) {
				buffer.rewind();
				final CharBuffer decode = decoder.decode(buffer);

				char c;
				while (decode.hasRemaining() && (c = decode.get()) > 0) {
					totalCharacters++;

					if (Character.isIdentifierIgnorable(c)) {
						ignorableCharacters++;
					}
				}

				buffer.flip();
			}

			Files.lines(file, charsetStyle.getCharset())
					.forEach(encoder::canEncode);

			result = performPlausibilityTest(totalCharacters, ignorableCharacters);
		} catch (CharacterCodingException | UncheckedIOException e) {
			LOG.debug("File({}) can not be read with Charset({})", file, charsetStyle.getCharset());
		}
		catch (IOException e) {
			LOG.error("An IOException occured during Charset probing:", e);
		}

		return result;
	}

	/**
	 * Checks that the number of ignorable characters is below 1%.
	 *
	 * @param totalCharacters Total number of characters read.
	 * @param ignorableCharacters Number of ignorable characters. See: {@link Character#isIdentifierIgnorable(char)}.
	 * @return False if the number of totalCharacters is 0 or the ratio is above 1%, true otherwise.
	 */
	private static boolean performPlausibilityTest(int totalCharacters, int ignorableCharacters) {
		LOG.trace("Entering #performPlausibilityTest({}, {}).", totalCharacters, ignorableCharacters);

		if (totalCharacters == 0) {
			LOG.debug("Total Characters read are 0. Plausibility check failed.");
			return false;
		}

		double ratio = ignorableCharacters / ((double) totalCharacters);

		if (LOG.isDebugEnabled()) {
			final int percent = (int) Math.ceil(ratio * 100);
			LOG.debug("Ratio of Ignorable characters to total is: {}%.", percent);
		}

		return ratio <= THRESHOLD_IGNORABLE_CHARACTERS;
	}
}
