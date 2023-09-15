/**

 The StudentRecordsInterface is an interface that defines the methods required to manage student records.
 It includes methods for adding a new student, removing an existing student, and getting a list of all students.
 Classes that implement this interface will have to implement these methods.
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


import com.example.studentrecords.finalproject.Model.StudentModel;

import java.util.List;

// Interface definition
public interface StudentRecordsInterface {
    /**
     * Adds a new student to the student records system.
     *
     * @param studentModel The StudentModel object representing the new student to be added.
     */
    void addStudent(StudentModel studentModel);

    /**
     * Removes an existing student from the student records system.
     *
     * @param studentModel The StudentModel object representing the student to be removed.
     */
    void removeStudent(StudentModel studentModel);

    /**
     * Returns a list of all students in the student records system.
     *
     * @return A List of StudentModel objects representing all the students.
     */
    List<StudentModel> getStudents();

// Note: There are no method implementations in an interface, only method signatures.
// Classes that implement this interface will have to implement these methods.
}
