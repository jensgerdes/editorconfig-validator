package eu.b1n4ry.editorconfig;

import eu.b1n4ry.editorconfig.checkstyle.CharacterBasedCheck;
import eu.b1n4ry.editorconfig.checkstyle.CharsetCheck;
import eu.b1n4ry.editorconfig.checkstyle.Check;
import eu.b1n4ry.editorconfig.exception.CheckInstantiationException;
import org.editorconfig.core.EditorConfigException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The FileValidator checks Files for compliance with the rules defined in a corresponding {@code .editorconfig} file.
 */
public class FileValidator {

	private static final Logger LOG = LoggerFactory.getLogger(FileValidator.class);

	private static final Collection<Class<? extends Check>> GENERIC_CHECK_CLASSES = new HashSet<>();
	private static final Collection<Class<? extends CharacterBasedCheck>> CHAR_BASED_CHECK_CLASSES = new HashSet<>();

	private final Collection<Check> genericChecks;
	private final Collection<CharacterBasedCheck> characterBasedChecks;
	private final Path fileToCheck;
	private final CodeStyle codeStyle;

	static {
		register(CharsetCheck.class);
	}

	/**
	 * Allows to register custom Check classes.
	 *
	 * @param clazz A Check class.
	 */
	@SuppressWarnings("unchecked")
	public static void register(Class<? extends Check> clazz) {
		LOG.debug("Registering Check({}).", clazz);

		if (CharacterBasedCheck.class.isAssignableFrom(clazz)) {
			LOG.trace("{} is a character-based Check.", clazz);
			CHAR_BASED_CHECK_CLASSES.add((Class<? extends CharacterBasedCheck>) clazz);
		} else {
			LOG.trace("{} is a generic check.", clazz);
			GENERIC_CHECK_CLASSES.add(clazz);
		}
	}

	/**
	 * Allows to reset all default checks. This may be useful if you want to replace existing checks
	 * with your own implementations.
	 */
	public static void resetChecks() {
		LOG.trace("Entering #resetChecks().");

		GENERIC_CHECK_CLASSES.clear();
		CHAR_BASED_CHECK_CLASSES.clear();

		LOG.info("Check registration has been reset!");
	}

	/**
	 * Initiates a file validation using all registered Checks.
	 *
	 * @param filePath The file to validate.
	 * @throws EditorConfigException
	 */
	public static void validate(Path filePath) throws EditorConfigException {
		LOG.trace("Entering #validate({}).", filePath);

		if (Files.isSymbolicLink(filePath)) {
			LOG.warn("File({}) is a symbolic link. Skipping check.", filePath);
			return;
		}

		checkFile(filePath);
		final CodeStyle codeStyle = CodeStyle.forPath(filePath);
		final Collection<Check> genericChecks;
		final Collection<CharacterBasedCheck> charBasedChecks;

		try {
			genericChecks = initGenericChecks(codeStyle);
			charBasedChecks = initCharacterBasedChecks(codeStyle);
		} catch (IllegalAccessException | InstantiationException e) {
			throw new CheckInstantiationException(e);
		}

		new FileValidator(genericChecks, charBasedChecks, filePath, codeStyle)
				.validate();
	}

	private FileValidator(
			Collection<Check> genericChecks,
			Collection<CharacterBasedCheck> characterBasedChecks,
			Path fileToCheck,
			CodeStyle codeStyle
	) {
		this.genericChecks = genericChecks;
		this.characterBasedChecks = characterBasedChecks;
		this.fileToCheck = fileToCheck;
		this.codeStyle = codeStyle;
	}

	private void validate() {
		try {
			feedCharacterBasedChecks();
			validateAll();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void validateAll() {
		Stream.concat(genericChecks.stream(), characterBasedChecks.stream())
				.map(check -> check.validate(fileToCheck))
				.collect(Collectors.toList());
	}

	private void feedCharacterBasedChecks() throws IOException {
		final Charset charset = codeStyle.getCharsetStyle().getCharset();
		try (final Reader reader = Files.newBufferedReader(fileToCheck, charset)) {
			int value;

			while ((value = reader.read()) > -1) {
				final char character = (char) value;
				characterBasedChecks.forEach(check -> check.readCharacter(character));
			}
		}
	}

	/**
	 * Creates all registered CharacterBased check classes.
	 *
	 * @param codeStyle The codeStyle used for validation.
	 * @return A Collection of {@link CharacterBasedCheck}s.
	 * @throws IllegalAccessException if the check class or its nullary constructor is not accessible.
	 * @throws InstantiationException if the instantiation fails for any reason.
	 */
	private static Collection<CharacterBasedCheck> initCharacterBasedChecks(CodeStyle codeStyle) throws IllegalAccessException, InstantiationException {
		LOG.trace("Entering #initCharacterBasedChecks({}).", codeStyle);
		final Collection<CharacterBasedCheck> checks = new ArrayList<>(CHAR_BASED_CHECK_CLASSES.size());

		for (Class<? extends CharacterBasedCheck> clazz : CHAR_BASED_CHECK_CLASSES) {
			LOG.trace("Instantiating CharacterBasedCheck({}).", clazz);
			final CharacterBasedCheck check = clazz.newInstance();
			check.setCodeStyle(codeStyle);
			checks.add(check);
		}

		return checks;
	}

	/**
	 * Creates all registered generic check classes.
	 *
	 * @param codeStyle The codeStyle used for validation.
	 * @return A Collection of {@link Check}s.
	 * @throws IllegalAccessException if the check class or its nullary constructor is not accessible.
	 * @throws InstantiationException if the instantiation fails for any reason.
	 */
	private static Collection<Check> initGenericChecks(CodeStyle codeStyle) throws IllegalAccessException, InstantiationException {
		LOG.trace("Entering #initGenericChecks({}).", codeStyle);
		final Collection<Check> checks = new ArrayList<>(GENERIC_CHECK_CLASSES.size());

		for (Class<? extends Check> clazz : GENERIC_CHECK_CLASSES) {
			LOG.trace("Instantiating Check({}).", clazz);
			final Check check = clazz.newInstance();
			check.setCodeStyle(codeStyle);
			checks.add(check);
		}

		return checks;
	}

	/**
	 * Checks if the given Path is no directory, exists and is readable.
	 *
	 * @param filePath The path to check.
	 */
	private static void checkFile(Path filePath) {
		LOG.trace("Entering #checkFile({}).", filePath);

		if (Files.isDirectory(filePath)) {
			throw new IllegalArgumentException(
					String.format("Given Path(%s) is a directory, expected a file!", filePath)
			);
		}

		if (Files.notExists(filePath)) {
			throw new IllegalArgumentException(
					String.format("Given File(%s) does not exist!", filePath)
			);
		}

		if (!Files.isReadable(filePath)) {
			throw new IllegalArgumentException(
					String.format("Given File(%s) is not readable!", filePath)
			);
		}
	}
}
