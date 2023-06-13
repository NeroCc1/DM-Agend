package com.example.dm_agenda;

public class Tarea {

    String TituloTarea;
    String FechaTarea;
    String  ExperienciaEducativaTarea;
    String AlarmaTarea;
    String DescripcionTarea;


    public Tarea(String TituloTarea,String FechaTarea,String  ExperienciaEducativaTarea,String AlarmaTarea,String DescripcionTarea){
        this.TituloTarea = TituloTarea;
        this.FechaTarea = FechaTarea;
        this.ExperienciaEducativaTarea = ExperienciaEducativaTarea;
        this.AlarmaTarea = AlarmaTarea;
        this.DescripcionTarea = DescripcionTarea;
    }

    public String getTituloTarea() {
        return TituloTarea;
    }

    public void setTituloTarea(String tituloTarea) {
        TituloTarea = tituloTarea;
    }

    public String getFechaTarea() {
        return FechaTarea;
    }

    public void setFechaTarea(String fechaTarea) {
        FechaTarea = fechaTarea;
    }

    public String getExperienciaEducativaTarea() {
        return ExperienciaEducativaTarea;
    }

    public void setExperienciaEducativaTarea(String experienciaEducativaTarea) {
        ExperienciaEducativaTarea = experienciaEducativaTarea;
    }

    public String getAlarmaTarea() {
        return AlarmaTarea;
    }

    public void setAlarmaTarea(String alarmaTarea) {
        AlarmaTarea = alarmaTarea;
    }

    public String getDescripcionTarea() {
        return DescripcionTarea;
    }

    public void setDescripcionTarea(String descripcionTarea) {
        DescripcionTarea = descripcionTarea;
    }


}
