package com.example.terceirotrabalho.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.terceirotrabalho.model.Parent;
import com.example.terceirotrabalho.model.Student;
import com.example.terceirotrabalho.model.Teacher;
import com.example.terceirotrabalho.model.Tutor;
import com.example.terceirotrabalho.model.User;

@Database(entities = {User.class, Student.class, Teacher.class, Parent.class, Tutor.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public static AppDatabase INSTANCE;

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "MyStudyMateDatabase").allowMainThreadQueries().build();
        }

        return INSTANCE;
    }
}
