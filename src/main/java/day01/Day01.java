package day01;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

public class Day01 {

    public static void main(String[] args) throws IOException {
        List<Integer> input = Files.lines(FileSystems.getDefault().getPath("src", "main", "java", "day01", "input.txt"))
                .map(Integer::parseInt).collect(Collectors.toList());
        for (int i = 0; i < input.size(); i++) {
            for (int j = i + 1; j < input.size(); j++) {
                for (int k = j + 1; k < input.size(); k++) {
                    int num1 = input.get(i);
                    int num2 = input.get(j);
                    int num3 = input.get(k);
                    if (num1 + num2 + num3 == 2020) {
                        System.out.println(num1 * num2 * num3);
                    }
                }
            }
        }
    }
}
