package com.example.terceirotrabalho.receiver;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.terceirotrabalho.R;
import com.example.terceirotrabalho.activities.CreateHomeworkActivity;


public class MyReceiver extends BroadcastReceiver {

    private Ringtone ringtone;
    private Handler handler = new Handler();

    private String homeworkName, homeworkDescription;

    @Override
    public void onReceive(Context context, Intent intent) {
        // Este método é chamado quando o BroadcastReceiver recebe um broadcast

        // Verifique se a ação recebida é a que você espera
        if ("ATIVIDADE_A_SER_REALIZADA".equals(intent.getAction())) {

            // Agora, vamos adicionar a lógica para tocar o alarme por 20 segundos
//            playAlarme(context);

            homeworkName = intent.getStringExtra("homeworkName");
            homeworkDescription = intent.getStringExtra("homeworkDescription");

            Log.d("Nome", "Resultado: " + homeworkName);

            setNotification(context, homeworkName, homeworkDescription);
            // Agendar o Handler para parar o alarme após 20 segundos
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Lógica para parar o alarme após 20 segundos
                    stopAlarme(context);
                }
            }, 20 * 1000); // 20 segundos
        }
    }

    private void playAlarme(Context context) {
        // Lógica para iniciar o alarme, por exemplo, tocar um som ou exibir uma notificação

        // Certifique-se de que o Ringtone não foi inicializado anteriormente
        if (ringtone == null) {
            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            ringtone = RingtoneManager.getRingtone(context, alarmSound);
//            setNotification(context);
            ringtone.play();
        }
    }

    private void stopAlarme(Context context) {
        // Lógica para parar o alarme, por exemplo, parar o som ou cancelar a notificação

        // Se o Ringtone está tocando, pare a reprodução
        if (ringtone != null && ringtone.isPlaying()) {
            ringtone.stop();
        }
    }

    private void setNotification(Context context, String homeworkName, String homeworkDescription) {
        // Crie um canal de notificação (necessário para Android 8.0 e superior)
        createNotificationChannel(context);

        // Crie uma notificação
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "canal_id")
                .setSmallIcon(R.drawable.notification_icon) // Substitua com o seu ícone de notificação
                .setContentTitle(homeworkName)
                .setContentText(homeworkDescription)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Exiba a notificação
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());
    }

    private void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Nome do Canal";
            String description = "Descrição do Canal";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("canal_id", name, importance);
            channel.setDescription(description);
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }
    }
}

