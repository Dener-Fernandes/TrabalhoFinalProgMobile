package com.example.terceirotrabalho.receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;


public class MyReceiver extends BroadcastReceiver {

    private Ringtone ringtone;
    private Handler handler = new Handler();

    @Override
    public void onReceive(Context context, Intent intent) {
        // Este método é chamado quando o BroadcastReceiver recebe um broadcast

        // Verifique se a ação recebida é a que você espera
        if ("ATIVIDADE_A_SER_REALIZADA".equals(intent.getAction())) {

            // Agora, vamos adicionar a lógica para tocar o alarme por 20 segundos
            playAlarme(context);

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
}

