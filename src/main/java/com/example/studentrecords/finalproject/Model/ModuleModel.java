/**
 * ModuleModel class represents a module of a student.
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

public record ModuleModel(String name, int grade, String code, String semester, String id) {

    // Constructors are not documented in Javadoc for record classes.

    /**
     * Returns a string representation of the ModuleModel object.
     *
     * @return a string representation of the ModuleModel object.
     */
    @Override
    public String toString() {
        return "- " + name + " - " + grade + "% - " + code + " - " + semester;
    } // Overrides the toString method to return a string representation of the ModuleModel object

    /**
     * Returns the name of the module.
     *
     * @return the name of the module.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the grade of the module.
     *
     * @return the grade of the module.
     */
    public int getGrade() {
        return grade;
    }

    /**
     * Returns the code of the module.
     *
     * @return the code of the module.
     */
    public String getCode() {
        return code;
    }

    /**
     * Returns the semester of the module.
     *
     * @return the semester of the module.
     */
    public String getSemester() {
        return semester;
    }

    /**
     * Returns the ID of the student which is the foreign key of the module table.
     *
     * @return the ID of the student which is the foreign key of the module table.
     */
    public String getId() {return id;}
}
