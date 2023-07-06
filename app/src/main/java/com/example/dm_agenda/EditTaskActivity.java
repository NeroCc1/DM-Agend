package com.example.dm_agenda;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dm_agenda.DB.DBHelper;
import com.example.dm_agenda.entidades.Task;

public class EditTaskActivity extends AppCompatActivity {
    EditText DescripcionMod;
    Spinner EEMod;
    EditText mifTitulo;

    ImageButton btnDeleteTask;
    ImageButton btnUpdateTask;

    private long taskId;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewtask);
        dbHelper = new DBHelper(this);

        DescripcionMod = findViewById(R.id.DescripcionMod);
        EEMod = findViewById(R.id.EEMod);
        mifTitulo = findViewById(R.id.mifTitulo);
        btnUpdateTask = findViewById(R.id.btnUpdateTask);
        btnDeleteTask = findViewById(R.id.btnDeleteTask);

        // Obtener el ID de la tarea seleccionada
        taskId = getIntent().getLongExtra("taskId", -1);
        if (taskId == -1) {
            // ID inválido, finalizar la actividad
            Toast.makeText(this, "Error al cargar la tarea", Toast.LENGTH_SHORT).show();
            finish();
        }



        // Obtener los datos de la tarea de la base de datos y mostrarlos en los campos de texto
        Task task = dbHelper.getTaskById(taskId);
        if (task != null) {
            mifTitulo.setText(task.getTitle());
            DescripcionMod.setText(task.getDescription());
        }

        btnUpdateTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los nuevos datos de la tarea desde los campos de texto
                String newTaskTitle = mifTitulo.getText().toString().trim();
                String newTaskDescription = DescripcionMod.getText().toString().trim();

                // Actualizar la tarea en la base de datos
                task.setTitle(newTaskTitle);
                task.setDescription(newTaskDescription);
                int rowsUpdated = dbHelper.updateTask(task);


                if (rowsUpdated > 0) {
                    Toast.makeText(EditTaskActivity.this, "Tarea actualizada", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(EditTaskActivity.this, "Error al actualizar la tarea", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDeleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Eliminar la tarea de la base de datos
                int rowsDeleted = dbHelper.deleteTask(task);

                if (rowsDeleted > 0) {
                    Toast.makeText(EditTaskActivity.this, "Tarea eliminada", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(EditTaskActivity.this, "Error al eliminar la tarea", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}