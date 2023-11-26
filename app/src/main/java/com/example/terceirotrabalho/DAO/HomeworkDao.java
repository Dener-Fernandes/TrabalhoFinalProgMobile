package com.example.terceirotrabalho.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.terceirotrabalho.model.Homework;

import java.util.List;

@Dao
public interface HomeworkDao {

    @Insert
    public long insertHomework(Homework homework);

    @Update
    public void updateHomework(Homework homework);

    @Query("SELECT * FROM homework WHERE homework_id = :homeworkId")
    public Homework getHomeworkById(int homeworkId);

    @Query("SELECT * FROM homework WHERE fk_student_id = :studentId")
    public List<Homework> getHomeworksForStudent(int studentId);

    @Query("SELECT * FROM homework WHERE fk_author_id = :authorId")
    public List<Homework> getHomeworksByAuthor(int authorId);

    @Delete
    public void deleteHomework(Homework homework);

    @Query("DELETE FROM homework")
    public void deleteAllHomeworks();
}
