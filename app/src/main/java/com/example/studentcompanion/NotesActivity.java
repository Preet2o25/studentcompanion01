package com.example.studentcompanion;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class NotesActivity extends AppCompatActivity {

    DatabaseHelper db;
    ArrayList<String> notes;
    ArrayList<Integer> ids;
    ArrayAdapter<String> adapter;
    String user;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_notes);

        db = new DatabaseHelper(this);

        user = getSharedPreferences("user", MODE_PRIVATE)
                .getString("username", "");

        Spinner spinner = findViewById(R.id.subjectSpinner);
        EditText input = findViewById(R.id.noteInput);
        ListView list = findViewById(R.id.notesList);
        Button add = findViewById(R.id.addNote);

        String[] subjects = {"MAD","CNS","DSC","DIS"};
        spinner.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, subjects));

        notes = new ArrayList<>();
        ids = new ArrayList<>();

        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, notes);
        list.setAdapter(adapter);

        load();

        add.setOnClickListener(v -> {
            db.addNote(user,
                    spinner.getSelectedItem().toString(),
                    input.getText().toString());

            input.setText("");
            load();
            adapter.notifyDataSetChanged();
        });

        list.setOnItemLongClickListener((p, v, pos, id) -> {
            db.deleteNote(ids.get(pos));
            load();
            adapter.notifyDataSetChanged();
            return true;
        });
    }

    void load() {
        notes.clear();
        ids.clear();

        Cursor c = db.getNotes(user);

        while (c.moveToNext()) {
            ids.add(c.getInt(0));
            notes.add(c.getString(2) + ": " + c.getString(3));
        }

        c.close();
    }
}