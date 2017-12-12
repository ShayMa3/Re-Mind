package com.example.shay.remindmainscreen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView headerText, dateText, quoteText, newTask, yourTasks;
    private ImageView upArrow;
    private float x1, x2, y1, y2;
    private int streak, lastDay, currentDay, lastHistorySize, lastStreakSize;
    private boolean firstTime;
    private List<Task> tasks, history;
    private ListView taskList;
    private ArrayAdapter<Task> adapter;
    public static final String EXTRA_NAME = "REMIND";
    private List<CheckBox> checkBoxes;
    private CheckBox check0, check1, check2, check3, check4, check5, check6, check7;
    //Variables to display the date
    private Calendar calendar;
    private SimpleDateFormat simpleDateFormat;
    private String date;

    //access database of quotes
    //fix date format for time picker popup

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        wireWidgets();
        setOnClickListeners();
        //initialize arrayList for first time setup
        if(getFirstTimeCheckFromSharedPrefs(this)==false) {
            tasks = new ArrayList<>();
            initCheckBoxList();
            initHistoryList();
            firstTime = true;
            saveFirstTimeCheckToSharedPrefs(this, firstTime);
        }
        else {
            checkBoxes = getCheckBoxesFromSharedPrefs(this);
            history = getHistoryFromSharedPrefs(this);
            streak = getStreakFromSharedPrefs(this);
            tasks = getTasksFromSharedPrefs(this);
        }
        adapter = new ArrayAdapter<Task>(this, R.layout.task, tasks);
        //set the adapter to the listview
        adapter = new ArrayAdapter<Task>(this, R.layout.task, tasks);
        taskList.setAdapter(adapter);
        taskList.setOnItemClickListener(new ListView.OnItemClickListener() {
            //when clicked, each item will open main activity and show name, description, and date
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                //send info to new activity
                Intent i = new Intent(MainActivity.this, TaskInfo.class);
                //get the object from the arrayList and put it in the extra for Intent
                i.putExtra(EXTRA_NAME, tasks.get(pos));
                startActivity(i);
            }
        });

        //Get the today's date to display
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        date = simpleDateFormat.format(calendar.getTime());
        dateText.setText(date);
        currentDay = calendar.get(Calendar.DAY_OF_YEAR);
    }

    public void wireWidgets(){
        headerText = (TextView) findViewById(R.id.text_header);
        dateText = (TextView) findViewById(R.id.text_date);
        quoteText = (TextView) findViewById(R.id.text_quote);
        newTask = (TextView) findViewById(R.id.new_task);
        upArrow = (ImageView) findViewById(R.id.up_arrow);
        taskList = (ListView) findViewById(R.id.listview_tasklist);
        yourTasks = (TextView) findViewById(R.id.text_your_tasks);
        check0 = (CheckBox) findViewById(R.id.check_0);
        check1 = (CheckBox) findViewById(R.id.check_1);
        check2 = (CheckBox) findViewById(R.id.check_2);
        check3 = (CheckBox) findViewById(R.id.check_3);
        check4 = (CheckBox) findViewById(R.id.check_4);
        check5 = (CheckBox) findViewById(R.id.check_5);
        check6 = (CheckBox) findViewById(R.id.check_6);
        check7 = (CheckBox) findViewById(R.id.check_7);
    }

    public void setOnClickListeners(){
        check1.setOnClickListener(this);
        check2.setOnClickListener(this);
    }


    private void initTaskList() {
        /*tasks.add(new Task("Do hw", "do hw by five", "Nov 8"));
        tasks.add(new Task("Do dishes", "do dishes by six", "Nov 20"));
        tasks.add(new Task("hehehe", "midnight", "Nov 9"));*/

    }


    private void initCheckBoxList() {
        checkBoxes= new ArrayList<>();
        checkBoxes.add((CheckBox)findViewById(R.id.check_0));
        checkBoxes.add((CheckBox)findViewById(R.id.check_1));
        checkBoxes.add((CheckBox)findViewById(R.id.check_2));
        checkBoxes.add((CheckBox)findViewById(R.id.check_3));
        checkBoxes.add((CheckBox)findViewById(R.id.check_4));
        checkBoxes.add((CheckBox)findViewById(R.id.check_5));
        checkBoxes.add((CheckBox)findViewById(R.id.check_6));
        checkBoxes.add((CheckBox)findViewById(R.id.check_7));
    }


    private void initHistoryList() {
        history = new ArrayList<>();
    }


    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.check_0:
                if (checked)
                    history.add(tasks.remove(0));
                    adapter.notifyDataSetChanged();
                    checkBoxes.get(checkBoxes.size()-1).setVisibility(View.GONE);
                for(int i = checkBoxes.size()-1; i >= 0; i--) {
                    if (checkBoxes.get(i).getVisibility() == View.VISIBLE) {
                        checkBoxes.get(i).setVisibility(View.GONE);
                        i=-1;
                    }
                }
                check0.setChecked(false);
                break;

            case R.id.check_1:
                if (checked)
                    history.add(tasks.remove(1));
                    adapter.notifyDataSetChanged();
                    checkBoxes.get(checkBoxes.size()-1).setVisibility(View.GONE);
                for(int i = checkBoxes.size()-1; i >= 0; i--) {
                    if (checkBoxes.get(i).getVisibility() == View.VISIBLE) {
                        checkBoxes.get(i).setVisibility(View.GONE);
                        i=-1;
                    }
                }
                check1.setChecked(false);
                break;

            case R.id.check_2:
                if (checked) {
                    history.add(tasks.remove(2));
                    adapter.notifyDataSetChanged();
                    for (int i = checkBoxes.size() - 1; i >= 0; i--) {
                        if (checkBoxes.get(i).getVisibility() == View.VISIBLE) {
                            checkBoxes.get(i).setVisibility(View.GONE);
                            i = -1;
                        }
                    }
                    check2.setChecked(false);
                    break;

                }

            case R.id.check_3:
                if(checked){
                    history.add(tasks.remove(3));
                    adapter.notifyDataSetChanged();
                    for (int i = checkBoxes.size() - 1; i >= 0; i--) {
                        if (checkBoxes.get(i).getVisibility() == View.VISIBLE) {
                            checkBoxes.get(i).setVisibility(View.GONE);
                            i = -1;
                        }
                    }
                    check3.setChecked(false);
                    break;
                }

            case R.id.check_4:
                if(checked){
                    history.add(tasks.remove(4));
                    adapter.notifyDataSetChanged();
                    for (int i = checkBoxes.size() - 1; i >= 0; i--) {
                        if (checkBoxes.get(i).getVisibility() == View.VISIBLE) {
                            checkBoxes.get(i).setVisibility(View.GONE);
                            i = -1;
                        }
                    }
                    check4.setChecked(false);
                    break;
                }

            case R.id.check_5:
                if(checked){
                    history.add(tasks.remove(5));
                    adapter.notifyDataSetChanged();
                    for (int i = checkBoxes.size() - 1; i >= 0; i--) {
                        if (checkBoxes.get(i).getVisibility() == View.VISIBLE) {
                            checkBoxes.get(i).setVisibility(View.GONE);
                            i = -1;
                        }
                    }
                    check5.setChecked(false);
                    break;
                }

            case R.id.check_6:
                if(checked){
                    history.add(tasks.remove(6));
                    adapter.notifyDataSetChanged();
                    for (int i = checkBoxes.size() - 1; i >= 0; i--) {
                        if (checkBoxes.get(i).getVisibility() == View.VISIBLE) {
                            checkBoxes.get(i).setVisibility(View.GONE);
                            i = -1;
                        }
                    }
                    check6.setChecked(false);
                    break;
                }

            case R.id.check_7:
                if(checked){
                    history.add(tasks.remove(7));
                    adapter.notifyDataSetChanged();
                    for (int i = checkBoxes.size() - 1; i >= 0; i--) {
                        if (checkBoxes.get(i).getVisibility() == View.VISIBLE) {
                            checkBoxes.get(i).setVisibility(View.GONE);
                            i = -1;
                        }
                    }
                    check7.setChecked(false);
                    break;
                }
        }
    }

    public void updateStreak() {
        if(lastHistorySize < history.size() && currentDay == lastDay + 1 && lastStreakSize == streak) {
            streak++;
        }
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
                    for(int i = 0; i<tasks.size(); i++)
                    {
                        checkBoxes.get(i).setVisibility(View.VISIBLE);
                    }
                }
            }
        }

    }

    //how to save and get arrayList to shared Pref, specifically current Task list
    /**
    how to save to user preferences??? Does this go in OnStop?
    gson is a converter for arraylist to storable object
    ARE YOU PROUD THAT I CAN COMMENT LIKE THIS. IMA PRO
    **/
    public void saveTasksToSharedPrefs(Context context, List<Task> tasks) {
    SharedPreferences appSharedPrefs = PreferenceManager
            .getDefaultSharedPreferences(context.getApplicationContext());
    SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
    Gson gson = new Gson();
    String json = gson.toJson(tasks);
    prefsEditor.putString("currentTasks", json);
    prefsEditor.commit();
    }

    /**
    how to retrieve from shared preferences
    **/
    public List<Task> getTasksFromSharedPrefs(Context context) {

    SharedPreferences appSharedPrefs = PreferenceManager
            .getDefaultSharedPreferences(context.getApplicationContext());
    Gson gson = new Gson();
    String json = appSharedPrefs.getString("currentTasks", "");

    tasks = gson.fromJson(json, new TypeToken<ArrayList<Task>>(){}.getType());
    return tasks;
    }


    //saving and getting completed tasks
    public void saveHistoryToSharedPrefs(Context context, List<Task> history) {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context.getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(history);
        prefsEditor.putString("completedTasks", json);
        prefsEditor.commit();
    }

    public List<Task> getHistoryFromSharedPrefs(Context context) {

        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context.getApplicationContext());
        Gson gson = new Gson();
        String json = appSharedPrefs.getString("completedTasks", "");
        history = gson.fromJson(json, new TypeToken<ArrayList<Task>>(){}.getType());
        return history;
    }


    //saving and getting completed tasks
    public void saveCheckBoxesToSharedPrefs(Context context, List<CheckBox> checkBoxes) {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context.getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(checkBoxes);
        prefsEditor.putString("checkBoxes", json);
        prefsEditor.commit();
    }

    public List<CheckBox> getCheckBoxesFromSharedPrefs(Context context) {

        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context.getApplicationContext());
        Gson gson = new Gson();
        String json = appSharedPrefs.getString("checkBoxes", "");
        checkBoxes = gson.fromJson(json, new TypeToken<ArrayList<CheckBox>>(){}.getType());
        return checkBoxes;
    }


    //saving and getting streak number
    public void saveStreakToSharedPrefs(Context context, int streak) {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context.getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        prefsEditor.putInt("streak", streak);
        prefsEditor.commit();
    }

    public int getStreakFromSharedPrefs(Context context) {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context.getApplicationContext());
        streak = appSharedPrefs.getInt("streak", streak);
        return streak;
    }


    //saving and getting first time setup
    public void saveFirstTimeCheckToSharedPrefs(Context context, boolean firstTime) {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context.getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        prefsEditor.putBoolean("firstTime", firstTime);
        prefsEditor.commit();
    }

    public boolean getFirstTimeCheckFromSharedPrefs(Context context) {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context.getApplicationContext());
        firstTime = appSharedPrefs.getBoolean("firstTime",firstTime);
        return firstTime;
    }

    @Override
    public void onClick(View view) {

    }

    protected void onPause(Bundle savedInstanceState) {
        saveCheckBoxesToSharedPrefs(this, checkBoxes);
        saveHistoryToSharedPrefs(this, history);
        saveStreakToSharedPrefs(this, streak);
        saveTasksToSharedPrefs(this, tasks);

        lastDay = calendar.get(Calendar.DAY_OF_YEAR);
        lastHistorySize = history.size();
        lastStreakSize = streak;
    }


    //here's a comment

}
