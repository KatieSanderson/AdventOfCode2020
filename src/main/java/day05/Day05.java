package day05;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.List;

public class Day05 {

    public static void main(String[] args) throws IOException {
        List<String> inputLines = Files.readAllLines(FileSystems.getDefault().getPath("src", "main", "java", "day05", "input.txt"));

        final int numRows = 128;
        final int numColumns = 8;

        int maxSeatID = 0;
        boolean[] seatsTaken = new boolean[numRows * numColumns];

        for (String input : inputLines) {
            input = input.replaceAll("L", "0");
            input = input.replaceAll("R", "1");
            input = input.replaceAll("F", "0");
            input = input.replaceAll("B", "1");

//            int row = findPosition(0, numRows, input.substring(0, 7));
//            int column = findPosition(0, numColumns, input.substring(7));
            int row = solveSmart(input.substring(0, 7));
            int column = solveSmart(input.substring(7));

            int id = row * 8 + column;
            maxSeatID = Math.max(maxSeatID, id);
            seatsTaken[id] = true;
        }
        System.out.println("Part 1 Solution: " + maxSeatID);

        boolean foundFirstTakenSeat = false;
        for (int i = numColumns; i < numColumns * numRows - numColumns; i++) {
            if (seatsTaken[i]) {
                foundFirstTakenSeat = true;
            }
            if (foundFirstTakenSeat && !seatsTaken[i]) {
                System.out.println("Part 2 Solution: " + i);
                break;
            }
        }

    }

    private static int solveSmart(String substring) {
        return Integer.parseInt(substring, 2);
    }

    private static int findPosition(int min, int max, String input) {
        if (min == max || input.isEmpty()) {
            return min;
        }
        int mid = (max - min) / 2 + min;
        if (input.charAt(0) == 'F') {
            return findPosition(min, mid, input.substring(1));
        } else if (input.charAt(0) == 'B') {
            return findPosition(mid, max, input.substring(1));
        }
        throw new IllegalStateException("Unknown character");
    }
}
