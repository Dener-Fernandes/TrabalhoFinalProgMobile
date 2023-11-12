package com.example.terceirotrabalho.database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;


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
