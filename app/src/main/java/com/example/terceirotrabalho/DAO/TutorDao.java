package com.example.terceirotrabalho.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.terceirotrabalho.model.Tutor;

import java.util.List;

@Dao
public interface TutorDao {
    @Insert
    void insertTutor(Tutor tutor);

    @Update
    void updateTutor(Tutor tutor);

    @Delete
    void deleteTutor(Tutor tutor);

    @Query("SELECT * FROM tutor INNER JOIN user ON :tutorId = user.user_id")
    Tutor getTutorById(int tutorId);

    @Query("SELECT * FROM tutor")
    List<Tutor> getAllTutors();
}
