package com.example.terceirotrabalho.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.util.Log;

import com.example.terceirotrabalho.receiver.MyReceiver;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

public class MyAlarm {
    static AlarmManager alarmManager;
    static PendingIntent pendingIntent;
    public void setAlarm(Context context, int year, int month, int day, int hour, int minute, String
                         homeworkName, String homeworkDescription) {
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Random randomNumber = new Random();

        int requestCode = randomNumber.nextInt();

        // Crie um Intent para o BroadcastReceiver
        Intent intent = new Intent(context, MyReceiver.class);
        intent.putExtra("homeworkName", homeworkName);
        intent.putExtra("homeworkDescription", homeworkDescription);
        intent.setAction("ATIVIDADE_A_SER_REALIZADA");

        // Crie um PendingIntent para o BroadcastReceiver
        pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Configure o Calendar com o tempo desejado
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    public static void cancelAlarm() {
        alarmManager.cancel(pendingIntent);
    }
}
