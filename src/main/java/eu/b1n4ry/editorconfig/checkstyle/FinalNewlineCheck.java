package eu.b1n4ry.editorconfig.checkstyle;

import eu.b1n4ry.editorconfig.codingstyle.InsertFinalNewlineStyle;

import java.nio.file.Path;

public class FinalNewlineCheck implements ComplianceCheck {

	private final InsertFinalNewlineStyle style;
	private final Path fileToCheck;

	public FinalNewlineCheck(InsertFinalNewlineStyle style, Path fileToCheck) {
		this.style = style;
		this.fileToCheck = fileToCheck;
	}

	@Override
	public CheckResult validate() {
		return null;
	}
}
