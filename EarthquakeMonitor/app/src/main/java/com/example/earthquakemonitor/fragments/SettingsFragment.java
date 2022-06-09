package com.example.earthquakemonitor.fragments;


import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.NotificationCompat;
import androidx.preference.ListPreference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreferenceCompat;

import com.example.earthquakemonitor.receivers.NotificationReceiver;
import com.example.earthquakemonitor.R;
import com.example.earthquakemonitor.constants.Constants;

public class SettingsFragment extends PreferenceFragmentCompat {

    SharedPreferences sharedPref;
    SharedPreferences.Editor sharedPrefEditor;
    PreferenceManager preferenceManager;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);

        preferenceManager = getPreferenceManager();
        sharedPref = requireActivity().getSharedPreferences(Constants.SETTINGS_SHARED_PREF, Context.MODE_PRIVATE);
        sharedPrefEditor = sharedPref.edit();
    }

    @RequiresApi(api = Build.VERSION_CODES.S)
    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);

        handleDarkThemePref();
        handleNotificationsPref();
        handleFeedDataTimeInterval();

        return view;
    }

    private void handleDarkThemePref() {

        SwitchPreferenceCompat darkThemePref = preferenceManager.findPreference(Constants.DARK_THEME_PREF);
        assert darkThemePref != null;
        darkThemePref.setOnPreferenceChangeListener((preference, option) -> {
            boolean enableDarkTheme = (boolean) option;
            if (enableDarkTheme) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
            darkThemePref.setChecked(enableDarkTheme);
            sharedPrefEditor.putBoolean(Constants.DARK_THEME_PREF, enableDarkTheme);
            sharedPrefEditor.apply();

            return false;
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.S)
    private void handleNotificationsPref() {

        SwitchPreferenceCompat notificationsPref = preferenceManager.findPreference(Constants.NOTIFICATIONS_PREF);
        assert notificationsPref != null;
        notificationsPref.setOnPreferenceChangeListener((preference, option) -> {
            boolean enableNotifications = (boolean) option;
            if (enableNotifications) {
                Notification notification = getNotification(getContext(), Constants.NOTIFICATION_MSG);
                scheduleNotification(this.getActivity(), notification, 1500);
            }
            notificationsPref.setChecked(enableNotifications);
            sharedPrefEditor.putBoolean(Constants.NOTIFICATIONS_PREF, enableNotifications);
            sharedPrefEditor.apply();

            return false;
        });
    }

    private void handleFeedDataTimeInterval() {

        ListPreference listPreference = preferenceManager.findPreference(Constants.DATA_FEED_INTERVAL_SHARED_PREF);

        assert listPreference != null;
        listPreference.setOnPreferenceChangeListener((preference, newValue) -> {

            listPreference.setValue((String) newValue);
            sharedPrefEditor.putString(Constants.DATA_FEED_INTERVAL_SHARED_PREF, (String) newValue);
            sharedPrefEditor.apply();

            return false;
        });
    }

    public void scheduleNotification(Context context, Notification notification, int delay) {
        Intent notificationIntent = new Intent(context, NotificationReceiver.class);
        notificationIntent.putExtra(Constants.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(Constants.NOTIFICATION, notification);
        PendingIntent pendingIntent = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getBroadcast(context,0, notificationIntent,PendingIntent.FLAG_MUTABLE);
        }
        long futureMillis = SystemClock.elapsedRealtime()+delay;
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        assert alarmManager !=null;
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureMillis, pendingIntent);

    }

    private Notification getNotification(Context context, String content){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, Constants.default_notification_id);

        builder.setContentTitle(Constants.NOTIFICATION_TITLE);
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.ic_launcher_foreground);
        builder.setAutoCancel(true);
        builder.setStyle(new NotificationCompat.BigTextStyle()
                .bigText(content));
        builder.setChannelId(Constants.NOTIFICATION_CHANNEL_ID);

        return builder.build();
    }
}