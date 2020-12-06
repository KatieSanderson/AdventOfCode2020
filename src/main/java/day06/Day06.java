package day06;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.*;

public class Day06 {

    public static void main(String[] args) throws IOException {
        List<String> inputLines = Files.readAllLines(FileSystems.getDefault().getPath("src", "main", "java", "day06", "input.txt"));

        int sumP1 = 0;
        int sumP2 = 0;
        int i = 0;
        while (i < inputLines.size()) {
            int numPeople = 0;
            Map<Character, Integer> answers = new HashMap<>();
            while (i < inputLines.size() && !inputLines.get(i).isEmpty()) {
                for (char c : inputLines.get(i).toCharArray()) {
                    if (!answers.containsKey(c)) {
                        answers.put(c, 0);
                    }
                    answers.put(c, answers.get(c) + 1);
                }
                i++;
                numPeople++;
            }

            int allAnsweredSum = 0;
            for (Map.Entry<Character, Integer> entry : answers.entrySet()) {
                if (entry.getValue() == numPeople) {
                    allAnsweredSum++;
                }
            }

            sumP1 += answers.size();
            sumP2 += allAnsweredSum;
            i++;
        }

        System.out.println("Part 1 Solution: " + sumP1);
        System.out.println("Part 2 Solution: " + sumP2);
    }
}
