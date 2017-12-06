package com.example.shay.remindmainscreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TaskInfo extends AppCompatActivity implements View.OnClickListener {

    private TextView taskName, taskDesc, taskDate;
    private Button goHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taskinfo);
        Intent i = getIntent(); //brings over info from main activity
        Task thing = i.getParcelableExtra(MainActivity.EXTRA_NAME);
        wireWidgets();
        setOnClickListeners();
        Task clout = getIntent().getParcelableExtra(MainActivity.EXTRA_NAME);
        //displays the text
        taskName.setText(thing.getName());
        taskDesc.setText(thing.getDescription());
        taskDate.setText(thing.getDate());
    }

    private void setOnClickListeners(){
        goHome.setOnClickListener(this);
    }

    private void wireWidgets() {
        taskDesc = (TextView) findViewById(R.id.task_desc);
        taskName = (TextView) findViewById(R.id.task_name);
        taskDate = (TextView) findViewById(R.id.task_date);
        goHome = (Button) findViewById(R.id.button_go_home);
    }

    public void onClick(View view){
        switch(view.getId()){
            case R.id.button_go_home:
                finish();
                break;
        }
    }
}
