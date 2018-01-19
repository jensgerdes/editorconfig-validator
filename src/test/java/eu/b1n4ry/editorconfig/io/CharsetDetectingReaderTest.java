package eu.b1n4ry.editorconfig.io;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import eu.b1n4ry.editorconfig.exception.NoSuitableCharsetException;
import eu.b1n4ry.editorconfig.style.CharsetStyle;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CharsetDetectingReaderTest {

	private static final Path TEST_DIR = Paths.get("src/test/resources/charset");
	private static final Path UTF8_FILE = TEST_DIR.resolve("utf8.example.txt");
	private static final Path LATIN1_FILE = TEST_DIR.resolve("latin1-germanUmlauts.example.txt");
	private static final Path PNG_FILE = TEST_DIR.getParent().resolve("example.png");

	@Test
	void whenPreferredMatchingStyleGivenThenFileCanBeRead() throws Exception {

		final String expectedContent = new String(Files.readAllBytes(UTF8_FILE), StandardCharsets.UTF_8);

		try (CharsetDetectingReader reader = new CharsetDetectingReader(UTF8_FILE, CharsetStyle.UTF8)) {
			assertReaderProvidesSameContent(reader, expectedContent);
		}
	}

	@Test
	void whenNonMatchingPreferredStyleGivenThenAutoDetectAndFileCanBeRead() throws Exception {
		final String expectedContent = new String(Files.readAllBytes(UTF8_FILE), StandardCharsets.UTF_8);

		try (CharsetDetectingReader reader = new CharsetDetectingReader(UTF8_FILE, CharsetStyle.UTF16LE)) {
			assertReaderProvidesSameContent(reader, expectedContent);
		}
	}

	@Test
	void whenNoPreferredStyleGivenAndNothingDetectedThenTryToGuessAndReadFile() throws Exception {
		final String expectedContent = new String(Files.readAllBytes(LATIN1_FILE), StandardCharsets.ISO_8859_1);

		try (CharsetDetectingReader reader = new CharsetDetectingReader(LATIN1_FILE, CharsetStyle.UNDEFINED)) {
			assertReaderProvidesSameContent(reader, expectedContent);
		}
	}

	@Test
	void whenImageGivenThenNoSuitableCharsetExceptionThrown() {
		final NoSuitableCharsetException exception = assertThrows(
				NoSuitableCharsetException.class,
				() -> new CharsetDetectingReader(PNG_FILE, CharsetStyle.UNDEFINED)
		);

		assertThat(exception.getMessage(), containsString("uses an unsupported charset."));
	}

	private void assertReaderProvidesSameContent(CharsetDetectingReader reader, String expectedContent) throws IOException {
		final StringBuilder builder = new StringBuilder();

		while (reader.hasNext()) {
			builder.append(reader.read());
		}

		assertThat(expectedContent, is(equalTo(builder.toString())));
	}
}
