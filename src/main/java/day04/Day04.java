package day04;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.*;
import java.util.regex.Pattern;

public class Day04 {

    public static void main(String[] args) throws IOException {
        List<String> inputLines = Files.readAllLines(FileSystems.getDefault().getPath("src", "main", "java", "day04", "input.txt"));

        Set<String> requiredFields = new HashSet<>();
        requiredFields.add("byr");
        requiredFields.add("iyr");
        requiredFields.add("eyr");
        requiredFields.add("hgt");
        requiredFields.add("hcl");
        requiredFields.add("ecl");
        requiredFields.add("pid");

        int validPassports = 0;
        for (int i = 0; i < inputLines.size(); i++) {
            Map<String, Boolean> passportFields = new HashMap<>();
            while (i < inputLines.size() && !inputLines.get(i).equals("")) {
                String[] splitFields = inputLines.get(i).split(" ");
                for (String pair : splitFields) {
                    String[] keyValue = pair.split(":");
                    if (!isValidField(keyValue[0], keyValue[1])) {
                        System.out.println("invalid");
                    }
                    passportFields.put(keyValue[0], isValidField(keyValue[0], keyValue[1]));
                }

                i++;
            }

            validPassports += containsAllValidFields(requiredFields, passportFields) ? 1 : 0;

        }
        System.out.println("Part 2 solution: " + validPassports);
    }

    private static boolean isValidField(String field, String value) {
        System.out.println(field + " " + value);
        switch (field) {
            case "byr" :
                int birthYear = Integer.parseInt(value);
                return birthYear >= 1920 && birthYear <= 2002;
            case "iyr" :
                int issueYear = Integer.parseInt(value);
                return issueYear >= 2010 && issueYear <= 2020;
            case "eyr" :
                int expiryYear = Integer.parseInt(value);
                return expiryYear >= 2020 && expiryYear <= 2030;
            case "hgt" :
                if (value.length() <= 2) {
                    return false;
                }
                int heightNum = Integer.parseInt(value.substring(0, value.length() - 2));
                String heightUnit = value.substring(value.length() - 2);
                switch (heightUnit) {
                    case "in" :
                        return heightNum >= 59 && heightNum <= 76;
                    case "cm" :
                        return heightNum >= 150 && heightNum <= 193;
                    default :
                        System.out.println("Unknown height!");
                }
                break;
            case "hcl" :
                String patternHCL = "#[0-9a-f]{6}";
                return Pattern.matches(patternHCL, value);
            case "ecl" :
                List<String> allowedColors = Arrays.asList("amb", "blu", "brn", "gry", "grn", "hzl", "oth");
                return allowedColors.contains(value);
            case "pid" :
                String patternPID = "[0-9]{9}";
                return Pattern.matches(patternPID, value);
            case "cid" :
                return true;
            default :
                System.out.println("Unknown field!");
        }
        return false;
    }

    private static boolean containsAllValidFields(Set<String> requiredFields, Map<String, Boolean> passportFields) {
        for (String requiredField : requiredFields) {
            if (!passportFields.containsKey(requiredField) || !passportFields.get(requiredField)) {
                return false;
            }
        }
        return true;
    }
}
