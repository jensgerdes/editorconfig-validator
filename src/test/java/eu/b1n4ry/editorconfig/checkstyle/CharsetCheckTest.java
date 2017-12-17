package eu.b1n4ry.editorconfig.checkstyle;

import eu.b1n4ry.editorconfig.ValidationException;
import eu.b1n4ry.editorconfig.codingstyle.CharsetStyle;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

class CharsetCheckTest {

	private static final Path TEST_DIR = Paths.get("src/test/resources/charset");

	/*
	 * SUCCESSFUL CHECKS
	 */

	@Test
	void whenUTF8FileGivenAndUTF8ExpectedReturnSuccess() throws ValidationException {
		validateAndExpectSuccess(CharsetStyle.UTF8, "utf8");
	}

	@Test
	void whenUTF8BOMFileGivenAndUTF8BOMExpectedReturnSuccess() throws ValidationException {
		validateAndExpectSuccess(CharsetStyle.UTF8BOM, "utf8bom");
	}

	@Test
	void whenUTF16BEFileGivenAndUTF16BEExptectedThenReturnSuccess() throws ValidationException {
		validateAndExpectSuccess(CharsetStyle.UTF16BE, "utf16be");
	}

	@Test
	void whenUTF16LEFileGivenAndUTF16LEExptectedThenReturnSuccess() throws ValidationException {
		validateAndExpectSuccess(CharsetStyle.UTF16LE, "utf16le");
	}

	@Test
	void whenLatin1FileGivenAndLatin1ExpectedThenReturnSuccess() throws ValidationException {
		validateAndExpectSuccess(CharsetStyle.LATIN1, "latin1");
		validateAndExpectSuccess(CharsetStyle.LATIN1, "latin1-germanUmlauts");
	}

	@Test
	void whenASCIIFileGivenAndLatin1ExpectedThenReturnSuccess() throws ValidationException {
		validateAndExpectSuccess(CharsetStyle.LATIN1, "ascii");
	}

	@Test
	void whenASCIIFileGivenAndUTF8ExpectedThenReturnSuccess() throws ValidationException {
		validateAndExpectSuccess(CharsetStyle.UTF8, "ascii");
	}

