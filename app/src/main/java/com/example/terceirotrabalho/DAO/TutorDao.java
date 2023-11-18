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
    public long insertTutor(Tutor tutor);

    @Update
    public void updateTutor(Tutor tutor);

    @Delete
    public void deleteTutor(Tutor tutor);

    @Query("SELECT * FROM tutor INNER JOIN user ON :tutorId = user.user_id")
    public Tutor getTutorById(int tutorId);

    @Query("SELECT * FROM tutor")
    public List<Tutor> getAllTutors();
}
