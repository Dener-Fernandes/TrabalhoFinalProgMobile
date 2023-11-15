package com.example.terceirotrabalho.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.terceirotrabalho.model.Teacher;

import java.util.List;

@Dao
public interface TeacherDao {
    @Insert
    void insertTeacher(Teacher teacher);

    @Update
    void updateTeacher(Teacher teacher);

    @Delete
    void deleteTeacher(Teacher teacher);

    @Query("SELECT * FROM teacher INNER JOIN user ON :teacherId = user.user_id")
    Teacher getTeacherById(int teacherId);

    @Query("SELECT * FROM teacher")
    List<Teacher> getAllTeachers();
}

