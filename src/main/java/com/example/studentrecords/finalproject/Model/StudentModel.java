/**
 * A class representing a student in a student records system.
 * <p>
 * A StudentModel object contains information about a student, including their name, ID number, date of birth,
 * current semester, and a list of module models representing the modules they are enrolled in.
 * </p>
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

package com.example.studentrecords.finalproject.Model;

import java.util.ArrayList;
import java.util.List;

public class StudentModel {

    /**
     * The String variable for a student's name
     */
    private final String name;

    /**
     * The String variable for a student's id
     */
    private final String id;

    /**
     * The String variable for a student's date of birth
     */
    private final String dateOfBirth;

    /**
     * The String variable for a student's current semester
     */
    private final String currentSemester;

    /**
     * A list of modules
     */
    private final List<ModuleModel> moduleModels;

    /**
     * Constructs a new StudentModel object with the given parameters.
     * @param name the name of the student
     * @param id the ID number of the student
     * @param dateOfBirth the date of birth of the student
     * @param currentSemester the current semester the student is enrolled in
     */
    public StudentModel(String name, String id, String dateOfBirth, String currentSemester) {
        this.name = name;
        this.id = id;
        this.dateOfBirth = dateOfBirth;
        this.currentSemester = currentSemester;
        this.moduleModels = new ArrayList<>();
    }

    /**
     * Returns the name of the student.
     * @return the name of the student
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the ID number of the student.
     * @return the ID number of the student
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the date of birth of the student.
     * @return the date of birth of the student
     */
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Returns the current semester the student is enrolled in.
     * @return the current semester the student is enrolled in
     */
    public String getCurrentSemester() {
        return currentSemester;
    }

    /**
     * Adds a moduleModel to the list of module models representing the modules the student is enrolled in.
     * @param moduleModel the moduleModel to be added
     */
    public void addModule(ModuleModel moduleModel) {
        moduleModels.add(moduleModel);
    }

    /**
     * Returns the list of module models representing the modules the student is enrolled in.
     * @return the list of module models representing the modules the student is enrolled in
     */
    public List<ModuleModel> getModules() {
        return moduleModels;
    }

    /**
     * Returns a string representation of a StudentModel object.
     * @return a string representation of a StudentModel object
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(name).append("\nStudent ID: ").append(id).append("\nDOB: ").append(dateOfBirth).
                append("\nCurrent Semester: ").append(currentSemester).append("\nModules:");
        for (ModuleModel moduleModel : moduleModels) {
            sb.append("\n  ").append(moduleModel);
        }
        return sb.toString();
    }
}
