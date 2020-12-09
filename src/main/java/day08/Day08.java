package day08;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.List;

public class Day08 {

    public static void main(String[] args) throws IOException {
        List<String> inputLines = Files.readAllLines(FileSystems.getDefault().getPath("src", "main", "java", "day08", "input.txt"));

        int acc = 0;

        for (int i = 0; i < inputLines.size(); i++) {
            // setup
            if (inputLines.get(i).contains("acc")) {
                continue;
            } else if (inputLines.get(i).contains("nop")) {
                inputLines.set(i, inputLines.get(i).replace("nop", "jmp"));
            } else if (inputLines.get(i).contains("jmp")) {
                inputLines.set(i, inputLines.get(i).replace("jmp", "nop"));
            } else {
                System.out.println("Unknown command!");
            }

            // execute current instructions
            acc = 0;
            int currentInstruction = 0;
            boolean[] infiniteLoopCheck = new boolean[inputLines.size()];
            boolean terminatedCorrectly = false;

            while (!terminatedCorrectly && !infiniteLoopCheck[currentInstruction]) {
                infiniteLoopCheck[currentInstruction] = true;
                String[] split = inputLines.get(currentInstruction).split(" ");
                switch (split[0]) {
                    case "acc":
                        acc += Integer.parseInt(split[1]);
                        currentInstruction++;
                        break;
                    case "jmp":
                        currentInstruction += Integer.parseInt(split[1]);
                        break;
                    case "nop":
                        currentInstruction++;
                        break;
                    default:
                        System.out.println("Unknown command!");
                }

                if (currentInstruction == inputLines.size()) {
                    terminatedCorrectly = true;
                }
            }

            // close-out
            // revert nop <-> jmp change
            if (inputLines.get(i).contains("acc")) {
                // no change
            } else if (inputLines.get(i).contains("nop")) {
                inputLines.set(i, inputLines.get(i).replace("nop", "jmp"));
            } else if (inputLines.get(i).contains("jmp")) {
                inputLines.set(i, inputLines.get(i).replace("jmp", "nop"));
            } else {
                System.out.println("Unknown command!");
            }
            if (terminatedCorrectly) {
                break;
            }
        }
        System.out.println("Part 2 Solution: " + acc);
    }
}
