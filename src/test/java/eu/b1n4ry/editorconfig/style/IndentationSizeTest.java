package eu.b1n4ry.editorconfig.style;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IndentationSizeTest {

	private static final String TEST_SIZE = "12";
	private static final String TAB_IDENTIFIER = "tab";
	private static final String ILLEGAL_VALUE = "nonsense";

	@Test
	void whenSizeIsGivenThenIndentSizeWithSizeReturned() {
		final IndentationSize size = IndentationSize.parse("17", null);

		assertThat(size.isUndefined(), is(false));
		assertThat(size.getSize(), is(17));
	}

	@Test
	void whenSizeAndTabWidthAreGivenThenTabWidthIsIgnored() {
		final IndentationSize size = IndentationSize.parse("17", "4");

		assertThat(size.isUndefined(), is(false));
		assertThat(size.getSize(), is(17));
	}

	@Test
	void whenNothingGivenThenUndefinedIsTrue() {
		final IndentationSize size = IndentationSize.parse(null, null);
		assertThat(size.isUndefined(), is(true));
	}

	@Test
	void whenOnlyTabWidthIsGivenThenUndefinedIsTrue() {
		final IndentationSize size = IndentationSize.parse(null, "2");
		assertThat(size.isUndefined(), is(true));
	}

	@Test
	void whenTabIsGivenWithoutTabWidthThenIndentSizeIsDefault() {
		final IndentationSize size = IndentationSize.parse(TAB_IDENTIFIER, null);
		assertThat(size.isUndefined(), is(false));
		assertThat(size.getSize(), is(1));
	}

	@Test
	void whenTabAndTabWidthAreGivenThenIndentSizeIsTabWidth() {
		final IndentationSize size = IndentationSize.parse(TAB_IDENTIFIER, "3");
		assertThat(size.isUndefined(), is(false));
		assertThat(size.getSize(), is(3));
	}

	@Test
	void whenCreatingObjectsWithEqualValuesThenHashCodeAndEqualsReturnTheSame() {
		final IndentationSize sizeA = IndentationSize.parse(TEST_SIZE, null);
		IndentationSize sizeB = IndentationSize.parse(TEST_SIZE, null);

		assertThat(sizeA, is(not(sameInstance(sizeB))));
		assertThat(sizeA, is(equalTo(sizeB)));
		assertThat(sizeA.hashCode(), is(equalTo(sizeB.hashCode())));
	}

	@Test
	void whenCreatingObjectsWithDifferentValuesThenHashCodeAndEqualsReturnDifferenz() {
		final IndentationSize sizeA = IndentationSize.parse(TEST_SIZE, null);
		IndentationSize sizeB = IndentationSize.parse(TAB_IDENTIFIER, null);

		assertThat(sizeA, is(not(equalTo(sizeB))));
		assertThat(sizeA.hashCode(), is(not(equalTo(sizeB.hashCode()))));
	}

	@Test
	void whenInvalidValueIsGivenThenIllegalArgumentExceptionIsThrown() {
		final Executable parse = () -> IndentationSize.parse(ILLEGAL_VALUE, null);
		final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, parse);
		assertThat(exception.getMessage(), is(equalTo("`nonsense` is not a valid value for `%s`. Valid values are (a positive number, tab).")));
	}
}
