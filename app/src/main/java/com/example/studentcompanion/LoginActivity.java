package com.example.studentcompanion;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText username;
    Button login;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        login = findViewById(R.id.loginBtn);

        login.setOnClickListener(v -> {
            String user = username.getText().toString().trim();

            if (user.isEmpty()) {
                Toast.makeText(this, "Enter Username", Toast.LENGTH_SHORT).show();
                return;
            }

            getSharedPreferences("user", MODE_PRIVATE)
                    .edit()
                    .putString("username", user)
                    .apply();

            startActivity(new Intent(this, DashboardActivity.class));
        });
    }
}