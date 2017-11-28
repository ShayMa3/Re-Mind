package com.example.shay.remindmainscreen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView headerText, dateText, quoteText, newTask;
    private ImageView upArrow;
    private float x1, x2, y1, y2;
    private List<Task> tasks;
    private ListView taskList;
    private ArrayAdapter<Task> adapter;
    public static final String EXTRA_NAME = "REMIND";

    //make custom constraint layout w/ checkbox and text
    //access database of quotes
    //make the date change everyday
    //fix date format for time picker popup




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        //wire widgets
        wireWidgets();
        //initialize arrayList
        tasks = new ArrayList<>();
        initTaskList();
        adapter = new ArrayAdapter<Task>(this, R.layout.task, tasks);
        //set the adapter to the listview
        taskList.setAdapter(adapter);
        taskList.setOnItemClickListener(new ListView.OnItemClickListener() {
            //when clicked, each item will open main activity and show name, description, and date
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                Intent i = new Intent(MainActivity.this, TaskInfo.class); //send info to new activity
                //get the object from the arraylist and put it in the extra for Intent
                i.putExtra(EXTRA_NAME, tasks.get(pos));
                startActivity(i);
            }
        });

    }

    public void wireWidgets(){
        headerText = (TextView) findViewById(R.id.text_header);
        dateText = (TextView) findViewById(R.id.text_date);
        quoteText = (TextView) findViewById(R.id.text_quote);
        newTask = (TextView) findViewById(R.id.new_task);
        upArrow = (ImageView) findViewById(R.id.up_arrow);
        taskList = (ListView) findViewById(R.id.listview_tasklist);
    }


    private void initTaskList() {
        /*tasks.add(new Task("Do hw", "do hw by five", "Nov 8"));
        tasks.add(new Task("Do dishes", "do dishes by six", "Nov 20"));
        tasks.add(new Task("hehehe", "midnight", "Nov 9"));*/

    }

    public boolean onTouchEvent(MotionEvent touchevent) { //allows us to swipe up to get to NewTaskActivity
        switch (touchevent.getAction()) {
            case MotionEvent.ACTION_DOWN:{
                x1 = touchevent.getX();
                y1 = touchevent.getY();
                break;
            }
            //Swipe up results
            case MotionEvent.ACTION_UP: {
                x2 = touchevent.getX();
                y2 = touchevent.getY();
                if (y1 > y2) {
                    //Toast.makeText(this, "Down to Up Swap Performed", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(MainActivity.this, NewTaskActivity.class);
                    //startActivity(i);
                    startActivityForResult(i, 1);
                }
                break;
            }
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode){
            case 1:{ //number 1 to show that it's from NewTaskActivity
                if(resultCode == RESULT_OK){
                    //add a created task with the data from NewTaskActivity
                    tasks.add(new Task(data.getStringExtra("name"), data.getStringExtra("description"), data.getStringExtra("date")));
                    adapter.notifyDataSetChanged();
                }
            }
        }

    }

    @Override
    public void onClick(View view) {

    }
}