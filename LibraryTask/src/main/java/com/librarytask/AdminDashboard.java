package com.librarytask;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDashboard {

    public void start(Stage stage) {
        VBox vbox = new VBox(10);
        ListView<String> bookListView = new ListView<>();
        ListView<String> userListView = new ListView<>();

        try {
            // Load books into the bookListView
            ResultSet books = LibraryService.getAllBooks();
            while (books.next()) {
                bookListView.getItems().add(
                    books.getInt("bookid") + " - " + books.getString("title") + " - " +
                            books.getString("author") + " - " + books.getString("isbn") + " - " +
                            (books.getBoolean("isavailable") ? "Available" : "Borrowed")
                );
            }

            // Load users into the userListView
            ResultSet users = LibraryService.getAllUsers();
            while (users.next()) {
                userListView.getItems().add(
                        users.getInt("patronid") + " - " + users.getString("name") + " - " + users.getString("email") + " - " + users.getString("role")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Button addBookButton = new Button("Add Book");
        Button removeBookButton = new Button("Remove Book");
        Button addUserButton = new Button("Add User");
        Button removeUserButton = new Button("Remove User");
        Button viewUsersButton = new Button("View All Users");

        vbox.getChildren().addAll(bookListView, addBookButton, removeBookButton, userListView, addUserButton, removeUserButton, viewUsersButton);

        // Handle Add Book button action
        addBookButton.setOnAction(e -> {
            AddBookDialog dialog = new AddBookDialog();
            dialog.showAndWait();
            // Refresh book list after adding a book
            refreshBookList(bookListView);
        });

        // Handle Remove Book button action
        removeBookButton.setOnAction(e -> {
            String selectedItem = bookListView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                int bookId = Integer.parseInt(selectedItem.split(" - ")[0]);
                try {
                    if (LibraryService.removeBook(bookId)) {
                        System.out.println("Book removed successfully");
                        refreshBookList(bookListView);
                    } else {
                        System.out.println("Failed to remove book");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Handle Add User button action
        addUserButton.setOnAction(e -> {
            System.out.println("Opening AddUserDialog...");
            AddUserDialog dialog = new AddUserDialog();
            dialog.showAndWait();
            // Refresh user list after adding a user
            refreshUserList(userListView);
        });

        // Handle Remove User button action
        removeUserButton.setOnAction(e -> {
            RemoveUserDialog dialog = new RemoveUserDialog();
            dialog.showAndWait();
            // Refresh user list after removing a user
            refreshUserList(userListView);
        });

        // Handle View All Users button action
        viewUsersButton.setOnAction(e -> {
            refreshUserList(userListView);
        });

        Scene scene = new Scene(vbox, 600, 400);
        stage.setScene(scene);
        stage.show();
    }

    private void refreshBookList(ListView<String> bookListView) {
        bookListView.getItems().clear();
        try {
            ResultSet books = LibraryService.getAllBooks();
            while (books.next()) {
                bookListView.getItems().add(
                        books.getInt("bookid") + " - " + books.getString("title") + " - " +
                                books.getString("author") + " - " + books.getString("isbn") + " - " +
                                (books.getBoolean("isavailable") ? "Available" : "Borrowed")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void refreshUserList(ListView<String> userListView) {
        userListView.getItems().clear();
        try {
            ResultSet users = LibraryService.getAllUsers();
            while (users.next()) {
                userListView.getItems().add(
                        users.getInt("patronid") + " - " + users.getString("name") + " - " + users.getString("email") + " - " + users.getString("role")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
