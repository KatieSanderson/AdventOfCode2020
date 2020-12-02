package day02;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.List;

public class Day02 {

    public static void main(String[] args) throws IOException {
        List<String> inputLines = Files.readAllLines(FileSystems.getDefault().getPath("src", "main", "java", "day02", "input.txt"));

        int validCount = 0;

        for (String input : inputLines) {
            String[] inputSplitSpace = input.split(" ");
            String[] range = inputSplitSpace[0].split("-");
            int lowRange = Integer.parseInt(range[0]);
            int highRange = Integer.parseInt(range[1]);
            char policyKey = inputSplitSpace[1].charAt(0);
            String password = inputSplitSpace[2];

            int policyKeyCount = 0;
            for (char c : password.toCharArray()) {
                policyKeyCount += c == policyKey ? 1 : 0;
            }

            validCount += (policyKeyCount <= highRange && policyKeyCount >= lowRange ? 1 : 0);
        }

        System.out.println(validCount);
    }
}
