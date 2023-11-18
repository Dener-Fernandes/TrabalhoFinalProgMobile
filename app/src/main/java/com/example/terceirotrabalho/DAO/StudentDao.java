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
    public long insertStudent(Student student);

    @Update
    public void updateStudent(Student student);

    @Delete
    public void deleteStudent(Student student);

    @Query("SELECT * FROM student INNER JOIN user ON :studentId = user.user_id")
    public Student getStudentById(int studentId);

    @Query("SELECT * FROM student")
    public List<Student> getAllStudents();
}

