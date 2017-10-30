package com.example.shay.remindmainscreen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ArrayList<Task> taskList;
    private TextView headerText, dateText, quoteText;
    private Button newTaskButton;
    private GestureDetector gestureDetector;
    //private ListView taskListView;
    //make custom constraint layout w/ checkbox and text
    //make custom adapter
    //access database of quotes
    //make the date change everyday


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wireWidgets();
        setListeners();

    }

    public void wireWidgets(){
        headerText = (TextView) findViewById(R.id.text_header);
        dateText = (TextView) findViewById(R.id.text_date);
        quoteText = (TextView) findViewById(R.id.text_quote);
        newTaskButton = (Button) findViewById(R.id.button_new_task);
    }

    public void setListeners(){
        newTaskButton.setOnClickListener(this);
    }

    public void addTask(Task addedTask)
    {
        taskList = new ArrayList<>();
        taskList.add(addedTask);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_new_task:
                Intent i = new Intent(MainActivity.this, NewTaskActivity.class);

                startActivity(i);
                break;
        }
    }}