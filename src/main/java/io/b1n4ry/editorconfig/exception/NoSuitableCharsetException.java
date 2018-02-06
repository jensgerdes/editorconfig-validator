package io.b1n4ry.editorconfig.exception;

import java.nio.file.Path;

import io.b1n4ry.editorconfig.style.CharsetStyle;

/**
 * Exception thrown when none of the supported Charsets (See {@link CharsetStyle} has been used.
 */
public class NoSuitableCharsetException extends RuntimeException {

	public NoSuitableCharsetException(Path file) {
		super(String.format("File `%s` uses an unsupported charset.", file));
	}
}
