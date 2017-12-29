package eu.b1n4ry.editorconfig.checkstyle;

import eu.b1n4ry.editorconfig.exception.ValidationException;
import eu.b1n4ry.editorconfig.codingstyle.CharsetStyle;
import eu.b1n4ry.editorconfig.codingstyle.InsertFinalNewlineStyle;
import eu.b1n4ry.editorconfig.codingstyle.LineEndingStyle;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsEmptyCollection.empty;

class FinalNewlineCheckTest {

	private static final Path TEST_DIR = Paths.get("src/test/resources/lineEndings");

	/*
	 * SUCCESS CASES
	 */

	@Test
	void whenAsciiGivenAndNothingExpectedThenReturnSuccess() throws ValidationException {
		validateAndExpectSuccess(InsertFinalNewlineStyle.UNDEFINED, CharsetStyle.UTF8, LineEndingStyle.LF, "asciiLFs");
		validateAndExpectSuccess(InsertFinalNewlineStyle.UNDEFINED, CharsetStyle.LATIN1, LineEndingStyle.LF, "asciiLFs");
		validateAndExpectSuccess(InsertFinalNewlineStyle.UNDEFINED, CharsetStyle.UNDEFINED, LineEndingStyle.LF, "asciiLFs");
		validateAndExpectSuccess(InsertFinalNewlineStyle.UNDEFINED, CharsetStyle.UNDEFINED, LineEndingStyle.UNDEFINED, "asciiLFs");
		validateAndExpectSuccess(InsertFinalNewlineStyle.UNDEFINED, CharsetStyle.UTF8, LineEndingStyle.LF, "asciiCRLFs");
		validateAndExpectSuccess(InsertFinalNewlineStyle.UNDEFINED, CharsetStyle.LATIN1, LineEndingStyle.LF, "asciiCRLFs");
		validateAndExpectSuccess(InsertFinalNewlineStyle.UNDEFINED, CharsetStyle.UNDEFINED, LineEndingStyle.LF, "asciiCRLFs");
		validateAndExpectSuccess(InsertFinalNewlineStyle.UNDEFINED, CharsetStyle.UNDEFINED, LineEndingStyle.UNDEFINED, "asciiCRLFs");
		validateAndExpectSuccess(InsertFinalNewlineStyle.UNDEFINED, CharsetStyle.UTF8, LineEndingStyle.LF, "asciiCRs");
		validateAndExpectSuccess(InsertFinalNewlineStyle.UNDEFINED, CharsetStyle.LATIN1, LineEndingStyle.LF, "asciiCRs");
		validateAndExpectSuccess(InsertFinalNewlineStyle.UNDEFINED, CharsetStyle.UNDEFINED, LineEndingStyle.LF, "asciiCRs");
		validateAndExpectSuccess(InsertFinalNewlineStyle.UNDEFINED, CharsetStyle.UNDEFINED, LineEndingStyle.UNDEFINED, "asciiCRs");
	}

	@Test
	void whenUtf16beGivenAndNothingExpectedThenReturnSuccess() throws ValidationException {
		validateAndExpectSuccess(InsertFinalNewlineStyle.UNDEFINED, CharsetStyle.UTF16BE, LineEndingStyle.LF, "utf16beLFs");
		validateAndExpectSuccess(InsertFinalNewlineStyle.UNDEFINED, CharsetStyle.UNDEFINED, LineEndingStyle.LF, "utf16beLFs");
		validateAndExpectSuccess(InsertFinalNewlineStyle.UNDEFINED, CharsetStyle.UNDEFINED, LineEndingStyle.UNDEFINED, "utf16beLFs");
		validateAndExpectSuccess(InsertFinalNewlineStyle.UNDEFINED, CharsetStyle.UTF16BE, LineEndingStyle.LF, "utf16beCRLFs");
		validateAndExpectSuccess(InsertFinalNewlineStyle.UNDEFINED, CharsetStyle.UNDEFINED, LineEndingStyle.LF, "utf16beCRLFs");
		validateAndExpectSuccess(InsertFinalNewlineStyle.UNDEFINED, CharsetStyle.UNDEFINED, LineEndingStyle.UNDEFINED, "utf16beCRLFs");
		validateAndExpectSuccess(InsertFinalNewlineStyle.UNDEFINED, CharsetStyle.UTF16BE, LineEndingStyle.LF, "utf16beCRs");
		validateAndExpectSuccess(InsertFinalNewlineStyle.UNDEFINED, CharsetStyle.UNDEFINED, LineEndingStyle.LF, "utf16beCRs");
		validateAndExpectSuccess(InsertFinalNewlineStyle.UNDEFINED, CharsetStyle.UNDEFINED, LineEndingStyle.UNDEFINED, "utf16beCRs");
	}

