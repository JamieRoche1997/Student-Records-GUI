/**
 * This class represents the controller in the MVC architecture for managing the student records application.
 * It handles the interactions between the view and model components of the application.
 * @author Jamie Roche
 * @version 1.0
 */

/*
Author: Jamie Roche
Purpose: OOP - Final Project
StudentModel ID: R00151829
Class: SDH2-B
*/

package com.example.studentrecords.finalproject.Controller;

import com.example.studentrecords.finalproject.Model.StudentModel;
import com.example.studentrecords.finalproject.Model.StudentRecordsModel;
import com.example.studentrecords.finalproject.View.StudentView;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class StudentController {

    // Instance variables
    private final StudentView View;
    public List<StudentModel> studentModels;
    private final StudentRecordsModel studentRecordsModel;

    /**
     * Constructor for the StudentController class.
     * @param View - The view component of the application
     */
    public StudentController(StudentView View){
        this.studentModels = new ArrayList<>();
        this.View = View;
        this.studentRecordsModel = new StudentRecordsModel();
        this.studentModels = studentRecordsModel.getStudents();
        updateListView();
    }

    /**
     * Adds a new student record to the list of student models.
     */
    public void addButton() {
        String name = View.nameField.getText();
        String id = View.idField.getText();
        String dob = View.dobField.getText();
        String currentSemester = View.currentSemesterField.getText();

        boolean studentFound = false;
        for (StudentModel studentModel : studentModels) {
            if (studentModel.getId().equals(id)) {
                studentFound = true;
                break;
            }
        }

        if (!studentFound) {
            StudentModel studentModel = new StudentModel(name, id, dob, currentSemester);
            studentModels.add(studentModel);
            studentRecordsModel.saveStudent(studentModel); // Fix: save the entire studentModels list
            updateListView();
            View.nameField.clear();
            View.idField.clear();
            View.dobField.clear();
            View.currentSemesterField.clear();
        }
    }

    /**
     * Removes a student record from the list of student models.
     */
    public void removeButton() {
        String selected = View.listView.getSelectionModel().getSelectedItem(); //get the selected item from the list view
        if (selected != null) {
            String[] parts = selected.split(" \\(");
            String name = parts[0];
            Iterator<StudentModel> iterator = studentModels.iterator();
            while (iterator.hasNext()) {
                StudentModel studentModel = iterator.next();
                if (studentModel.getName().equals(name)) {
                    iterator.remove();
                    studentRecordsModel.deleteStudent(studentModel); //save the changes to file
                    updateListView();
                    break;
                }
            }
        }
    }

    /**
     * Loads the list of student models from the file.
     */
    public void loadButton(){
        studentRecordsModel.getStudents(); //load data from file
        studentModels = studentRecordsModel.getStudents(); //update the list of studentModels
        updateListView(); //update listView with the new changes
    }

    /**
     * Saves a student record to the file.
     */
    public void saveButton(){
        String selected = View.listView.getSelectionModel().getSelectedItem(); //get the selected item from the list view
        if (selected != null) {
            String[] parts = selected.split(" \\(");
            String name = parts[0];
            for (StudentModel studentModel : studentModels) {
                if (studentModel.getName().equals(name)) {
                    studentRecordsModel.saveStudent(studentModel); //save the changes to file
                    updateListView();
                    break;
                }
            }
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText("Student records saved successfully.");
        alert.showAndWait();
    }

    /**
     This method is called when the "Exit" button is clicked. It displays a confirmation alert and exits the
     program if "OK" is clicked.
     */
    public void exitButton(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Are you sure you wish to exit?");
        alert.setContentText("Click OK to exit, or Cancel to continue.");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Platform.exit();
        }
    }

    public void updateListView() {

        View.listView.getItems().clear();  // Clear the existing list view items
        List<String> names = new ArrayList<>();
        for (StudentModel studentModel : studentModels) {
            names.add(studentModel.getName());
        }
        View.listView.getItems().setAll(names); // Add each student's name to the list view
    }

}
