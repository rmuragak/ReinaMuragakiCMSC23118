package assignment3;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class GradeCalculatorApp {

    public static void main(String[] args) {
        System.out.println("Assignment 3, grade calculator");
        Scanner scanner = new Scanner(System.in);

        int numScores = 0;
        while (true) {
            System.out.print("number of scores (must be greater than 1): ");
            try {
                numScores = Integer.parseInt(scanner.nextLine().trim());
                if (numScores < 1) {
                    System.out.println("number must be at least 1, try again");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("invalid input, make sure it is positive");
            }
        }

        List<Double> scoresList = new ArrayList<>();
        for (int i = 0; i < numScores; ) {
            System.out.printf("Enter score #%d (0-100): ", i + 1);
            String line = scanner.nextLine().trim();
            try {
                double s = Double.parseDouble(line);
                if (!GradeCalculator.isValidScore(s)) {
                    System.out.println("score should be between 0 and 100, try again");
                    continue;
                }
                scoresList.add(s);
                i++; // valid score added then go on
            } catch (NumberFormatException e) {
                System.out.println("invalid number format, provide a number score");
            }
        }

        // turn into array and compute
        double[] scores = new double[scoresList.size()];
        for (int i = 0; i < scoresList.size(); i++) scores[i] = scoresList.get(i);

        try {
            double avg = GradeCalculator.calculateAverage(scores);
            String letter = GradeCalculator.calculateLetterGrade(avg);
            System.out.printf("%nResults:%nAverage score: %.2f%nLetter grade: %s%n", avg, letter);
        } catch (IllegalArgumentException e) {
            System.out.println("Error computing grades: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
