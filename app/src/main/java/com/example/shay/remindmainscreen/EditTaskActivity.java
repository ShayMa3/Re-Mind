package com.example.shay.remindmainscreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Evan on 12/21/2017.
 */

public class EditTaskActivity extends NewTaskActivity {
    private TextView newHeaderText;
    private Button reminderButton, saveButton;
    private EditText taskNameEditText, taskDetailsEditText, doDateEditText;
    private int doDate;
    private boolean completed;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wireWidgets();
    }

    @Override
    public void wireWidgets(){
        super.wireWidgets();
        saveButton = (Button) findViewById(R.id.button_save);
    }

    public void setOnClickListeners(){
        super.setOnClickListeners();
    }

    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.button_save:
                Intent j = new Intent(EditTaskActivity.this, TaskInfo.class);
                String name = taskNameEditText.getText().toString();
                String desc = taskDetailsEditText.getText().toString();
                String date = doDateEditText.getText().toString();
                j.putExtra("name", name);
                j.putExtra("description", desc);
                j.putExtra("date", date);
                setResult(Activity.RESULT_OK, j); //checks if there is data
                finish();
                startActivity(j);
                break;
        }
    }


}
