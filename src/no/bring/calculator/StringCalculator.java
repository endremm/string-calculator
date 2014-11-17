package no.bring.calculator;

import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringCalculator {

    private String delimiter = ",|\\n";

    public int add(String numbers) {
        if (isBlank(numbers)) {
            return 0;
        }

        if (definesDelimiter(numbers)) {
            String[] numberParts = numbers.split("\n");
            delimiter = parseDelimiters(numberParts[0]);
            return addNumbers(numberParts[1].split(delimiter));
        }

        return addNumbers(numbers.split(delimiter));
    }

    private boolean isBlank(String numbers) {
        return numbers == null || numbers.isEmpty();
    }

    private boolean definesDelimiter(String numbers) {
        return numbers.startsWith("//");
    }

    private String parseDelimiters(String delimiters) {
        return Arrays.stream(delimiters.substring(2).split("\\]\\["))
            .map(this::parseDelimiter)
            .collect(Collectors.joining("|"));
    }

    private String parseDelimiter(String delimiter) {
        return Pattern.quote(delimiter.replace("[", "").replace("]", ""));
    }

    private int addNumbers(String[] numbers) {
        assertNoNegatives(numbers);
        return Arrays.stream(numbers)
            .mapToInt(Integer::valueOf)
            .filter(n -> n < 1000)
            .sum();
    }

    private void assertNoNegatives(String[] numbers) {
        String negatives = Arrays.stream(numbers)
            .filter(n -> n.startsWith("-"))
            .collect(Collectors.joining(","));
        if (!negatives.isEmpty()) {
            throw new IllegalArgumentException("Negatives not allowed: " + negatives);
        }
    }

}
