package eu.b1n4ry.editorconfig.checkstyle;

import eu.b1n4ry.editorconfig.exception.ValidationException;
import eu.b1n4ry.editorconfig.checkstyle.lineending.ByteSequenceDeterminator;
import eu.b1n4ry.editorconfig.codingstyle.CharsetStyle;
import eu.b1n4ry.editorconfig.codingstyle.InsertFinalNewlineStyle;
import eu.b1n4ry.editorconfig.codingstyle.LineEndingStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Path;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Checks that a given text file has a final new line if required.
 */
public class FinalNewlineCheckOld {

	private static final Logger LOG = LoggerFactory.getLogger(FinalNewlineCheckOld.class);
	private static final String ERROR_MESSAGE = "File(%s) is missing a final new line.";

	private final InsertFinalNewlineStyle style;
	private final Path fileToCheck;
	private final CharsetStyle charsetStyle;
	private final LineEndingStyle lineEndingStyle;

	public FinalNewlineCheckOld(
			InsertFinalNewlineStyle newlineStyle,
			CharsetStyle charsetStyle,
			LineEndingStyle lineEndingStyle,
			Path fileToCheck) {
		this.style = newlineStyle;
		this.charsetStyle = charsetStyle;
		this.fileToCheck = fileToCheck;
		this.lineEndingStyle = lineEndingStyle;
		LOG.trace("Initialized Check with InsertFinalNewlineStyle({}), CharsetStyle({}), File({}).", newlineStyle, charsetStyle, fileToCheck);
	}

	public CheckResult validate() throws ValidationException {
		LOG.trace("Entering #validate().");

		if (style == InsertFinalNewlineStyle.UNDEFINED) {
			LOG.debug("InsertFinalNewlineStyle of File({}) is UNDEFINED. Skipping check...", fileToCheck);
			return CheckResult.SUCCESS;
		}

		final Set<byte[]> sequences = ByteSequenceDeterminator.determinePossibleByteSequences(charsetStyle, lineEndingStyle);
		final int longestSequence = determineLongestSequence(sequences);
		final byte[] lastBytesFromFile = readLastBytesFromFile(longestSequence);

		if (style == InsertFinalNewlineStyle.TRUE) {
			return expectFinalNewLine(sequences, lastBytesFromFile);
		}

		return expectNoFinalNewLine(sequences, lastBytesFromFile);
	}

	private CheckResult expectNoFinalNewLine(Set<byte[]> sequences, byte[] lastBytesFromFile) {
		return null;
	}

	/**
	 * Checks that the file ends with a final newline.
	 *
	 * @param sequences The possible byte representations of valid line breaks.
	 * @param lastBytesFromFile The last bytes of the file.
	 * @return {@link CheckResult#SUCCESS} if true, a {@link CheckResult} with an error message otherwise.
	 */
	private CheckResult expectFinalNewLine(Set<byte[]> sequences, byte[] lastBytesFromFile) {
		final boolean positiveMatch = sequences.stream().anyMatch(sequenceEquals(lastBytesFromFile));

		if (positiveMatch) {
            LOG.debug("Found a matching line ending.");
			return CheckResult.SUCCESS;
        }

		return createErrorMessage();
	}

	private CheckResult createErrorMessage() {
		final String error = String.format(ERROR_MESSAGE, fileToCheck);
		return CheckResult.withViolation(error);
	}

	/**
	 * Checks if the given sequence equals the last bytes of the file by iterating backwards through
	 * both arrays and comparing their content.
	 *
	 * @param lastBytesFromFile The last bytes of the file.
	 * @return True if both are equal, false otherwise.
	 */
	private Predicate<byte[]> sequenceEquals(byte[] lastBytesFromFile) {
		LOG.trace("Entering #sequenceEquals({})", lastBytesFromFile);

		return (sequence) -> {
			for (int i = 1; i <= sequence.length; i++) {
				if (sequence[sequence.length - i] != lastBytesFromFile[lastBytesFromFile.length - i]) {
					return false;
				}
			}

			return true;
		};
	}

	/**
	 * Returns the length of the longest array in the given set.
	 *
	 * @param sequences The set to check for the longest array.
	 * @return The length of the longest array.
	 */
	private int determineLongestSequence(Set<byte[]> sequences) {
		LOG.trace("Entering #determineLongestSequence().");
		return sequences.stream()
				.map(s -> s.length)
				.max(Integer::compare).orElse(0);
	}

	/**
	 * Reads the last numberOfBytes from the end of the file, like tail.
	 *
	 * @param numberOfBytes The number of bytes to read.
	 * @return An array of bytes from the end of the file.
	 * @throws ValidationException Throws an Exception if an error occured during file access.
	 */
	private byte[] readLastBytesFromFile(int numberOfBytes) throws ValidationException {
		LOG.trace("Entering #readLastBytesFromFile({}).", numberOfBytes);

		final byte[] lastBytes = new byte[numberOfBytes];

		try (final RandomAccessFile file = new RandomAccessFile(fileToCheck.toFile(), "r")) {
			final int length = calculateLengthToRead(file.length(), lastBytes.length);
			final int offset = calculateOffset(file.length(), lastBytes.length);


			if (length > 0L) {
				file.seek(offset);
				file.read(lastBytes, 0, length);
			}

		} catch (IOException e) {
			LOG.info("Caught IOException during validation.", e);
			throw new ValidationException(e);
		}

		return lastBytes;
	}

	/**
	 * Calculates the maximum number of bytes to read.
	 *
	 * @param fileSize            The size of the file to read.
	 * @param numberOfBytesToRead The requested number of bytes.
	 * @return The numberOfBytesToRead if greater than the filesize, the filesize otherwise.
	 */
	private int calculateLengthToRead(long fileSize, int numberOfBytesToRead) {
		LOG.trace("Entering #calculateLengthToRead({}, {}).", fileSize, numberOfBytesToRead);
		return (int) Math.min(numberOfBytesToRead, fileSize);
	}

	/**
	 * Calculates the offset from which the file should be read to get the last numberOfBytesToRead from the file.
	 *
	 * @param fileSize            The size of the file to read.
	 * @param numberOfBytesToRead The requested number of bytes.
	 * @return The offset from which the file should be read.
	 */
	private int calculateOffset(long fileSize, int numberOfBytesToRead) {
		LOG.trace("Entering #calculateOffset({}, {}).", fileSize, numberOfBytesToRead);
		return (int) Math.max(fileSize - numberOfBytesToRead, 0L);
	}
}
