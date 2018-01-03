package eu.b1n4ry.editorconfig.io;

import eu.b1n4ry.editorconfig.style.CharsetStyle;
import org.apache.tika.Tika;
import org.apache.tika.detect.AutoDetectReader;
import org.apache.tika.exception.TikaException;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.mime.MimeTypes;
import org.junit.jupiter.api.Test;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CharsetProberTest {

	private static final Path TEST_DIR = Paths.get("src/test/resources/charset");

	public static void main(String... args) throws IOException, TikaException {
		final Tika tika = new Tika();

		/*System.out.println(tika.detect(Paths.get("/Users/jgerdes/Projects/editorconfig/editorconfig-validator/src/test/java/eu/b1n4ry/editorconfig/TestCodeStyle.java")));
		System.out.println(tika.detect(Paths.get("/Users/jgerdes/Projects/editorconfig/editorconfig-validator/src/test/resources/charset/onlyTabsAndSpaces.txt")));
		System.out.println(tika.detect(Paths.get("/Users/jgerdes/Projects/editorconfig/editorconfig-validator/src/test/resources/charset/utf8bom.example.txt")));
		System.out.println(tika.detect(Paths.get("/Users/jgerdes/Projects/editorconfig/editorconfig-validator/src/test/resources/charset/utf16le.example.txt")));
		System.out.println(tika.detect(Paths.get("/Users/jgerdes/Projects/editorconfig/editorconfig-validator/pom.xml")));
		System.out.println(tika.detect(Paths.get("/Users/jgerdes/Projects/editorconfig/editorconfig-validator/mvnw")));
		System.out.println(tika.detect(Paths.get("/Users/jgerdes/Projects/editorconfig/editorconfig-validator/.travis.yml")));
		System.out.println(tika.detect(Paths.get("/Users/jgerdes/Projects/editorconfig/editorconfig-validator/target/editorconfig-validator-1.0-SNAPSHOT.jar")));
		System.out.println(tika.detect(Paths.get("/usr/bin/less")));*/
		System.out.println(tika.detect(Paths.get("/immonet_git/frontend-resources/src/lib/fotorama/fotorama.css")));


	}

	@Test
	void whenJarAndAnyCharsetStyleGivenThenReturnError() {
		assertThat(CharsetProber.canReadFile(Paths.get(".mvn/wrapper/maven-wrapper.jar"), CharsetStyle.UTF16BE), is(false));
	}
}
