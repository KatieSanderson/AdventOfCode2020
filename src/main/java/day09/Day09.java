package day09;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day09 {

    public static void main(String[] args) throws IOException {
        List<Long> inputLines = Files.lines(FileSystems.getDefault().getPath("src", "main", "java", "day09", "input.txt"))
                .map(Long::parseLong).collect(Collectors.toList());

        long[] recentNumbers = new long[26];

        int currentLine = 0;
        while (currentLine < recentNumbers.length) {
            recentNumbers[currentLine] = inputLines.get(currentLine);
            currentLine++;
        }

        boolean isValidNumber = true;
        while (isValidNumber) {
            long nextNumber = inputLines.get(currentLine);
            if (containsTwoSum(nextNumber, recentNumbers)) {
                currentLine++;
                recentNumbers[currentLine % 26] = nextNumber;
            } else {
                isValidNumber = false;
            }
        }
        
        System.out.println("Part 1: " + inputLines.get(currentLine));
        
        long requiredSum = inputLines.get(currentLine);
        for (int setSize = 2; setSize < inputLines.size(); setSize++) {
            for (int startingIndex = 0; startingIndex < inputLines.size() - setSize; startingIndex++) {
                int currentSum = 0;
                for (int k = 0; k < setSize; k++) {
                    currentSum += inputLines.get(startingIndex + k);
                }
                if (currentSum == requiredSum) {
                    long min = inputLines.subList(startingIndex, startingIndex + setSize).stream().mapToLong(Long::longValue).min().getAsLong();
                    long max = inputLines.subList(startingIndex, startingIndex + setSize).stream().mapToLong(Long::longValue).max().getAsLong();

                    System.out.println("Part 2: " + (min + max));
                    return;
                }
            }
        }
    }

    private static boolean containsTwoSum(long currentNumber, long[] recentNumbers) {
        for (int i = 0; i < recentNumbers.length; i++) {
            for (int j = 0; j < recentNumbers.length && i != j; j++) {
                if (recentNumbers[i] + recentNumbers[j] == currentNumber) {
                    return true;
                }
            }
        }
        return false;
    }
}
