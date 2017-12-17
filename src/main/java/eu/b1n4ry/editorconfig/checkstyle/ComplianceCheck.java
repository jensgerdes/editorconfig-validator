package eu.b1n4ry.editorconfig.checkstyle;

import eu.b1n4ry.editorconfig.ValidationException;

public interface ComplianceCheck {
	CheckResult validate() throws ValidationException;
}
