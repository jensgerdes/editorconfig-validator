package eu.b1n4ry.editorconfig.style;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Defines the Charset to be used.
 */
public enum CharsetStyle {
	LATIN1(StandardCharsets.ISO_8859_1, "latin1", "windows-1255", "iso-8859-8"),
	UTF8(StandardCharsets.UTF_8),
	UTF8BOM(StandardCharsets.UTF_8),
	UTF16BE(StandardCharsets.UTF_16BE),
	UTF16LE(StandardCharsets.UTF_16LE),
	UNDEFINED(Charset.defaultCharset());

	private final Charset charset;
	private final Set<String> charsetAlias;

	CharsetStyle(Charset charset, String... alias) {
		this.charset = charset;
		this.charsetAlias = new HashSet<>();

		charsetAlias.add(charset.name().toLowerCase());
		charsetAlias.addAll(Arrays.asList(alias));
	}

	/**
	 * Parses the given value as CharsetStyle and returns an interpreted enum.
	 *
	 * @param charset The config value to parse.
	 * @return The corresponding CharsetStyle.
	 * @throws IllegalArgumentException Throws an exception when neither 'latin1', 'utf-8', 'utf-16be', 'utf-16le' nor null is given.
	 */
	public static CharsetStyle parse(String charset) {
		if (charset == null) {
			return UNDEFINED;
		}

		switch (charset) {
			case "latin1":
				return LATIN1;
			case "utf-8":
				return UTF8;
			case "utf-8-bom":
				return UTF8BOM;
			case "utf-16be":
				return UTF16BE;
			case "utf-16le":
				return UTF16LE;
			default:
				throw new IllegalArgumentException(
						String.format(
								"`%s` is not a valid value for `%%s`. Valid values are (latin1, utf-8, utf-8-bom, utf-16be, utf-16le).",
								charset
						)
				);
		}
	}

	public Charset getCharset() {
		return charset;
	}

	public boolean equalsCharset(String charsetName) {
		return charsetName != null && charsetAlias.contains(charsetName.toLowerCase());
	}
}
