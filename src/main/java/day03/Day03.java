package day03;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day03 {

    public static void main(String[] args) throws IOException {
        List<char[]> inputLines = Files.lines(FileSystems.getDefault().getPath("src", "main", "java", "day03", "input.txt"))
                .map(String::toCharArray).collect(Collectors.toList());

        int[][] slopeCombos = new int[5][2];
        slopeCombos[0] = new int[] {1, 1};
        slopeCombos[1] = new int[] {3, 1};
        slopeCombos[2] = new int[] {5, 1};
        slopeCombos[3] = new int[] {7, 1};
        slopeCombos[4] = new int[] {1, 2};

        long productTrees = 1;
        for (int[] slopeCombo : slopeCombos) {
            int rightJump = slopeCombo[0];
            int downJump = slopeCombo[1];

            int numTrees = 0;
            int horizontalPosition = 0;
            for (int i = 0; i < inputLines.size(); i += downJump) {
                char[] currentForest = inputLines.get(i);
                int horizontalPositionModified = horizontalPosition % currentForest.length;
                if (currentForest[horizontalPositionModified] == '#') {
                    numTrees++;
                }
                horizontalPosition += rightJump;
            }
            productTrees *= numTrees;
        }
        System.out.println(productTrees);
    }
}
