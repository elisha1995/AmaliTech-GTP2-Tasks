package com.librarytask;

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
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);

        Label authorLabel = new Label("Author:");
        grid.add(authorLabel, 0, 0);

        TextField authorTextField = new TextField();
        grid.add(authorTextField, 1, 0);

        Label isbnLabel = new Label("ISBN:");
        grid.add(isbnLabel, 0, 1);

        TextField isbnTextField = new TextField();
        grid.add(isbnTextField, 1, 1);

        Button addButton = new Button("Add Book");
        grid.add(addButton, 1, 2);

        addButton.setOnAction(e -> {
            String author = authorTextField.getText();
            String isbn = isbnTextField.getText();
            try {
                if (LibraryService.addBook(author, isbn)) {
                    System.out.println("Book added successfully");
                    close();
                } else {
                    System.out.println("Failed to add book");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        Scene scene = new Scene(grid, 300, 200);
        setScene(scene);
        initModality(Modality.APPLICATION_MODAL);
    }
}

