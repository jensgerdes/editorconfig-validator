package io.b1n4ry.editorconfig.check;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import io.b1n4ry.editorconfig.CodeStyle;
import io.b1n4ry.editorconfig.TestCodeStyle;
import io.b1n4ry.editorconfig.TestValidator;
import io.b1n4ry.editorconfig.style.InsertFinalNewlineStyle;
import io.b1n4ry.editorconfig.CheckResultMatcher;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

import static io.b1n4ry.editorconfig.CheckResultMatcher.hasViolations;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

class FinalNewlineCheckTest {

	private static final Path TEST_DIR = Paths.get("src/test/resources/lineEndings");
	private static final String ASCII_CRS = "asciiCRs";
	private static final String ASCII_CRLFS = "asciiCRLFs";
	private static final String ASCII_LFS = "asciiLFs";
	private static final String ASCII_CRLFS_NO_FINAL_NEWLINE = "asciiCRLFsNoFinalNewline";
	private static final String ASCII_CRS_NO_FINAL_NEWLINE = "asciiCRsNoFinalNewline";
	private static final String ASCII_LFS_NO_FINAL_NEWLINE = "asciiLFsNoFinalNewline";

	/*
	 * SUCCESS CASES
	 */
	@Test
	void whenFinalNewlineGivenThenReturnSuccess() throws IOException {
		validateAndExpectSuccess(InsertFinalNewlineStyle.TRUE, ASCII_CRLFS);
		validateAndExpectSuccess(InsertFinalNewlineStyle.TRUE, ASCII_CRS);
		validateAndExpectSuccess(InsertFinalNewlineStyle.TRUE, ASCII_LFS);
	}

	@Test
	void whenNothingExpectedAlwaysReturnSuccess() throws IOException {
		validateAndExpectSuccess(InsertFinalNewlineStyle.UNDEFINED, ASCII_CRLFS);
		validateAndExpectSuccess(InsertFinalNewlineStyle.UNDEFINED, ASCII_CRS);
		validateAndExpectSuccess(InsertFinalNewlineStyle.UNDEFINED, ASCII_LFS);
		validateAndExpectSuccess(InsertFinalNewlineStyle.UNDEFINED, ASCII_CRLFS_NO_FINAL_NEWLINE);
		validateAndExpectSuccess(InsertFinalNewlineStyle.UNDEFINED, ASCII_CRS_NO_FINAL_NEWLINE);
		validateAndExpectSuccess(InsertFinalNewlineStyle.UNDEFINED, ASCII_LFS_NO_FINAL_NEWLINE);

		validateAndExpectSuccess(InsertFinalNewlineStyle.FALSE, ASCII_CRLFS);
		validateAndExpectSuccess(InsertFinalNewlineStyle.FALSE, ASCII_CRS);
		validateAndExpectSuccess(InsertFinalNewlineStyle.FALSE, ASCII_LFS);
		validateAndExpectSuccess(InsertFinalNewlineStyle.FALSE, ASCII_CRLFS_NO_FINAL_NEWLINE);
		validateAndExpectSuccess(InsertFinalNewlineStyle.FALSE, ASCII_CRS_NO_FINAL_NEWLINE);
		validateAndExpectSuccess(InsertFinalNewlineStyle.FALSE, ASCII_LFS_NO_FINAL_NEWLINE);
	}

	/*
	 * FAILING CASES
	 */

	@Test
	void whenNewlineExpectedButNotPresentThenFail() throws IOException {
		validateAndExpectFailure(ASCII_CRLFS_NO_FINAL_NEWLINE, InsertFinalNewlineStyle.TRUE);
		validateAndExpectFailure(ASCII_CRS_NO_FINAL_NEWLINE, InsertFinalNewlineStyle.TRUE);
		validateAndExpectFailure(ASCII_LFS_NO_FINAL_NEWLINE, InsertFinalNewlineStyle.TRUE);
	}

	private void validateAndExpectFailure(String fileName, InsertFinalNewlineStyle style) throws IOException {
		final CheckResult result = validate(style, fileName);
		MatcherAssert.assertThat(result, CheckResultMatcher.hasViolations(1));
		MatcherAssert.assertThat(result, CoreMatchers.is(CoreMatchers.not(CheckResultMatcher.successful())));
	}

	private void validateAndExpectSuccess(
			InsertFinalNewlineStyle insertFinalNewlineStyle,
			String identifier
	) throws IOException {
		final CheckResult result = validate(insertFinalNewlineStyle, identifier);
		MatcherAssert.assertThat(result, CoreMatchers.is(CheckResultMatcher.successful()));
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
