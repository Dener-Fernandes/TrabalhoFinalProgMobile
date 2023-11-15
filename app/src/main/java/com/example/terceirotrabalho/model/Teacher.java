package com.example.terceirotrabalho.model;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "teacher", foreignKeys = {
        @ForeignKey(entity = User.class,
                parentColumns = "user_id",
                childColumns = "fk_user_id",
                onDelete = CASCADE)
})
public class Teacher {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "teacher_id")
    public int teacherId;

    @ColumnInfo(name = "fk_user_id")
    public int fkUserId;

    public Teacher(int fkUserId) {
        this.fkUserId = fkUserId;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public int getFkUserId() {
        return fkUserId;
    }
}
