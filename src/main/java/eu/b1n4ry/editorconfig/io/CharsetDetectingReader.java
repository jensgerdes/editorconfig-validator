package eu.b1n4ry.editorconfig.io;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import eu.b1n4ry.editorconfig.exception.NoSuitableCharsetException;
import eu.b1n4ry.editorconfig.style.CharsetStyle;
import org.mozilla.universalchardet.UniversalDetector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * "Reader" (not in the way of {@link Reader}) that auto-detects the required charset
 * and allows reading a file character by character.
 */
public class CharsetDetectingReader implements AutoCloseable {

	private static final Logger LOG = LoggerFactory.getLogger(CharsetDetectingReader.class);

	private final Reader delegate;
	private int nextChar;

	public CharsetDetectingReader(Path file, CharsetStyle preferredStyle) throws IOException {
		LOG.trace("Initialized CharsetDetectingReader({}, {})", file, preferredStyle);

		final Charset charset = determineCharset(file, preferredStyle);
		delegate = Files.newBufferedReader(file, charset);
		nextChar = delegate.read();
	}

	/**
	 * Determines the required {@link Charset} while preferring the recommended one if applicable.
	 *
	 * @param file           The file which shall be read.
	 * @param preferredStyle The preferred CharsetStyle.
	 * @return An applicable charset. If no charset could be found, a {@link NoSuitableCharsetException} is thrown.
	 */
	private Charset determineCharset(Path file, CharsetStyle preferredStyle) throws IOException {
		LOG.trace("Entering #determineCharset({}).", preferredStyle);
		final Collection<CharsetStyle> availableStyles = findPossibleCharsetStyles();

		if (preferredStyle != CharsetStyle.UNDEFINED) {
			if (CharsetProber.canReadFile(file, preferredStyle)) {
				LOG.debug("Using preferred Charset({}).", preferredStyle.getCharset());
				return preferredStyle.getCharset();
			} else {
				availableStyles.remove(preferredStyle);
			}
		}

		return detectCharset(file).orElseGet(() -> findApplicableCharset(file, availableStyles));

	}

	/**
	 * Checks all possible styles for a Charset with which the file can be read and returns that one.
	 *
	 * @param file           The file which shall be read.
	 * @param possibleStyles A collection of styles.
	 * @return An applicable Charset. If no matching charset could be found, a {@link NoSuitableCharsetException} is thrown.
	 */
	private static Charset findApplicableCharset(Path file, Collection<CharsetStyle> possibleStyles) {
		return possibleStyles.stream()
				.filter(style -> CharsetProber.canReadFile(file, style))
				.findFirst()
				.map(CharsetStyle::getCharset)
				.orElseThrow(() -> new NoSuitableCharsetException(file));
	}

	/**
	 * Detects the charset for a given file.
	 *
	 * @param file The file for which the charset shall be detected.
	 * @return A charset or null if none could be detected.
	 * @throws IOException if an I/O error occurs.
	 */
	private Optional<Charset> detectCharset(Path file) throws IOException {
		LOG.trace("Entering #detectCharset({}).", file);
		final String charsetName = UniversalDetector.detectCharset(file.toFile());

		if (charsetName == null) {
			LOG.debug("Failed to detect Charset for File({}).", file);
			return Optional.empty();
		}

		try {
			return Optional.of(Charset.forName(charsetName));
		} catch (IllegalCharsetNameException e) {
			LOG.debug(String.format("The Charset(%s) does not exist.", charsetName), e);
		} catch (UnsupportedCharsetException e) {
			LOG.warn("The Charset({}) is not supported by this JVM!", charsetName);
		}

		return Optional.empty();
	}

	/**
	 * Returns a Set of officially supported charsets.
	 *
	 * @return A set of charsets supported by editorconfig.
	 */
	private static Collection<CharsetStyle> findPossibleCharsetStyles() {
		LOG.trace("Entering #findPossibleCharsetStyles().");
		final Set<CharsetStyle> availableStyles = new HashSet<>(Arrays.asList(CharsetStyle.values()));
		availableStyles.remove(CharsetStyle.UNDEFINED);
		availableStyles.remove(CharsetStyle.UTF8BOM);
		return availableStyles;
	}

	@Override
	public void close() throws Exception {
		LOG.trace("Entering #close().");
		if (delegate != null) {
			delegate.close();
		}
	}

	/**
	 * Reads a {@link Character} from the file.
	 *
	 * @return A character.
	 * @throws IOException if an I/O error occurs.
	 */
	public char read() throws IOException {
		final char result = (char) nextChar;
		nextChar = delegate.read();

		return result;
	}

	/**
	 * Checks if there is more content to read.
	 *
	 * @return True if there is another character, false otherwise.
	 */
	public boolean hasNext() {
		return nextChar != -1;
	}
}
