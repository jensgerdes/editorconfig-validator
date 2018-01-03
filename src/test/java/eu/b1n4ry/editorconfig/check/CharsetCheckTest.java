package eu.b1n4ry.editorconfig.check;

import eu.b1n4ry.editorconfig.CodeStyle;
import eu.b1n4ry.editorconfig.TestCodeStyle;
import eu.b1n4ry.editorconfig.TestValidator;
import eu.b1n4ry.editorconfig.style.CharsetStyle;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static eu.b1n4ry.editorconfig.CheckResultMatchers.successful;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

class CharsetCheckTest {

	private static final Path TEST_DIR = Paths.get("src/test/resources/charset");

	/*
	 * SUCCESSFUL CHECKS
	 */

	@Test
	void whenUTF8FileGivenAndUTF8ExpectedReturnSuccess() {
		validateAndExpectSuccess(CharsetStyle.UTF8, "utf8");
	}

	@Test
	void whenUTF8BOMFileGivenAndUTF8BOMExpectedReturnSuccess() {
		validateAndExpectSuccess(CharsetStyle.UTF8BOM, "utf8bom");
	}

	@Test
	void whenUTF16BEFileGivenAndUTF16BEExptectedThenReturnSuccess() {
		validateAndExpectSuccess(CharsetStyle.UTF16BE, "utf16be");
	}

	@Test
	void whenUTF16LEFileGivenAndUTF16LEExptectedThenReturnSuccess() {
		validateAndExpectSuccess(CharsetStyle.UTF16LE, "utf16le");
	}

	@Test
	void whenLatin1FileGivenAndLatin1ExpectedThenReturnSuccess() {
		validateAndExpectSuccess(CharsetStyle.LATIN1, "latin1");
		validateAndExpectSuccess(CharsetStyle.LATIN1, "latin1-germanUmlauts");
	}

	@Test
	void whenASCIIFileGivenAndLatin1ExpectedThenReturnSuccess() {
		validateAndExpectSuccess(CharsetStyle.LATIN1, "ascii");
	}

	@Test
	void whenASCIIFileGivenAndUTF8ExpectedThenReturnSuccess() {
		validateAndExpectSuccess(CharsetStyle.UTF8, "ascii");
	}

	@Test
	void whenAnythingGivenAndUndefinedExpectedThenReturnSuccess() {
		validateAndExpectSuccess(CharsetStyle.UNDEFINED, "ascii");
		validateAndExpectSuccess(CharsetStyle.UNDEFINED, "latin1");
		validateAndExpectSuccess(CharsetStyle.UNDEFINED, "latin1-germanUmlauts");
		validateAndExpectSuccess(CharsetStyle.UNDEFINED, "utf8");
		validateAndExpectSuccess(CharsetStyle.UNDEFINED, "utf8bom");
		validateAndExpectSuccess(CharsetStyle.UNDEFINED, "utf16be");
		validateAndExpectSuccess(CharsetStyle.UNDEFINED, "utf16le");
	}

	/*
	 * FAILING CHECKS
	 */

	@Test
	void whenASCIIGivenAndUTF16BEExpectedThenFail() {
		validateAndExpectFail(CharsetStyle.UTF16BE, "ascii");
	}

	@Test
	void whenASCIIGivenAndUTF16LEExpectedThenFail() {
		validateAndExpectFail(CharsetStyle.UTF16LE, "ascii");
	}

	@Test
	void whenLatin1GivenAndUTF8ExpectedThenFail() {
		validateAndExpectFail(CharsetStyle.UTF8, "latin1");
		validateAndExpectFail(CharsetStyle.UTF8, "latin1-germanUmlauts");
	}

	@Test
	void whenLatin1GivenAndUTF8BOMExpectedThenFail() {
		validateAndExpectFail(CharsetStyle.UTF8BOM, "latin1");
		validateAndExpectFail(CharsetStyle.UTF8BOM, "latin1-germanUmlauts");
	}

