package eu.b1n4ry.editorconfig;

import eu.b1n4ry.editorconfig.codingstyle.CharsetStyle;
import eu.b1n4ry.editorconfig.codingstyle.IndentationSize;
import eu.b1n4ry.editorconfig.codingstyle.IndentationStyle;
import eu.b1n4ry.editorconfig.codingstyle.InsertFinalNewlineStyle;
import eu.b1n4ry.editorconfig.codingstyle.LineEndingStyle;
import eu.b1n4ry.editorconfig.codingstyle.TrimTrailingWhiteSpaceStyle;
import org.editorconfig.core.EditorConfig;
import org.editorconfig.core.EditorConfigException;

import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public class CodeStyle {

	private static final String KEY_CHARSET = "charset";
	private static final String KEY_INDENT_SIZE = "indent_size";
	private static final String KEY_INDENT_STYLE = "indent_style";
	private static final String KEY_INSERT_FINAL_NEWLINE = "insert_final_newline";
	private static final String KEY_END_OF_LINE = "end_of_line";
	private static final String KEY_TRIM_TRAILING_WHITESPACE = "trim_trailing_whitespace";

	private final CharsetStyle charsetStyle;
	private final IndentationSize indentationSize;
	private final IndentationStyle indentationStyle;
	private final InsertFinalNewlineStyle insertFinalNewlineStyle;
	private final LineEndingStyle lineEndingStyle;
	private final TrimTrailingWhiteSpaceStyle trimTrailingWhiteSpaceStyle;

	private CodeStyle(
			CharsetStyle charsetStyle,
			IndentationSize indentationSize,
			IndentationStyle indentationStyle,
			InsertFinalNewlineStyle insertFinalNewlineStyle,
			LineEndingStyle lineEndingStyle,
			TrimTrailingWhiteSpaceStyle trimTrailingWhiteSpaceStyle
	) {
		this.charsetStyle = charsetStyle;
		this.indentationSize = indentationSize;
		this.indentationStyle = indentationStyle;
		this.insertFinalNewlineStyle = insertFinalNewlineStyle;
		this.lineEndingStyle = lineEndingStyle;
		this.trimTrailingWhiteSpaceStyle = trimTrailingWhiteSpaceStyle;
	}

	public CharsetStyle getCharsetStyle() {
		return charsetStyle;
	}

	public IndentationSize getIndentationSize() {
		return indentationSize;
	}

	public IndentationStyle getIndentationStyle() {
		return indentationStyle;
	}

	public InsertFinalNewlineStyle getInsertFinalNewlineStyle() {
		return insertFinalNewlineStyle;
	}

	public LineEndingStyle getLineEndingStyle() {
		return lineEndingStyle;
	}

	public TrimTrailingWhiteSpaceStyle getTrimTrailingWhiteSpaceStyle() {
		return trimTrailingWhiteSpaceStyle;
	}

	/**
	 * Takes a file path and returns the Code style for this file.
	 *
	 * @param file The Path to the file for which a Code style shall be returned.
	 * @return A Code style object.
	 * @throws EditorConfigException If an {@code .editorconfig} file could not be parsed.
	 */
	public static CodeStyle forPath(Path file) throws EditorConfigException {
		final List<EditorConfig.OutPair> properties = new EditorConfig().getProperties(file.toAbsolutePath().toString());

		final CharsetStyle charsetStyle = CharsetStyle.parse(findByKey(properties, KEY_CHARSET));
		final IndentationSize indentationSize = IndentationSize.parse(findByKey(properties, KEY_INDENT_SIZE));
		final IndentationStyle indentationStyle = IndentationStyle.parse(findByKey(properties, KEY_INDENT_STYLE));
		final InsertFinalNewlineStyle insertFinalNewlineStyle = InsertFinalNewlineStyle.parse(findByKey(properties, KEY_INSERT_FINAL_NEWLINE));
		final LineEndingStyle lineEndingStyle = LineEndingStyle.parse(findByKey(properties, KEY_END_OF_LINE));
		final TrimTrailingWhiteSpaceStyle trimTrailingWhiteSpaceStyle = TrimTrailingWhiteSpaceStyle.parse(findByKey(properties, KEY_TRIM_TRAILING_WHITESPACE));

		return new CodeStyle(
				charsetStyle,
				indentationSize,
				indentationStyle,
				insertFinalNewlineStyle,
				lineEndingStyle,
				trimTrailingWhiteSpaceStyle
		);
	}

	private static String findByKey(List<EditorConfig.OutPair> properties, String key) {
		return properties.stream()
				.filter(Objects::nonNull)
				.filter(outPair -> outPair.getKey().equals(key))
				.map(EditorConfig.OutPair::getVal)
				.findFirst()
				.orElse(null);
	}
}
