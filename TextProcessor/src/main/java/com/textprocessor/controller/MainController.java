package com.textprocessor.controller;

import com.textprocessor.model.DataManager;
import com.textprocessor.model.RegexProcessor;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;
import java.util.Optional;


public class MainController {
    @FXML
    public Button addDataButton;

    @FXML
    private Button patternMatchButton;

    @FXML
    private TextField regexTextField;

    @FXML
    private Button searchButton;

    @FXML
    private Button replaceButton;

    @FXML
    private TextField replaceTextField;

    @FXML
    private TextArea outputTextArea;

    @FXML
    private TextField dataInputField;

    @FXML
    private ListView<String> dataListView;

    private final RegexProcessor regexProcessor = new RegexProcessor();
    private final DataManager dataManager = new DataManager(); // Initialize DataManager

    @FXML
    public void onSearchButtonClick() {
        try {
            String regexPattern = regexTextField.getText();
            List<String> matches = regexProcessor.searchInCollection(dataManager.getAllData(), regexPattern);
            outputTextArea.setText(String.join("\n", matches));

        } catch (Exception e) {
            outputTextArea.setText("Error: " + e.getMessage());
        }
    }

    @FXML
    public void onPatternMatchButtonClick() {
        try {
            String regexPattern = regexTextField.getText();
            List<String> matches = regexProcessor.matchInCollection(dataManager.getAllData(), regexPattern);
            if (!matches.isEmpty()) {
                outputTextArea.setText("Match found:\n" + String.join("\n", matches));
            } else {
                outputTextArea.setText("No match found.");
            }
        } catch (Exception e) {
            outputTextArea.setText("Error: " + e.getMessage());
        }
    }

    /*@FXML
    public void onPatternMatchButtonClick() {
        try {
            String regexPattern = regexTextField.getText();

            List<String> matches = regexProcessor.searchInCollection(dataManager.getAllData(), regexPattern);
            if (!matches.isEmpty()) {
                outputTextArea.setText("Match found:\n" + String.join(" ", matches));

            } else {
                outputTextArea.setText("No match found.");
            }
        } catch (Exception e) {
            outputTextArea.setText("Error: " + e.getMessage());
        }
    }*/


    @FXML
    public void onReplaceButtonClick() {
        try {
            String regexPattern = regexTextField.getText();
            String replacement = replaceTextField.getText();

            List<String> modifiedData = regexProcessor.searchAndReplaceInCollection(dataManager.getAllData(), regexPattern, replacement);
            dataManager.setDataList(modifiedData); // Update DataManager's data

            // Update ListView with the modified data
             dataListView.getItems().clear();
             dataListView.getItems().addAll(modifiedData);

            outputTextArea.setText("Data replaced successfully.");
        } catch (Exception e) {
            outputTextArea.setText("Error: " + e.getMessage());
        }
    }


    @FXML
    public void onAddDataButtonClick() {
        String newData = dataInputField.getText();
        if (!newData.isEmpty()) {
            dataManager.addData(newData);
            dataListView.getItems().add(newData);
            dataInputField.clear();
        }
    }

    @FXML
    public void onUpdateDataButtonClick() {
        int selectedIndex = dataListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex != -1) {
            String newData = dataInputField.getText();
            if (!newData.isEmpty()) {
                dataManager.updateData(selectedIndex, newData);
                dataListView.getItems().set(selectedIndex, newData);
                dataInputField.clear();
            }
        }
    }

    @FXML
    public void onDeleteDataButtonClick() {
        int selectedIndex = dataListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex != -1) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Data");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete this data?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                dataManager.deleteData(selectedIndex);
                dataListView.getItems().remove(selectedIndex);
            }
        }
    }

    @FXML
    public void initialize() {
        dataListView.getItems().addAll(dataManager.getAllData());
    }
}
