package eu.b1n4ry.editorconfig.codingstyle;

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
	void whenNumberIsGivenThenUndefinedAndTabIsFalse() {
		final IndentationSize size = IndentationSize.parse("17");

		assertThat(size.isTab(), is(false));
		assertThat(size.isUndefined(), is(false));
		assertThat(size.getSize(), is(17));
	}

	@Test
	void whenNullIsGivenThenUndefinedIsTrue() {
		final IndentationSize size = IndentationSize.parse(null);
		assertThat(size.isUndefined(), is(true));
		assertThat(size.isTab(), is(false));
	}

	@Test
	void whenTabIsGivenThenTabIsTrue() {
		final IndentationSize size = IndentationSize.parse(TAB_IDENTIFIER);
		assertThat(size.isTab(), is(true));
		assertThat(size.isUndefined(), is(false));
	}

	@Test
	void whenCreatingObjectsWithEqualValuesThenHashCodeAndEqualsReturnTheSame() {
		final IndentationSize sizeA = IndentationSize.parse(TEST_SIZE);
		IndentationSize sizeB = IndentationSize.parse(TEST_SIZE);

		assertThat(sizeA, is(not(sameInstance(sizeB))));
		assertThat(sizeA, is(equalTo(sizeB)));
		assertThat(sizeA.hashCode(), is(equalTo(sizeB.hashCode())));
	}

	@Test
	void whenCreatingObjectsWithDifferentValuesThenHashCodeAndEqualsReturnDifferenz() {
		final IndentationSize sizeA = IndentationSize.parse(TEST_SIZE);
		IndentationSize sizeB = IndentationSize.parse(TAB_IDENTIFIER);

		assertThat(sizeA, is(not(equalTo(sizeB))));
		assertThat(sizeA.hashCode(), is(not(equalTo(sizeB.hashCode()))));
	}

	@Test
	void whenInvalidValueIsGivenThenIllegalArgumentExceptionIsThrown() {
		final Executable parse = () -> IndentationSize.parse(ILLEGAL_VALUE);
		final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, parse);
		assertThat(exception.getMessage(), is(equalTo("`nonsense` is not a valid value for `%s`. Valid values are (a positive number, tab).")));
	}
}
