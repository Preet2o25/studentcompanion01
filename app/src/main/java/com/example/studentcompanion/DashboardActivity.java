package com.example.studentcompanion;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_dashboard);

        String user = getSharedPreferences("user", MODE_PRIVATE)
                .getString("username", "");

        TextView name = findViewById(R.id.userName);
        name.setText("Welcome, " + user);

        findViewById(R.id.btnAttendance).setOnClickListener(v ->
                startActivity(new Intent(this, AttendanceActivity.class)));

        findViewById(R.id.btnTodo).setOnClickListener(v ->
                startActivity(new Intent(this, TodoActivity.class)));

        findViewById(R.id.btnNotes).setOnClickListener(v ->
                startActivity(new Intent(this, NotesActivity.class)));

        findViewById(R.id.btnTimetable).setOnClickListener(v ->
                startActivity(new Intent(this, TimetableActivity.class)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menu_profile) {
            startActivity(new Intent(this, ProfileActivity.class));
            return true;
        }

        if (item.getItemId() == R.id.menu_logout) {
            getSharedPreferences("user", MODE_PRIVATE).edit().clear().apply();
            Intent i = new Intent(this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}