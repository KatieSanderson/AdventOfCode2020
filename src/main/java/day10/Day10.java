package day10;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day10 {

    public static void main(String[] args) throws IOException {
        List<Integer> inputLines = Files.lines(FileSystems.getDefault().getPath("src", "main", "java", "day10", "input.txt"))
                .map(Integer::parseInt).sorted().collect(Collectors.toList());

        int currentVoltage = 0;
        int[] voltageDifferences = new int[3];

        for (int input : inputLines) {
            int currentVoltageDifference = input - currentVoltage;
            if (currentVoltageDifference > 0 && currentVoltageDifference <= 3) {
                voltageDifferences[currentVoltageDifference - 1]++;
            } else {
                System.out.println("Unacceptable voltage difference!");
            }
            currentVoltage = input;
        }
        System.out.println("Part 1 solution: " + (voltageDifferences[0] * voltageDifferences[2]));

        Map<Integer, Long> cache = new HashMap<>();
        long numPaths = findNumPaths(1, inputLines, cache) + findNumPaths(2, inputLines, cache) + findNumPaths(3, inputLines, cache);

        System.out.println("Part 2 solution: " + numPaths);
    }

    private static long findNumPaths(int currentVoltage, List<Integer> inputLines, Map<Integer, Long> cache) {
        // cached already, just pull that number
        if (cache.containsKey(currentVoltage)) {
            return cache.get(currentVoltage);
        }
        // this number isn't our list, this isn't a valid path - don't cache for space
        if (!inputLines.contains(currentVoltage)) {
            return 0;
        }
        //  last value of the list is the only valid end of path
        long lastValue = inputLines.get(inputLines.size() - 1);
        if (currentVoltage > lastValue) {
            cache.put(currentVoltage, 0L);
            return 0;
        } else if (currentVoltage == lastValue) {
            cache.put(currentVoltage, 1L);
            return 1;
        }

        long paths = 0;
        int plusOne = currentVoltage + 1;
        if (findNumPaths(plusOne, inputLines, cache) > 0) {
            paths += cache.get(plusOne);
        }
        int plusTwo = currentVoltage + 2;
        if (findNumPaths(plusTwo, inputLines, cache) > 0) {
            paths += cache.get(plusTwo);
        }
        int plusThree = currentVoltage + 3;
        if (findNumPaths(plusThree, inputLines, cache) > 0) {
            paths += cache.get(plusThree);
        }
        cache.put(currentVoltage, paths);
        return paths;
    }
}
