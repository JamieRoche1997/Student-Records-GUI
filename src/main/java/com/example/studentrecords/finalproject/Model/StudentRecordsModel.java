/**
 * The StudentRecordsModel class is responsible for managing the database connection and performing CRUD operations
 * on the students and modules tables. The class uses SQLite database for data storage and retrieval.
 * @author Jamie Roche
 * @version 1.0
 * @since 2023-05-02
 * <p>
 * Class Variables:
 * conn: Connection object representing the connection to the database
 * </p>
 * <p>
 * Constructor:
 * Initializes a connection to the SQLite database.
 * Creates students and modules tables if they don't exist.
 * </p>
 * <p>
 * Public Methods:
 * getStudents(): returns a list of StudentModel objects from the database.
 * saveStudent(StudentModel student): saves the given student in the database.
 * deleteStudent(StudentModel student): deletes the given student from the database.
 * getModules(): returns a list of ModuleModel objects from the database.
 * saveModule(ModuleModel module): saves the given module in the database.
 * saveModules(StudentModel student): saves all the modules for the given student in the database.
 * deleteModules(StudentModel student): deletes all the modules for the given student from the database.
 * </p>
 */

/*
Author: Jamie Roche
Purpose: OOP - Final Project
StudentModel ID: R00151829
Class: SDH2-B
*/

package com.example.studentrecords.finalproject.Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentRecordsModel {

    /**
     * Connection object representing the connection to the database.
     */
    public Connection conn;

    /**
     * Initializes a connection to the SQLite database.
     * Creates students and modules tables if they don't exist.
     */
    public StudentRecordsModel() {
        try {
            // Load the SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");

            // Connect to the database
            conn = DriverManager.getConnection("jdbc:sqlite:student_records.db");

            // Create the students table if it doesn't exist
            PreparedStatement stmt = conn.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS students (id TEXT PRIMARY KEY, name TEXT, dateOfBirth TEXT, currentSemester TEXT)");
            PreparedStatement stmt2 = conn.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS modules (name TEXT, grade INT, code TEXT, semester TEXT, student_id TEXT, FOREIGN KEY(student_id) REFERENCES students(id))");
            stmt.executeUpdate();
            stmt2.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns a list of StudentModel objects from the database.
     * @return List<StudentModel> A list of StudentModel objects from the database.
     */
    public List<StudentModel> getStudents() {
        List<StudentModel> students = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM students");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                String dateOfBirth = rs.getString("dateOfBirth");
                String currentSemester = rs.getString("currentSemester");
                StudentModel student = new StudentModel(name, id, dateOfBirth, currentSemester);
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    /**
     * Saves the given student to the database. Updates existing records with the same ID, or creates a new record
     * if no existing record with the same ID is found. Also saves all modules associated with the student.
     * @param student The student to save.
     */
    public void saveStudent(StudentModel student) {
        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT OR REPLACE INTO students (id, name, dateOfBirth, " +
                    "currentSemester) VALUES (?, ?, ?, ?)");
            stmt.setString(1, student.getId());
            stmt.setString(2, student.getName());
            stmt.setString(3, student.getDateOfBirth());
            stmt.setString(4, student.getCurrentSemester());
            stmt.executeUpdate();
            // Save the modules for the student
            saveModules(student);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes the given student from the database. Also deletes all modules associated with the student.
     * @param student The student to delete.
     */
    public void deleteStudent(StudentModel student) {
        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM students WHERE name = ?");
            stmt.setString(1, student.getName());
            stmt.executeUpdate();
            // Delete the modules for the student
            deleteModules(student);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns a list of all modules from the database, along with their associated students.
     * @return List<ModuleModel> A list of all modules from the database.
     */
    public List<ModuleModel> getModules() {
        List<ModuleModel> modules = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT modules.name, modules.grade, modules.code, modules.semester, students.id, students.name AS student_name, students.dateOfBirth, students.currentSemester " +
                    "FROM modules INNER JOIN students ON modules.student_id = students.id");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                int grade = rs.getInt("grade");
                String code = rs.getString("code");
                String semester = rs.getString("semester");
                String studentId = rs.getString("id");
                String studentName = rs.getString("student_name");
                String dateOfBirth = rs.getString("dateOfBirth");
                String currentSemester = rs.getString("currentSemester");

                StudentModel student = new StudentModel(studentName, studentId, dateOfBirth, currentSemester);
                ModuleModel module = new ModuleModel(name, grade, code, semester, studentId);
                modules.add(module);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return modules;
    }

    /**
     * Saves the given module to the database.
     * @param module The module to save.
     */
    public void saveModule(ModuleModel module) {
        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO modules (name, grade, code, semester, student_id) " +
                    "VALUES (?, ?, ?, ?, ?)");
            stmt.setString(1, module.getName());
            stmt.setInt(2, module.getGrade());
            stmt.setString(3, module.getCode());
            stmt.setString(4, module.getSemester());
            stmt.setString(5, module.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves all modules associated with the given student to the database.
     * @param student The student whose modules to save.
     */
    public void saveModules(StudentModel student) {
        for (ModuleModel module : student.getModules()) {
            saveModule(module);
        }
    }

    /**
     * Deletes all modules associated with the given student from the database.
     * @param student The student whose modules are to be deleted from the database.
     */
    public void deleteModules(StudentModel student) {
        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM modules WHERE student_id = ?");
            stmt.setString(1, student.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
