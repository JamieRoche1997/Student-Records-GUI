/**
 * This class represents the controller for the ModuleView.
 * It allows the user to add, remove, load, and save module records.
 * The controller communicates with the Model and View classes to perform these functions.
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

import com.example.studentrecords.finalproject.Model.ModuleModel;
import com.example.studentrecords.finalproject.Model.StudentModel;
import com.example.studentrecords.finalproject.Model.StudentRecordsModel;
import com.example.studentrecords.finalproject.View.ModuleView;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ModuleController {

    // Instance Variables
    private final ModuleView View;
    protected List<StudentModel> studentModels;
    private final StudentRecordsModel studentRecordsModel;

    /**
     * Constructor for ModuleController class.
     * Initializes the list of students, retrieves the student data from the database, and updates the view.
     * @param View The ModuleView object to which this controller is attached
     */
    public ModuleController(ModuleView View){
        this.studentModels = new ArrayList<>();
        this.View = View;
        this.studentRecordsModel = new StudentRecordsModel();
        this.studentModels = studentRecordsModel.getStudents();
        updateListView();
    }

    /**
     * Method to handle adding a module record.
     * Gets user input from the ModuleView and saves the record to the database.
     * Displays error messages if the input is invalid.
     */
    public void addButton(){
        List<String> selectedNames = View.listView.getSelectionModel().getSelectedItems();
        if (selectedNames.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a student from the list.", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        String module = View.moduleField.getText();
        int grade;
        String code = View.codeField.getText();
        String semester = View.semesterField.getText();
        try {
            grade = Integer.parseInt(View.gradeField.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a valid grade.", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        for (String selectedName : selectedNames) {
            for (StudentModel studentModel : studentModels) {
                if (selectedName.equals(studentModel.getName())) {
                    List<ModuleModel> moduleModels = studentModel.getModules();
                    boolean moduleFound = false;
                    String id = studentModel.getId();
                    for (ModuleModel mod : moduleModels) {
                        if (mod.name().equals(module)) {
                            moduleFound = true;
                            break;
                        }
                    }
                    if (!moduleFound) {
                        ModuleModel mod = new ModuleModel(module, grade, code, semester, id);
                        studentModel.addModule(mod);
                        studentRecordsModel.saveModules(studentModel);
                    }
                    break;
                }
            }
        }
        updateTextArea();
        View.moduleField.clear();
        View.gradeField.clear();
        View.codeField.clear();
        View.semesterField.clear();
    }

    /**
     * Method to handle removing a student and their module records.
     * Removes the selected student and their associated module records from the database.
     */
    public void removeButton() {
        String selected = View.listView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            String[] parts = selected.split(" \\(");
            String name = parts[0];
            for (StudentModel studentModel : studentModels) {
                if (studentModel.getName().equals(name)) {
                    // delete the student's modules from the database
                    studentRecordsModel.deleteModules(studentModel);
                    // update the text area
                    View.textArea.clear();
                    break;
                }
            }
        }
    }

    /**
     * Load student models and update list view.
     */
    public void loadButton() {
        studentRecordsModel.getStudents();
        studentModels = studentRecordsModel.getStudents();
        updateListView();
    }

    /**
     * Save module records and update list view.
     */
    public void saveButton(){
        String selected = View.listView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            String[] parts = selected.split(" \\(");
            String name = parts[0];
            for (StudentModel studentModel : studentModels) {
                if (studentModel.getName().equals(name)) {
                    studentRecordsModel.saveModules(studentModel);
                    updateListView();
                    break;
                }
            }
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText("Module records saved successfully.");
        alert.showAndWait();
    }

    /**
     * Confirm exit and exit the application.
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

    /**
     * Update list view with student names.
     */
    public void updateListView() {
        View.listView.getItems().clear();
        List<String> names = new ArrayList<>();
        for (StudentModel studentModel : studentModels) {
            names.add(studentModel.getName());
        }
        View.listView.getItems().setAll(names);
    }

    /**
     * Update text area with selected student models.
     */
    public void updateTextArea() {
        View.textArea.clear();
        List<String> selectedNames = View.listView.getSelectionModel().getSelectedItems();
        for (StudentModel studentModel : studentModels) {
            if (selectedNames.contains(studentModel.getName())) {
                View.textArea.appendText(studentModel + "\n");
            }
        }
    }
}
