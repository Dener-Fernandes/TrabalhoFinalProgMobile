package com.example.terceirotrabalho.model;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "tutor", foreignKeys = {
        @ForeignKey(entity = User.class,
                parentColumns = "user_id",
                childColumns = "fk_user_id",
                onDelete = CASCADE)
})
public class Tutor {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "tutor_id")
    public int tutorId;

    @ColumnInfo(name = "fk_user_id")
    public int fkUserId;

    public Tutor(int fkUserId) {
        this.fkUserId = fkUserId;
    }

    public int getTutorId() {
        return tutorId;
    }

    public int getFkUserId() {
        return fkUserId;
    }
}
