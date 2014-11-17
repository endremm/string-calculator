package no.bring.calculator;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class StringCalculatorTest {

    private StringCalculator calculator;

    @Before
    public void setUp() throws Exception {
        calculator = new StringCalculator();
    }

    @Test
    public void add_should_default_to_0() throws Exception {
        assertThat(calculator.add(""), is(0));
    }

    @Test
    public void add_should_return_value_for_one_number() throws Exception {
        assertThat(calculator.add("1"), is(1));
    }

    @Test
    public void add_should_summarizes_two_numbers() throws Exception {
        assertThat(calculator.add("1,2"), is(3));
    }

    @Test
    public void add_should_summarizes_several_numbers() throws Exception {
        assertThat(calculator.add("1,2,3,4"), is(10));
    }

    @Test
    public void add_should_handle_comma_and_newline_as_separators() throws Exception {
        assertThat(calculator.add("1\n2,3"), is(6));
    }

    @Test
    public void add_should_support_delimiter_change() throws Exception {
        assertThat(calculator.add("//;\n1;2"), is(3));
    }

    @Test(expected = IllegalArgumentException.class)
    public void add_does_not_support_negatives() throws Exception {
        calculator.add("1,-2");
    }

    @Test
    public void add_returns_negatives_in_exception() throws Exception {
        try {
            calculator.add("-3,1,-2");
            fail();
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), containsString("-3,-2"));
        }
    }

    @Test
    public void add_should_ignore_numbers_bigger_than_thousand() throws Exception {
        assertThat(calculator.add("2,1001"), is(2));
    }

    @Test
    public void add_should_support_multiple_character_delimiters() throws Exception {
        assertThat(calculator.add("//[***]\n1***2***3"), is(6));
    }

    @Test
    public void add_should_support_multiple_delimiters() throws Exception {
        assertThat(calculator.add("//[**][%]\n1**2%3"), is(6));
    }

}