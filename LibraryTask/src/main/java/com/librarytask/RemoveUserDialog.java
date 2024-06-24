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

public class RemoveUserDialog extends Stage {
    public RemoveUserDialog() {
        setTitle("Remove User");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);

        Label userIdLabel = new Label("User ID:");
        grid.add(userIdLabel, 0, 0);

        TextField userIdTextField = new TextField();
        grid.add(userIdTextField, 1, 0);

        Button removeButton = new Button("Remove User");
        grid.add(removeButton, 1, 1);

        removeButton.setOnAction(e -> {
            int userId = Integer.parseInt(userIdTextField.getText());
            try {
                if (LibraryService.removeUser(userId)) {
                    System.out.println("User removed successfully");
                    close();
                } else {
                    System.out.println("Failed to remove user");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        Scene scene = new Scene(grid, 300, 150);
        setScene(scene);
        initModality(Modality.APPLICATION_MODAL);
    }
}
