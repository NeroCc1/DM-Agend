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
    private static final String DATABASE_NAME = "tareas4.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Tasks";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITULO = "titulo";
    private static final String COLUMN_DESCRIPCION = "descripcion";
    private static final String COLUMN_EE = "ee";
    private static final String COLUMN_FECHA_HORA = "fecha_hora";
    private static final String COLUMN_USER_ID = "userID";

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
                COLUMN_USER_ID + " INTEGER)"; // Agregar la columna userID de tipo INTEGER
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
        values.put(COLUMN_USER_ID, userID); // Asociar el ID del usuario con la tarea
        values.put(COLUMN_TITULO, titulo);
        values.put(COLUMN_DESCRIPCION, descripcion);
        values.put(COLUMN_EE, ee);
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
                COLUMN_EE.toLowerCase(),
                COLUMN_USER_ID.toLowerCase() // Incluir la columna userID en la proyección
        };

        String selection = COLUMN_USER_ID + " = ?";
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
            task.setUserID(userID); // Establecer el ID del usuario en la tarea
            taskList.add(task);
        }

        cursor.close();
        db.close();

        return taskList;
    }
    public Task getTaskById(long taskId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Task task = null;

        String[] projection = {
                COLUMN_ID,
                COLUMN_TITULO,
                COLUMN_DESCRIPCION,
                COLUMN_EE,
                COLUMN_USER_ID
        };

        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(taskId)};

        Cursor cursor = db.query(
                TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            long taskID = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID));
            String titulo = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITULO));
            String descripcion = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPCION));
            String ee = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EE));
            long userID = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_USER_ID));

            task = new Task(taskID, titulo, descripcion, ee);
            task.setUserID(userID);
        }

        if (cursor != null) {
            cursor.close();
        }

        db.close();

        return task;
    }
    public int updateTask(Task task) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITULO, task.getTitle());
        values.put(COLUMN_DESCRIPCION, task.getDescription());
        // Agregar los demás campos que necesites actualizar

        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = { String.valueOf(task.getId()) };

        int rowsUpdated = db.update(TABLE_NAME, values, selection, selectionArgs);
        db.close();

        return rowsUpdated;
    }
    public int deleteTask(Task task) {
        SQLiteDatabase db = getWritableDatabase();
        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = { String.valueOf(task.getId()) };
        int rowsDeleted = db.delete(TABLE_NAME, selection, selectionArgs);
        db.close();
        return rowsDeleted;
    }
}
