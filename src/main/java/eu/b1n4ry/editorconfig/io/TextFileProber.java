package eu.b1n4ry.editorconfig.io;

import eu.b1n4ry.editorconfig.style.CharsetStyle;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Checks if a given file is a text file.
 */
public class TextFileProber {

	private static final Logger LOG = LoggerFactory.getLogger(TextFileProber.class);
	private static final String MIMETYPE_TEXT_PREFIX = "text/";
	private static final Set<String> MIMETYPE_WHITELIST = new HashSet<>(
			Arrays.asList(
					"application/javascript",
					"application/json",
					"application/xml",
					"application/xhtml+xml",
					"application/x-csh",
					"application/x-latex",
					"application/x-sh",
					"application/x-tex"
			)
	);

	private static final CharsetStyle[] STYLES = {
			CharsetStyle.UTF8,
			CharsetStyle.UTF16LE,
			CharsetStyle.UTF16BE,
			CharsetStyle.LATIN1
	};

	/**
	 * Checks if a given file contains text only be decoding it with one of the Charsets supported.
	 *
	 * @param file The text file to check.
	 * @return True if it is a file.
	 */
	public static boolean isTextfile(final Path file) throws IOException {
		LOG.trace("Entering #isTextfile({}).", file);
		return hasTextLikeMimetype(file) && canBeDecoded(file);
	}

	private static boolean hasTextLikeMimetype(Path file) throws IOException {
		LOG.trace("Entering #hasTextLikeMimetype({}).", file);
		final Tika detector = new Tika();
		final String mimetype = detector.detect(file);

		return mimetype.startsWith(MIMETYPE_TEXT_PREFIX) || isWhitelistedMimetype(mimetype);
	}

	private static boolean isWhitelistedMimetype(String mimetype) {
		LOG.trace("Entering #isWhitelistedMimetype({}).", mimetype);
		return MIMETYPE_WHITELIST.contains(mimetype.toLowerCase());
	}

	private static boolean canBeDecoded(Path file) {
		LOG.trace("Entering #canBeDecoded({}).", file);
		return Arrays.stream(STYLES).anyMatch(style -> CharsetProber.canReadFile(file, style));
	}
}
