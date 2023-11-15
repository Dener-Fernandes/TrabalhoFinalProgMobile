package com.example.terceirotrabalho.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.terceirotrabalho.model.Student;

import java.util.List;

@Dao
public interface StudentDao {
    @Insert
    void insertStudent(Student student);

    @Update
    void updateStudent(Student student);

    @Delete
    void deleteStudent(Student student);

    @Query("SELECT * FROM student INNER JOIN user ON :studentId = user.user_id")
    Student getStudentById(int studentId);

    @Query("SELECT * FROM student")
    List<Student> getAllStudents();
}

