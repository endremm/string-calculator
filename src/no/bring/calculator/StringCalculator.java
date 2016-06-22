package no.bring.calculator;

import java.util.Arrays;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.joining;

public class StringCalculator {

    private String delimiter = ",|\\n";

    public int add(String expression) {
        if (expression.isEmpty()) {
            return 0;
        }

        if (containsDelimiter(expression)) {
            String[] numberParts = expression.split("\n");
            delimiter = parseDelimiter(numberParts[0]);
            return summarize(numberParts[1].split(delimiter));
        }

        return summarize(expression.split(delimiter));
    }

    private boolean containsDelimiter(String numbers) {
        return numbers.startsWith("//");
    }

    private String parseDelimiter(String delimiters) {
        return Arrays.stream(delimiters.substring(2).split("\\]\\["))
            .map(d -> Pattern.quote(d.replace("[", "").replace("]", "")))
            .collect(joining("|"));
    }

    private int summarize(String[] numbers) {
        assertNoNegatives(numbers);
        return Arrays.stream(numbers)
            .mapToInt(Integer::valueOf)
            .filter(n -> n < 1000)
            .sum();
    }

    private void assertNoNegatives(String[] numbers) {
        String negatives = Arrays.stream(numbers)
            .filter(n -> n.startsWith("-"))
            .collect(joining(","));
        if (!negatives.isEmpty()) {
            throw new IllegalArgumentException("Negatives not allowed: " + negatives);
        }
    }

}
