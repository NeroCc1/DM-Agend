package com.example.dm_agenda;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // This method is called when the BroadcastReceiver receives an alarm

        // Get the task title from the intent
        String taskTitle = intent.getStringExtra("task_title");

        // Display a toast with the task title
        Toast.makeText(context, "¡Alarma! Tarea: " + taskTitle, Toast.LENGTH_SHORT).show();

        // Play a notification sound
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone ringtone = RingtoneManager.getRingtone(context, alarmSound);
        ringtone.play();
    }
}
