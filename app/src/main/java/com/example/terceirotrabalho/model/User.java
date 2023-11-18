package com.example.terceirotrabalho.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user")
public class User {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    public int userId;

    @ColumnInfo(name = "user_name")
    public String userName;

    @ColumnInfo(name = "user_email")
    public String userEmail;

    @ColumnInfo(name = "user_password")
    public String userPassword;

    @ColumnInfo(name = "user_type")
    public String userType;

    @ColumnInfo(name = "is_user_logged")
    public boolean isUserLogged;

    public User(String userName, String userEmail, String userPassword, String userType) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userType = userType;
        this.isUserLogged = true;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public String getUserType() {
        return userType;
    }

    public boolean getIsUserLogged() {
        return isUserLogged;
    }
}
