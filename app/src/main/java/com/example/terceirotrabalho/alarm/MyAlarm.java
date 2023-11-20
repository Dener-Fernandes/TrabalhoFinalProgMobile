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
import java.util.TimeZone;

public class MyAlarm {
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    public void setAlarm(Context context, int year, int month, int day, int hour, int minute) {
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        // Crie um Intent para o BroadcastReceiver
        Intent intent = new Intent(context, MyReceiver.class);
        intent.setAction("ATIVIDADE_A_SER_REALIZADA");

        // Crie um PendingIntent para o BroadcastReceiver
        pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);


        // Configure o Calendar com o tempo desejado
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month); // Meses começam do 0 (janeiro) até 11 (dezembro)
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }
}
