package com.example.terceirotrabalho.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.terceirotrabalho.model.User;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    public long insertUser(User user);

    @Update
    public void updateUser(User user);

    @Delete
    public void deleteUser(User user);

    @Query("SELECT * FROM user WHERE user_id = :userId")
    public User getUserById(int userId);

    @Query("SELECT * FROM user WHERE user_email = :userEmail")
    public User getUserByEmail(String userEmail);

    @Query("SELECT * FROM user")
    public List<User> getAllUsers();
}

