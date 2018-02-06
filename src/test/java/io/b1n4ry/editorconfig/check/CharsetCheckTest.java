package io.b1n4ry.editorconfig.check;

import io.b1n4ry.editorconfig.CodeStyle;
import io.b1n4ry.editorconfig.TestCodeStyle;
import io.b1n4ry.editorconfig.TestValidator;
import io.b1n4ry.editorconfig.style.CharsetStyle;
import io.b1n4ry.editorconfig.CheckResultMatcher;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static io.b1n4ry.editorconfig.CheckResultMatcher.hasErrors;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

class CharsetCheckTest {

	private static final Path TEST_DIR = Paths.get("src/test/resources/charset");
	private static final String FILEPREFIX_UTF_16_LE = "utf16le";
	private static final String FILEPREFIX_UTF_16_BE = "utf16be";
	private static final String FILEPREFIX_UTF_8_BOM = "utf8bom";
	private static final String FILEPREFIX_UTF_8 = "utf8";
	private static final String FILEPREFIX_LATIN_1 = "latin1";
	private static final String FILEPREFIX_LATIN1_GERMAN_UMLAUTS = "latin1-germanUmlauts";
	private static final String FILEPREFIX_ASCII = "ascii";
	private static final String FILEPREFIX_NONEXISTING = "nonexisting";

	/*
	 * SUCCESSFUL CHECKS
	 */

	@Test
	void whenUTF8FileGivenAndUTF8ExpectedReturnSuccess() {
		validateAndExpectSuccess(CharsetStyle.UTF8, FILEPREFIX_UTF_8);
	}

	@Test
	void whenUTF8BOMFileGivenAndUTF8BOMExpectedReturnSuccess() {
		validateAndExpectSuccess(CharsetStyle.UTF8BOM, FILEPREFIX_UTF_8_BOM);
	}

	@Test
	void whenUTF16BEFileGivenAndUTF16BEExptectedThenReturnSuccess() {
		validateAndExpectSuccess(CharsetStyle.UTF16BE, FILEPREFIX_UTF_16_BE);
	}

	@Test
	void whenUTF16LEFileGivenAndUTF16LEExptectedThenReturnSuccess() {
		validateAndExpectSuccess(CharsetStyle.UTF16LE, FILEPREFIX_UTF_16_LE);
	}

	@Test
	void whenLatin1FileGivenAndLatin1ExpectedThenReturnSuccess() {
		validateAndExpectSuccess(CharsetStyle.LATIN1, FILEPREFIX_LATIN_1);
		validateAndExpectSuccess(CharsetStyle.LATIN1, FILEPREFIX_LATIN1_GERMAN_UMLAUTS);
	}

	@Test
	void whenASCIIFileGivenAndLatin1ExpectedThenReturnSuccess() {
		validateAndExpectSuccess(CharsetStyle.LATIN1, FILEPREFIX_ASCII);
	}

	@Test
	void whenASCIIFileGivenAndUTF8ExpectedThenReturnSuccess() {
		validateAndExpectSuccess(CharsetStyle.UTF8, FILEPREFIX_ASCII);
	}

	@Test
	void whenAnythingGivenAndUndefinedExpectedThenReturnSuccess() {
		validateAndExpectSuccess(CharsetStyle.UNDEFINED, FILEPREFIX_ASCII);
		validateAndExpectSuccess(CharsetStyle.UNDEFINED, FILEPREFIX_LATIN_1);
		validateAndExpectSuccess(CharsetStyle.UNDEFINED, FILEPREFIX_LATIN1_GERMAN_UMLAUTS);
		validateAndExpectSuccess(CharsetStyle.UNDEFINED, FILEPREFIX_UTF_8);
		validateAndExpectSuccess(CharsetStyle.UNDEFINED, FILEPREFIX_UTF_8_BOM);
		validateAndExpectSuccess(CharsetStyle.UNDEFINED, FILEPREFIX_UTF_16_BE);
		validateAndExpectSuccess(CharsetStyle.UNDEFINED, FILEPREFIX_UTF_16_LE);
	}

	/*
	 * FAILING CHECKS
	 */

	@Test
	void whenASCIIGivenAndUTF16BEExpectedThenFail() {
		validateAndExpectFail(CharsetStyle.UTF16BE, FILEPREFIX_ASCII);
	}

	@Test
	void whenASCIIGivenAndUTF16LEExpectedThenFail() {
		validateAndExpectFail(CharsetStyle.UTF16LE, FILEPREFIX_ASCII);
	}

	@Test
	void whenLatin1GivenAndUTF8ExpectedThenFail() {
		validateAndExpectFail(CharsetStyle.UTF8, FILEPREFIX_LATIN_1);
		validateAndExpectFail(CharsetStyle.UTF8, FILEPREFIX_LATIN1_GERMAN_UMLAUTS);
	}

	@Test
	void whenLatin1GivenAndUTF8BOMExpectedThenFail() {
		validateAndExpectFail(CharsetStyle.UTF8BOM, FILEPREFIX_LATIN_1);
		validateAndExpectFail(CharsetStyle.UTF8BOM, FILEPREFIX_LATIN1_GERMAN_UMLAUTS);
	}

