package com.example.dm_agenda;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class createTask extends AppCompatActivity {
    TextView HmTxtTareas;
    SharedPreferences shPreferences;
    String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agenda);

        FloatingActionButton fb = findViewById(R.id.fb);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear un Intent para iniciar la nueva actividad
                Intent intent = new Intent(createTask.this, homeTasks.class);
                startActivity(intent);
            }
        });

        shPreferences = getSharedPreferences("SavedData", MODE_PRIVATE);
        HmTxtTareas = findViewById(R.id.HmTxtTareas);

        // Obtener el usuario actual de las preferencias compartidas
        SharedPreferences sharedPrefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        currentUser = sharedPrefs.getString("email", "");

        String jsonString = shPreferences.getString("ArrayJSON", "");

        if (!jsonString.isEmpty()) {
            try {
                // Convierte la cadena JSON a una lista de objetos
                Gson gson = new GsonBuilder().create();
                Type tipoLista = new TypeToken<List<Map<String, Object>>>() {}.getType();
                List<Map<String, Object>> listaObjetos = gson.fromJson(jsonString, tipoLista);

                // Construye la cadena formateada
                StringBuilder stringBuilder = new StringBuilder();
                for (Map<String, Object> objeto : listaObjetos) {
                    String usuario = (String) objeto.get("Usuario");
                    // Verifica si la tarea pertenece al usuario actual
                    if (currentUser.equals(usuario)) {
                        stringBuilder.append("- Titulo: ").append(objeto.get("TituloTarea"))
                                .append(", Fecha: ").append(objeto.get("FechaTarea"))
                                .append(", EE: ").append(objeto.get("ExperienciaEducativaTarea"))
                                .append(", Alarma: ").append(objeto.get("AlarmaTarea"))
                                .append(", Descripcion: ").append(objeto.get("DescripcionTarea"))
                                .append("\n")
                                .append("\n");
                    }
                }

                String listaFormateada = stringBuilder.toString();

                // Muestra la cadena formateada en el TextView
                HmTxtTareas.setText(listaFormateada);
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
                // Maneja cualquier error de análisis de JSON aquí
            }
        } else {
            // Si el jsonString está vacío, puedes mostrar un mensaje alternativo o hacer algo más
            HmTxtTareas.setText("No hay datos disponibles");
        }
    }

    public void cerrarActividad(View view) {
        // Finaliza la actividad actual
        finish();

        // Redirige a la actividad createTask
        Intent intent = new Intent(this, createTask.class);
        startActivity(intent);
    }
}
