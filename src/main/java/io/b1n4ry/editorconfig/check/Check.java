package io.b1n4ry.editorconfig.check;

import io.b1n4ry.editorconfig.CodeStyle;
import io.b1n4ry.editorconfig.FileValidator;

import java.nio.file.Path;

/**
 * Basic Check interface for all checks.
 */
public interface Check {

	/**
	 * This method is being called by an instance of {@link FileValidator} once before validation
	 * starts and sets the required CodeStyle upon which the validation relies.
	 *
	 * @param codeStyle The codeStyle that applies to the current file.
	 */
	void setCodeStyle(CodeStyle codeStyle);

	/**
	 * This method is being called by an instance of {@link FileValidator} once to validate the
	 * current file with the given Code Style.
	 *
	 * @param fileToCheck The file to validate.
	 * @return An instance of {@link CheckResult}, never null.
	 */
	CheckResult validate(Path fileToCheck);
}
