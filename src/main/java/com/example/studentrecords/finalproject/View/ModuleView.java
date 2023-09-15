/**
 * The ModuleView class represents the view component of the Module feature in the student record system.
 * It extends the Tab class and contains the UI elements for adding, removing, loading, saving and exiting modules.
 * It also has a ListView to display the list of modules and a TextArea to display information about the selected module.
 * The class also has fields to store information about the module including the module name, grade, code and semester.
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

import com.example.studentrecords.finalproject.Controller.ModuleController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ModuleView extends Tab {

    /**
     * The ModuleController associated with this view.
     */
    private final ModuleController Controller;

    /**
     * The ListView to display the list of modules.
     */
    public final ListView<String> listView;

    /**
     * The TextArea to display information about the selected module.
     */
    public final TextArea textArea;

    /**
     * The TextField for the module name.
     */
    public TextField moduleField;

    /**
     * The TextField for the module grade.
     */
    public TextField gradeField;

    /**
     * The TextField for the module code.
     */
    public TextField codeField;

    /**
     * The TextField for the module semester.
     */
    public TextField semesterField;

    /**
     * Constructs a ModuleView object and initializes the UI components.
     */
    public ModuleView() {

        // Initialize the ListView
        listView = new ListView<>();
        listView.setPrefSize(200, 150);
        listView.getStyleClass().add("listview");

        // Initialize the ModuleController
        Controller = new ModuleController((this));

        // Set the Tab title
        setText("Modules");

        // Initialize the VBox for the UI components
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(10, 100, 10, 100));
        vbox.setSpacing(10);

        // Initialize UI controls
        textArea = new TextArea();
        moduleField = new TextField();
        gradeField = new TextField();

        // Set up the list view with student names
        Controller.updateListView();

        // Create labels for text fields
        Label moduleLabel = new Label("Module:");
        Label gradeLabel = new Label("Grade:");
        Label codeLabel = new Label("Code:");
        Label semesterLabel = new Label("Semester:");

        // Initialize text fields
        moduleField = new TextField();
        gradeField = new TextField();
        codeField = new TextField();
        semesterField = new TextField();

        // Create buttons
        Button addButton = new Button("Add");
        Button removeButton = new Button("Remove");
        Button loadButton = new Button("Load");
        Button saveButton = new Button("Save");
        Button exitButton = new Button("Exit");

        // Create the HBox for the buttons
        HBox buttonBox = new HBox();
        buttonBox.setPadding(new Insets(10, 10, 10, 10));
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(10);
        buttonBox.getChildren().addAll(addButton, removeButton, loadButton, saveButton, exitButton);

        // Add labels and text fields to grid pane
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.addRow(0, moduleLabel, moduleField);
        gridPane.addRow(1, gradeLabel, gradeField);
        gridPane.addRow(2, codeLabel, codeField);
        gridPane.addRow(3, semesterLabel, semesterField);

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
        buttonBox2.getChildren().addAll(addButton, removeButton, loadButton, saveButton, exitButton);
        buttonBox2.setPadding(new Insets(10, 10, 10, 10));
        buttonBox2.setAlignment(Pos.CENTER);
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
