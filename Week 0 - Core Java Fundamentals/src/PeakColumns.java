import java.util.InputMismatchException;
import java.util.Scanner;

public class PeakColumns {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[][] matrix = getMatrixInput(scanner, "A");

        System.out.println("\nPeak-Column(s):");
        findAndDisplayPeakColumns(matrix);
    }

    // Function to display peak columns
    private static void findAndDisplayPeakColumns(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (isPeakColumn(matrix, i, j)) {
                    System.out.println("A(" + (i+1) + "," + (j+1) + ") = " + matrix[i][j]);
                }
            }
        }
    }

    // Function to check if an element is a peak-column
    private static boolean isPeakColumn(int[][] matrix, int row, int col) {
        int value = matrix[row][col];

        // Check if maximum in row
        for (int j = 0; j < matrix[0].length; j++) {
            if (matrix[row][j] > value) {
                return false; // Not max in row
            }
        }

        // Check if minimum in column
        for (int[] num : matrix) {
            if (num[col] < value) {
                return false; // Not min in column
            }
        }

        return true; // Peak-column found
    }

    // Function to get matrix input from the user with error handling
    public static int[][] getMatrixInput(Scanner scanner, String matrixName) {
        while (true) {
            System.out.print("Enter dimensions of matrix " + matrixName + " separated by space: ");
            try {
                int rows = scanner.nextInt();
                int cols = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                int[][] matrix = new int[rows][cols];
                System.out.println("Enter the elements of the matrix, row by row:");
                boolean validInput = true;
                for (int i = 0; i < rows; i++) {
                    String[] rowValues = scanner.nextLine().split(" ");

                    // Check if the number of elements in the row matches the expected column count
                    if (rowValues.length != cols) {
                        System.out.println("Error: Incorrect number of elements in row " + (i + 1) + ". Expected " + cols + " elements.");
                        validInput = false;
                        break; // Start over from getting the dimensions
                    }

                    for (int j = 0; j < cols; j++) {
                        // Check for non-numeric input
                        try {
                            matrix[i][j] = Integer.parseInt(rowValues[j]);
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input: Non-numeric value found in row " + (i + 1) + ".");
                            validInput = false;
                            break;  // Start over from getting the dimensions
                        }
                    }
                    if (!validInput) break;
                }
                if (validInput) return matrix;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Enter integer values separated by space. ");
                scanner.nextLine(); // Clear invalid input
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Enter integer values only. ");

            }
        }
    }
}
