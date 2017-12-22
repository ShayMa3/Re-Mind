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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class History extends AppCompatActivity implements View.OnClickListener {


    private ListView historyView;
    private Button backButton;
    private ArrayAdapter<Task> historyAdapter;
    private TextView instructionsText, historyHeaderText;
    private ArrayList <Task> tasks, history, removedHistory;
    private Intent data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        wireWidgets();

        tasks = getIntent().getParcelableArrayListExtra("tasks");
        history = getIntent().getParcelableArrayListExtra("history");
        historyAdapter = new ArrayAdapter<Task>(this, android.R.layout.simple_list_item_1, history);
        historyView.setAdapter(historyAdapter);
        data = new Intent(History.this, MainActivity.class);
        removedHistory = new ArrayList<>();
        historyView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                removedHistory.add(history.remove(pos));
                historyAdapter.notifyDataSetChanged();
                data.putExtra("history!", removedHistory);
            }
        });
        backButton.setOnClickListener(this);
    }

    public void wireWidgets(){
        backButton = (Button) findViewById(R.id.button_back);
        instructionsText = (TextView) findViewById(R.id.text_instructions);
        historyHeaderText = (TextView) findViewById(R.id.text_history_header);
        historyView = (ListView) findViewById(R.id.history_list_view);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.button_back:
                setResult(RESULT_OK, data);
                finish();
                break;
        }
    }
}
