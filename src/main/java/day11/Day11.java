package day11;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static day11.Day11.SeatType.*;

public class Day11 {

    public static void main(String[] args) throws IOException {
        List<List<SeatType>> previousGrid = Files.lines(FileSystems.getDefault().getPath("src", "main", "java", "day11", "input.txt"))
                .map(i -> {
                    List<SeatType> seats = new ArrayList<>();
                    for (char s : i.toCharArray()) {
                        seats.add(getSeatType(s));
                    }
                    return seats;
                }).collect(Collectors.toList());

        boolean areGridsEqual = false;
        while (!areGridsEqual) {
            List<List<SeatType>> nextGrid = new ArrayList<>();
            for (int i = 0; i < previousGrid.size(); i++) {
                nextGrid.add(new ArrayList<>());
            }
            for (int row = 0; row < previousGrid.size(); row++) {
                for (int column = 0; column < previousGrid.get(0).size(); column++) {
                    SeatType seatType = previousGrid.get(row).get(column);
                    switch (seatType) {
                        case FLOOR:
                            nextGrid.get(row).add(FLOOR);
                            continue;
                        case OCCUPIED:
                            if (findOccupiedP2(row, column, previousGrid) >= 5) {
                                nextGrid.get(row).add(OPEN);
                            } else {
                                nextGrid.get(row).add(OCCUPIED);
                            }
                            break;
                        case OPEN:
                            if (findOccupiedP2(row, column, previousGrid) == 0) {
                                nextGrid.get(row).add(OCCUPIED);
                            } else {
                                nextGrid.get(row).add(OPEN);
                            }
                            break;
                        default:
                            throw new IllegalStateException("Shouldn't be here...");
                    }

                }
            }
            areGridsEqual = areGridsEqual(previousGrid, nextGrid);
            previousGrid = nextGrid;
        }

        int occupiedSeats = 0;
        for (int i = 0; i < previousGrid.size(); i++) {
            for (int j = 0; j < previousGrid.get(0).size(); j++) {
                if (isOccupied(i, j, previousGrid)) {
                    occupiedSeats++;
                }
            }
        }
        System.out.println("Part 2: " + occupiedSeats);
    }

    private static boolean areGridsEqual(List<List<SeatType>> previousGrid, List<List<SeatType>> grid) {
        for (int i = 0; i < grid.size(); i++) {
            for (int j = 0; j < grid.get(0).size(); j++) {
                if (previousGrid.get(i).get(j) != grid.get(i).get(j)) {
                    return false;
                }
            }
        }
        return true;
    }


    private static int findOccupiedP2(int row, int column, List<List<SeatType>> grid) {
        int occupiedAdjCount = 0;

        // upper left check
        int rowCheck = row - 1;
        int colCheck = column - 1;
        while (coordsInGrid(rowCheck, colCheck, grid) && grid.get(rowCheck).get(colCheck) == FLOOR) {
            rowCheck--;
            colCheck--;
        }
        if (coordsInGrid(rowCheck, colCheck, grid) && grid.get(rowCheck).get(colCheck) == OCCUPIED) {
            occupiedAdjCount++;
        }

        // up check
        rowCheck = row - 1;
        colCheck = column;
        while (coordsInGrid(rowCheck, colCheck, grid) && grid.get(rowCheck).get(colCheck) == FLOOR) {
            rowCheck--;
        }
        if (coordsInGrid(rowCheck, colCheck, grid) && grid.get(rowCheck).get(colCheck) == OCCUPIED) {
            occupiedAdjCount++;
        }

        // upper right check
        rowCheck = row - 1;
        colCheck = column + 1;
        while (coordsInGrid(rowCheck, colCheck, grid) && grid.get(rowCheck).get(colCheck) == FLOOR) {
            rowCheck--;
            colCheck++;
        }
        if (coordsInGrid(rowCheck, colCheck, grid) && grid.get(rowCheck).get(colCheck) == OCCUPIED) {
            occupiedAdjCount++;
        }



        // right check
        rowCheck = row;
        colCheck = column + 1;
        while (coordsInGrid(rowCheck, colCheck, grid) && grid.get(rowCheck).get(colCheck) == FLOOR) {
            colCheck++;
        }
        if (coordsInGrid(rowCheck, colCheck, grid) && grid.get(rowCheck).get(colCheck) == OCCUPIED) {
            occupiedAdjCount++;
        }

        // lower right check
        rowCheck = row + 1;
        colCheck = column + 1;
        while (coordsInGrid(rowCheck, colCheck, grid) && grid.get(rowCheck).get(colCheck) == FLOOR) {
            rowCheck++;
            colCheck++;
        }
        if (coordsInGrid(rowCheck, colCheck, grid) && grid.get(rowCheck).get(colCheck) == OCCUPIED) {
            occupiedAdjCount++;
        }



        // low check
        rowCheck = row + 1;
        colCheck = column;
        while (coordsInGrid(rowCheck, colCheck, grid) && grid.get(rowCheck).get(colCheck) == FLOOR) {
            rowCheck++;
        }
        if (coordsInGrid(rowCheck, colCheck, grid) && grid.get(rowCheck).get(colCheck) == OCCUPIED) {
            occupiedAdjCount++;
        }



        // lower left check
        rowCheck = row + 1;
        colCheck = column - 1;
        while (coordsInGrid(rowCheck, colCheck, grid) && grid.get(rowCheck).get(colCheck) == FLOOR) {
            rowCheck++;
            colCheck--;
        }
        if (coordsInGrid(rowCheck, colCheck, grid) && grid.get(rowCheck).get(colCheck) == OCCUPIED) {
            occupiedAdjCount++;
        }



        // left check
        rowCheck = row;
        colCheck = column - 1;
        while (coordsInGrid(rowCheck, colCheck, grid) && grid.get(rowCheck).get(colCheck) == FLOOR) {
            colCheck--;
        }
        if (coordsInGrid(rowCheck, colCheck, grid) && grid.get(rowCheck).get(colCheck) == OCCUPIED) {
            occupiedAdjCount++;
        }


        return occupiedAdjCount;
    }

    private static boolean coordsInGrid(int row, int column, List<List<SeatType>> grid) {
        return !(row < 0 || column < 0 || row >= grid.size() || column >= grid.get(0).size());
    }

    private static int findOccupiedAdjacent(int row, int column, List<List<SeatType>> grid) {
        int occupiedAdjCount = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <=1; j++) {
                int rowAdj = row + i;
                int colAdj = column + j;

                if (rowAdj == row && colAdj == column) {
                    // don't count the seat in adjacent calc
                    continue;
                }

                if (isOccupied(rowAdj, colAdj, grid)) {
                    occupiedAdjCount++;
                }
            }
        }
        return occupiedAdjCount;
    }

    private static boolean isOccupied(int row, int column, List<List<SeatType>> grid) {
        if (!coordsInGrid(row, column, grid)) {
            return false;
        }
        return grid.get(row).get(column) == OCCUPIED;
    }

    public static SeatType getSeatType(char seat) {
        switch (seat) {
            case '.' :
                return FLOOR;
            case '#' :
                return OCCUPIED;
            case 'L':
                return OPEN;
            default :
                throw new IllegalArgumentException("Unknown seat type [" + seat + "]");
        }
    }

    public enum SeatType {
        FLOOR,
        OCCUPIED,
        OPEN
    }
}
