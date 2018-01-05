package eu.b1n4ry.editorconfig.check;

import eu.b1n4ry.editorconfig.CodeStyle;
import eu.b1n4ry.editorconfig.TestCodeStyle;
import eu.b1n4ry.editorconfig.TestValidator;
import eu.b1n4ry.editorconfig.style.LineEndingStyle;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static eu.b1n4ry.editorconfig.CheckResultMatchers.hasViolations;
import static eu.b1n4ry.editorconfig.CheckResultMatchers.successful;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;


class LineEndingCheckTest {

	private static final Path TEST_DIR = Paths.get("src/test/resources/lineEndings");

	/*
	 * SUCCESS CASES
	 */

	@Test
	void whenLineEndingStyleUndefinedThenAlwaysSucceed() throws IOException {
		validateAndExpectSuccess("asciiCRLFs", LineEndingStyle.UNDEFINED);
		validateAndExpectSuccess("asciiCRs", LineEndingStyle.UNDEFINED);
		validateAndExpectSuccess("asciiLFs", LineEndingStyle.UNDEFINED);
	}

	@Test
	void whenLineEndingStyleLfAndFileWithLfGivenThenSucceed() throws IOException {
		validateAndExpectSuccess("asciiLFs", LineEndingStyle.LF);
	}

	@Test
	void whenLineEndingStyleCrLfAndFileWithCrLfGivenThenSucceed() throws IOException {
		validateAndExpectSuccess("asciiCRLFs", LineEndingStyle.CRLF);
	}

	@Test
	void whenLineEndingStyleCrAndFileWithCrGivenThenSucceed() throws IOException {
		validateAndExpectSuccess("asciiCRs", LineEndingStyle.CR);
	}

	/*
	 * FAILING CASES
	 */

	@Test
	void whenLineEndingStyleCrAndFilesWithOtherLineEndingsGivenThenFail() throws IOException {
		validateAndExpectFailure("asciiLFs", LineEndingStyle.CR);
		validateAndExpectFailure("asciiCRLFs", LineEndingStyle.CR);
	}

	@Test
	void whenLineEndingStyleLfAndFilesWithOtherLineEndingsGivenThenFail() throws IOException {
		validateAndExpectFailure("asciiCRs", LineEndingStyle.LF);
		validateAndExpectFailure("asciiCRLFs", LineEndingStyle.LF);
	}

	@Test
	void whenLineEndingStyleCrLfAndFilesWithOtherLineEndingsGivenThenFail() throws IOException {
		validateAndExpectFailure("asciiCRs", LineEndingStyle.CRLF);
		validateAndExpectFailure("asciiLFs", LineEndingStyle.CRLF);
	}

	private void validateAndExpectFailure(String fileName, LineEndingStyle style) throws IOException {
		final CheckResult result = validate(fileName, style);
		assertThat(result, hasViolations(1));
		assertThat(result, is(not(successful())));
	}

	private void validateAndExpectSuccess(String fileName, LineEndingStyle style) throws IOException {
		final CheckResult result = validate(fileName, style);
		assertThat(result, is(successful()));
	}

	private CheckResult validate(String fileName, LineEndingStyle style) throws IOException {
		final Path path = TEST_DIR.resolve(String.format("%s.txt", fileName));
		final CodeStyle codeStyle = new TestCodeStyle.Builder()
				.withLineEndingStyle(style)
				.build();

		return TestValidator.apply(new LineEndingCheck(), codeStyle, path);
	}
}
