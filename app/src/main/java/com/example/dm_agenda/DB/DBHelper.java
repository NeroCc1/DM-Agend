package com.example.dm_agenda.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.dm_agenda.entidades.Task;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "tareas3.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Tasks";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITULO = "titulo";
    private static final String COLUMN_DESCRIPCION = "descripcion";
    private static final String COLUMN_EE = "ee";
    private static final String COLUMN_FECHA_HORA = "fecha_hora";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITULO + " TEXT, " +
                COLUMN_DESCRIPCION + " TEXT, " +
                COLUMN_EE + " TEXT, " +
                COLUMN_FECHA_HORA + " TEXT, " +
                "userID INTEGER)"; // Agregar la columna userID de tipo INTEGER
        db.execSQL(createTableQuery);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long insertTask(long userID, String titulo, String descripcion, String ee) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("userID", userID); // Asociar el ID del usuario con la tarea
        values.put("titulo", titulo);
        values.put("descripcion", descripcion);
        values.put("ee", ee);
        long taskId = db.insert(TABLE_NAME, null, values); // Utilizar el nombre de la tabla definido en la constante TABLE_NAME
        db.close();
        return taskId;
    }

    public Cursor getAllTasks() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    public List<Task> getTasksByUserID(long userID) {
        List<Task> taskList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                COLUMN_ID.toLowerCase(),
                COLUMN_TITULO.toLowerCase(),
                COLUMN_DESCRIPCION.toLowerCase(),
                COLUMN_EE.toLowerCase()
        };

        String selection = "userID = ?";
        String[] selectionArgs = {String.valueOf(userID)};

        Cursor cursor = db.query(
                TABLE_NAME, // Utilizar el nombre de la tabla definido en la constante TABLE_NAME
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            long taskID = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID));
            String titulo = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITULO));
            String descripcion = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPCION));
            String ee = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EE));

            Task task = new Task(taskID, titulo, descripcion, ee);
            taskList.add(task);
        }

        cursor.close();
        db.close();

        return taskList;
    }
}
