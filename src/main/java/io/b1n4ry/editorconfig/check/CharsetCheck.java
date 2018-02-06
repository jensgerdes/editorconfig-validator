package io.b1n4ry.editorconfig.check;

import io.b1n4ry.editorconfig.CodeStyle;
import io.b1n4ry.editorconfig.io.CharsetProber;
import io.b1n4ry.editorconfig.style.CharsetStyle;
import org.mozilla.universalchardet.UniversalDetector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

/**
 * Checks that a given text file uses the required Charset.
 */
public class CharsetCheck implements Check {

	private static final Logger LOG = LoggerFactory.getLogger(CharsetCheck.class);
	private static final String ERROR_OTHER_CHARSET = "File `%s` violates Rule `%s`. Found charset `%s` instead!";
	private static final String ERROR_UNKNOWN_CHARSET = "File `%s` violates Rule `%s`. No charset determined.!";
	private static final byte[] BYTE_ORDER_MARK = {(byte) 0xEF, (byte) 0xBB, (byte) 0xBF};

	private CharsetStyle style;
	private Path fileToCheck;

	@Override
	public CheckResult validate(Path fileToCheck) {
		LOG.trace("Entering #validate({}).", fileToCheck);

		this.fileToCheck = fileToCheck;

		if (style == CharsetStyle.UNDEFINED) {
			LOG.debug("CharsetStyle of File({}) is UNDEFINED. Skipping check...", fileToCheck);
			return CheckResult.SUCCESS;
		}

		CheckResult result;

		try {
			final String detectedCharset = UniversalDetector.detectCharset(fileToCheck.toFile());

			if (style.equalsCharset(detectedCharset)) {
				LOG.debug("Detected Charset({}) complies with required CharsetStyle({}).", detectedCharset, style);
				result = (isUTF8(detectedCharset)) ? checkForUTF8BOM(detectedCharset) : CheckResult.SUCCESS;
			} else if (detectedCharset == null) {
				result = tryToDecode();
			} else {
				result = createError(detectedCharset);
			}
		} catch (IOException e) {
			LOG.info("Caught IOException during validation.", e);
			result = CheckResult.withError(e);
		}

		return result;
	}

	private CheckResult tryToDecode() {
		LOG.trace("Entering #tryToDecode().");

		return CharsetProber.canReadFile(fileToCheck, style) ? CheckResult.SUCCESS : createErrorForUnknownCharset();
	}

	private CheckResult checkForUTF8BOM(String detectedCharset) throws IOException {
		LOG.trace("Entering #checkForUTF8BOM().");

		String actualCharset = detectedCharset;
		boolean hasBOM;

		try (SeekableByteChannel channel = Files.newByteChannel(fileToCheck, StandardOpenOption.READ)) {
			byte[] firstThreeBytes = new byte[3];
			ByteBuffer bb = ByteBuffer.allocateDirect(3);
			channel.read(bb);
			bb.flip();
			bb.get(firstThreeBytes);

			hasBOM = Arrays.equals(BYTE_ORDER_MARK, firstThreeBytes);
		}

		if (hasBOM) {
			LOG.debug("Detected BOM in UTF-8 file.");
			actualCharset += " with BOM";

			if (style == CharsetStyle.UTF8BOM) {
				LOG.debug("Charset({}) complies with required CharsetStyle({}).", actualCharset, style);
				return CheckResult.SUCCESS;
			}
		} else if (style == CharsetStyle.UTF8) {
			LOG.debug("Charset({}) complies with required CharsetStyle({}).", actualCharset, style);
			return CheckResult.SUCCESS;
		}

		return createError(actualCharset);
	}

	private boolean isUTF8(String detectedCharset) {
		LOG.trace("Entering #isUTF8().");
		return "UTF-8".equals(detectedCharset);
	}

	private CheckResult createError(String detectedCharset) {
		LOG.trace("Entering #createError().");
		final String errorMessage = String.format(ERROR_OTHER_CHARSET, fileToCheck, style, detectedCharset);
		LOG.debug(errorMessage);
		return CheckResult.withViolation(errorMessage);
	}

	private CheckResult createErrorForUnknownCharset() {
		LOG.trace("Entering #createErrorForUnknownCharset().");
		final String errorMessage = String.format(ERROR_UNKNOWN_CHARSET, fileToCheck, style);
		LOG.debug(errorMessage);
		return CheckResult.withViolation(errorMessage);
	}

	@Override
	public void setCodeStyle(CodeStyle codeStyle) {
		LOG.trace("Entering #setCodeStyle({}).", codeStyle);
		this.style = codeStyle.getCharsetStyle();
	}
}
