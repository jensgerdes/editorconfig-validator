package eu.b1n4ry.editorconfig.io;

import eu.b1n4ry.editorconfig.style.CharsetStyle;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class CharsetProberTest {

	@Test
	void whenJarAndAnyCharsetStyleGivenThenReturnError() {
		assertThat(CharsetProber.canReadFile(Paths.get(".mvn/wrapper/maven-wrapper.jar"), CharsetStyle.LATIN1), is(false));
		assertThat(CharsetProber.canReadFile(Paths.get(".mvn/wrapper/maven-wrapper.jar"), CharsetStyle.UTF8), is(false));
		assertThat(CharsetProber.canReadFile(Paths.get(".mvn/wrapper/maven-wrapper.jar"), CharsetStyle.UTF16BE), is(false));
		assertThat(CharsetProber.canReadFile(Paths.get(".mvn/wrapper/maven-wrapper.jar"), CharsetStyle.UTF16LE), is(false));
	}

	/**
	 * If the file is effectively empty, we can not determine the charset.
	 */
	@Test
	void whenEmptyFileGivenThenReturnError() {
		Arrays.stream(CharsetStyle.values())
				.forEach(style -> assertThat(
                        CharsetProber.canReadFile(Paths.get("src/test/resources/charset/empty.example.txt"), style),
                        is(false)
                ));
	}
}
