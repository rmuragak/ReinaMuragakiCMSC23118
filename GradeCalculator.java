
public class GradeCalculator {

    /**
     * Calculates the average of the provided scores.
     * @param scores an array 
     * @return average as double
     * @throws if score has invalid values
     */
    public static double calculateAverage(double[] scores) {
        if (scores == null) {
            throw new IllegalArgumentException("scores array can't be null");
        }
        if (scores.length == 0) {
            throw new IllegalArgumentException("at least one score is required");
        }

        double sum = 0.0;
        for (double s : scores) {
            if (!isValidScore(s)) {
                throw new IllegalArgumentException("scores must be between 0 and 100, invalid value: " + s);
            }
            sum += s;
        }
        return sum / scores.length;
    }


    public static String calculateLetterGrade(double average) {
        if (!isValidScore(average)) {
            throw new IllegalArgumentException("average should be between 0 and 100");
        }
        if (average >= 90.0) return "A";
        if (average >= 80.0) return "B";
        if (average >= 70.0) return "C";
        if (average >= 60.0) return "D";
        return "F";
    }


    public static boolean isValidScore(double score) {
        return score >= 0.0 && score <= 100.0;
    }
}
