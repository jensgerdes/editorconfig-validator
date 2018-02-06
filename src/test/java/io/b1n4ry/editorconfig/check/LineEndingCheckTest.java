package io.b1n4ry.editorconfig.check;

import io.b1n4ry.editorconfig.CodeStyle;
import io.b1n4ry.editorconfig.TestCodeStyle;
import io.b1n4ry.editorconfig.TestValidator;
import io.b1n4ry.editorconfig.style.LineEndingStyle;
import io.b1n4ry.editorconfig.CheckResultMatcher;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static io.b1n4ry.editorconfig.CheckResultMatcher.hasViolations;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests for the {@link LineEndingCheck}.
 */
class LineEndingCheckTest {

	private static final Path TEST_DIR = Paths.get("src/test/resources/lineEndings");
	private static final String ASCII_CRLFS = "asciiCRLFs";
	private static final String ASCII_CRS = "asciiCRs";
	private static final String ASCII_LFS = "asciiLFs";

	/*
	 * SUCCESS CASES
	 */

	@Test
	void whenLineEndingStyleUndefinedThenAlwaysSucceed() throws IOException {
		validateAndExpectSuccess(ASCII_CRLFS, LineEndingStyle.UNDEFINED);
		validateAndExpectSuccess(ASCII_CRS, LineEndingStyle.UNDEFINED);
		validateAndExpectSuccess(ASCII_LFS, LineEndingStyle.UNDEFINED);
	}

	@Test
	void whenLineEndingStyleLfAndFileWithLfGivenThenSucceed() throws IOException {
		validateAndExpectSuccess(ASCII_LFS, LineEndingStyle.LF);
	}

	@Test
	void whenLineEndingStyleCrLfAndFileWithCrLfGivenThenSucceed() throws IOException {
		validateAndExpectSuccess(ASCII_CRLFS, LineEndingStyle.CRLF);
	}

	@Test
	void whenLineEndingStyleCrAndFileWithCrGivenThenSucceed() throws IOException {
		validateAndExpectSuccess(ASCII_CRS, LineEndingStyle.CR);
	}

	/*
	 * FAILING CASES
	 */

	@Test
	void whenLineEndingStyleCrAndFilesWithOtherLineEndingsGivenThenFail() throws IOException {
		validateAndExpectFailure(ASCII_LFS, LineEndingStyle.CR);
		validateAndExpectFailure(ASCII_CRLFS, LineEndingStyle.CR);
	}

	@Test
	void whenLineEndingStyleLfAndFilesWithOtherLineEndingsGivenThenFail() throws IOException {
		validateAndExpectFailure(ASCII_CRS, LineEndingStyle.LF);
		validateAndExpectFailure(ASCII_CRLFS, LineEndingStyle.LF);
	}

	@Test
	void whenLineEndingStyleCrLfAndFilesWithOtherLineEndingsGivenThenFail() throws IOException {
		validateAndExpectFailure(ASCII_CRS, LineEndingStyle.CRLF);
		validateAndExpectFailure(ASCII_LFS, LineEndingStyle.CRLF);
	}

	private void validateAndExpectFailure(String fileName, LineEndingStyle style) throws IOException {
		final CheckResult result = validate(fileName, style);
		MatcherAssert.assertThat(result, CheckResultMatcher.hasViolations(1));
		MatcherAssert.assertThat(result, CoreMatchers.is(CoreMatchers.not(CheckResultMatcher.successful())));
	}

	private void validateAndExpectSuccess(String fileName, LineEndingStyle style) throws IOException {
		final CheckResult result = validate(fileName, style);
		MatcherAssert.assertThat(result, CoreMatchers.is(CheckResultMatcher.successful()));
	}

	private CheckResult validate(String fileName, LineEndingStyle style) throws IOException {
		final Path path = TEST_DIR.resolve(String.format("%s.txt", fileName));
		final CodeStyle codeStyle = new TestCodeStyle.Builder()
				.withLineEndingStyle(style)
				.build();

		return TestValidator.apply(new LineEndingCheck(), codeStyle, path);
	}
}
