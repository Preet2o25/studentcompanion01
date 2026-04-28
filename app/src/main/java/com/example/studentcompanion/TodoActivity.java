package com.example.studentcompanion;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.*;

public class TodoActivity extends AppCompatActivity {

    DatabaseHelper db;
    ArrayList<String> tasks;
    ArrayList<Integer> ids;
    ArrayAdapter<String> adapter;

    String selectedDate = "";
    String user;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_todo);

        db = new DatabaseHelper(this);

        user = getSharedPreferences("user", MODE_PRIVATE)
                .getString("username", "");

        EditText taskInput = findViewById(R.id.taskInput);
        TextView dateView = findViewById(R.id.dateView);
        Button addBtn = findViewById(R.id.addTask);
        ListView list = findViewById(R.id.todoList);

        tasks = new ArrayList<>();
        ids = new ArrayList<>();

        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, tasks);
        list.setAdapter(adapter);

        load();

        dateView.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            new DatePickerDialog(this, (view, y, m, d) -> {
                selectedDate = d + "/" + (m + 1) + "/" + y;
                dateView.setText(selectedDate);
            }, c.get(Calendar.YEAR),
                    c.get(Calendar.MONTH),
                    c.get(Calendar.DAY_OF_MONTH)).show();
        });

        addBtn.setOnClickListener(v -> {

            String task = taskInput.getText().toString().trim();

            if (task.isEmpty()) {
                Toast.makeText(this, "Enter Task", Toast.LENGTH_SHORT).show();
                return;
            }

            if (selectedDate.equals("")) {
                Toast.makeText(this, "Select Deadline", Toast.LENGTH_SHORT).show();
                return;
            }

            db.addTask(user, task, selectedDate);

            taskInput.setText("");
            dateView.setText("Select Deadline");
            selectedDate = "";

            load();
            adapter.notifyDataSetChanged();

            Toast.makeText(this, "Task Added", Toast.LENGTH_SHORT).show();
        });

        list.setOnItemLongClickListener((p, v, pos, id) -> {
            db.deleteTask(ids.get(pos));
            load();
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    void load() {
        tasks.clear();
        ids.clear();

        Cursor c = db.getTasks(user);

        while (c.moveToNext()) {
            ids.add(c.getInt(0));
            tasks.add(c.getString(2) + " (Due: " + c.getString(3) + ")");
        }

        c.close();
    }
}