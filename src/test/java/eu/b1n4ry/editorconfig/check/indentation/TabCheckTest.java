package eu.b1n4ry.editorconfig.check.indentation;

import java.nio.file.Path;
import java.nio.file.Paths;

import eu.b1n4ry.editorconfig.check.CheckResult;
import org.junit.jupiter.api.Test;

import static eu.b1n4ry.editorconfig.CheckResultMatcher.hasViolations;
import static eu.b1n4ry.editorconfig.CheckResultMatcher.successful;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests the {@link TabCheck}.
 */
class TabCheckTest {

	private static final Path FAKE_PATH = Paths.get("FAKE_FILE");

	@Test
	void whenUsingTabAsIndentationThenReturnSuccess() {
		assertThat(feedWithCharsAndValidate(""), is(successful()));
		assertThat(feedWithCharsAndValidate("\r\n\tHello\r\n\t\tThis is an indented-file.\r\n"), is(successful()));
		assertThat(feedWithCharsAndValidate("\t"), is(successful()));
		assertThat(feedWithCharsAndValidate("\r"), is(successful()));
		assertThat(feedWithCharsAndValidate("\n"), is(successful()));
		assertThat(feedWithCharsAndValidate("Some text"), is(successful()));
		assertThat(feedWithCharsAndValidate("public void main() {\n\tprint 'hello';\n}\n"), is(successful()));
		assertThat(feedWithCharsAndValidate("Some text\nOther\t \tText"), is(successful()));
	}

	@Test
	void whenHasPrecedingSpaceThenReportViolation() {
		assertThat(feedWithCharsAndValidate(" public void main() {}"), hasViolations());
		assertThat(feedWithCharsAndValidate("Text\r\t \tOther text\t\n"), hasViolations());
	}

	private CheckResult feedWithCharsAndValidate(CharSequence characters) {
		final TabCheck subject = new TabCheck();

		for (int i = 0; i < characters.length(); i++) {
			subject.readCharacter(characters.charAt(i));
		}

		return subject.validate(FAKE_PATH);
	}
}
