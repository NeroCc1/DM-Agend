package com.example.dm_agenda;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ///IDs de elementos utilizados en el layout.
        final EditText etEmail = findViewById(R.id.editTextEmail);
        final EditText etPass = findViewById(R.id.editTextPassword);
        TextView tvRegister = findViewById(R.id.textViewRegister);
        Button btnLog = findViewById(R.id.buttonLogin);

        //Intent al layout de registro.
        tvRegister.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, PantallaRegistro.class);
            startActivity(intent);
        });

        btnLog.setOnClickListener(v -> {
            //Recuperación de la información en los textField.
            String email = etEmail.getText().toString().trim();
            String pass = etPass.getText().toString().trim();

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)) { //Verificación de campos no vacíos.
                Toast.makeText(MainActivity.this, "Rellene toda la información", Toast.LENGTH_SHORT).show();
            } else {
                //Se crean instancias de la BD para poder leer la información.
                DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
                SQLiteDatabase db = databaseHelper.getReadableDatabase();
//Se crea un arreglo que permite recuperar las columbas Email y Password de la BD.
                String[] projection = {DatabaseHelper.COLUMN_EMAIL, DatabaseHelper.COLUMN_PASSWORD};
//Se buscan coincidencias de valor '?' en las columnas EMAIL y PASSWORD de la BD esperando respuesta a remplazar..
                String selection = DatabaseHelper.COLUMN_EMAIL + " = ? AND " + DatabaseHelper.COLUMN_PASSWORD + " = ?";
//Valores que van a remplazar las columnas con valor '?'.
                String[] selectionArgs = {email, pass};
//Se ejecuta la consulta.
                Cursor cursor = db.query(DatabaseHelper.TABLE_USERS, projection, selection, selectionArgs, null, null, null);

                if (cursor.moveToFirst()) { //Verifica si hay resultados.
                    // Las credenciales son correctas, redireccionar a la actividad de éxito de inicio de sesión
                    Toast.makeText(MainActivity.this, "Login correcto.", Toast.LENGTH_SHORT).show();
                    Intent LogSuc = new Intent(MainActivity.this, LogSucess.class);
                    LogSuc.putExtra("email", email); //Se manda el valor del email al siguiente activity.
                    startActivity(LogSuc);
                } else {
                    // Las credenciales son incorrectas
                    Toast.makeText(MainActivity.this, "Datos incorrectos.", Toast.LENGTH_SHORT).show();
                }
//Se cierran conexiones.
                cursor.close();
                db.close();
            }
        });
    }
}
