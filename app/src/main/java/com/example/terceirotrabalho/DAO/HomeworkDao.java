package com.example.terceirotrabalho.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.terceirotrabalho.model.Homework;

import java.util.List;

@Dao
public interface HomeworkDao {

    @Insert
    long insertHomework(Homework homework);

    @Query("SELECT * FROM homework WHERE homework_id = :homeworkId")
    Homework getHomeworkById(int homeworkId);

    @Query("SELECT * FROM homework WHERE fk_student_id = :studentId")
    List<Homework> getHomeworksForStudent(int studentId);

    @Query("SELECT * FROM homework WHERE fk_author_id = :authorId")
    List<Homework> getHomeworksByAuthor(int authorId);

    @Query("DELETE FROM homework WHERE homework_id = :homeworkId")
    void deleteHomeworkById(int homeworkId);

    @Query("DELETE FROM homework")
    void deleteAllHomeworks();
}
