package eu.b1n4ry.editorconfig.check;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

/**
 * The result of of a {@link Check}.
 */
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

	public static CheckResult withViolation(String message) {
		final CheckResult result = new CheckResult();
		result.violations.add(message);

		return result;
	}

	public static CheckResult withError(Exception e) {
		final CheckResult result = new CheckResult();
		result.errors.add(e);

		return result;
	}

	/**
	 * Merges two CheckResults into one by creating a new CheckResult that contains all Warnings, Violations and Errors.
	 *
	 * @param resultA The first CheckResult.
	 * @param resultB The second CheckResult.
	 * @return A new CheckResult that combines the given information.
	 */
	public static CheckResult merge(CheckResult resultA, CheckResult resultB) {
		final CheckResult mergedResult = new CheckResult();

		mergedResult.errors.addAll(resultA.errors);
		mergedResult.errors.addAll(resultB.errors);

		mergedResult.violations.addAll(resultA.violations);
		mergedResult.violations.addAll(resultB.violations);

		mergedResult.warnings.addAll(resultA.warnings);
		mergedResult.warnings.addAll(resultB.warnings);

		return mergedResult;
	}
}
