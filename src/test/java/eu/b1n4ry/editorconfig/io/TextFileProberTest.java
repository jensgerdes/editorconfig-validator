package eu.b1n4ry.editorconfig.io;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

class TextFileProberTest {

	private static final Path TEST_DIR = Paths.get("src/test/resources/charset");

	@Test
	void whenTextFilesGivenTheReturnTrue() {
		assertThat(TEST_DIR.resolve("ascii.example.txt"), is(textfile()));
		assertThat(TEST_DIR.resolve("latin1.example.txt"), is(textfile()));
		assertThat(TEST_DIR.resolve("latin1-germanUmlauts.example.txt"), is(textfile()));
		assertThat(TEST_DIR.resolve("utf8.example.txt"), is(textfile()));
		assertThat(TEST_DIR.resolve("utf8bom.example.txt"), is(textfile()));
		assertThat(TEST_DIR.resolve("utf16be.example.txt"), is(textfile()));
		assertThat(TEST_DIR.resolve("utf16le.example.txt"), is(textfile()));
	}

	@Test
	void whenJarGivenThenReturnFalse() {
		assertThat(Paths.get(".mvn/wrapper/maven-wrapper.jar"), is(not(textfile())));
	}

	private Matcher<Path> textfile() {
		return new TypeSafeDiagnosingMatcher<Path>() {
			@Override
			protected boolean matchesSafely(Path path, Description mismatchDescription) {
				final boolean isTextfile;
				try {
					isTextfile = TextFileProber.isTextfile(path);
				} catch (IOException e) {
					throw new UncheckedIOException(e);
				}
				mismatchDescription.appendText(" was ")
						.appendValue(isTextfile);

				return isTextfile;
			}

			@Override
			public void describeTo(Description description) {
				description.appendText("should be true");
			}
		};
	}
}
