package com.example.dm_agenda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PantallaRegistro extends AppCompatActivity {

    EditText regEmail;
    EditText regPass;
    EditText regPassCon;
    Button btnRegister;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_registro);

        regEmail = findViewById(R.id.registerEmail);
        regPass = findViewById(R.id.registerPass);
        regPassCon = findViewById(R.id.registerPassConfirm);
        btnRegister = findViewById(R.id.btnRegister);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blue)));
        }


        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = regEmail.getText().toString().trim();
                String pass = regPass.getText().toString().trim();
                String conPass = regPassCon.getText().toString().trim();

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(conPass)) {
                    Toast.makeText(PantallaRegistro.this, "Rellene toda la información", Toast.LENGTH_SHORT).show();
                } else if (!pass.equals(conPass)) {
                    Toast.makeText(PantallaRegistro.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                } else {

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("email", email);
                    editor.putString("pass", pass);
                    editor.apply();

                    Toast.makeText(PantallaRegistro.this, "Registro exitoso.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });


    }

    //Método que permite qué, al seleccionar regresar al menu nos regrese al home.
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

