package com.example.studentcompanion;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class AttendanceActivity extends AppCompatActivity {

    String selectedDate = "";
    DatabaseHelper db;
    String user;

    TextView percent;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_attendance);

        db = new DatabaseHelper(this);

        user = getSharedPreferences("user", MODE_PRIVATE)
                .getString("username", "");

        Button pick = findViewById(R.id.pickDate);
        Button present = findViewById(R.id.presentBtn);
        Button absent = findViewById(R.id.absentBtn);
        TextView date = findViewById(R.id.dateView);
        percent = findViewById(R.id.percentView);

        pick.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            DatePickerDialog dialog = new DatePickerDialog(this, (view, y, m, d) -> {
                selectedDate = d + "/" + (m + 1) + "/" + y;
                date.setText(selectedDate);
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

            dialog.getDatePicker().setMaxDate(c.getTimeInMillis());

            dialog.show();
        });

        present.setOnClickListener(v -> mark("Present"));
        absent.setOnClickListener(v -> mark("Absent"));

        updatePercent();
    }

    void mark(String status) {
        if (selectedDate.equals("")) {
            Toast.makeText(this, "Select Date", Toast.LENGTH_SHORT).show();
            return;
        }

        if (db.isDateExists(user, selectedDate)) {
            Toast.makeText(this, "Already Marked!", Toast.LENGTH_SHORT).show();
        } else {
            db.insertAttendance(user, selectedDate, status);
            Toast.makeText(this, status + " Marked", Toast.LENGTH_SHORT).show();
        }

        updatePercent();
    }

    void updatePercent() {
        Cursor c = db.getAttendance(user);

        int total = 0, present = 0;

        while (c.moveToNext()) {
            total++;
            if (c.getString(3).equals("Present"))
                present++;
        }

        c.close();

        if (total == 0) {
            percent.setText("Attendance: 0%");
        } else {
            int per = (present * 100) / total;
            percent.setText("Attendance: " + per + "%");
        }
    }
}