	@Test
	void whenLatin1GivenAndUTF16BEExpectedThenFail() {
		validateAndExpectFail(CharsetStyle.UTF16BE, "latin1");
		validateAndExpectFail(CharsetStyle.UTF16BE, "latin1-germanUmlauts");
	}

	@Test
	void whenLatin1GivenAndUTF16LEExpectedThenFail() {
		validateAndExpectFail(CharsetStyle.UTF16LE, "latin1");
		validateAndExpectFail(CharsetStyle.UTF16LE, "latin1-germanUmlauts");
	}

	@Test
	void whenUTF8GivenAndLatin1ExpectedThenFail() {
		validateAndExpectFail(CharsetStyle.LATIN1, "utf8");
	}

	@Test
	void whenUTF8GivenAndUTF8BOMExpectedThenFail() {
		validateAndExpectFail(CharsetStyle.UTF8BOM, "utf8");
	}

	@Test
	void whenUTF8GivenAndUTF16BEExpectedThenFail() {
		validateAndExpectFail(CharsetStyle.UTF16BE, "utf8");
	}

	@Test
	void whenUTF8GivenAndUTF16LEExpectedThenFail() {
		validateAndExpectFail(CharsetStyle.UTF16LE, "utf8");
	}

	@Test
	void whenUTF8BOMGivenAndLatin1ExpectedThenFail() {
		validateAndExpectFail(CharsetStyle.LATIN1, "utf8bom");
	}

	@Test
	void whenUTF8BOMGivenAndUTF8ExpectedThenFail() {
		validateAndExpectFail(CharsetStyle.UTF8, "utf8bom");
	}

	@Test
	void whenUTF8BOMGivenAndUTF16BEExpectedThenFail() {
		validateAndExpectFail(CharsetStyle.UTF16BE, "utf8bom");
	}

	@Test
	void whenUTF8BOMGivenAndUTF16LEExpectedThenFail() {
		validateAndExpectFail(CharsetStyle.UTF16LE, "utf8bom");
	}

	@Test
	void whenUTF16BEGivenAndLatin1ExpectedThenFail() {
		validateAndExpectFail(CharsetStyle.LATIN1, "utf16be");
	}

	@Test
	void whenUTF16BEGivenAndUTF8ExpectedThenFail() {
		validateAndExpectFail(CharsetStyle.UTF8, "utf16be");
	}

	@Test
	void whenUTF16BEGivenAndUTF8BOMExpectedThenFail() {
		validateAndExpectFail(CharsetStyle.UTF8BOM, "utf16be");
	}

	@Test
	void whenUTF16BEGivenAndUTF16LEExpectedThenFail() {
		validateAndExpectFail(CharsetStyle.UTF16LE, "utf16be");
	}

	@Test
	void whenUTF16LEGivenAndLatin1ExpectedThenFail() {
		validateAndExpectFail(CharsetStyle.LATIN1, "utf16le");
	}

	@Test
	void whenUTF16LEGivenAndUTF8ExpectedThenFail() {
		validateAndExpectFail(CharsetStyle.UTF8, "utf16le");
	}

	@Test
	void whenUTF16LEGivenAndUTF8BOMExpectedThenFail() {
		validateAndExpectFail(CharsetStyle.UTF8BOM, "utf16le");
	}

	@Test
	void whenUTF16LEGivenAndUTF16BEExpectedThenFail() {
		validateAndExpectFail(CharsetStyle.UTF16BE, "utf16le");
	}

	private void validateAndExpectFail(CharsetStyle style, String charsetName) {
		final CheckResult validation = validate(style, charsetName);
		assertThat(validation, is(not(successful())));
	}

	private void validateAndExpectSuccess(CharsetStyle style, String charsetName) {
		final CheckResult validation = validate(style, charsetName);
		assertThat(validation, is(successful()));
	}

	private CheckResult validate(CharsetStyle style, String charsetName) {
		final CodeStyle codeStyle = new TestCodeStyle.Builder().withCharsetStyle(style).build();

		final Path file = TEST_DIR.resolve(String.format("%s.example.txt", charsetName));
		return TestValidator.applyGeneric(new CharsetCheck(), codeStyle, file);
	}
}