	@Test
	void whenLatin1GivenAndUTF16BEExpectedThenFail() {
		validateAndExpectFail(CharsetStyle.UTF16BE, FILEPREFIX_LATIN_1);
		validateAndExpectFail(CharsetStyle.UTF16BE, FILEPREFIX_LATIN1_GERMAN_UMLAUTS);
	}

	@Test
	void whenLatin1GivenAndUTF16LEExpectedThenFail() {
		validateAndExpectFail(CharsetStyle.UTF16LE, FILEPREFIX_LATIN_1);
		validateAndExpectFail(CharsetStyle.UTF16LE, FILEPREFIX_LATIN1_GERMAN_UMLAUTS);
	}

	@Test
	void whenUTF8GivenAndLatin1ExpectedThenFail() {
		validateAndExpectFail(CharsetStyle.LATIN1, FILEPREFIX_UTF_8);
	}

	@Test
	void whenUTF8GivenAndUTF8BOMExpectedThenFail() {
		validateAndExpectFail(CharsetStyle.UTF8BOM, FILEPREFIX_UTF_8);
	}

	@Test
	void whenUTF8GivenAndUTF16BEExpectedThenFail() {
		validateAndExpectFail(CharsetStyle.UTF16BE, FILEPREFIX_UTF_8);
	}

	@Test
	void whenUTF8GivenAndUTF16LEExpectedThenFail() {
		validateAndExpectFail(CharsetStyle.UTF16LE, FILEPREFIX_UTF_8);
	}

	@Test
	void whenUTF8BOMGivenAndLatin1ExpectedThenFail() {
		validateAndExpectFail(CharsetStyle.LATIN1, FILEPREFIX_UTF_8_BOM);
	}

	@Test
	void whenUTF8BOMGivenAndUTF8ExpectedThenFail() {
		validateAndExpectFail(CharsetStyle.UTF8, FILEPREFIX_UTF_8_BOM);
	}

	@Test
	void whenUTF8BOMGivenAndUTF16BEExpectedThenFail() {
		validateAndExpectFail(CharsetStyle.UTF16BE, FILEPREFIX_UTF_8_BOM);
	}

	@Test
	void whenUTF8BOMGivenAndUTF16LEExpectedThenFail() {
		validateAndExpectFail(CharsetStyle.UTF16LE, FILEPREFIX_UTF_8_BOM);
	}

	@Test
	void whenUTF16BEGivenAndLatin1ExpectedThenFail() {
		validateAndExpectFail(CharsetStyle.LATIN1, FILEPREFIX_UTF_16_BE);
	}

	@Test
	void whenUTF16BEGivenAndUTF8ExpectedThenFail() {
		validateAndExpectFail(CharsetStyle.UTF8, FILEPREFIX_UTF_16_BE);
	}

	@Test
	void whenUTF16BEGivenAndUTF8BOMExpectedThenFail() {
		validateAndExpectFail(CharsetStyle.UTF8BOM, FILEPREFIX_UTF_16_BE);
	}

	@Test
	void whenUTF16BEGivenAndUTF16LEExpectedThenFail() {
		validateAndExpectFail(CharsetStyle.UTF16LE, FILEPREFIX_UTF_16_BE);
	}

	@Test
	void whenUTF16LEGivenAndLatin1ExpectedThenFail() {
		validateAndExpectFail(CharsetStyle.LATIN1, FILEPREFIX_UTF_16_LE);
	}

	@Test
	void whenUTF16LEGivenAndUTF8ExpectedThenFail() {
		validateAndExpectFail(CharsetStyle.UTF8, FILEPREFIX_UTF_16_LE);
	}

	@Test
	void whenUTF16LEGivenAndUTF8BOMExpectedThenFail() {
		validateAndExpectFail(CharsetStyle.UTF8BOM, FILEPREFIX_UTF_16_LE);
	}

	@Test
	void whenUTF16LEGivenAndUTF16BEExpectedThenFail() {
		validateAndExpectFail(CharsetStyle.UTF16BE, FILEPREFIX_UTF_16_LE);
	}

	@Test
	void whenExceptionOccursErrorIsReturned() {
		final CheckResult result = validate(CharsetStyle.UTF8, FILEPREFIX_NONEXISTING);
		MatcherAssert.assertThat(result, CheckResultMatcher.hasErrors(1));
	}

	private void validateAndExpectFail(CharsetStyle style, String charsetName) {
		final CheckResult validation = validate(style, charsetName);
		MatcherAssert.assertThat(validation, CoreMatchers.is(CoreMatchers.not(CheckResultMatcher.successful())));
	}

	private void validateAndExpectSuccess(CharsetStyle style, String charsetName) {
		final CheckResult validation = validate(style, charsetName);
		MatcherAssert.assertThat(validation, CoreMatchers.is(CheckResultMatcher.successful()));
	}

	private CheckResult validate(CharsetStyle style, String charsetName) {
		final CodeStyle codeStyle = new TestCodeStyle.Builder().withCharsetStyle(style).build();

		final Path file = TEST_DIR.resolve(String.format("%s.example.txt", charsetName));
		return TestValidator.applyGeneric(new CharsetCheck(), codeStyle, file);
	}
}
