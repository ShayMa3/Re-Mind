package com.example.shay.remindmainscreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TaskInfo extends AppCompatActivity implements View.OnClickListener {

    private TextView taskName, taskDesc, taskDate, taskDetailSign, taskDoDateSign;
    private Button goHome, edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taskinfo);
        //brings over info from main activity
        Intent i = getIntent();
        Task thing = i.getParcelableExtra(MainActivity.EXTRA_NAME);
        Task todo = getIntent().getParcelableExtra(MainActivity.EXTRA_NAME);
        wireWidgets();
        setOnClickListeners();

        //displays the text from a task
        taskName.setText(thing.getName());
        taskDesc.setText(thing.getDescription());
        taskDate.setText(thing.getDate());
    }

    private void setOnClickListeners(){
        goHome.setOnClickListener(this);
        edit.setOnClickListener(this);
    }

    private void wireWidgets() {
        taskDesc = (TextView) findViewById(R.id.task_desc);
        taskName = (TextView) findViewById(R.id.task_name);
        taskDate = (TextView) findViewById(R.id.task_date);
        goHome = (Button) findViewById(R.id.button_go_home);
        taskDetailSign = (TextView) findViewById(R.id.text_descsign);
        taskDoDateSign = (TextView) findViewById(R.id.text_dodatesign);
        edit = (Button) findViewById(R.id.edit_task);
    }

    public void onClick(View view){
        switch(view.getId()){
            case R.id.button_go_home:
                finish();
                break;
            case R.id.edit_task:
                Intent j = new Intent(TaskInfo.this, EditTaskInfoActivity.class);
                startActivity(j);
                break;
        }
    }
}
