package io.b1n4ry.editorconfig.check.indentation;

import java.nio.file.Path;
import java.nio.file.Paths;

import io.b1n4ry.editorconfig.check.CheckResult;
import io.b1n4ry.editorconfig.CheckResultMatcher;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

import static io.b1n4ry.editorconfig.CheckResultMatcher.hasViolations;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests the {@link TabCheck}.
 */
class TabCheckTest {

	private static final Path FAKE_PATH = Paths.get("FAKE_FILE");

	@Test
	void whenUsingTabAsIndentationThenReturnSuccess() {
		MatcherAssert.assertThat(feedWithCharsAndValidate(""), CoreMatchers.is(CheckResultMatcher.successful()));
		MatcherAssert.assertThat(feedWithCharsAndValidate("\r\n\tHello\r\n\t\tThis is an indented-file.\r\n"), CoreMatchers.is(CheckResultMatcher.successful()));
		MatcherAssert.assertThat(feedWithCharsAndValidate("\t"), CoreMatchers.is(CheckResultMatcher.successful()));
		MatcherAssert.assertThat(feedWithCharsAndValidate("\r"), CoreMatchers.is(CheckResultMatcher.successful()));
		MatcherAssert.assertThat(feedWithCharsAndValidate("\n"), CoreMatchers.is(CheckResultMatcher.successful()));
		MatcherAssert.assertThat(feedWithCharsAndValidate("Some text"), CoreMatchers.is(CheckResultMatcher.successful()));
		MatcherAssert.assertThat(feedWithCharsAndValidate("public void main() {\n\tprint 'hello';\n}\n"), CoreMatchers.is(CheckResultMatcher.successful()));
		MatcherAssert.assertThat(feedWithCharsAndValidate("Some text\nOther\t \tText"), CoreMatchers.is(CheckResultMatcher.successful()));
	}

	@Test
	void whenHasPrecedingSpaceThenReportViolation() {
		MatcherAssert.assertThat(feedWithCharsAndValidate(" public void main() {}"), CheckResultMatcher.hasViolations());
		MatcherAssert.assertThat(feedWithCharsAndValidate("Text\r\t \tOther text\t\n"), CheckResultMatcher.hasViolations());
	}

	private CheckResult feedWithCharsAndValidate(CharSequence characters) {
		final TabCheck subject = new TabCheck();

		for (int i = 0; i < characters.length(); i++) {
			subject.readCharacter(characters.charAt(i));
		}

		return subject.validate(FAKE_PATH);
	}
}
