package com.example.dm_agenda;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class homeTasks extends AppCompatActivity {
    TextView TituloTarea;
    TextView FechaTarea;
    Spinner ExperienciaEducativaTarea;
    Spinner AlarmaTarea;
    EditText DescripcionTarea;

    ImageButton GuardarTareaBtn;

    SharedPreferences shPreferences;
    String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardar_tareas);

        TituloTarea = findViewById(R.id.TituloTarea);
        FechaTarea = findViewById(R.id.FechaTarea);
        ExperienciaEducativaTarea = findViewById(R.id.EE);
        AlarmaTarea = findViewById(R.id.Alarma);
        DescripcionTarea = findViewById(R.id.Descripcion);
        GuardarTareaBtn = findViewById(R.id.GuardarTareaBtn);

        shPreferences = getSharedPreferences("SavedData", MODE_PRIVATE);

        // Obtener el usuario actual de las preferencias compartidas
        SharedPreferences sharedPrefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        currentUser = sharedPrefs.getString("email", "");

        // Define an array of items
        String[] items = {"Algebra lineal", "Calculo diferencial", "Español"};

        // Create an ArrayAdapter using the array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);

        // Set the layout style for the dropdown items
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set the adapter to the Spinner
        ExperienciaEducativaTarea.setAdapter(adapter);

        // Define an array of items
        String[] item = {"Urgente", "Leve", "Mínimo"};

        // Create an ArrayAdapter using the array and a default spinner layout
        ArrayAdapter<String> adapto = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, item);

        // Set the layout style for the dropdown items
        adapto.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set the adapter to the Spinner
        AlarmaTarea.setAdapter(adapto);


        GuardarTareaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the values from the input fields
                String Titulo = TituloTarea.getText().toString().trim();
                String fecha = FechaTarea.getText().toString().trim();
                String EE = (String) ExperienciaEducativaTarea.getSelectedItem();
                String Alarma = (String) AlarmaTarea.getSelectedItem();
                String Descripcion = DescripcionTarea.getText().toString().trim();

                // Retrieve the previous JSON array from SharedPreferences
                String arrayprevio = shPreferences.getString("ArrayJSON", "");

                // Create an ArrayList to hold the tarea objects
                ArrayList<Tarea> tareas = new ArrayList<>();

                try {
                    // Parse the previous JSON array
                    JSONArray jsonArray = new JSONArray(arrayprevio);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String tTare = jsonObject.getString("TituloTarea");
                        String fTare = jsonObject.getString("FechaTarea");
                        String EEt = jsonObject.getString("ExperienciaEducativaTarea");
                        String ATr = jsonObject.getString("AlarmaTarea");
                        String DscT = jsonObject.getString("DescripcionTarea");
                        // Add tarea object to the ArrayList if it belongs to the current user
                        if (currentUser.equals(jsonObject.getString("Usuario"))) {
                            tareas.add(new Tarea(tTare, fTare, EEt, ATr, DscT));
                        }
                    }

                    // Create a new Tarea object with the input values and add it to the ArrayList
                    tareas.add(new Tarea(Titulo, fecha, EE, Alarma, Descripcion));

                    // Create a new JSONArray and add JSON objects for each tarea in the ArrayList
                    JSONArray jsonArreglo = new JSONArray();
                    for (Tarea tarea : tareas) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("TituloTarea", tarea.getTituloTarea());
                        jsonObject.put("FechaTarea", tarea.getFechaTarea());
                        jsonObject.put("ExperienciaEducativaTarea", tarea.getExperienciaEducativaTarea());
                        jsonObject.put("AlarmaTarea", tarea.getAlarmaTarea());
                        jsonObject.put("DescripcionTarea", tarea.getDescripcionTarea());
                        jsonObject.put("Usuario", currentUser); // Add the current user to the JSON object
                        jsonArreglo.put(jsonObject);
                    }

                    // Convert the JSONArray to a string
                    String jsonString = jsonArreglo.toString();

                    // Save the updated JSON string to SharedPreferences
                    SharedPreferences.Editor editor = shPreferences.edit();
                    editor.putString("ArrayJSON", jsonString);
                    editor.apply();

                    // Print the updated JSON string
                    System.out.println(shPreferences.getString("ArrayJSON", ""));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
