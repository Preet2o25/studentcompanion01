package com.example.studentcompanion;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class TimetableActivity extends AppCompatActivity {

    TextView result;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_timetable);

        result = findViewById(R.id.result);

        findViewById(R.id.mon).setOnClickListener(v ->
                result.setText("MONDAY:\nDIS\nMAD\nLAB MAD & DSC\nMINOR\nBREAK\nCNS\nMINOR"));

        findViewById(R.id.tue).setOnClickListener(v ->
                result.setText("TUESDAY:\nDSC\nSEPM\nLAB MAD & SEPM\nMINOR\nBREAK\nDIS\nLAB CNS & DPA"));

        findViewById(R.id.wed).setOnClickListener(v ->
                result.setText("WEDNESDAY:\nDIS LAB\nMINOR\nBREAK\nDIS\nDPA\nLAB SEPM & DPA"));

        findViewById(R.id.thu).setOnClickListener(v ->
                result.setText("THURSDAY:\nMAD\nCNS\nMINOR\nBREAK\nSEPM\nDSC"));

        findViewById(R.id.fri).setOnClickListener(v ->
                result.setText("FRIDAY:\nLAB\nDSC\nMAD\nBREAK\nLAB CNS\nDSC\nSEPM\nDPA"));
    }
}