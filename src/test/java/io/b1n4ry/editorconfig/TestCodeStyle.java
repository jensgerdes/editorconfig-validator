package io.b1n4ry.editorconfig;

import io.b1n4ry.editorconfig.style.CharsetStyle;
import io.b1n4ry.editorconfig.style.IndentationSize;
import io.b1n4ry.editorconfig.style.IndentationStyle;
import io.b1n4ry.editorconfig.style.InsertFinalNewlineStyle;
import io.b1n4ry.editorconfig.style.LineEndingStyle;
import io.b1n4ry.editorconfig.style.TrimTrailingWhiteSpaceStyle;

public class TestCodeStyle extends CodeStyle {
	private TestCodeStyle(
			CharsetStyle charsetStyle,
			IndentationSize indentationSize,
			IndentationStyle indentationStyle,
			InsertFinalNewlineStyle insertFinalNewlineStyle,
			LineEndingStyle lineEndingStyle,
			TrimTrailingWhiteSpaceStyle trimTrailingWhiteSpaceStyle
	) {
		super(charsetStyle, indentationSize, indentationStyle, insertFinalNewlineStyle, lineEndingStyle, trimTrailingWhiteSpaceStyle);
	}

	public static class Builder {
		private CharsetStyle charsetStyle = CharsetStyle.UNDEFINED;
		private IndentationSize indentationSize;
		private IndentationStyle indentationStyle = IndentationStyle.UNDEFINED;
		private InsertFinalNewlineStyle insertFinalNewlineStyle = InsertFinalNewlineStyle.UNDEFINED;
		private LineEndingStyle lineEndingStyle = LineEndingStyle.UNDEFINED;
		private TrimTrailingWhiteSpaceStyle trimTrailingWhiteSpaceStyle = TrimTrailingWhiteSpaceStyle.UNDEFINED;

		public Builder withCharsetStyle(CharsetStyle style) {
			this.charsetStyle = style;
			return this;
		}

		public Builder withInsertFinalNewlineStyle(InsertFinalNewlineStyle insertFinalNewlineStyle) {
			this.insertFinalNewlineStyle = insertFinalNewlineStyle;
			return this;
		}

		public Builder withLineEndingStyle(LineEndingStyle lineEndingStyle) {
			this.lineEndingStyle = lineEndingStyle;
			return this;
		}

		public Builder withIndentationStyle(IndentationStyle indentationStyle) {
			this.indentationStyle = indentationStyle;
			return this;
		}

		public Builder withIndentationSize(IndentationSize indentationSize) {
			this.indentationSize = indentationSize;
			return this;
		}

		public Builder withTrimTrailingWhiteSpace(TrimTrailingWhiteSpaceStyle trimTrailingWhiteSpace) {
			this.trimTrailingWhiteSpaceStyle = trimTrailingWhiteSpace;
			return this;
		}

		public CodeStyle build() {

			return new TestCodeStyle(
					charsetStyle,
					indentationSize,
					indentationStyle,
					insertFinalNewlineStyle,
					lineEndingStyle,
					trimTrailingWhiteSpaceStyle
			);
		}
	}
}
