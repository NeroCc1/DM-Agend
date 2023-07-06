package com.example.dm_agenda;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.dm_agenda.DB.DBHelper;
import com.example.dm_agenda.TaskAdapter.OnItemClickListener;
import com.example.dm_agenda.entidades.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class homeTask extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    private List<Task> taskList;
    private DBHelper dbHelper;
    private long userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agenda);

        dbHelper = new DBHelper(this);
        recyclerView = findViewById(R.id.listaTareas);
        taskList = new ArrayList<>();

        // Obtener el ID del usuario registrado desde SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        userID = sharedPreferences.getLong("UserID", userID); // Valor predeterminado: el valor actual de userID

        // Configurar el adaptador del RecyclerView
        taskAdapter = new TaskAdapter(this, taskList);

        // Configurar el diseño del RecyclerView (puede usar un LinearLayoutManager o GridLayoutManager según sus necesidades)
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(taskAdapter);

        // Establecer un listener de clic para los elementos del RecyclerView
        taskAdapter.setOnItemClickListener(new TaskAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Obtén la tarea seleccionada en la posición dada
                Task selectedTask = taskList.get(position);

                // Crea un intent para abrir la actividad EditTaskActivity
                Intent intent = new Intent(homeTask.this, EditTaskActivity.class);

                // Pasa los datos de la tarea seleccionada a la actividad EditTaskActivity utilizando putExtra()
                intent.putExtra("selectedTask", selectedTask);

                // Inicia la actividad EditTaskActivity
                startActivity(intent);
            }
        });


        FloatingActionButton fabCreateTask = findViewById(R.id.newcreate);
        fabCreateTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redireccionar a la actividad CreateTask
                Intent intent = new Intent(homeTask.this, createTask.class);
                startActivity(intent);
            }
        });

        // Llamar al método loadTasks() para mostrar las tareas del usuario en sesión
        loadTasks();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Actualizar la vista de las tareas al volver a la actividad
        loadTasks();
    }

    private void loadTasks() {
        // Obtener las tareas del usuario actual desde la base de datos
        List<Task> updatedTaskList = dbHelper.getTasksByUserID(userID);
        taskList.clear();
        taskList.addAll(updatedTaskList);
        // Notificar al adaptador que los datos han cambiado
        taskAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        // Cerrar sesión del usuario y redirigir al Activity de inicio de sesión
        logoutUser();
        Intent intent = new Intent(homeTask.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void logoutUser() {
        // Eliminar el indicador de sesión activa de SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("IsLoggedIn", false);
        editor.apply();
    }
    public void refreshTasks() {
        // Obtener el ID del usuario registrado desde SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        long userID = sharedPreferences.getLong("UserID", -1); // Valor predeterminado -1 si no se encuentra el ID

        // Obtener las tareas del usuario actualizado
        List<Task> taskList = dbHelper.getTasksByUserID(userID);

        // Actualizar la lista de tareas en tu interfaz de usuario (RecyclerView, ListView, etc.)
        // ...
    }
}
