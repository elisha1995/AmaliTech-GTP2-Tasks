package com.librarytask;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.SQLException;

public class AddBookDialog extends Stage {
    public AddBookDialog() {
        setTitle("Add New Book");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        //grid.setAlignment(Pos.CENTER);
        grid.setHgap(8);
        grid.setVgap(10);

        Label titleLabel = new Label("Title:");
        GridPane.setConstraints(titleLabel, 0, 0);
        TextField titleInput = new TextField();
        GridPane.setConstraints(titleInput, 1, 0);

        Label authorLabel = new Label("Author:");
        GridPane.setConstraints(authorLabel, 0, 1);
        TextField authorInput = new TextField();
        GridPane.setConstraints(authorInput, 1, 1);

        Label isbnLabel = new Label("ISBN:");
        GridPane.setConstraints(isbnLabel, 0, 2);
        TextField isbnInput = new TextField();
        GridPane.setConstraints(isbnInput, 1, 2);

        Button addButton = new Button("Add Book");
        GridPane.setConstraints(addButton, 1, 3);

        addButton.setOnAction(e -> {
            String title = titleInput.getText();
            String author = authorInput.getText();
            String isbn = isbnInput.getText();
            try {
                if (!title.isEmpty() && !author.isEmpty() && !isbn.isEmpty()) {
                    LibraryService.addBook(title, author, isbn);
                    System.out.println("Book added successfully");
                    close();
                } else {
                    System.out.println("Failed to add book");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        grid.getChildren().addAll(titleLabel, titleInput, authorLabel, authorInput, isbnLabel, isbnInput, addButton);

        Scene scene = new Scene(grid, 300, 200);
        setScene(scene);
        initModality(Modality.APPLICATION_MODAL);
    }
}

