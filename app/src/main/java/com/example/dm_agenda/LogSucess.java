package com.example.dm_agenda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LogSucess extends AppCompatActivity {


    private SQLiteOpenHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_sucess);

        TextView tvInfo = (TextView) findViewById(R.id.userInfo);
        //Se recupera el valor de la activity anterior.
        String userEmail = getIntent().getStringExtra("email");
        tvInfo.setText(userEmail);
    }

    public void hometask(View view){
        Intent intent = new Intent(this, homeTasks.class);
        startActivity(intent);
    }
}