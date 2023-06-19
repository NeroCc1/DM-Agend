package com.example.dm_agenda.entidades;

public class Task {
    private long id; // Nuevo campo para el ID
    private String title;
    private String description;
    private String date;
    private String time;
    private String ee;

    public Task(long id, String title, String description, String ee) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.ee = ee;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getEe() {
        return ee;
    }

    public void setEe(String ee) {
        this.ee = ee;
    }
}
