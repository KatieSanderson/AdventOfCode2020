package day07;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.*;

public class Day07 {

    public static void main(String[] args) throws IOException {
        List<String> inputLines = Files.readAllLines(FileSystems.getDefault().getPath("src", "main", "java", "day07", "input.txt"));

        Map<String, Map<String, Integer>> bags = new HashMap<>();

        for (String inputLine : inputLines) {
            String[] parentBagSplit = inputLine.split(" bag");
            String parentBagColor = parentBagSplit[0];

            String[] containSplit = inputLine.split("contain ");
            Map<String, Integer> children = new HashMap<>();
            if (containSplit[1].equalsIgnoreCase("no other bags.")) {
                // no action - empty children map
            } else {
                String[] childNumSplit = containSplit[1].split(" bags?,? ?\\.?");
                for (String childNum : childNumSplit) {
                    List<String> split = Arrays.asList(childNum.split(" "));
                    if (split.isEmpty()) {
                        System.out.println("Split error");
                    }
                    int quantity = Integer.parseInt(split.get(0));
                    String name = String.join(" ", split.subList(1, split.size()));
                    children.put(name, quantity);
                }
            }

            bags.put(parentBagColor, children);
        }

        Set<String> bagsWhichContainShinyGold = findShinyGoldContainingBags(bags);
        System.out.println("Part 1 solution: " + bagsWhichContainShinyGold.size());

        int numBags = findBagsInShinyGold(bags, "shiny gold");
        System.out.println("Part 2 solution: " + numBags);
    }

    private static int findBagsInShinyGold(Map<String, Map<String, Integer>> bags, String bagColor) {
        Map<String, Integer> childrenInBagCache = new HashMap<>();
        return findNumBags(bags, childrenInBagCache, bagColor);
    }

    private static int findNumBags(Map<String, Map<String, Integer>> bags, Map<String, Integer> childrenInBagCache, String bagColor) {
        if (bags.get(bagColor).isEmpty()) {
            return 0;
        }
        if (childrenInBagCache.containsKey(bagColor)) {
            return childrenInBagCache.get(bagColor);
        }
        int sum = 0;
        for (Map.Entry<String, Integer> childBag : bags.get(bagColor).entrySet()) {
            String childColor = childBag.getKey();
            int numBagsInChild = findNumBags(bags, childrenInBagCache, childColor);
            childrenInBagCache.put(childColor, numBagsInChild);
            sum += (1 + numBagsInChild) * bags.get(bagColor).get(childColor);
        }
        return sum;
    }

    private static Set<String> findShinyGoldContainingBags(Map<String, Map<String, Integer>> bags) {
        Set<String> bagsWhichContainShinyGoldCache = new HashSet<>();
        for (Map.Entry<String, Map<String, Integer>> entry : bags.entrySet()) {
            if (containsShinyGold(bags, bagsWhichContainShinyGoldCache, entry.getKey(), entry.getValue())) {
                bagsWhichContainShinyGoldCache.add(entry.getKey());
            }
        }
        return bagsWhichContainShinyGoldCache;
    }

    private static boolean containsShinyGold(Map<String, Map<String, Integer>> bags, Set<String> bagsWhichContainShinyGoldCache, String bagColor, Map<String, Integer> bagChildren) {
        if (bagChildren.isEmpty()) {
            return false;
        }
        if (bagsWhichContainShinyGoldCache.contains(bagColor)) {
            return true;
        }
        if (bagChildren.containsKey("shiny gold")) {
            bagsWhichContainShinyGoldCache.add(bagColor);
            return true;
        }
        for (Map.Entry<String, Integer> childBag : bagChildren.entrySet()) {
            if (containsShinyGold(bags, bagsWhichContainShinyGoldCache, childBag.getKey(), bags.get(childBag.getKey()))) {
                return true;
            }
        }
        return false;
    }
}
