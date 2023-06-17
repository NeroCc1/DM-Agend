package com.example.dm_agenda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PantallaRegistro extends AppCompatActivity {
//Declaración de elementos.
    EditText regEmail;
    EditText regPass;
    EditText regPassCon;
    Button btnRegister;
    DatabaseHelper databaseHelper = new DatabaseHelper(PantallaRegistro.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_registro);

        //Recuperación de elementos.
        regEmail = findViewById(R.id.registerEmail);
        regPass = findViewById(R.id.registerPass);
        regPassCon = findViewById(R.id.registerPassConfirm);
        btnRegister = findViewById(R.id.btnRegister);

        //Botón de "regreso" en el navBar.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blue)));
        }

        btnRegister.setOnClickListener(v -> {
//Recuperación de los valores en los textFields.
            String email = regEmail.getText().toString().trim();
            String pass = regPass.getText().toString().trim();
            String conPass = regPassCon.getText().toString().trim();
//Verificación de campos no vacíos e igualdad en las contraseñas.
            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(conPass)) {
                Toast.makeText(PantallaRegistro.this, "Rellene toda la información", Toast.LENGTH_SHORT).show();
            } else if (!pass.equals(conPass)) {
                Toast.makeText(PantallaRegistro.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            } else {
//Instancia de la base de datos para escribir en ella.
                SQLiteDatabase db = databaseHelper.getWritableDatabase();
//Se crea un objeto para añadir valores. Se añaden los valores de email y contraseña al objeeto values.
                ContentValues values = new ContentValues();
                values.put(DatabaseHelper.COLUMN_EMAIL, email);
                values.put(DatabaseHelper.COLUMN_PASSWORD, pass);
//Se almacenan los valores de values en la BD de usuarios.
                long result = db.insert(DatabaseHelper.TABLE_USERS, null, values);
                db.close(); //Se cierra la conexión de la BD.

//Se verifica el resultado, si es distinto a -1 significa que se almacenó correctamente la información.
                if (result != -1) {
                    Toast.makeText(PantallaRegistro.this, "Registro exitoso.", Toast.LENGTH_SHORT).show();
                    finish(); //Finalización de la activity y retorna a la activity anterior.
                } else {
                    Toast.makeText(PantallaRegistro.this, "Error al registrar.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Método que permite qué, al seleccionar regresar al menu nos regrese al home.
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

