package com.example.mindfull.Fragments;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mindfull.BroadcastReceiverClass;
import com.example.mindfull.Models.AlarmRecord;
import com.example.mindfull.Models.AnalyticsRecord;
import com.example.mindfull.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;


public class AlarmFragment extends Fragment {


    TimePicker timePic;
    Button btnSet;
    Button btnStop;
    private int hour, minute;
    FirebaseDatabase database;
    FirebaseAuth auth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_alarm, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        timePic = view.findViewById(R.id.time_pic);
        btnSet = view.findViewById(R.id.btn_set);
        btnStop = view.findViewById(R.id.btn_stop);

        timePic.setOnTimeChangedListener((timePicker, sHour, sMinute) -> {
            hour = sHour;
            minute = sMinute;
        });
        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Alarm is set at " + hour + " : " + minute, Toast.LENGTH_SHORT).show();
                setAlarm();
                setNotif();

                String Date = new Date().toString();
                String[] separated = Date.split(" ");
                String currentDate = separated[1] + " " + separated[2];
                String timeseperated[] = separated[3].split(":");
//                Log.e("TAG",timeseperated[0]);
                int currentHour = Integer.parseInt(timeseperated[0]);
                int alarmHour = hour;
                int timeSlept = alarmHour - currentHour;
                if(currentHour>alarmHour) {
                    timeSlept+=24;
                }


                final AlarmRecord alarmRecord = new AlarmRecord(timeSlept,currentDate);

                database.getReference().child("AlarmRecord").child(auth.getUid()).push().setValue(alarmRecord).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                    }
                });


            }
        });
        //    stop notification by disabling reciever
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
                System.exit(0);
            }
        });
    }
    private void setAlarm() {
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

        Calendar call_alarm = Calendar.getInstance();
        Calendar call_now = Calendar.getInstance();

        call_alarm.setTime(new Date());
        call_now.setTime(new Date());

        call_alarm.set(Calendar.HOUR_OF_DAY, hour);
        call_alarm.set(Calendar.MINUTE, minute);
        call_alarm.set(Calendar.SECOND, 0);

        if (call_alarm.before(call_now)) {
            call_alarm.add(Calendar.DATE, 1);
        }

        Intent i = new Intent(getActivity(), BroadcastReceiverClass.class);
        @SuppressLint("UnspecifiedImmutableFlag")
        PendingIntent p = PendingIntent.getBroadcast(getActivity(), 0, i, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP, call_alarm.getTimeInMillis(), p);
        Toast.makeText(getActivity(), "Your Alarm is set", Toast.LENGTH_SHORT).show();
    }
    private void setNotif() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("Notify", "Alarm", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Alarm Reminder");
            NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);

            notificationManager.createNotificationChannel(channel);
        }
    }


}