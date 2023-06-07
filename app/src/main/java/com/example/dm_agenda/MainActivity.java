package com.example.dm_agenda;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText etEmail = findViewById(R.id.editTextEmail);
        final EditText etPass = findViewById(R.id.editTextPassword);
        TextView tvRegister = findViewById(R.id.textViewRegister);
        Button btnLog = findViewById(R.id.buttonLogin);

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PantallaRegistro.class);
                startActivity(intent);
            }
        });

        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                String pass = etPass.getText().toString().trim();

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)) {
                    Toast.makeText(MainActivity.this, "Rellene toda la información", Toast.LENGTH_SHORT).show();
                } else {

                    String savedEmail = sharedPreferences.getString("email", "");
                    String savedPassword = sharedPreferences.getString("pass", "");

                    if (email.equals(savedEmail) && pass.equals(savedPassword)) {
                        Toast.makeText(MainActivity.this, "Login correcto.", Toast.LENGTH_SHORT).show();
                        Intent LogSuc = new Intent(MainActivity.this, LogSucess.class);
                        startActivity(LogSuc);

                    } else {
                        Toast.makeText(MainActivity.this, "Datos incorrectos.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

}
