/**

 The StudentRecordsGUI class is the main entry point for the Student Records application GUI.
 It extends the JavaFX Application class and provides a JavaFX-based graphical user interface
 for managing student records stored in an SQLite database.
 @author Jamie Roche
 @version 1.0
 @since 2023-05-02
 */

/*
Author: Jamie Roche
Purpose: OOP - Final Project
StudentModel ID: R00151829
Class: SDH2-B
*/

package com.example.studentrecords.finalproject;

import com.example.studentrecords.finalproject.View.ModuleView;
import com.example.studentrecords.finalproject.View.StudentRecordView;
import com.example.studentrecords.finalproject.View.StudentView;
import javafx.application.Application;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class StudentRecordsGUI extends Application {

    /** The database connection object used for interacting with the SQLite database. */
    private Connection conn;

    /**
     * The main method that launches the JavaFX application.
     * @param args The command-line arguments passed to the application
     */
    public static void main(String[] args) {
        launch(args);
    }


    /**
     * The start method is called when the application is launched and creates the main
     * graphical user interface for the Student Records application. It also sets up a
     * database connection to an SQLite database.
     * @param primaryStage The main stage of the application
     */
    @Override
    public void start(Stage primaryStage) {

        try {
            // Load the SQLite JDBC driver and establish a connection to the database
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:student_records.db");
            System.out.println("Database connection established.");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Create the main pane for the application
        BorderPane mainPane = new BorderPane();
        Group root = new Group();
        Scene scene = new Scene(root, 800, 400);

        // Load the application's CSS stylesheet
        scene.getStylesheets().add("StudentRecord.css");

        // Create a tabbed pane containing the views for managing students, modules, and student records
        TabPane tp = new TabPane();
        tp.getTabs().add(new StudentView());
        tp.getTabs().add(new ModuleView());
        tp.getTabs().add(new StudentRecordView());

        tp.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tp.setTabMinWidth(248);
        tp.setTabMinHeight(33);
        tp.setTabMaxWidth(267);
        tp.setTabMaxHeight(69);

        mainPane.setCenter(tp);

        mainPane.prefHeightProperty().bind(scene.heightProperty());
        mainPane.prefWidthProperty().bind(scene.widthProperty());

        root.getChildren().add(mainPane);
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setTitle("Student Records");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * The stop method is called when the application is closed and closes the database connection.
     */
    public void stop() {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
