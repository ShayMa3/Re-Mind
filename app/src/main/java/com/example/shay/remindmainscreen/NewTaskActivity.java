package com.example.shay.remindmainscreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class NewTaskActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView newHeaderText;
    private Button submitButton, reminderButton ;
    private EditText taskNameEditText, taskDetailsEditText, doDateEditText;
    private int doDate;
    private boolean completed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        wireWidgets();
        setOnClickListeners();
    }

    public void wireWidgets(){
        submitButton = (Button) findViewById(R.id.button_submit);
        reminderButton = (Button) findViewById(R.id.button_reminder);
        taskNameEditText = (EditText) findViewById(R.id.edittext_task_name);
        taskDetailsEditText = (EditText) findViewById(R.id.edittext_task_details);
        doDateEditText = (EditText) findViewById(R.id.edittext_do_date);
    }

    public void setOnClickListeners(){
        submitButton.setOnClickListener(this);
        reminderButton.setOnClickListener(this);
    }

    public void onClick(View view){
        switch(view.getId()){
            case R.id.button_submit:
                createTask();
                break;
            case R.id.button_reminder:
                Intent j = new Intent(NewTaskActivity.this, ReminderActivity.class);
                startActivity(j);
                break;
            }
        }


    public void createTask(){
        //Sets user input values to ta Task object
        String name = taskNameEditText.getText().toString();
        String desc = taskDetailsEditText.getText().toString();
        String date = doDateEditText.getText().toString();
        Intent i = new Intent();
        //transfers data from child class to parent class when child class is closed.
        i.putExtra("name", name);
        i.putExtra("description", desc);
        i.putExtra("date", date);
        setResult(Activity.RESULT_OK, i); //checks if there is data
        finish();
    }

 //Add the task to the list.
}
