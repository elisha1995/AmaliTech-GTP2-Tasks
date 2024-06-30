package com.textprocessor.controller;

import com.textprocessor.model.DataItem;
import com.textprocessor.model.DataManager;
import com.textprocessor.model.RegexProcessor;
import com.textprocessor.model.RegexProcessor.MatchResult;


import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

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
    private TextFlow outputTextFlow; // Changed from TextArea to TextFlow


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
            List<MatchResult> matches = regexProcessor.searchWithPositions(dataManager.getAllData(), regexPattern);

            outputTextFlow.getChildren().clear(); // Clear previous output
            Text title = new Text(matches.isEmpty() ? "No Search Found!\n\n" : "Search Found:\n\n");
            title.setFill(Color.BLUE);
            if (!matches.isEmpty()) {
                outputTextFlow.getChildren().add(title);
                for (MatchResult match : matches) {
                    highlightMatches(match);
                }
            } else {
                title.setFill(Color.RED);
                outputTextFlow.getChildren().add(title);
            }

        } catch (Exception e) {
            outputTextFlow.getChildren().clear();
            outputTextFlow.getChildren().add(new Text("Error: " + e.getMessage()));
        }
    }


    @FXML
    public void onPatternMatchButtonClick() {
        try {
            String regexPattern = regexTextField.getText();
            List<MatchResult> matches = regexProcessor.matchWithPositions(dataManager.getAllData(), regexPattern);

            outputTextFlow.getChildren().clear(); // Clear previous output
            Text title = new Text(matches.isEmpty() ? "No Match Found!\n\n" : "Match Found:\n\n");
            title.setFill(Color.rgb(230, 14, 212, 1));
            if (!matches.isEmpty()) {
                outputTextFlow.getChildren().add(title);
                for (MatchResult match : matches) {
                    highlightMatches(match);
                }
            } else {
                title.setFill(Color.RED);
                outputTextFlow.getChildren().add(title);
            }

        } catch (Exception e) {
            outputTextFlow.getChildren().clear();
            outputTextFlow.getChildren().add(new Text("Error: " + e.getMessage()));
        }
    }

    private void highlightMatches(MatchResult match) {
        String line = match.line();
        int start = 0;

        for (int[] position : match.positions()) {
            int matchStart = position[0];
            int matchEnd = position[1];

            if (start < matchStart) {
                outputTextFlow.getChildren().add(new Text(line.substring(start, matchStart)));
            }

            Text matchingText = new Text(line.substring(matchStart, matchEnd));
            matchingText.setFill(Color.rgb(43, 14, 230, 1));
            matchingText.setFont(Font.font(14));
            outputTextFlow.getChildren().add(matchingText);

            start = matchEnd;
        }

        if (start < line.length()) {
            outputTextFlow.getChildren().add(new Text(line.substring(start)));
        }

        outputTextFlow.getChildren().add(new Text("\n"));
    }

    @FXML
    public void onReplaceButtonClick() {
        try {
            String regexPattern = regexTextField.getText();
            String replacement = replaceTextField.getText();

            List<DataItem> modifiedData = regexProcessor.searchAndReplaceInCollection(dataManager.getAllData(), regexPattern, replacement);
            dataManager.setDataList(modifiedData); // Update DataManager's data

            // Update ListView with the modified data
            dataListView.getItems().clear();
            dataListView.getItems().addAll(modifiedData.stream().map(DataItem::getData).toList());

            outputTextFlow.getChildren().clear();
            outputTextFlow.getChildren().add(new Text("Data replaced successfully."));
        } catch (Exception e) {
            outputTextFlow.getChildren().clear();
            outputTextFlow.getChildren().add(new Text("Error: " + e.getMessage()));
        }
    }


    @FXML
    public void onAddDataButtonClick() {
        String newData = dataInputField.getText();
        if (!newData.isEmpty()) {
            DataItem newItem = new DataItem(dataManager.getAllData().size() + 1, newData);
            dataManager.addData(newItem);
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
                DataItem selectedItem = dataManager.getAllData().get(selectedIndex);
                DataItem newItem = new DataItem(selectedItem.getId(), newData);
                dataManager.updateData(selectedIndex, newItem);
                dataListView.getItems().set(selectedIndex, newData);  // Update the String representation
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
        dataListView.getItems().addAll(dataManager.getAllData().stream().map(DataItem::getData).toList());
    }
}
