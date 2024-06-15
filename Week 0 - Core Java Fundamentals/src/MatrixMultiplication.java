import java.util.InputMismatchException;
import java.util.Scanner;

public class MatrixMultiplication {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int[][] matrixA = getMatrixInput(scanner, "A");
        int[][] matrixB = getMatrixInput(scanner, "B");

        // Perform matrix multiplication
        int[][] matrixC = multiplyMatrices(matrixA, matrixB);

        // Display the resulting matrix C
        System.out.println("\nMatrix C:");
        displayMatrix(matrixC);
    }

    // Function to get matrix input from the user
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

    // Function to multiply two matrices
    private static int[][] multiplyMatrices(int[][] a, int[][] b) {
        int rowsA = a.length;
        int colsA = a[0].length;
        int rowsB = b.length;
        int colsB = b[0].length;

        // Check if matrices can be multiplied, otherwise throw an error and exit the program
        if (colsA != rowsB) {
            // throw new IllegalArgumentException("Matrix multiplication not possible: Invalid dimensions.");
            System.out.println("Matrix multiplication not possible: Incompatible dimensions.");
            System.exit(1); // Terminate the program
        }

        int[][] result = new int[rowsA][colsB];
        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsB; j++) {
                for (int k = 0; k < colsA; k++) {
                    result[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        return result;
    }

    // Function to display a matrix
    private static void displayMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            System.out.print("| ");
            for (int element : row) {
                System.out.print(element + " ");
            }
            System.out.println("|"); // Move to the next line
        }
    }
}
