package eu.b1n4ry.editorconfig.check;

import java.nio.file.Path;
import java.nio.file.Paths;

import eu.b1n4ry.editorconfig.CodeStyle;
import eu.b1n4ry.editorconfig.TestCodeStyle;
import eu.b1n4ry.editorconfig.style.TrimTrailingWhiteSpaceStyle;
import org.junit.jupiter.api.Test;

import static eu.b1n4ry.editorconfig.CheckResultMatcher.hasViolations;
import static eu.b1n4ry.editorconfig.CheckResultMatcher.successful;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;

/**
 * Tests the {@link TrailingWhitespaceCheck} class.
 */
class TrailingWhitespaceCheckTest {

	private static final Path FAKE_PATH = Paths.get("FAKE_FILE");
	private static final String TRAILING_WHITESPACE = "This is a string with \ntrailing \r\n whitespace.";
	private static final String NO_TRAILING_WHITESPACE = "  This file does\r not  contain trailing\nwhitespace.\n";

	@Test
	void whenStyleUndefinedAndTrailingWhitespaceGivenThenReturnSuccess() {
		final TrailingWhitespaceCheck check = prepareWithStyle(TrimTrailingWhiteSpaceStyle.UNDEFINED);
		final CheckResult result = feedWithCharsAndValidate(check, TRAILING_WHITESPACE);
		assertThat(result, is(successful()));
	}

	@Test
	void whenTrimmingDisabledAndTrailingWhitespaceGivenThenReturnSuccess() {
		final TrailingWhitespaceCheck check = prepareWithStyle(TrimTrailingWhiteSpaceStyle.FALSE);
		final CheckResult result = feedWithCharsAndValidate(check, TRAILING_WHITESPACE);
		assertThat(result, is(successful()));
	}

	@Test
	void whenTrimmingIsExpectedAndTrailingWhitespaceGivenThenReturnViolation() {
		final TrailingWhitespaceCheck check = prepareWithStyle(TrimTrailingWhiteSpaceStyle.TRUE);
		final CheckResult result = feedWithCharsAndValidate(check, TRAILING_WHITESPACE);
		assertThat(result, is(not(successful())));
		assertThat(result, hasViolations());
	}

	@Test
	void whenNoTrailingWhiteSpaceGivenThenReturnSuccess() {
		final TrailingWhitespaceCheck check = prepareWithStyle(TrimTrailingWhiteSpaceStyle.TRUE);
		final CheckResult result = feedWithCharsAndValidate(check, NO_TRAILING_WHITESPACE);
		assertThat(result, is(successful()));
	}

	private TrailingWhitespaceCheck prepareWithStyle(TrimTrailingWhiteSpaceStyle style) {
		final TrailingWhitespaceCheck check = new TrailingWhitespaceCheck();
		final CodeStyle codeStyle = new TestCodeStyle.Builder()
				.withTrimTrailingWhiteSpace(style)
				.build();

		check.setCodeStyle(codeStyle);

		return check;
	}

	private CheckResult feedWithCharsAndValidate(TrailingWhitespaceCheck check, CharSequence characters) {

		for (int i = 0; i < characters.length(); i++) {
			check.readCharacter(characters.charAt(i));
		}

		return check.validate(FAKE_PATH);
	}
}
