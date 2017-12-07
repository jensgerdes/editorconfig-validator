package eu.b1n4ry.editorconfig;

import org.editorconfig.core.EditorConfig;
import org.editorconfig.core.EditorConfigException;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EditorConfigTest {

	private static final String FILEPATH = "../resources/test-editorconfig";

	@Test
	void doSo() throws EditorConfigException {
		final EditorConfig ec = new EditorConfig(FILEPATH, EditorConfig.VERSION);
		final List<EditorConfig.OutPair> properties = ec.getProperties("src/test/java/Edit.java");


		final List<EditorConfig.OutPair> collect = properties.stream()
				.filter(p -> "indent_style".equals(p.getKey()))
				.collect(Collectors.toList());

		assertEquals(1, collect.size());
	}
}
