package com.example.dm_agenda;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.dm_agenda.DB.DBHelper;
import com.example.dm_agenda.entidades.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class homeTask extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    private List<Task> taskList;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agenda);

        dbHelper = new DBHelper(this);
        recyclerView = findViewById(R.id.listaTareas);
        taskList = new ArrayList<>();

        // Obtener el ID del usuario registrado desde SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        long userID = sharedPreferences.getLong("UserID", -1); // Valor predeterminado -1 si no se encuentra el ID

        // Obtener las tareas del usuario desde la base de datos
        taskList = dbHelper.getTasksByUserID(userID);
        System.out.println(taskList);

        // Configurar el adaptador del RecyclerView
        taskAdapter = new TaskAdapter(this, taskList);

        // Configurar el diseño del RecyclerView (puede usar un LinearLayoutManager o GridLayoutManager según sus necesidades)
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(taskAdapter);

        FloatingActionButton fabCreateTask = findViewById(R.id.newcreate);
        fabCreateTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redireccionar a la actividad CreateTask
                Intent intent = new Intent(homeTask.this, createTask.class);
                startActivity(intent);
            }
        });
    }

}
