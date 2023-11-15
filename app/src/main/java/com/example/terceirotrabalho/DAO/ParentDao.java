package com.example.terceirotrabalho.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.terceirotrabalho.model.Parent;

import java.util.List;

@Dao
public interface ParentDao {
    @Insert
    void insertParent(Parent parent);

    @Update
    void updateParent(Parent parent);

    @Delete
    void deleteParent(Parent parent);

    @Query("SELECT * FROM parent INNER JOIN user ON :parentId = user.user_id")
    Parent getParentById(int parentId);

    @Query("SELECT * FROM parent")
    List<Parent> getAllParents();
}
