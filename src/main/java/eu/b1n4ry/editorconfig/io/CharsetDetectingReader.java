package eu.b1n4ry.editorconfig.io;

import eu.b1n4ry.editorconfig.exception.NoSuitableCharsetException;
import eu.b1n4ry.editorconfig.style.CharsetStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.CharBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Implementation of the {@link Readable} interface that tries to auto-detect the required charset.
 */
public class CharsetDetectingReader implements Readable, AutoCloseable {

	private static final Logger LOG = LoggerFactory.getLogger(CharsetDetectingReader.class);

	private final Path file;
	//private final CharsetStyle charset;

	public CharsetDetectingReader(Path file) {
		this(file, CharsetStyle.UNDEFINED);
	}

	public CharsetDetectingReader(Path file, CharsetStyle preferredStyle) {
		LOG.trace("Initialized CharsetDetectingReader({}, {})", file, preferredStyle);
		this.file = file;

		final Charset charset = determineCharset(preferredStyle);

	}

	private Charset determineCharset(CharsetStyle preferredStyle) {
		LOG.trace("Entering #determineCharset({}).", preferredStyle);
		final Set<CharsetStyle> availableStyles = findPossibleCharsetStyles();

		if (preferredStyle != CharsetStyle.UNDEFINED) {
			if (CharsetProber.canReadFile(file, preferredStyle)) {
				LOG.debug("Using preferred Charset({}).", preferredStyle.getCharset());
				return preferredStyle.getCharset();
			} else {
				availableStyles.remove(preferredStyle);
			}
		}

		return availableStyles.stream()
				.filter(style -> CharsetProber.canReadFile(file, style))
				.findFirst()
				.map(CharsetStyle::getCharset)
				.orElseThrow(() -> new NoSuitableCharsetException(file));
	}

	private Set<CharsetStyle> findPossibleCharsetStyles() {
		final Set<CharsetStyle> availableStyles = new HashSet<>(Arrays.asList(CharsetStyle.values()));
		availableStyles.remove(CharsetStyle.UNDEFINED);
		availableStyles.remove(CharsetStyle.UTF8BOM);
		return availableStyles;
	}

	@Override
	public void close() throws Exception {

	}

	@Override
	public int read(CharBuffer cb) throws IOException {
		return 0;
	}
}
