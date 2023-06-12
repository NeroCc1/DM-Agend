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

        ///IDs de elementos utilizados en el layout.
        final EditText etEmail = findViewById(R.id.editTextEmail);
        final EditText etPass = findViewById(R.id.editTextPassword);
        TextView tvRegister = findViewById(R.id.textViewRegister);
        Button btnLog = findViewById(R.id.buttonLogin);

        //Inicialización de SharedPreferences.
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        tvRegister.setOnClickListener(new View.OnClickListener() { //Intent al layout de registro.
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PantallaRegistro.class);
                startActivity(intent);
            }
        });

        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Recuperar el texto almacenado en los textFields.
                String email = etEmail.getText().toString().trim();
                String pass = etPass.getText().toString().trim();

                //Verificación si alguno de los textFields(email o password) se encuentra vacío.
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)) {
                    Toast.makeText(MainActivity.this, "Rellene toda la información", Toast.LENGTH_SHORT).show();
                } else {

//Se recupera los valores almacenados en 'email' y 'pass' del objeto SharedPreferences realizado en activity de registro.
                    String savedEmail = sharedPreferences.getString("email", "");
                    String savedPassword = sharedPreferences.getString("pass", "");

//Comparación si las cadenas guardadas en los textField de este activity son iguales a las cadenas almacenadas usando sharedPreferences.
                    if (email.equals(savedEmail) && pass.equals(savedPassword)) {
                        //En caso que sean iguales, muestra un Toast y redirecciona a otra activity de login.
                        Toast.makeText(MainActivity.this, "Login correcto.", Toast.LENGTH_SHORT).show();
                        Intent LogSuc = new Intent(MainActivity.this, LogSucess.class);
                        startActivity(LogSuc);
                    } else {
                        //En caso contrario, un Toast que muestra el mensaje que los datos ingresados son incorrectos por no estar almacenados.
                        Toast.makeText(MainActivity.this, "Datos incorrectos.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
