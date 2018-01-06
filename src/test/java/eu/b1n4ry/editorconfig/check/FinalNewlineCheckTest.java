package eu.b1n4ry.editorconfig.check;

import eu.b1n4ry.editorconfig.CodeStyle;
import eu.b1n4ry.editorconfig.TestCodeStyle;
import eu.b1n4ry.editorconfig.TestValidator;
import eu.b1n4ry.editorconfig.style.InsertFinalNewlineStyle;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static eu.b1n4ry.editorconfig.CheckResultMatchers.hasViolations;
import static eu.b1n4ry.editorconfig.CheckResultMatchers.successful;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

class FinalNewlineCheckTest {

	private static final Path TEST_DIR = Paths.get("src/test/resources/lineEndings");

	/*
	 * SUCCESS CASES
	 */
	@Test
	void whenFinalNewlineGivenThenReturnSuccess() throws IOException {
		validateAndExpectSuccess(InsertFinalNewlineStyle.TRUE, "asciiCRLFs");
		validateAndExpectSuccess(InsertFinalNewlineStyle.TRUE, "asciiCRs");
		validateAndExpectSuccess(InsertFinalNewlineStyle.TRUE, "asciiLFs");
	}

	@Test
	void whenNothingExpectedAlwaysReturnSuccess() throws IOException {
		validateAndExpectSuccess(InsertFinalNewlineStyle.UNDEFINED, "asciiCRLFs");
		validateAndExpectSuccess(InsertFinalNewlineStyle.UNDEFINED, "asciiCRs");
		validateAndExpectSuccess(InsertFinalNewlineStyle.UNDEFINED, "asciiLFs");
		validateAndExpectSuccess(InsertFinalNewlineStyle.UNDEFINED, "asciiCRLFsNoFinalNewline");
		validateAndExpectSuccess(InsertFinalNewlineStyle.UNDEFINED, "asciiCRsNoFinalNewline");
		validateAndExpectSuccess(InsertFinalNewlineStyle.UNDEFINED, "asciiLFsNoFinalNewline");

		validateAndExpectSuccess(InsertFinalNewlineStyle.FALSE, "asciiCRLFs");
		validateAndExpectSuccess(InsertFinalNewlineStyle.FALSE, "asciiCRs");
		validateAndExpectSuccess(InsertFinalNewlineStyle.FALSE, "asciiLFs");
		validateAndExpectSuccess(InsertFinalNewlineStyle.FALSE, "asciiCRLFsNoFinalNewline");
		validateAndExpectSuccess(InsertFinalNewlineStyle.FALSE, "asciiCRsNoFinalNewline");
		validateAndExpectSuccess(InsertFinalNewlineStyle.FALSE, "asciiLFsNoFinalNewline");
	}

	/*
	 * FAILING CASES
	 */

	@Test
	void whenNewlineExpectedButNotPresentThenFail() throws IOException {
		validateAndExpectFailure("asciiCRLFsNoFinalNewline", InsertFinalNewlineStyle.TRUE);
		validateAndExpectFailure("asciiCRsNoFinalNewline", InsertFinalNewlineStyle.TRUE);
		validateAndExpectFailure("asciiLFsNoFinalNewline", InsertFinalNewlineStyle.TRUE);
	}

	private void validateAndExpectFailure(String fileName, InsertFinalNewlineStyle style) throws IOException {
		final CheckResult result = validate(style, fileName);
		assertThat(result, hasViolations(1));
		assertThat(result, is(not(successful())));
	}

	private void validateAndExpectSuccess(
			InsertFinalNewlineStyle insertFinalNewlineStyle,
			String identifier
	) throws IOException {
		final CheckResult result = validate(insertFinalNewlineStyle, identifier);
		assertThat(result, is(successful()));
	}

	private CheckResult validate(
			InsertFinalNewlineStyle insertFinalNewlineStyle,
			String identifier
	) throws IOException {
		final Path file = TEST_DIR.resolve(String.format("%s.txt", identifier));
		final FinalNewLineCheck check = new FinalNewLineCheck();
		final CodeStyle style = new TestCodeStyle.Builder()
				.withInsertFinalNewlineStyle(insertFinalNewlineStyle)
				.build();

		return TestValidator.apply(check, style, file);
	}
}
