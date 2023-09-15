/**
 * The StudentView class represents the UI for the Student Records application. It extends the Tab class from the
 * JavaFX library and contains a ListView of student names and various UI controls for adding, removing, loading,
 * and saving student information.
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

import com.example.studentrecords.finalproject.Controller.StudentController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class StudentView extends Tab {

    /**
     * The StudentController object responsible for handling user interactions with the UI controls in this view.
     */
    private final StudentController Controller;

    /**
     * The ListView used to display the names of students.
     */
    public final ListView<String> listView;

    /**
     * The TextField used to enter a student's name.
     */
    public final TextField nameField;

    /**
     * The TextField used to enter a student's ID.
     */
    public final TextField idField;

    /**
     * The TextField used to enter a student's date of birth.
     */
    public final TextField dobField;

    /**
     * The TextField used to enter a student's current semester.
     */
    public final TextField currentSemesterField;

    /**
     * Constructs a new StudentView object. Initializes the UI controls and sets up their actions.
     */
    public StudentView() {

        // Initialize the ListView
        listView = new ListView<>();
        listView.setPrefSize(200, 150);
        listView.getStyleClass().add("listview");

        // Create the StudentController object
        Controller = new StudentController(this);

        // Set the tab text
        setText("Students");

        // Create the main VBox and set its properties
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(10, 200, 10, 200));
        vbox.setSpacing(10);

        // Initialize the text fields
        nameField = new TextField();
        idField = new TextField();
        dobField = new TextField();
        currentSemesterField = new TextField();

        // Populate the ListView with the names of students
        Controller.updateListView();

        // Create labels for text fields
        Label nameLabel = new Label("Name:");
        Label idLabel = new Label("Student ID:");
        Label dobLabel = new Label("Date of Birth:");
        Label currentSemesterLabel = new Label("Current Semester:");

        // Create buttons
        Button addButton = new Button("Add");
        Button removeButton = new Button("Remove");
        Button loadButton = new Button("Load");
        Button saveButton = new Button("Save");
        Button exitButton = new Button("Exit");

        // Create the HBox for the buttons
        HBox buttonBox = new HBox();
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10, 10, 10, 10));
        buttonBox.setSpacing(10);
        buttonBox.getChildren().addAll(addButton, removeButton, loadButton, saveButton, exitButton);

        // Add labels and text fields to grid pane
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.addRow(0, nameLabel, nameField);
        gridPane.addRow(1, idLabel, idField);
        gridPane.addRow(2, dobLabel, dobField);
        gridPane.addRow(3, currentSemesterLabel, currentSemesterField);

        // Create the main border pane
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(10, 10, 10, 10));
        borderPane.setCenter(listView);
        borderPane.setBottom(gridPane);
        BorderPane.setAlignment(listView, Pos.CENTER);

        // Create a new HBox for the buttons and set it to the right of the border pane
        HBox buttonBox2 = new HBox();
        buttonBox2.getChildren().addAll(addButton, removeButton, loadButton, saveButton, exitButton);
        buttonBox2.setAlignment(Pos.CENTER);
        buttonBox2.setPadding(new Insets(10, 10, 10, 10));
        buttonBox2.setSpacing(10);
        borderPane.setTop(buttonBox2);

        vbox.getChildren().add(borderPane);
        setContent(vbox);

        // Set up button actions
        addButton.setOnAction(event -> Controller.addButton());

        removeButton.setOnAction(event -> Controller.removeButton());

        loadButton.setOnAction(event -> Controller.loadButton());

        saveButton.setOnAction(event -> Controller.saveButton());

        exitButton.setOnAction(event -> Controller.exitButton());
    }

}
