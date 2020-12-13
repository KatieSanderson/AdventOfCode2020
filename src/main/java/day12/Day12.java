package day12;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

public class Day12 {

    public static void main(String[] args) throws IOException {
        List<Instruction> input = Files.lines(FileSystems.getDefault().getPath("src", "main", "java", "day12", "input.txt"))
                .map(i -> {
                    char action = i.charAt(0);
                    int value = Integer.parseInt(i.substring(1));
                    return new Instruction(action, value);
                }).collect(Collectors.toList());

        int xPos = 0;
        int yPos = 0;
        // 0 = north, 1 = east, 2 = south, 3 = west
//        int direction = 1;

        int waypointRelX = 10;
        int waypointRelY = 1;

        for (Instruction instruction : input) {
            switch (instruction.action) {
                case 'N':
                    waypointRelY += instruction.value;
                    break;
                case 'E':
                    waypointRelX += instruction.value;
                    break;
                case 'S':
                    waypointRelY -= instruction.value;
                    break;
                case 'W':
                    waypointRelX -= instruction.value;
                    break;
                case 'L':
                    int timesToRotate90 = instruction.value / 90;
                    for (int i = 0; i < timesToRotate90; i++) {
                        int temp = waypointRelX;
                        waypointRelX = -waypointRelY;
                        waypointRelY = temp;
                    }
//                    int directionChange = instruction.value / 90;
//                    direction = (direction + 4 - directionChange) % 4;
                    break;
                case 'R':
                    timesToRotate90 = instruction.value / 90;
                    for (int i = 0; i < timesToRotate90; i++) {
                        int temp = waypointRelX;
                        waypointRelX = waypointRelY;
                        waypointRelY = -temp;
                    }
//                    directionChange = instruction.value / 90;
//                    direction = (direction + directionChange) % 4;
                    break;
                case 'F':
                    xPos += instruction.value * waypointRelX;
                    yPos += instruction.value * waypointRelY;
//                    switch (direction) {
//                        case 0:
//                            yPos += instruction.value;
//                            break;
//                        case 1:
//                            xPos += instruction.value;
//                            break;
//                        case 2:
//                            yPos -= instruction.value;
//                            break;
//                        case 3:
//                            xPos -= instruction.value;
//                            break;
//                        default:
//                            System.out.println("Unknown direction : [" + direction + "]");
//                    }
                    break;
                default:
                    System.out.println("Unknown instruction action: [" + instruction.action + "]");
            }

        }

        System.out.println("Part 2: " + (Math.abs(xPos) + Math.abs(yPos)));
    }

    public static class Instruction {

        char action;
        int value;

        public Instruction(char action, int value) {
            this.action = action;
            this.value = value;
        }
    }
}
