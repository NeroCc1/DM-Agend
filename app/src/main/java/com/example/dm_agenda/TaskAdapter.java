package com.example.dm_agenda;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dm_agenda.entidades.Task;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private Context context;
    private List<Task> taskList;

    public TaskAdapter(Context context, List<Task> taskList) {
        this.context = context;
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);

        holder.txtTitulo.setText(task.getTitle());
        holder.txtEE.setText(task.getEe());
        holder.txtFecha.setText(task.getDate());
        // Configurar otros datos de la tarea en los elementos de la vista
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView txtTitulo;
        TextView txtEE;
        TextView txtFecha;
        // Otros elementos de la vista relacionados con la tarea

        public TaskViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            txtTitulo = itemView.findViewById(R.id.TituloTarea);
            txtEE = itemView.findViewById(R.id.EE);
            txtFecha = itemView.findViewById(R.id.datePickerButton);
            // Inicializar otros elementos de la vista relacionados con la tarea
        }
    }
}