	@Test
	void whenUtf16leGivenAndNothingExpectedThenReturnSuccess() throws ValidationException {
		validateAndExpectSuccess(InsertFinalNewlineStyle.UNDEFINED, CharsetStyle.UTF16LE, LineEndingStyle.LF, "utf16leLFs");
		validateAndExpectSuccess(InsertFinalNewlineStyle.UNDEFINED, CharsetStyle.UNDEFINED, LineEndingStyle.LF, "utf16leLFs");
		validateAndExpectSuccess(InsertFinalNewlineStyle.UNDEFINED, CharsetStyle.UNDEFINED, LineEndingStyle.UNDEFINED, "utf16leLFs");
		validateAndExpectSuccess(InsertFinalNewlineStyle.UNDEFINED, CharsetStyle.UTF16LE, LineEndingStyle.LF, "utf16leCRLFs");
		validateAndExpectSuccess(InsertFinalNewlineStyle.UNDEFINED, CharsetStyle.UNDEFINED, LineEndingStyle.LF, "utf16leCRLFs");
		validateAndExpectSuccess(InsertFinalNewlineStyle.UNDEFINED, CharsetStyle.UNDEFINED, LineEndingStyle.UNDEFINED, "utf16leCRLFs");
		validateAndExpectSuccess(InsertFinalNewlineStyle.UNDEFINED, CharsetStyle.UTF16LE, LineEndingStyle.LF, "utf16leCRs");
		validateAndExpectSuccess(InsertFinalNewlineStyle.UNDEFINED, CharsetStyle.UNDEFINED, LineEndingStyle.LF, "utf16leCRs");
		validateAndExpectSuccess(InsertFinalNewlineStyle.UNDEFINED, CharsetStyle.UNDEFINED, LineEndingStyle.UNDEFINED, "utf16leCRs");
	}

	@Test
	void whenAsciiLfGivenAndAsciiLfExpectedThenReturnSuccess() throws ValidationException {
		validateAndExpectSuccess(InsertFinalNewlineStyle.TRUE, CharsetStyle.UTF8, LineEndingStyle.LF, "asciiLFs");
		validateAndExpectSuccess(InsertFinalNewlineStyle.TRUE, CharsetStyle.LATIN1, LineEndingStyle.LF, "asciiLFs");
		validateAndExpectSuccess(InsertFinalNewlineStyle.TRUE, CharsetStyle.UNDEFINED, LineEndingStyle.LF, "asciiLFs");
		validateAndExpectSuccess(InsertFinalNewlineStyle.TRUE, CharsetStyle.UNDEFINED, LineEndingStyle.UNDEFINED, "asciiLFs");
	}

	@Test
	void whenUTF16BeLfGivenAndUTF16BeLfExpectedThenReturnSuccess() throws ValidationException {
		validateAndExpectSuccess(InsertFinalNewlineStyle.TRUE, CharsetStyle.UTF16BE, LineEndingStyle.LF, "utf16beLFs");
		validateAndExpectSuccess(InsertFinalNewlineStyle.TRUE, CharsetStyle.UNDEFINED, LineEndingStyle.LF, "utf16beLFs");
		validateAndExpectSuccess(InsertFinalNewlineStyle.TRUE, CharsetStyle.UNDEFINED, LineEndingStyle.UNDEFINED, "utf16beLFs");
	}

	@Test
	void whenUTF16LeLfGivenAndUTF16LeLfExpectedThenReturnSuccess() throws ValidationException {
		validateAndExpectSuccess(InsertFinalNewlineStyle.TRUE, CharsetStyle.UTF16LE, LineEndingStyle.LF, "utf16leLFs");
		validateAndExpectSuccess(InsertFinalNewlineStyle.TRUE, CharsetStyle.UNDEFINED, LineEndingStyle.LF, "utf16leLFs");
		validateAndExpectSuccess(InsertFinalNewlineStyle.TRUE, CharsetStyle.UNDEFINED, LineEndingStyle.UNDEFINED, "utf16leLFs");
	}

	@Test
	void whenAsciiCrLfGivenAndAsciiCrLfExpectedThenReturnSuccess() throws ValidationException {
		validateAndExpectSuccess(InsertFinalNewlineStyle.TRUE, CharsetStyle.UTF8, LineEndingStyle.CRLF, "asciiCRLFs");
		validateAndExpectSuccess(InsertFinalNewlineStyle.TRUE, CharsetStyle.LATIN1, LineEndingStyle.CRLF, "asciiCRLFs");
		validateAndExpectSuccess(InsertFinalNewlineStyle.TRUE, CharsetStyle.UNDEFINED, LineEndingStyle.CRLF, "asciiCRLFs");
		validateAndExpectSuccess(InsertFinalNewlineStyle.TRUE, CharsetStyle.UNDEFINED, LineEndingStyle.UNDEFINED, "asciiCRLFs");
	}

