package eu.b1n4ry.editorconfig.check;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

public class CheckResult implements Serializable {

	public static final CheckResult SUCCESS = new CheckResult();
	private final Collection<String> warnings;
	private final Collection<String> violations;
	private final Collection<Exception> errors;

	public CheckResult() {
		violations = new LinkedList<>();
		warnings = new LinkedList<>();
		errors = new LinkedList<>();
	}

	public boolean isSuccessful() {
		return violations.isEmpty() && errors.isEmpty();
	}

	public Collection<String> getWarnings() {
		return Collections.unmodifiableCollection(warnings);
	}

	public Collection<String> getViolations() {
		return Collections.unmodifiableCollection(violations);
	}

	public Collection<Exception> getErrors() {
		return Collections.unmodifiableCollection(errors);
	}

	public void addWarning(String message) {
		warnings.add(message);
	}

	public void addViolation(String message) {
		violations.add(message);
	}

	public void addError(Exception e) {
		errors.add(e);
	}

	public static CheckResult withViolation(String message) {
		final CheckResult result = new CheckResult();
		result.addViolation(message);

		return result;
	}

	public static CheckResult withError(Exception e) {
		final CheckResult result = new CheckResult();
		result.addError(e);

		return result;
	}
}
