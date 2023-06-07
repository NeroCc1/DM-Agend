package com.example.dm_agenda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class LogSucess extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_sucess);

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        String savedEmail = sharedPreferences.getString("email", "");
        String savedPassword = sharedPreferences.getString("pass", "");

        TextView tvInfo = (TextView) findViewById(R.id.userInfo);
        tvInfo.setText(savedEmail);

    }
}