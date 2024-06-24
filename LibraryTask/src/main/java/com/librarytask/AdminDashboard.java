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

        try {
            ResultSet books = LibraryService.getAllBooks();
            while (books.next()) {
                bookListView.getItems().add(
                        books.getInt("bookid") + " - " + books.getString("author") + " - " + books.getString("isbn") + " - " + (books.getBoolean("isavailable") ? "Available" : "Borrowed")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Button addBookButton = new Button("Add Book");
        Button removeBookButton = new Button("Remove Book");
        Button removeUserButton = new Button("Remove User");

        vbox.getChildren().addAll(bookListView, addBookButton, removeBookButton, removeUserButton);

        // Handle button actions
        addBookButton.setOnAction(e -> {
            AddBookDialog dialog = new AddBookDialog();
            dialog.showAndWait();
            // Refresh book list after adding a book
            bookListView.getItems().clear();
            try {
                ResultSet books = LibraryService.getAllBooks();
                while (books.next()) {
                    bookListView.getItems().add(
                            books.getInt("bookid") + " - " + books.getString("author") + " - " + books.getString("isbn") + " - " + (books.getBoolean("isavailable") ? "Available" : "Borrowed")
                    );
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        removeBookButton.setOnAction(e -> {
            String selectedItem = bookListView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                int bookId = Integer.parseInt(selectedItem.split(" - ")[0]);
                try {
                    if (LibraryService.removeBook(bookId)) {
                        System.out.println("Book removed successfully");
                        bookListView.getItems().clear();
                        ResultSet books = LibraryService.getAllBooks();
                        while (books.next()) {
                            bookListView.getItems().add(
                                    books.getInt("bookid") + " - " + books.getString("author") + " - " + books.getString("isbn") + " - " + (books.getBoolean("isavailable") ? "Available" : "Borrowed")
                            );
                        }
                    } else {
                        System.out.println("Failed to remove book");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        removeUserButton.setOnAction(e -> {
            RemoveUserDialog dialog = new RemoveUserDialog();
            dialog.showAndWait();
        });

        Scene scene = new Scene(vbox, 400, 300);
        stage.setScene(scene);
        stage.show();
    }
}

