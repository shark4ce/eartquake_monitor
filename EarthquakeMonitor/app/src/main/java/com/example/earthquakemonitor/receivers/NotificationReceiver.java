package com.example.earthquakemonitor.receivers;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.earthquakemonitor.constants.Constants;

public class NotificationReceiver extends BroadcastReceiver {

    private final String TAG = this.getClass().getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "notifier started");
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = intent.getParcelableExtra(Constants.NOTIFICATION);
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel notificationChannel = new NotificationChannel(Constants.NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
        assert notificationManager != null;
        notificationManager.createNotificationChannel(notificationChannel);

        int id = intent.getIntExtra(Constants.NOTIFICATION_ID, 0);
        notificationManager.notify(id, notification);

    }
}
