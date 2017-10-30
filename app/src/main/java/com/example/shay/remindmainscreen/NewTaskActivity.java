package com.example.shay.remindmainscreen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class NewTaskActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView newHeaderText;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        submitButton = (Button) findViewById(R.id.button_submit);
        submitButton.setOnClickListener(this);
        createTask();
    }

    public void onClick(View view){
        switch(view.getId()){
            case R.id.button_submit:
                Intent i = new Intent(NewTaskActivity.this, MainActivity.class);

                startActivity(i);
                break;
    }}

    public void createTask(){
        //Task create = new Task()
    }

    //have a textView where you can get user input. Store that input in a variable (using getText method)and use it to create a Task. Add the task to the list.
}
