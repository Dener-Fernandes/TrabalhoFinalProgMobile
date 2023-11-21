package com.example.terceirotrabalho.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.time.LocalTime;

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
    public LocalDate homeworkDate;

    @ColumnInfo(name = "homework_time")
    public LocalTime homeworkTime;

    @ColumnInfo(name = "fk_student_id")
    public int fkStudentId;

    @ColumnInfo(name = "fk_author_id")
    public int fkAuthorId;

    public Homework(String homeworkName, String homeworkDescription, LocalDate homeworkDate,
                    LocalTime homeworkTime, int fkStudentId, int fkAuthorId) {
        this.homeworkName = homeworkName;
        this.homeworkDescription = homeworkDescription;
        this.homeworkTime = homeworkTime;
        this.homeworkDate = homeworkDate;
        this.fkStudentId = fkStudentId;
        this.fkAuthorId = fkAuthorId;
    }

    public int getHomeworkId() { return homeworkId; }

    public String getHomeworkName() { return homeworkName; }

    public String getHomeworkDescription() { return homeworkDescription; }

    public LocalDate getHomeworkDate() { return homeworkDate; }

    public LocalTime getHomeworkTime() { return homeworkTime; }
}
