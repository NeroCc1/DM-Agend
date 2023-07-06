package com.example.dm_agenda;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.dm_agenda.DB.DBHelper;

import java.util.Calendar;
public class createTask extends AppCompatActivity {
    TextView textView;
    ImageButton imgBtnExt;
    ImageButton GuardarTareaBtn;
    EditText Descripcion;
    Spinner EE;
    Button datePickerButton;
    Button timePickerButton;
    EditText TituloTarea;

    DBHelper dbHelper;

    private int selectedYear, selectedMonth, selectedDayOfMonth;
    private int selectedHourOfDay, selectedMinute;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardar_tareas);

        dbHelper = new DBHelper(this);

        // Obtener el ID del usuario registrado desde SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        long userID = sharedPreferences.getLong("UserID", -1); // Valor predeterminado -1 si no se encuentra el ID

        textView = findViewById(R.id.textView);
        imgBtnExt = findViewById(R.id.imgBtnExt);
        GuardarTareaBtn = findViewById(R.id.GuardarTareaBtn);
        Descripcion = findViewById(R.id.Descripcion);
        EE = findViewById(R.id.EE);
        datePickerButton = findViewById(R.id.datePickerButton);
        timePickerButton = findViewById(R.id.timePickerButton);
        TituloTarea = findViewById(R.id.TituloTarea);

        // Set up the toolbar
        // Toolbar toolbar = findViewById(R.id.GuardarTarea);

        // Configure la ruleta para EE
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.ee_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        EE.setAdapter(adapter);

        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener la fecha actual
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                // Mostrar el diálogo de selección de fecha
                DatePickerDialog datePickerDialog = new DatePickerDialog(createTask.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                selectedYear = year;
                                selectedMonth = month;
                                selectedDayOfMonth = dayOfMonth;
                            }
                        }, year, month, dayOfMonth);

                // Mostrar el diálogo de selección de fecha
                datePickerDialog.show();
            }
        });

        timePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener la hora actual
                Calendar calendar = Calendar.getInstance();
                int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                // Mostrar el diálogo de selección de hora
                TimePickerDialog timePickerDialog = new TimePickerDialog(createTask.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                selectedHourOfDay = hourOfDay;
                                selectedMinute = minute;
                            }
                        }, hourOfDay, minute, false);

                // Mostrar el diálogo de selección de hora
                timePickerDialog.show();
            }
        });


        imgBtnExt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement your close activity logic here
            }
        });

        GuardarTareaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los valores de los campos de entrada
                String Titulo = TituloTarea.getText().toString().trim();
                String DescripcionTarea = Descripcion.getText().toString().trim();
                String EESelected = EE.getSelectedItem().toString();

                // Insertar la tarea en la base de datos con el ID del usuario registrado
                long taskId = dbHelper.insertTask(userID, Titulo, DescripcionTarea, EESelected);


                if (taskId != -1) {
                    Toast.makeText(createTask.this, "Tarea guardada exitosamente", Toast.LENGTH_SHORT).show();

                    // Configure la alarma usando AlarmManager
                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    Intent intent = new Intent(createTask.this, AlarmReceiver.class);
                    intent.putExtra("task_title", Titulo);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(createTask.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                    // Establecer la fecha y hora seleccionadas en el calendario
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(selectedYear, selectedMonth, selectedDayOfMonth, selectedHourOfDay, selectedMinute);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

                    // Actualizar la lista de tareas en homeTask
                    loadTasks();

                    // Finalizar la actividad createTask y volver a la actividad homeTask
                    finish();
                } else {
                    Toast.makeText(createTask.this, "Error al guardar la tarea", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loadTasks() {
        // Obtener el ID del usuario registrado desde SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        long userID = sharedPreferences.getLong("UserID", -1); // Valor predeterminado -1 si no se encuentra el ID

        // Obtener la instancia de la actividad homeTask (actividad padre)
        Activity parentActivity = getParent();

        if (parentActivity instanceof homeTask) {
            // Actualizar la lista de tareas en la actividad homeTask
            ((homeTask) parentActivity).refreshTasks();
        }
    }
}
