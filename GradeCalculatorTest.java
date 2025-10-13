package assignment3;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class GradeCalculatorTest {

    @Test
    public void testCalculateAverage_singleValue() {
        double[] scores = { 100.0 };
        assertEquals(100.0, GradeCalculator.calculateAverage(scores), 1e-9);
    }

    @Test
    public void testCalculateAverage_multipleValues() {
        double[] scores = { 80.0, 90.0, 70.0 };
        assertEquals(80.0, GradeCalculator.calculateAverage(scores), 1e-9);
    }

    @Test
    public void testCalculateLetterGrade_boundaries() {
        assertEquals("A", GradeCalculator.calculateLetterGrade(90.0));
        assertEquals("B", GradeCalculator.calculateLetterGrade(89.99));
        assertEquals("C", GradeCalculator.calculateLetterGrade(79.5));
        assertEquals("D", GradeCalculator.calculateLetterGrade(60.0));
        assertEquals("F", GradeCalculator.calculateLetterGrade(59.99));
    }

    @Test
    public void testInvalidScores_arrayNull() {
        assertThrows(IllegalArgumentException.class, () -> GradeCalculator.calculateAverage(null));
    }

    @Test
    public void testInvalidScores_emptyArray() {
        assertThrows(IllegalArgumentException.class, () -> GradeCalculator.calculateAverage(new double[] {}));
    }

    @Test
    public void testInvalidScoreValue_negative() {
        double[] scores = { 90.0, -5.0 };
        assertThrows(IllegalArgumentException.class, () -> GradeCalculator.calculateAverage(scores));
    }

    @Test
    public void testInvalidScoreValue_greaterThan100() {
        double[] scores = { 95.0, 101.0 };
        assertThrows(IllegalArgumentException.class, () -> GradeCalculator.calculateAverage(scores));
    }

    @Test
    public void testInvalidAverageForLetterGrade() {
        assertThrows(IllegalArgumentException.class, () -> GradeCalculator.calculateLetterGrade(-1.0));
        assertThrows(IllegalArgumentException.class, () -> GradeCalculator.calculateLetterGrade(150.0));
    }
}
