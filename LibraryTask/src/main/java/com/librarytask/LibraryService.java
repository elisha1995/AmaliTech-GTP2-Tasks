package com.librarytask;

import java.sql.*;

public class LibraryService {
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

    public static boolean addBook(String author, String isbn) throws SQLException {
        try (Connection connection = DatabaseUtil.getConnection()) {
            String insertBookQuery = "INSERT INTO book (author, isbn, isavailable) VALUES (?, ?, TRUE)";
            try (PreparedStatement stmt = connection.prepareStatement(insertBookQuery)) {
                stmt.setString(1, author);
                stmt.setString(2, isbn);
                stmt.executeUpdate();
                return true;
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

}

