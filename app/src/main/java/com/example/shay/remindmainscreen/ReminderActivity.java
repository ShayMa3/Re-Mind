package com.example.shay.remindmainscreen;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;

public class ReminderActivity extends AppCompatActivity {

    private Calendar myCalendar;
    private TimePicker timePickerDialog;
    private DatePicker calendarDate;
    private Button submitReminder;
    private String reminderName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        wireWidgets();
        //submitReminder.setOnClickListener(this);

        Intent i = getIntent();
        //Task thing = i.getParcelableExtra(MainActivity.EXTRA_NAME);
        //reminderName = thing.getName();
    }

    public void wireWidgets(){
        timePickerDialog = (TimePicker) findViewById(R.id.popup_element);
        calendarDate = (DatePicker) findViewById(R.id.calendar_reminder);
        submitReminder = (Button) findViewById(R.id.button_submit_reminder);
    }

    public void onClick(View view){
        switch(view.getId()){
            case R.id.button_submit_reminder:
                Intent i = new Intent(ReminderActivity.this, NewTaskActivity.class);
                startActivity(i);
                //set the reminder! .. etc variables
                //int hour = timePickerDialog.getHour();
                //int minute = timePickerDialog.getMinute();
                int month = calendarDate.getMonth();

        }
    }

    public void showHourPicker() {
        final Calendar myCalender = Calendar.getInstance();
        int hour = myCalender.get(Calendar.HOUR_OF_DAY);
        int minute = myCalender.get(Calendar.MINUTE);


        TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (view.isShown()) {
                    myCalender.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    myCalender.set(Calendar.MINUTE, minute);

                }
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, myTimeListener, hour, minute, true);
        timePickerDialog.setTitle("Choose hour:");
        //timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        timePickerDialog.show();
    }

    public void sendNotification(View view) {
        //Get an instance of NotificationManager//
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.notificationicon1)
                        .setContentTitle("Re-Mind")
                        .setContentText("Complete your task!");

        Intent intent = new Intent(ReminderActivity.this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        mBuilder.setContentIntent(pendingIntent);

        // Gets an instance of the NotificationManager service//
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //When you issue multiple notifications about the same type of event, it’s best practice for your app to try to update an existing notification with this new information, rather than immediately creating a new notification. If you want to update this notification at a later date, you need to assign it an ID. You can then use this ID whenever you issue a subsequent notification. If the previous notification is still visible, the system will update this existing notification, rather than create a new one. In this example, the notification’s ID is 001//
        mNotificationManager.notify(001, mBuilder.build());
    }
}

