/**
 * This class serves as the controller for the Student Records application.
 * It manages interactions between the view and the model, and contains methods
 * for handling button events.
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

package com.example.studentrecords.finalproject.Controller;

import com.example.studentrecords.finalproject.Model.ModuleModel;
import com.example.studentrecords.finalproject.Model.StudentModel;
import com.example.studentrecords.finalproject.Model.StudentRecordsModel;
import com.example.studentrecords.finalproject.View.StudentRecordView;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.*;

public class StudentRecordsController {

    private final StudentRecordView View;
    public List<StudentModel> studentModels;
    private final StudentRecordsModel studentRecordsModel;

    /**
     * This constructor initializes the StudentRecordsController object with a
     * StudentRecordView object. It also initializes the studentModels list
     * and the StudentRecordsModel object, and updates the list view.
     *
     * @param View The StudentRecordView object to be used by the controller.
     */
    public StudentRecordsController(StudentRecordView View) {
        this.studentModels = new ArrayList<>();
        this.View = View;
        this.studentRecordsModel = new StudentRecordsModel();
        this.studentModels = studentRecordsModel.getStudents();
        updateListView();
    }

    /**
     * This method is called when the "List" button is clicked. It retrieves
     * the selected item from the list view, and displays the student and module
     * details for that student in the text area.
     */
    public void listButton() {
        String selected = View.listView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            String[] parts = selected.split("\\(");
            String name = parts[0];
            StringBuilder sb = new StringBuilder();
            List<StudentModel> students = studentRecordsModel.getStudents();
            for (StudentModel student : students) {
                if (student.getName().equals(name)) {
                    sb.append("Name: ").append(student.getName()).append("\n")
                            .append("Student ID: ").append(student.getId()).append("\n")
                            .append("Date of Birth: ").append(student.getDateOfBirth()).append("\n")
                            .append("Current Semester: ").append(student.getCurrentSemester()).append("\n\n")
                            .append("Modules: ").append("\n");
                    List<ModuleModel> modules = studentRecordsModel.getModules();
                    for (ModuleModel module : modules) {
                        if (module.getId().equals(student.getId())) {
                            sb.append("Name: ").append(module.getName()).append("\n")
                                    .append("Grade: ").append(module.getGrade()).append("%\n")
                                    .append("Code: ").append(module.getCode()).append("\n")
                                    .append("Semester: ").append(module.getSemester()).append("\n\n");
                        }
                    }
                    break;
                }
            }
            View.textArea.setText(sb.toString());
        }
    }

    /**
     * This method is called when the "Load" button is clicked. It loads the data
     * from the file, updates the studentModels list, and updates the list view.
     */
    public void loadButton() {
        studentRecordsModel.getStudents();
        studentModels = studentRecordsModel.getStudents();
        updateListView();
    }

    /**
     This method is called when the "Sort" button is clicked. It displays an alert with three sorting options
     for modules and sorts them based on the selected option.
     If "Sort by Student Name" is selected, sorts the studentModels by name and updates the ListView.
     If "Sort by Module Name (Alphabetical)" is selected, sorts the selected students' modules by name and updates the TextArea.
     If "Sort by Grade (Highest First)" is selected, sorts the selected students' modules by grade (highest first) and updates the TextArea.
     */
    public void sortButton() {
        List<String> selectedNames = View.listView.getSelectionModel().getSelectedItems();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Sort Option");
        alert.setHeaderText("How would you like to sort the modules?");
        alert.setContentText("Choose a sorting option:");
        ButtonType studentButton = new ButtonType("Sort by Student Name");
        ButtonType alphabeticalButton = new ButtonType("Sort by Module Name (Alphabetical)");
        ButtonType gradeButton = new ButtonType("Sort by Grade (Highest First)");
        alert.getButtonTypes().setAll(studentButton, alphabeticalButton, gradeButton);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            List<StudentModel> selectedStudents = new ArrayList<>();
            for (StudentModel studentModel : studentModels) {
                if (selectedNames.contains(studentModel.getName())) {
                    selectedStudents.add(studentModel);
                }
            }
            if (result.get() == studentButton) {
                studentModels.sort(Comparator.comparing(StudentModel::getName));
                updateListView();
            }

            else {
                for (StudentModel studentModel : selectedStudents) {
                    List<ModuleModel> modules = studentModel.getModules();
                    if (result.get() == alphabeticalButton) {
                        modules.sort(Comparator.comparing(ModuleModel::name));
                    } else if (result.get() == gradeButton) {
                        modules.sort(Comparator.comparing(ModuleModel::grade).reversed());
                    }
                    View.textArea.clear();
                    for (ModuleModel moduleModel : modules) {
                        View.textArea.appendText(moduleModel + "\n");
                    }
                }
            }
        }
    }

    /**
     This method is called when the "Save" button is clicked. It saves changes to a student's record and
     modules to a file and displays an information alert upon success.
     */
    public void saveButton() {
        String selected = View.listView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            String[] parts = selected.split(" \\(");
            String name = parts[0];
            for (StudentModel studentModel : studentModels) {
                if (studentModel.getName().equals(name)) {
                    studentRecordsModel.saveStudent(studentModel);
                    studentRecordsModel.saveModules(studentModel);
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
     This method is called when the "Clear" button is clicked. it clears the text area.
     */
    public void clearButton() {
        View.textArea.clear();
    }

    /**
     This method is called when the "Exit" button is clicked. It displays a confirmation alert and exits the
     program if "OK" is clicked.
     */
    public void exitButton() {
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
     Clears the existing list view items and adds each student's name to the list view.
     */
    public void updateListView() {
        View.listView.getItems().clear();
        List<String> names = new ArrayList<>();
        for (StudentModel studentModel : studentModels) {
            names.add(studentModel.getName());
        }
        View.listView.getItems().setAll(names);
    }
}

