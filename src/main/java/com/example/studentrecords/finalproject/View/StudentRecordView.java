/**
 * This class represents the view of the Student Records application, which displays a list of students and their records.
 * It extends the JavaFX Tab class to enable it to be added as a tab to a TabPane.
 * @author Jamie Roche
 * @version 1.0
 * @since 2023-05-02
 */

/*
Author: Jamie Roche
Purpose: OOP - Final Project
StudentModel ID: R00151829
Class: SDH2-B
*/

package com.example.studentrecords.finalproject.View;

import com.example.studentrecords.finalproject.Controller.StudentRecordsController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class StudentRecordView extends Tab {

    /**
     * A reference to the StudentRecordsController that controls this view.
     */
    private final StudentRecordsController Controller;

    /**
     * The list view that displays the names of the students.
     */
    public final ListView<String> listView;

    /**
     * The text area that displays the records of the selected student.
     */
    public final TextArea textArea;

    /**
     * Creates a new instance of the StudentRecordView class and initializes the UI controls.
     * Also sets up the buttons and the list view with student names.
     */
    public StudentRecordView() {

        // Initialize UI controls
        textArea = new TextArea();
        listView = new ListView<>();
        listView.setPrefSize(200, 150);
        listView.getStyleClass().add("listview");

        Controller = new StudentRecordsController(this);

        setText("Student Records");
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(10, 100, 10, 100));
        vbox.setSpacing(10);

        // Set up the list view with student names
        Controller.updateListView();

        // Create buttons
        Button listButton = new Button("List");
        Button loadButton = new Button("Load");
        Button saveButton = new Button("Save");
        Button clearButton = new Button("Clear");
        Button exitButton = new Button("Exit");
        Button sortButton = new Button("Sort");

        // Create the HBox for the buttons
        HBox buttonBox = new HBox();
        buttonBox.setPadding(new Insets(10, 10, 10, 10));
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(10);
        buttonBox.getChildren().addAll(listButton, loadButton, sortButton, saveButton, clearButton, exitButton);

        // Add labels and text fields to grid pane
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Create the main border pane
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(10, 10, 10, 10));
        borderPane.setLeft(listView);
        borderPane.setCenter(textArea);
        borderPane.setBottom(gridPane);
        BorderPane.setAlignment(listView, Pos.CENTER);
        BorderPane.setAlignment(textArea, Pos.CENTER);

        // Create a new HBox for the buttons and set it to the right of the border pane
        HBox buttonBox2 = new HBox();
        buttonBox2.getChildren().addAll(listButton, clearButton, loadButton, sortButton, saveButton, exitButton);
        buttonBox2.setPadding(new Insets(10, 10, 10, 10));
        buttonBox2.setAlignment(Pos.CENTER);
        buttonBox2.setSpacing(10);
        borderPane.setTop(buttonBox2);

        vbox.getChildren().add(borderPane);
        setContent(vbox);

        // Set up button actions
        listButton.setOnAction(event -> Controller.listButton());

        loadButton.setOnAction(event -> Controller.loadButton());

        sortButton.setOnAction(event -> Controller.sortButton());

        saveButton.setOnAction(event -> Controller.saveButton());

        clearButton.setOnAction(event -> Controller.clearButton());

        exitButton.setOnAction(event -> Controller.exitButton());
    }

}
