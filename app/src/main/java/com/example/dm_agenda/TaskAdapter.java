package com.example.dm_agenda;

import android.content.Context;
import android.content.Intent;
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
    private OnItemClickListener listener;


    public TaskAdapter(Context context, List<Task> taskList) {
        this.context = context;
        this.taskList = taskList;
    }
    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, final int position) {
        final Task task = taskList.get(position);
        holder.txtTitulo.setText(task.getTitle());
        holder.txtEE.setText(task.getEe());

        final int clickedPosition = position; // Crear una variable final y asignarle el valor de position

        // Manejar el evento de clic en una tarea
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(clickedPosition); // Utilizar la variable clickedPosition
                }
            }
        });
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
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


}
