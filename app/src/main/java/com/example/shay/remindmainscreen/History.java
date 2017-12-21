package com.example.shay.remindmainscreen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class History extends AppCompatActivity implements View.OnClickListener {


    private ListView historyView;
    private Button backButton;
    private ArrayAdapter<Task> historyAdapter;
    private TextView instructionsText, historyHeaderText;
    private List<Task> tasks, history;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        wireWidgets();

        tasks = getIntent().getParcelableArrayListExtra("tasks");
        history = getIntent().getParcelableArrayListExtra("history");
        history.add(new Task("Hello", "Your", "Task"));
        tasks.add(new Task("Hello", "Your", "Task"));
        historyAdapter = new ArrayAdapter<Task>(this, android.R.layout.simple_list_item_1, history);
        historyView.setAdapter(historyAdapter);
        historyView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                tasks.add(history.remove(pos));
                historyAdapter.notifyDataSetChanged();
            }
        });
    }

    public void wireWidgets(){
        backButton = (Button) findViewById(R.id.button_back);
        backButton.setOnClickListener(this);
        instructionsText = (TextView) findViewById(R.id.text_instructions);
        historyHeaderText = (TextView) findViewById(R.id.text_history_header);
        historyView = (ListView) findViewById(R.id.history_list_view);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.button_back:
                Intent i = new Intent(History.this, MainActivity.class);
                startActivity(i);
                break;
        }
    }
}
