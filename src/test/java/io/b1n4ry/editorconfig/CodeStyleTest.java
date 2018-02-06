package io.b1n4ry.editorconfig;

import io.b1n4ry.editorconfig.style.CharsetStyle;
import io.b1n4ry.editorconfig.style.IndentationStyle;
import io.b1n4ry.editorconfig.style.InsertFinalNewlineStyle;
import io.b1n4ry.editorconfig.style.LineEndingStyle;
import io.b1n4ry.editorconfig.style.TrimTrailingWhiteSpaceStyle;
import org.editorconfig.core.EditorConfigException;
import org.editorconfig.core.ParsingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests the functionality on the base of this projects {@code .editorconfig} file.
 */
class CodeStyleTest {

	@Test
	void parseJavaDefinitions() throws EditorConfigException {
		final Path javaFile = Paths.get("src/test/java/eu/b1n4ry/editorconfig/CodeStyleTest.java");
		final CodeStyle codeStyle = CodeStyle.forPath(javaFile);

		assertThat(codeStyle.getCharsetStyle(), is(CharsetStyle.UTF8));
		assertThat(codeStyle.getIndentationSize().getSize(), is(4));
		assertThat(codeStyle.getIndentationStyle(), is(IndentationStyle.TAB));
		assertThat(codeStyle.getTrimTrailingWhiteSpaceStyle(), is(TrimTrailingWhiteSpaceStyle.TRUE));
		assertThat(codeStyle.getInsertFinalNewlineStyle(), is(InsertFinalNewlineStyle.TRUE));
		assertThat(codeStyle.getLineEndingStyle(), is(LineEndingStyle.LF));
	}

	@Test
	void parseNotExplicitlyDefinedFile() throws EditorConfigException {
		final Path javaFile = Paths.get("a/file.so");
		final CodeStyle codeStyle = CodeStyle.forPath(javaFile);

		assertThat(codeStyle.getCharsetStyle(), is(CharsetStyle.UTF8));
		assertThat(codeStyle.getIndentationSize().getSize(), is(2));
		assertThat(codeStyle.getIndentationStyle(), is(IndentationStyle.UNDEFINED));
		assertThat(codeStyle.getTrimTrailingWhiteSpaceStyle(), is(TrimTrailingWhiteSpaceStyle.TRUE));
		assertThat(codeStyle.getInsertFinalNewlineStyle(), is(InsertFinalNewlineStyle.TRUE));
		assertThat(codeStyle.getLineEndingStyle(), is(LineEndingStyle.LF));
	}

	@Test
	void parseFileWithoutEditorConfig() throws EditorConfigException {
		final Path fileWithoutEditorConfig = Paths.get("/test-c-A-X.dat");
		final CodeStyle codeStyle = CodeStyle.forPath(fileWithoutEditorConfig);

		assertThat(codeStyle.getCharsetStyle(), is(CharsetStyle.UNDEFINED));
		assertThat(codeStyle.getIndentationSize().isUndefined(), is(true));
		assertThat(codeStyle.getIndentationStyle(), is(IndentationStyle.UNDEFINED));
		assertThat(codeStyle.getTrimTrailingWhiteSpaceStyle(), is(TrimTrailingWhiteSpaceStyle.UNDEFINED));
		assertThat(codeStyle.getInsertFinalNewlineStyle(), is(InsertFinalNewlineStyle.UNDEFINED));
		assertThat(codeStyle.getLineEndingStyle(), is(LineEndingStyle.UNDEFINED));
	}

	@Test
	void parseWithBrokenEditorConfig() {
		final Path testPath = Paths.get("src/test/resources/brokenEditorConfig/abc.test");
		final Executable parse = () -> CodeStyle.forPath(testPath);
		assertThrows(ParsingException.class, parse);
	}

	@Test
	void parseWithUnreadableEditorConfig() {
		final Path testPath = Paths.get("src/test/resources/unreadableEditorConfig/abc.test");
		final File editorConfig = new File("src/test/resources/unreadableEditorConfig/.editorconfig");
		try {
			assertThat(editorConfig.exists(), is(true));
			assertThat(editorConfig.setReadable(false), is(true));
			final Executable parse = () -> CodeStyle.forPath(testPath);
			assertThrows(EditorConfigException.class, parse);
		} finally {
			assertThat(editorConfig.setReadable(true), is(true));
		}
	}
}
