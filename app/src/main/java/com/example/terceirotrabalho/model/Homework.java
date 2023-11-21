package com.example.terceirotrabalho.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Date;
import java.sql.Time;

@Entity(tableName = "homework")
public class Homework {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "homework_id")
    public int homeworkId;

    @ColumnInfo(name = "homework_name")
    public String homeworkName;

    @ColumnInfo(name = "homework_description")
    public String homeworkDescription;

    @ColumnInfo(name = "homework_date")
    public Long homeworkDate;  // Alterado para Long

    @ColumnInfo(name = "homework_time")
    public Long homeworkTime;  // Alterado para Long

    @ColumnInfo(name = "fk_student_id")
    public int fkStudentId;

    @ColumnInfo(name = "fk_author_id")
    public int fkAuthorId;

    public Homework(String homeworkName, String homeworkDescription, Long homeworkDate,
                    Long homeworkTime, int fkStudentId, int fkAuthorId) {
        this.homeworkName = homeworkName;
        this.homeworkDescription = homeworkDescription;
        this.homeworkDate = homeworkDate;
        this.homeworkTime = homeworkTime;
        this.fkStudentId = fkStudentId;
        this.fkAuthorId = fkAuthorId;
    }

    public void setId(long result) {
        this.homeworkId = (int) result;
    }

    public int getHomeworkId() {
        return homeworkId;
    }

    public String getHomeworkName() {
        return homeworkName;
    }

    public String getHomeworkDescription() {
        return homeworkDescription;
    }

    public long getHomeworkDate() {
        return homeworkDate;
    }

    public long getHomeworkTime() {
        return homeworkTime;
    }

}
