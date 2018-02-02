package eu.b1n4ry.editorconfig.check;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static eu.b1n4ry.editorconfig.CheckResultMatcher.hasErrors;
import static eu.b1n4ry.editorconfig.CheckResultMatcher.hasViolations;
import static eu.b1n4ry.editorconfig.CheckResultMatcher.successful;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests the {@link CheckResult} class.
 */
class CheckResultTest {

	@Test
	void whenModifyingACheckResultThenThrowsException() {
		final CheckResult result = CheckResult.withViolation("Oops");

		final Executable modifyViolations = () -> result.getViolations().add("A");
		final Executable modifyErrors = () -> result.getErrors().add(new RuntimeException());
		final Executable modifyWarnings = () -> result.getWarnings().add("B");

		assertThrows(UnsupportedOperationException.class, modifyViolations);
		assertThrows(UnsupportedOperationException.class, modifyErrors);
		assertThrows(UnsupportedOperationException.class, modifyWarnings);
	}

	@Test
	void whenMergingTwoSuccessResultsThenResultIsSuccess() {
		final CheckResult merge = CheckResult.merge(CheckResult.SUCCESS, CheckResult.SUCCESS);
		assertThat(merge, is(successful()));
	}

	@Test
	void whenMergingSuccessAndErrorThenResultIsNotSuccessful() {
		final CheckResult merge = CheckResult.merge(CheckResult.SUCCESS, CheckResult.withError(new RuntimeException()));
		assertThat(merge, is(not(successful())));
		assertThat(merge, not(hasViolations()));
		assertThat(merge, hasErrors(1));
	}

	@Test
	void whenMergingSuccessAndViolationThenResultIsNotSuccessful() {
		final CheckResult merge = CheckResult.merge(CheckResult.SUCCESS, CheckResult.withViolation("Problem occured."));
		assertThat(merge, is(not(successful())));
		assertThat(merge, not(hasErrors()));
		assertThat(merge, hasViolations(1));
	}

	@Test
	void whenMergingMultipleErrorsAndWarningsThenResultContainsAllMessages() {
		final CheckResult errorA = CheckResult.withError(new RuntimeException("A"));
		final CheckResult errorB = CheckResult.withError(new RuntimeException("B"));
		final CheckResult errorC = CheckResult.withError(new RuntimeException("C"));
		final CheckResult errorD = CheckResult.withError(new RuntimeException("D"));

		final CheckResult violationA = CheckResult.withViolation("A");
		final CheckResult violationB = CheckResult.withViolation("B");
		final CheckResult violationC = CheckResult.withViolation("C");

		final CheckResult merge = Stream.of(errorA, errorB, errorC, errorD, violationA, violationB, violationC)
				.reduce(CheckResult::merge)
				.orElseThrow(() -> new IllegalStateException("Expected this stream to return a CheckResult!"));

		assertThat(merge, is(not(successful())));
		assertThat(merge, hasViolations(3));
		assertThat(merge, hasErrors(4));
	}
}
