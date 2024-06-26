package com.librarytask;

import java.sql.*;
import java.util.LinkedList;
import java.util.Stack;

public class LibraryService {
    private LinkedList<Book> bookList;
    private LinkedList<Patron> patronList;
    private Stack<Transaction> transactions = new Stack<>();

    public static boolean borrowBook(int bookId, int patronId) throws SQLException {
        try (Connection connection = DatabaseUtil.getConnection()) {
            // Check if the book is available
            String checkAvailabilityQuery = "SELECT isavailable FROM book WHERE bookid = ?";
            try (PreparedStatement checkStmt = connection.prepareStatement(checkAvailabilityQuery)) {
                checkStmt.setInt(1, bookId);
                ResultSet resultSet = checkStmt.executeQuery();
                if (resultSet.next() && resultSet.getBoolean("isavailable")) {
                    // Update the book availability
                    String updateBookQuery = "UPDATE book SET isavailable = FALSE WHERE bookid = ?";
                    try (PreparedStatement updateStmt = connection.prepareStatement(updateBookQuery)) {
                        updateStmt.setInt(1, bookId);
                        updateStmt.executeUpdate();
                    }
                    // Insert transaction record
                    String insertTransactionQuery = "INSERT INTO transaction (bookid, patronid, date_borrowed) VALUES (?, ?, CURDATE())";
                    try (PreparedStatement insertStmt = connection.prepareStatement(insertTransactionQuery)) {
                        insertStmt.setInt(1, bookId);
                        insertStmt.setInt(2, patronId);
                        insertStmt.executeUpdate();
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean returnBook(int bookId, int patronId) throws SQLException {
        try (Connection connection = DatabaseUtil.getConnection()) {
            // Check if the book was borrowed by the patron
            String checkTransactionQuery = "SELECT * FROM transaction WHERE bookid = ? AND patronid = ? AND date_returned IS NULL";
            try (PreparedStatement checkStmt = connection.prepareStatement(checkTransactionQuery)) {
                checkStmt.setInt(1, bookId);
                checkStmt.setInt(2, patronId);
                ResultSet resultSet = checkStmt.executeQuery();
                if (resultSet.next()) {
                    // Update the transaction record
                    String updateTransactionQuery = "UPDATE transaction SET date_returned = CURDATE() WHERE bookid = ? AND patronid = ? AND date_returned IS NULL";
                    try (PreparedStatement updateStmt = connection.prepareStatement(updateTransactionQuery)) {
                        updateStmt.setInt(1, bookId);
                        updateStmt.setInt(2, patronId);
                        updateStmt.executeUpdate();
                    }
                    // Update the book availability
                    String updateBookQuery = "UPDATE book SET isavailable = TRUE WHERE bookid = ?";
                    try (PreparedStatement updateStmt = connection.prepareStatement(updateBookQuery)) {
                        updateStmt.setInt(1, bookId);
                        updateStmt.executeUpdate();
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public static ResultSet getAllBooks() throws SQLException {
        Connection connection = DatabaseUtil.getConnection();
        String query = "SELECT * FROM book";
        PreparedStatement statement = connection.prepareStatement(query);
        return statement.executeQuery();
    }

    public static ResultSet getUserTransactions(int patronId) throws SQLException {
        Connection connection = DatabaseUtil.getConnection();
        String query = "SELECT * FROM transaction WHERE patronid = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, patronId);
        return statement.executeQuery();
    }

    public static boolean addBook(String title, String author, String isbn) throws SQLException {
        try (Connection connection = DatabaseUtil.getConnection()) {
            String insertBookQuery = "INSERT INTO book (title, author, isbn, isavailable) VALUES (?, ?, ?, TRUE)";
            try (PreparedStatement stmt = connection.prepareStatement(insertBookQuery)) {
                stmt.setString(1, title);
                stmt.setString(2, author);
                stmt.setString(3, isbn);
                int rowsInserted = stmt.executeUpdate();
                return rowsInserted > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    public static boolean removeBook(int bookId) throws SQLException {
        try (Connection connection = DatabaseUtil.getConnection()) {
            String deleteBookQuery = "DELETE FROM book WHERE bookid = ?";
            try (PreparedStatement stmt = connection.prepareStatement(deleteBookQuery)) {
                stmt.setInt(1, bookId);
                stmt.executeUpdate();
                return true;
            }
        }
    }

    public static boolean removeUser(int patronId) throws SQLException {
        try (Connection connection = DatabaseUtil.getConnection()) {
            String deleteUserQuery = "DELETE FROM patron WHERE patronid = ?";
            try (PreparedStatement stmt = connection.prepareStatement(deleteUserQuery)) {
                stmt.setInt(1, patronId);
                stmt.executeUpdate();
                return true;
            }
        }
    }

    public static int getPatronId(String email) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseUtil.getConnection();
            pstmt = conn.prepareStatement("SELECT patronid FROM patron WHERE email = ?");
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("patronid");
            } else {
                throw new SQLException("User not found");
            }
        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    public static boolean validateUser(String email, String password) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseUtil.getConnection();
            pstmt = conn.prepareStatement("SELECT * FROM patron WHERE email = ? AND password = ?");
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            rs = pstmt.executeQuery();
            return rs.next();
        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    public static boolean isAdmin(String email) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseUtil.getConnection();
            pstmt = conn.prepareStatement("SELECT role FROM patron WHERE email = ?");
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return "admin".equals(rs.getString("role"));
            } else {
                throw new SQLException("User not found");
            }
        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    public static void addUser(String name, String email, String password, String role) throws SQLException {
        System.out.println("Connecting to database...");
        Connection conn = DatabaseUtil.getConnection();
        String sql = "INSERT INTO patron (name, email, password, role) VALUES (?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        System.out.println("Preparing statement...");
        pstmt.setString(1, name);
        pstmt.setString(2, email);
        pstmt.setString(3, password);
        pstmt.setString(4, role);
        System.out.println("Executing statement...");
        pstmt.executeUpdate();
    }

    public static ResultSet getAllUsers() throws SQLException {
        Connection conn = DatabaseUtil.getConnection();
        String sql = "SELECT * FROM patron";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        return pstmt.executeQuery();
    }

    public Stack<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Stack<Transaction> transactions) {
        this.transactions = transactions;
    }
}

