package io.b1n4ry.editorconfig;

import io.b1n4ry.editorconfig.check.CheckResult;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public class CheckResultMatcher {

	public static Matcher<CheckResult> hasViolations(Integer expectedViolations) {
		return new CheckResultViolationMatcher(expectedViolations);
	}

	public static Matcher<CheckResult> hasViolations() {
		return new CheckResultViolationMatcher();
	}


	public static Matcher<CheckResult> hasErrors(Integer expectedErrors) {
		return new CheckResultErrorMatcher(expectedErrors);
	}

	public static Matcher<CheckResult> hasErrors() {
		return new CheckResultErrorMatcher();
	}

	public static Matcher<CheckResult> successful() {
		return new CheckResultSuccessMatcher();
	}

	private static class CheckResultSuccessMatcher extends TypeSafeDiagnosingMatcher<CheckResult> {

		@Override
		protected boolean matchesSafely(CheckResult checkResult, Description mismatchDescription) {
			mismatchDescription.appendText(" was getErrors().size() => ")
					.appendValue(checkResult.getErrors().size())
					.appendText("; getViolations().size() => ")
					.appendValue(checkResult.getViolations().size())
					.appendText("; isSuccessful() => ")
					.appendValue(checkResult.isSuccessful());

			return checkResult.isSuccessful() && checkResult.getViolations().isEmpty() && checkResult.getErrors().isEmpty();
		}

		@Override
		public void describeTo(Description description) {
			description.appendText("getErrors().size() => <0>; getViolations().size() => <0>; isSuccessful() => <true>");
		}
	}

	private static class CheckResultViolationMatcher extends TypeSafeDiagnosingMatcher<CheckResult> {

		private final Integer expectedViolations;

		CheckResultViolationMatcher() {
			expectedViolations = null;
		}

		CheckResultViolationMatcher(Integer expectedErrors) {
			this.expectedViolations = expectedErrors;
		}

		@Override
		protected boolean matchesSafely(CheckResult checkResult, Description mismatchDescription) {
			mismatchDescription.appendText(" was getErrors().size() => ")
					.appendValue(checkResult.getErrors().size())
					.appendText("; getViolations().size() => ")
					.appendValue(checkResult.getViolations().size())
					.appendText("; isSuccessful() => ")
					.appendValue(checkResult.isSuccessful());

			final boolean violationComparison = (expectedViolations == null) ? !checkResult.getViolations().isEmpty()
					: checkResult.getViolations().size() == expectedViolations;

			return !checkResult.isSuccessful() && violationComparison;
		}

		@Override
		public void describeTo(Description description) {
			if (expectedViolations == null) {
				description.appendText("getViolations().isEmpty() => <false>");
			} else {
				description.appendText("getViolations().size() => ").appendValue(expectedViolations);
			}

			description.appendText("; isSuccessful() => <false>");
		}
	}

	private static class CheckResultErrorMatcher extends TypeSafeDiagnosingMatcher<CheckResult> {

		private final Integer expectedErrors;

		CheckResultErrorMatcher() {
			expectedErrors = null;
		}

		CheckResultErrorMatcher(Integer expectedErrors) {
			this.expectedErrors = expectedErrors;
		}

		@Override
		protected boolean matchesSafely(CheckResult checkResult, Description mismatchDescription) {
			mismatchDescription.appendText(" was getErrors().size() => ")
					.appendValue(checkResult.getErrors().size())
					.appendText("; getViolations().size() => ")
					.appendValue(checkResult.getViolations().size())
					.appendText("; isSuccessful() => ")
					.appendValue(checkResult.isSuccessful());

			final boolean errorComparison = (expectedErrors == null) ? !checkResult.getErrors().isEmpty()
					: checkResult.getErrors().size() == expectedErrors;

			return !checkResult.isSuccessful() && errorComparison;
		}

		@Override
		public void describeTo(Description description) {
			if (expectedErrors == null) {
				description.appendText("getErrors().isEmpty() => <false>");
			} else {
				description.appendText("getErrors().size() => ").appendValue(expectedErrors);
			}

			description.appendText("; isSuccessful() => <false>");
		}
	}
}