	@Test
	void whenUTF16BeCrLfGivenAndUTF16BeCrLfExpectedThenReturnSuccess() throws ValidationException {
		validateAndExpectSuccess(InsertFinalNewlineStyle.TRUE, CharsetStyle.UTF16BE, LineEndingStyle.CRLF, "utf16beCRLFs");
		validateAndExpectSuccess(InsertFinalNewlineStyle.TRUE, CharsetStyle.UNDEFINED, LineEndingStyle.CRLF, "utf16beCRLFs");
		validateAndExpectSuccess(InsertFinalNewlineStyle.TRUE, CharsetStyle.UNDEFINED, LineEndingStyle.UNDEFINED, "utf16beCRLFs");
	}

	@Test
	void whenUTF16LeCrLfGivenAndUTF16LeCrLfExpectedThenReturnSuccess() throws ValidationException {
		validateAndExpectSuccess(InsertFinalNewlineStyle.TRUE, CharsetStyle.UTF16LE, LineEndingStyle.CRLF, "utf16leCRLFs");
		validateAndExpectSuccess(InsertFinalNewlineStyle.TRUE, CharsetStyle.UNDEFINED, LineEndingStyle.CRLF, "utf16leCRLFs");
		validateAndExpectSuccess(InsertFinalNewlineStyle.TRUE, CharsetStyle.UNDEFINED, LineEndingStyle.UNDEFINED, "utf16leCRLFs");
	}

	@Test
	void whenAsciiCrGivenAndAsciiCrExpectedThenReturnSuccess() throws ValidationException {
		validateAndExpectSuccess(InsertFinalNewlineStyle.TRUE, CharsetStyle.UTF8, LineEndingStyle.CR, "asciiCRs");
		validateAndExpectSuccess(InsertFinalNewlineStyle.TRUE, CharsetStyle.LATIN1, LineEndingStyle.CR, "asciiCRs");
		validateAndExpectSuccess(InsertFinalNewlineStyle.TRUE, CharsetStyle.UNDEFINED, LineEndingStyle.CR, "asciiCRs");
		validateAndExpectSuccess(InsertFinalNewlineStyle.TRUE, CharsetStyle.UNDEFINED, LineEndingStyle.UNDEFINED, "asciiCRs");
	}

	@Test
	void whenUTF16BeCrGivenAndUTF16BeCrExpectedThenReturnSuccess() throws ValidationException {
		validateAndExpectSuccess(InsertFinalNewlineStyle.TRUE, CharsetStyle.UTF16BE, LineEndingStyle.CR, "utf16beCRs");
		validateAndExpectSuccess(InsertFinalNewlineStyle.TRUE, CharsetStyle.UNDEFINED, LineEndingStyle.CR, "utf16beCRs");
		validateAndExpectSuccess(InsertFinalNewlineStyle.TRUE, CharsetStyle.UNDEFINED, LineEndingStyle.UNDEFINED, "utf16beCRs");
	}

	@Test
	void whenUTF16LeCrGivenAndUTF16LeCrExpectedThenReturnSuccess() throws ValidationException {
		validateAndExpectSuccess(InsertFinalNewlineStyle.TRUE, CharsetStyle.UTF16LE, LineEndingStyle.CR, "utf16leCRs");
		validateAndExpectSuccess(InsertFinalNewlineStyle.TRUE, CharsetStyle.UNDEFINED, LineEndingStyle.CR, "utf16leCRs");
		validateAndExpectSuccess(InsertFinalNewlineStyle.TRUE, CharsetStyle.UNDEFINED, LineEndingStyle.UNDEFINED, "utf16leCRs");
	}

	private void validateAndExpectSuccess(
			InsertFinalNewlineStyle insertFinalNewlineStyle,
			CharsetStyle charsetStyle,
			LineEndingStyle lineEndingStyle,
			String identifier
	) throws ValidationException {
		final CheckResult result = validate(insertFinalNewlineStyle, charsetStyle, lineEndingStyle, identifier);
		assertThat(result.isSuccessful(), is(true));
		assertThat(result.getViolations(), is(empty()));
		assertThat(result.getErrors(), is(empty()));
	}

	private CheckResult validate(
			InsertFinalNewlineStyle insertFinalNewlineStyle,
			CharsetStyle charsetStyle,
			LineEndingStyle lineEndingStyle,
			String identifier
	) throws ValidationException {
		final Path file = TEST_DIR.resolve(String.format("%s.txt", identifier));

		final FinalNewlineCheckOld check = new FinalNewlineCheckOld(
				insertFinalNewlineStyle,
				charsetStyle,
				lineEndingStyle,
				file
		);
		return check.validate();
	}
}
