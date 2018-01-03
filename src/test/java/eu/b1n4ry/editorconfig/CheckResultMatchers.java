package eu.b1n4ry.editorconfig;

import eu.b1n4ry.editorconfig.check.CheckResult;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public class CheckResultMatchers {

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
}
