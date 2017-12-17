package eu.b1n4ry.editorconfig.checkstyle;

public class CheckResult {

	public static final CheckResult SUCCESS = new CheckResult();
	private final boolean successful;
	private final String errorMessage;

	private CheckResult() {
		this.successful = true;
		this.errorMessage = null;
	}

	public CheckResult(String errorMessage) {
		this.successful = false;
		this.errorMessage = errorMessage;
	}

	public boolean isSuccessful() {
		return successful;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
}
