package eu.b1n4ry.editorconfig.check;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import eu.b1n4ry.editorconfig.CodeStyle;
import eu.b1n4ry.editorconfig.TestCodeStyle;
import eu.b1n4ry.editorconfig.TestValidator;
import eu.b1n4ry.editorconfig.style.IndentationSize;
import eu.b1n4ry.editorconfig.style.IndentationStyle;
import org.junit.jupiter.api.Test;

import static eu.b1n4ry.editorconfig.CheckResultMatcher.hasViolations;
import static eu.b1n4ry.editorconfig.CheckResultMatcher.successful;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

class IndentationCheckTest {

	private static final Path TEST_DIR = Paths.get("src/test/resources/indentation");
	private static final Path TAB_VALID = TEST_DIR.resolve("TabValid.txt");
	private static final Path EMPTY = TEST_DIR.resolve("empty.txt");
	private static final Path SPACE_INDENT_4 = TEST_DIR.resolve("SpaceIndent4.txt");
	private static final Path SPACE_INDENT_VARYING = TEST_DIR.resolve("SpaceIndentVarying.txt");

	private static final IndentationSize INDENTATION_SIZE_UNDEFINED = IndentationSize.parse(null, null);
	private static final IndentationSize INDENTATION_SIZE_FOUR = IndentationSize.parse("4", null);
	/*
	 * SUCCESS CASES
	 */

	@Test
	void whenIndentationStyleUndefinedThenReturnSuccess() throws IOException {
		validateAndExpectSuccess(TAB_VALID, IndentationStyle.UNDEFINED, INDENTATION_SIZE_UNDEFINED);
		validateAndExpectSuccess(SPACE_INDENT_4, IndentationStyle.UNDEFINED, INDENTATION_SIZE_UNDEFINED);
		validateAndExpectSuccess(SPACE_INDENT_VARYING, IndentationStyle.UNDEFINED, INDENTATION_SIZE_UNDEFINED);
	}

	@Test
	void whenTabExpectedAndTabGivenThenReturnSuccess() throws IOException {
		validateAndExpectSuccess(TAB_VALID, IndentationStyle.TAB, INDENTATION_SIZE_UNDEFINED);
	}

	@Test
	void whenSpaceExpectedWithoutSizeAndSpaceIndentGivenThenReturnSuccess() throws IOException {
		validateAndExpectSuccess(SPACE_INDENT_4, IndentationStyle.SPACE, INDENTATION_SIZE_UNDEFINED);
		validateAndExpectSuccess(SPACE_INDENT_VARYING, IndentationStyle.SPACE, INDENTATION_SIZE_UNDEFINED);
	}

	@Test
	void whenSpaceExpectedWithSizeAndCorrectlyIndentedFileGivenThenReturnSuccess() throws IOException {
		validateAndExpectSuccess(SPACE_INDENT_4, IndentationStyle.SPACE, INDENTATION_SIZE_FOUR);
	}

	@Test
	void whenEmptyFileGivenThenAlwaysReturnSuccess() throws IOException {
		validateAndExpectSuccess(EMPTY, IndentationStyle.TAB, INDENTATION_SIZE_UNDEFINED);
		validateAndExpectSuccess(EMPTY, IndentationStyle.SPACE, INDENTATION_SIZE_UNDEFINED);
		validateAndExpectSuccess(EMPTY, IndentationStyle.SPACE, INDENTATION_SIZE_FOUR);
	}

	/*
	 * FAILING CASES
	 */

	@Test
	void whenSpaceExpectedAndTabGivenThenReturnViolation() throws IOException {
		validateAndExpectFailure(TAB_VALID, IndentationStyle.SPACE, INDENTATION_SIZE_UNDEFINED);
	}

	@Test
	void whenTabExpectedAndSpaceGivenThenReturnViolation() throws IOException {
		validateAndExpectFailure(SPACE_INDENT_VARYING, IndentationStyle.TAB, INDENTATION_SIZE_UNDEFINED);
	}

	@Test
	void whenSpaceWithSizeFourExpectedAndSpaceWithVaryingSizeGivenThenReturnViolation() throws IOException {
		validateAndExpectFailure(SPACE_INDENT_VARYING, IndentationStyle.SPACE, INDENTATION_SIZE_FOUR);
	}

	private void validateAndExpectFailure(Path file, IndentationStyle style, IndentationSize size) throws IOException {
		final CheckResult result = validate(file, style, size);
		assertThat(result, hasViolations(1));
		assertThat(result, is(not(successful())));
	}

	private void validateAndExpectSuccess(Path file, IndentationStyle style, IndentationSize size) throws IOException {
		final CheckResult result = validate(file, style, size);
		assertThat(result, is(successful()));
	}

	private CheckResult validate(Path file, IndentationStyle style, IndentationSize size) throws IOException {
		final CodeStyle codeStyle = new TestCodeStyle.Builder()
				.withIndentationStyle(style)
				.withIndentationSize(size)
				.build();

		return TestValidator.apply(new IndentationCheck(), codeStyle, file);
	}
}
