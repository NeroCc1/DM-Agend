package com.example.dm_agenda.entidades;
import android.os.Parcel;
import android.os.Parcelable;

public class Task implements Parcelable{
    private long id; // Nuevo campo para el ID
    private String title;
    private String description;
    private String date;
    private String time;
    private String ee;
    private long userID;

    // Constructor
    public Task(long id, String title, String description, String ee) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.ee = ee;
    }

    // Getter y Setter para el campo userID

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    // Getter y Setter para los demás campos

    // Implementación de Parcelable
    protected Task(Parcel in) {
        id = in.readLong();
        title = in.readString();
        description = in.readString();
        date = in.readString();
        time = in.readString();
        ee = in.readString();
        userID = in.readLong();
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(date);
        dest.writeString(time);
        dest.writeString(ee);
        dest.writeLong(userID);
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
