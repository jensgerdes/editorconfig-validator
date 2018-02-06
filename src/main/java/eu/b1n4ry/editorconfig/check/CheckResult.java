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

	/**
	 * Returns the result of a number of CodeStyle checks performed on a certain file.
	 *
	 * @return True if no errors occurred and no violations have been reported, false otherwise.
	 */
	public boolean isSuccessful() {
		return violations.isEmpty() && errors.isEmpty();
	}

	public Collection<String> getWarnings() {
		return Collections.unmodifiableCollection(warnings);
	}

	/**
	 * @return An unmodifiable view on the violations of this Check.
	 */
	public Collection<String> getViolations() {
		return Collections.unmodifiableCollection(violations);
	}

	/**
	 * @return An unmodifiable view on the errors of this Check.
	 */
	public Collection<Exception> getErrors() {
		return Collections.unmodifiableCollection(errors);
	}

	/**
	 * Creates an immutable instance of {@link CheckResult} with a CodeStyle violation.
	 * A check will report a violation if a file does not comply with a defined CodeStyle.
	 *
	 * @param message The message that will be reported to the user.
	 * @return A new instance of CheckResult.
	 */
	public static CheckResult withViolation(String message) {
		final CheckResult result = new CheckResult();
		result.violations.add(message);

		return result;
	}

	/**
	 * Creates an immutable instance of {@link CheckResult} with an Exception that occurred during a check.
	 *
	 * @param e The exception that occurred during check.
	 * @return A new instance of CheckResult.
	 */
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
