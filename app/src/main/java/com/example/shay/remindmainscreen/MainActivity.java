package com.example.shay.remindmainscreen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

    private static final String TAG = "taskList activity";
    private static final int HISTORY_REQUEST = 2;
    private TextView headerText, dateText, quoteText, newTaskLine, yourTasks;
    private ImageView upArrow;
    private float x1, x2, y1, y2;
    private int streak, lastDay, currentDay, lastHistorySize, lastStreakSize;
    private boolean firstTime;
    private List<Task> tasks, history, broughtBackHistory;
    private ListView taskList;
    private ArrayAdapter<Task> adapter;
    public static final String EXTRA_NAME = "REMIND";
    private List<CheckBox> checkBoxes;
    private CheckBox check0, check1, check2, check3, check4, check5, check6, check7;
    private Button historyButton;
    //Variables to display the date
    private Calendar calendar;
    private SimpleDateFormat simpleDateFormat;
    private String date;
    private Task newTask;

    //access database of quotes
    //fix date format for time picker popup

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        wireWidgets();
        historyButton.setOnClickListener(this);
        //initialize arrayList for first time setup


        //Get the today's date to display
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        date = simpleDateFormat.format(calendar.getTime());
        dateText.setText(date);
        currentDay = calendar.get(Calendar.DAY_OF_YEAR);
    }

    @Override
    protected void onResume() {
//        if (getFirstTimeCheckFromSharedPrefs(this) == false) {
//            initCheckBoxList();
//            initHistoryList();
//            firstTime = true;
//            saveFirstTimeCheckToSharedPrefs(this, firstTime);
//        } else {
            //heckBoxes = getCheckBoxesFromSharedPrefs(this);
            initCheckBoxList();
            //Log.d("CHECKBOXES", "onResume: " + checkBoxes.toString());
            history = getHistoryFromSharedPrefs(this);
            Log.d("TASKS", "onResume: " + history);
            streak = getStreakFromSharedPrefs(this);
            Log.d(TAG, "onResume: " + streak);
            tasks = getTasksFromSharedPrefs(this);
            Log.d(TAG, "onResume: " + tasks);
            if (tasks == null) {
                tasks = new ArrayList<>();
                tasks.add(new Task("Your first task!", "Check this task to complete your first task!", "01/01/18"));
                checkBoxes.get(0).setVisibility(View.VISIBLE);
            }

            if (history == null) {
                initHistoryList();
            }
            if (broughtBackHistory != null) {
                tasks.addAll(broughtBackHistory);
                broughtBackHistory = null;
            }

        Log.d("tasklist", "onCreate: taskList = " + taskList);
        adapter = new ArrayAdapter<Task>(this, R.layout.task, tasks);
        //set the adapter to the listview
        taskList.setAdapter(adapter);
        Log.d("sean!", "onCreate: adapter= " + adapter);
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
        if (newTask != null) {
            //move all this to onResume
            //instead of adding the task, create a Task object & store it in newTask
            //in the onResume, check if newTask is null...if not, add it to the list, show the checkbox, etc,
            //then set newTask back to null

            tasks.add(newTask);
            newTask = null;
            adapter.notifyDataSetChanged();
        }
        for (int i = 0; i < tasks.size() && i < 8; i++) {
            checkBoxes.get(i).setVisibility(View.VISIBLE);
            checkBoxes.get(i).setChecked(tasks.get(i).isDone());
        }
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.button_history:
                //Intent i = new Intent(MainActivity.this, History.class);
                Intent i = new Intent(MainActivity.this, History.class);
                i.putExtra("history", (ArrayList<Task>) history);
                i.putExtra("tasks", (ArrayList<Task>) tasks);
                startActivityForResult(i, HISTORY_REQUEST);
                break;
        }
    }

    public void wireWidgets() {
        headerText = (TextView) findViewById(R.id.text_header);
        dateText = (TextView) findViewById(R.id.text_date);
        newTaskLine = (TextView) findViewById(R.id.new_task);
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
        historyButton = (Button) findViewById(R.id.button_history);
    }


    private void initCheckBoxList() {
        checkBoxes = new ArrayList<>();
        checkBoxes.add((CheckBox) findViewById(R.id.check_0));
        checkBoxes.add((CheckBox) findViewById(R.id.check_1));
        checkBoxes.add((CheckBox) findViewById(R.id.check_2));
        checkBoxes.add((CheckBox) findViewById(R.id.check_3));
        checkBoxes.add((CheckBox) findViewById(R.id.check_4));
        checkBoxes.add((CheckBox) findViewById(R.id.check_5));
        checkBoxes.add((CheckBox) findViewById(R.id.check_6));
        checkBoxes.add((CheckBox) findViewById(R.id.check_7));
    }


    private void initHistoryList() {
        history = new ArrayList<>();
    }


    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.check_0:
                if (checked)
                    history.add(tasks.remove(0));
                adapter.notifyDataSetChanged();
                updateStreak();
                for (int i = checkBoxes.size() - 1; i >= 0; i--) {
                    if (checkBoxes.get(i).getVisibility() == View.VISIBLE) {
                        checkBoxes.get(i).setVisibility(View.GONE);
                        i = -1;
                    }
                }
                check0.setChecked(false);
                break;

            case R.id.check_1:
                if (checked) {
                    history.add(tasks.remove(1));
                    adapter.notifyDataSetChanged();
                    updateStreak();
                    for (int i = checkBoxes.size() - 1; i >= 0; i--) {
                        if (checkBoxes.get(i).getVisibility() == View.VISIBLE) {
                            checkBoxes.get(i).setVisibility(View.GONE);
                            i = -1;
                            }
                        }
                        check1.setChecked(false);
                        break;
                    }

            case R.id.check_2:
                if (checked) {
                    history.add(tasks.remove(2));
                    adapter.notifyDataSetChanged();
                    updateStreak();
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
                if (checked) {
                    history.add(tasks.remove(3));
                    adapter.notifyDataSetChanged();
                    updateStreak();
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
                if (checked) {
                    history.add(tasks.remove(4));
                    adapter.notifyDataSetChanged();
                    updateStreak();
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
                if (checked) {
                    history.add(tasks.remove(5));
                    adapter.notifyDataSetChanged();
                    updateStreak();
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
                if (checked) {
                    history.add(tasks.remove(6));
                    adapter.notifyDataSetChanged();
                    updateStreak();
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
                if (checked) {
                    history.add(tasks.remove(7));
                    adapter.notifyDataSetChanged();
                    updateStreak();
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
        if (lastHistorySize < history.size() && currentDay == lastDay + 1 && lastStreakSize == streak) {
            streak++;
        } else if (currentDay > lastDay + 1) {
            streak = 0;
        }
    }

    public boolean onTouchEvent(MotionEvent touchevent) { //allows us to swipe up to get to NewTaskActivity
        switch (touchevent.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                x1 = touchevent.getX();
                y1 = touchevent.getY();
                break;
            }
            //Swipe up results
            case MotionEvent.ACTION_UP: {
                x2 = touchevent.getX();
                y2 = touchevent.getY();
                if (y1 > y2) {
                    Intent i = new Intent(MainActivity.this, NewTaskActivity.class);
                    startActivityForResult(i, 1);
                }
                break;
            }
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1: { //number 1 to show that it's from NewTaskActivity
                if (resultCode == RESULT_OK) {
                    //add a created task with the data from NewTaskActivity
                    newTask = new Task(data.getStringExtra("name"), data.getStringExtra("description"), data.getStringExtra("date"));

//                    //move all this to onResume
//                    //instead of adding the task, create a Task object & store it in newTask
//                    //in the onResume, check if newTask is null...if not, add it to the list, show the checkbox, etc,
//                    //then set newTask back to null
//
//                    //tasks.add(new Task(data.getStringExtra("name"), data.getStringExtra("description"), data.getStringExtra("date")));
//                    adapter.notifyDataSetChanged();
//                    Log.d(TAG, "onActivityResult: " + data.getStringExtra("name"));
//                    for(int i = 0; i<tasks.size(); i++)
//                    {
//                        checkBoxes.get(i).setVisibility(View.VISIBLE);
//                    }
                }
            }
            break;
            case HISTORY_REQUEST:
                if (resultCode == RESULT_OK) {
                    //add a created task with the data from NewTaskActivity
                    broughtBackHistory = data.getParcelableArrayListExtra("history!");
                }
        }

    }

    //how to save and get arrayList to shared Pref, specifically current Task list

    /**
     * how to save to user preferences??? Does this go in OnStop?
     * gson is a converter for arraylist to storable object
     * ARE YOU PROUD THAT I CAN COMMENT LIKE THIS. IMA PRO
     **/
    public void saveTasksToSharedPrefs(Context context) {
    SharedPreferences appSharedPrefs = PreferenceManager
            .getDefaultSharedPreferences(context.getApplicationContext());
    SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
    Gson gson = new Gson();
    String json = gson.toJson(tasks);
    prefsEditor.putString("currentTasks", json);
    prefsEditor.commit();
    }

    /**
     * how to retrieve from shared preferences
     **/
    public List<Task> getTasksFromSharedPrefs(Context context) {

    SharedPreferences appSharedPrefs = PreferenceManager
            .getDefaultSharedPreferences(context.getApplicationContext());
    Gson gson = new Gson();
    String json = appSharedPrefs.getString("currentTasks", "");

    tasks = gson.fromJson(json, new TypeToken<ArrayList<Task>>(){}.getType());
    Log.d("hi", "getTasksFromSharedPrefs: tasks= " + tasks);
    return tasks;
    }


    //saving and getting completed tasks
    public void saveHistoryToSharedPrefs(Context context) {
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
        history = gson.fromJson(json, new TypeToken<ArrayList<Task>>() {
        }.getType());
        Log.d("hello", "getHistoryFromSharedPrefs: history= " + history);
        return history;
    }


    //saving and getting completed tasks
    public void saveCheckBoxesToSharedPrefs(Context context) {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context.getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        //create a boolean arraylist
        List<Boolean> checkBoxValues = new ArrayList<>();
        //loop through checkboxes and add the value to the new list
        for (int i = 0; i < checkBoxes.size(); i++) {
            checkBoxValues.add(checkBoxes.get(i).isChecked());
        }
        //save the new list
        String json = gson.toJson(checkBoxValues);
        prefsEditor.putString("checkBoxValues", json);
        prefsEditor.commit();
    }

    public List<CheckBox> getCheckBoxesFromSharedPrefs(Context context) {

        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context.getApplicationContext());
        Gson gson = new Gson();
        String json = appSharedPrefs.getString("checkBoxValues", "");
        List<Boolean> checkBoxValues = gson.fromJson(json, new TypeToken<List<Boolean>>() {
        }.getType());
        if (checkBoxValues == null) {
            checkBoxValues = new ArrayList<>();
        }
        Log.d("yo", "getCheckBoxesFromSharedPrefs: checkBoxValues= " + checkBoxes);
        //if(checkBoxes == null) {
        checkBoxes = new ArrayList<>();
        checkBoxes.add((CheckBox) findViewById(R.id.check_0));
        checkBoxes.add((CheckBox) findViewById(R.id.check_1));
        checkBoxes.add((CheckBox) findViewById(R.id.check_2));
        checkBoxes.add((CheckBox) findViewById(R.id.check_3));
        checkBoxes.add((CheckBox) findViewById(R.id.check_4));
        checkBoxes.add((CheckBox) findViewById(R.id.check_5));
        checkBoxes.add((CheckBox) findViewById(R.id.check_6));
        checkBoxes.add((CheckBox) findViewById(R.id.check_7));
        //
        for (int i = 0; i < checkBoxValues.size(); i++) {
            checkBoxes.get(i).setChecked(checkBoxValues.get(i));
        }
        return checkBoxes;
    }


    //saving and getting streak number
    public void saveStreakToSharedPrefs(Context context) {
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
        Log.d("yolo swag", "getFirstTimeCheckFromSharedPrefs: ");
        return firstTime;
    }

    @Override
    protected void onPause() {
        // saveCheckBoxesToSharedPrefs(this, checkBoxes);
        saveHistoryToSharedPrefs(this);
        saveStreakToSharedPrefs(this);
        saveTasksToSharedPrefs(this);

        lastDay = calendar.get(Calendar.DAY_OF_YEAR);
        lastHistorySize = history.size();
        lastStreakSize = streak;
        super.onPause();
    }
    //here's a comment
}




