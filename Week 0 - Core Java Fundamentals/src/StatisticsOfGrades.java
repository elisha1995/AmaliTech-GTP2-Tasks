import java.util.Arrays;
import java.util.Scanner;

public class StatisticsOfGrades {
    public static void main(String[] args) {
        int[] scores = getScoresFromUser();

        int minScore = findMinScore(scores);
        int maxScore = findMaxScore(scores);
        double avgScore = calculateAverageScore(scores);

        displaySummaryStatistics(minScore, maxScore, avgScore);

        int[] gradeDistribution = calculateGradeDistribution(scores);
        displayGradeDistributionGraph(gradeDistribution);
    }

    // Function to get the scores from a user
    private static int[] getScoresFromUser() {
        // System.out.print("Enter the scores(integers) of the students separated by a space: ");
        Scanner scanner = new Scanner(System.in);

        // Ensuring user enters valid inputs
        while (true) {
            System.out.print("Enter the scores (in integers) of the students separated by a space: ");
            String inputScores = scanner.nextLine().trim(); // Trim for leading/trailing spaces

            // Handle empty input
            if (inputScores.isEmpty()) {
                System.out.println("Input is empty. Please enter at least one score. ");
                continue; // Ask for input again
            }

            try {
                return Arrays.stream(inputScores.split(" "))
                        .mapToInt(Integer::parseInt)
                        .toArray();
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter only integer scores separated by spaces.");
            }
        }
        // String inputScores = scanner.nextLine();

        // String[] scoreStrings = inputScores.split(" ");
        // return Arrays.stream(scoreStrings).mapToInt(Integer::parseInt).toArray();
    }

    // Function to find the minimum score in an array
    private static int findMinScore(int[] scores) {
        return Arrays.stream(scores).min().getAsInt();
    }

    // Function to find the maximum score in an array
    private static int findMaxScore(int[] scores) {
        return Arrays.stream(scores).max().getAsInt();
    }

    // Function to calculate the average score
    private static double calculateAverageScore(int[] scores) {
        return (double) Arrays.stream(scores).sum() / scores.length;
    }

    // Function to display the summary statistics
    private static void displaySummaryStatistics(int min, int max, double avg) {
        System.out.println("\nValues: \n");
        System.out.println("The maximum grade is " + max);
        System.out.println("The minimum grade is " + min);
        System.out.println("The average grade is " + avg);
    }

    // Function to calculate the grade distribution
    private static int[] calculateGradeDistribution(int[] scores) {
        int[] stats = new int[5]; // 0-20, 21-40, 41-60, 61-80, 81-100

        for (int grade : scores) {
            if (grade >= 0 && grade <= 20) stats[0]++;
            else if (grade >= 21 && grade <= 40) stats[1]++;
            else if (grade >= 41 && grade <= 60) stats[2]++;
            else if (grade >= 61 && grade <= 80) stats[3]++;
            else if (grade >= 81 && grade <= 100) stats[4]++;
        }

        return stats;
    }

    // Function to display the grade distribution bar graph
    private static void displayGradeDistributionGraph(int[] gradeDistribution) {
        int maxCount = Arrays.stream(gradeDistribution).max().getAsInt();

        System.out.println("\nGraph: \n");
        for (int row = maxCount; row > 0; row--) {
            System.out.print(row + "  > ");
            for (int count : gradeDistribution) {
                System.out.print(count >= row ? " #######   " : "           "); // Conditional for symbol/space
            }
            System.out.println();
        }

        System.out.println("   +----------+----------+----------+----------+-----------+");
        System.out.println("   I   0-20   I   21-40  I  41-60   I  61-80   I  81-100   I");
    }
}
