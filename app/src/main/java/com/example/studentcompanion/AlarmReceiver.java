package com.example.studentcompanion;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("todo", "ToDo", NotificationManager.IMPORTANCE_HIGH);
            nm.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "todo")
                .setContentTitle("Task Reminder")
                .setContentText(intent.getStringExtra("task"))
                .setSmallIcon(android.R.drawable.ic_dialog_info);

        nm.notify(1, builder.build());
    }
}