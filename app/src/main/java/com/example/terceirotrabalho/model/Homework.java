package com.example.terceirotrabalho.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

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

    @ColumnInfo(name = "finished")
    public boolean finished;

    public Homework(String homeworkName, String homeworkDescription, Long homeworkDate,
                    Long homeworkTime, int fkStudentId, int fkAuthorId, boolean finished) {
        this.homeworkName = homeworkName;
        this.homeworkDescription = homeworkDescription;
        this.homeworkDate = homeworkDate;
        this.homeworkTime = homeworkTime;
        this.fkStudentId = fkStudentId;
        this.fkAuthorId = fkAuthorId;
        this.finished = finished;
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

    public String formatDateAndTime() {
        // Convertendo os timestamps para milissegundos
        long dateMillis = getHomeworkDate() * 1000;
        long timeMillis = getHomeworkTime() * 1000;

        // Criando um objeto Calendar
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dateMillis);

        // Definindo a hora e os minutos
        calendar.set(Calendar.HOUR_OF_DAY, (int) (timeMillis / (60 * 60 * 1000)));
        calendar.set(Calendar.MINUTE, (int) ((timeMillis / (60 * 1000)) % 60));
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, 1);

        // Criando um formato de data e hora com o fuso horário de São Paulo
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - HH:mm", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));

        // Formatando a data e hora
        return sdf.format(calendar.getTime());
    }

}
