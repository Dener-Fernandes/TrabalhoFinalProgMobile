package com.example.terceirotrabalho.model;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "student", foreignKeys = {
        @ForeignKey(entity = User.class,
                    parentColumns = "user_id",
                    childColumns = "fk_user_student_id",
                    onDelete = CASCADE)
})
public class Student {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "student_id")
    public int studentId;

    @ColumnInfo(name = "fk_user_student_id")
    public int fkUserStudentId;

    public Student(int fkUserStudentId) {
        this.fkUserStudentId = fkUserStudentId;
    }

    public int getStudentId() {
        return studentId;
    }

    public int getFkUserId() {
        return fkUserStudentId;
    }
}
