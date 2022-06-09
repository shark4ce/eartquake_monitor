package com.example.earthquakemonitor;

import android.annotation.SuppressLint;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.earthquakemonitor.constants.Constants;
import com.example.earthquakemonitor.databinding.ActivityMainBinding;
import com.example.earthquakemonitor.fragments.HomeFragment;
import com.example.earthquakemonitor.fragments.SettingsFragment;
import com.example.earthquakemonitor.scheduled_jobs.GetEarthquakesJobService;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private SharedPreferences sharedPref;
    private final String TAG = this.getClass().getSimpleName();

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        sharedPref = this.getSharedPreferences(Constants.SETTINGS_SHARED_PREF, Context.MODE_PRIVATE);
        setContentView(binding.getRoot());
        if (savedInstanceState == null) {
            initJobScheduler();
            replaceFragment(new HomeFragment());
        }
        initTheme();
        initBottomNavBar();
        initNotifications();
        initDataFeedTImeInterval();
    }

    private void initTheme() {
        boolean darkThemeEnabled = sharedPref.getBoolean(Constants.DARK_THEME_PREF, false);
        if (darkThemeEnabled) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    private void initNotifications() {
        boolean notificationsEnabled = sharedPref.getBoolean(Constants.NOTIFICATIONS_PREF, false);
        Log.d(TAG, String.valueOf(notificationsEnabled));
    }

    private void initDataFeedTImeInterval() {
        String initDataFeedTImeInterval = sharedPref.getString(Constants.DATA_FEED_INTERVAL_SHARED_PREF, "0");
        Log.d(TAG, initDataFeedTImeInterval);
    }

    @SuppressLint("NonConstantResourceId")
    private void initBottomNavBar() {
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.settings:
                    replaceFragment(new SettingsFragment());
                    break;
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

    private void initJobScheduler() {
        JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        ComponentName jobService = new ComponentName(getApplicationContext(), GetEarthquakesJobService.class);

        JobInfo jobInfo = new JobInfo.Builder(1, jobService).setPeriodic(15 * 60 * 1000).build();

        // schedule/start the job
        int result = jobScheduler.schedule(jobInfo);
        if (result == JobScheduler.RESULT_SUCCESS)
            Log.d(TAG, "Successfully scheduled job: " + result);
        else
            Log.e(TAG, "RESULT_FAILURE scheduled job: " + result);
    }
}