	@Test
	void whenAnythingGivenAndUndefinedExpectedThenReturnSuccess() throws ValidationException {
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
	void whenASCIIGivenAndUTF16BEExpectedThenFail() throws ValidationException {
		validateAndExpectFail(CharsetStyle.UTF16BE, "ascii");
	}

	@Test
	void whenASCIIGivenAndUTF16LEExpectedThenFail() throws ValidationException {
		validateAndExpectFail(CharsetStyle.UTF16LE, "ascii");
	}

	@Test
	void whenLatin1GivenAndUTF8ExpectedThenFail() throws ValidationException {
		validateAndExpectFail(CharsetStyle.UTF8, "latin1");
		validateAndExpectFail(CharsetStyle.UTF8, "latin1-germanUmlauts");
	}

	@Test
	void whenLatin1GivenAndUTF8BOMExpectedThenFail() throws ValidationException {
		validateAndExpectFail(CharsetStyle.UTF8BOM, "latin1");
		validateAndExpectFail(CharsetStyle.UTF8BOM, "latin1-germanUmlauts");
	}

	@Test
	void whenLatin1GivenAndUTF16BEExpectedThenFail() throws ValidationException {
		validateAndExpectFail(CharsetStyle.UTF16BE, "latin1");
		validateAndExpectFail(CharsetStyle.UTF16BE, "latin1-germanUmlauts");
	}

	@Test
	void whenLatin1GivenAndUTF16LEExpectedThenFail() throws ValidationException {
		validateAndExpectFail(CharsetStyle.UTF16LE, "latin1");
		validateAndExpectFail(CharsetStyle.UTF16LE, "latin1-germanUmlauts");
	}

	@Test
	void whenUTF8GivenAndLatin1ExpectedThenFail() throws ValidationException {
		validateAndExpectFail(CharsetStyle.LATIN1, "utf8");
	}

	@Test
	void whenUTF8GivenAndUTF8BOMExpectedThenFail() throws ValidationException {
		validateAndExpectFail(CharsetStyle.UTF8BOM, "utf8");
	}

	@Test
	void whenUTF8GivenAndUTF16BEExpectedThenFail() throws ValidationException {
		validateAndExpectFail(CharsetStyle.UTF16BE, "utf8");
	}

	@Test
	void whenUTF8GivenAndUTF16LEExpectedThenFail() throws ValidationException {
		validateAndExpectFail(CharsetStyle.UTF16LE, "utf8");
	}

	@Test
	void whenUTF8BOMGivenAndLatin1ExpectedThenFail() throws ValidationException {
		validateAndExpectFail(CharsetStyle.LATIN1, "utf8bom");
	}

	@Test
	void whenUTF8BOMGivenAndUTF8ExpectedThenFail() throws ValidationException {
		validateAndExpectFail(CharsetStyle.UTF8, "utf8bom");
	}

	@Test
	void whenUTF8BOMGivenAndUTF16BEExpectedThenFail() throws ValidationException {
		validateAndExpectFail(CharsetStyle.UTF16BE, "utf8bom");
	}

	@Test
	void whenUTF8BOMGivenAndUTF16LEExpectedThenFail() throws ValidationException {
		validateAndExpectFail(CharsetStyle.UTF16LE, "utf8bom");
	}

	@Test
	void whenUTF16BEGivenAndLatin1ExpectedThenFail() throws ValidationException {
		validateAndExpectFail(CharsetStyle.LATIN1, "utf16be");
	}

	@Test
	void whenUTF16BEGivenAndUTF8ExpectedThenFail() throws ValidationException {
		validateAndExpectFail(CharsetStyle.UTF8, "utf16be");
	}

	@Test
	void whenUTF16BEGivenAndUTF8BOMExpectedThenFail() throws ValidationException {
		validateAndExpectFail(CharsetStyle.UTF8BOM, "utf16be");
	}

	@Test
	void whenUTF16BEGivenAndUTF16LEExpectedThenFail() throws ValidationException {
		validateAndExpectFail(CharsetStyle.UTF16LE, "utf16be");
	}

	@Test
	void whenUTF16LEGivenAndLatin1ExpectedThenFail() throws ValidationException {
		validateAndExpectFail(CharsetStyle.LATIN1, "utf16le");
	}

	@Test
	void whenUTF16LEGivenAndUTF8ExpectedThenFail() throws ValidationException {
		validateAndExpectFail(CharsetStyle.UTF8, "utf16le");
	}

	@Test
	void whenUTF16LEGivenAndUTF8BOMExpectedThenFail() throws ValidationException {
		validateAndExpectFail(CharsetStyle.UTF8BOM, "utf16le");
	}

	@Test
	void whenUTF16LEGivenAndUTF16BEExpectedThenFail() throws ValidationException {
		validateAndExpectFail(CharsetStyle.UTF16BE, "utf16le");
	}

	private void validateAndExpectFail(CharsetStyle style, String charsetName) throws ValidationException {
		final CheckResult result = validate(style, charsetName);
		assertThat(result.isSuccessful(), is(false));
		assertThat(
				result.getErrorMessage(),
				containsString(String.format(" violates Rule `%s`.", style))
		);
	}

	private void validateAndExpectSuccess(CharsetStyle style, String charsetName) throws ValidationException {
		final CheckResult result = validate(style, charsetName);
		assertThat(result.isSuccessful(), is(true));
		assertThat(result.getErrorMessage(), is(nullValue()));
	}

	private CheckResult validate(CharsetStyle style, String charsetName) throws ValidationException {
		final Path file = TEST_DIR.resolve(String.format("%s.example.txt", charsetName));
		final CharsetCheck check = new CharsetCheck(style, file);
		return check.validate();
	}
}
