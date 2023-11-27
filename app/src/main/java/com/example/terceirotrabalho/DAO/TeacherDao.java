package com.example.terceirotrabalho.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.terceirotrabalho.model.Student;
import com.example.terceirotrabalho.model.Teacher;

import java.util.List;

@Dao
public interface TeacherDao {
    @Insert
    public long insertTeacher(Teacher teacher);

    @Update
    public void updateTeacher(Teacher teacher);

    @Delete
    public void deleteTeacher(Teacher teacher);

    @Query("SELECT * FROM teacher INNER JOIN user ON :teacherId = user.user_id")
    public Teacher getTeacherById(int teacherId);

    @Query("SELECT * FROM teacher")
    public List<Teacher> getAllTeachers();

    @Query("SELECT teacher.teacher_id FROM teacher INNER JOIN user ON teacher.fk_user_teacher_id = user.user_id WHERE user.user_email = :teacherEmail")
    public int getTeacherIdByEmail(String teacherEmail);
}

