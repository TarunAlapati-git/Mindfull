package com.example.mindfull;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class BroadcastReceiverClass extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        @SuppressLint("UnspecifiedImmutableFlag")
        PendingIntent p = PendingIntent.getActivity(context, 0, i, 0);

        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(2000);

        NotificationCompat.Builder b = new NotificationCompat.Builder(context, "Notify")
                .setSmallIcon(R.drawable.ic_alarm_light)
                .setContentTitle("Alarm")
                .setContentText("Alarm Reminder")
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(p);

        NotificationManagerCompat n = NotificationManagerCompat.from(context);
        n.notify(200, b.build());

        Uri s = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);

        Ringtone r = RingtoneManager.getRingtone(context, s);
        r.play();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                r.stop();
            }
        }, 30000);


    }
}