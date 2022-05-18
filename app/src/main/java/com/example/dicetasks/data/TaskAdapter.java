package com.example.dicetasks.data;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dicetasks.R;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private List<Task> data;

    public TaskAdapter(final List<Task> data) {
        this.data = data;
    }


    @NonNull
    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_card, parent, false);
        view.setOnClickListener(v -> {
            Toast toast = Toast.makeText(v.getContext(), "Hello",Toast.LENGTH_LONG);
            toast.show();
        });
        return new ViewHolder(view);
        /*return new ViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.task_card, parent, false));*/
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task task = data.get(position);
        RadioButton radioButton = holder.radioButton;

        radioButton.setOnClickListener(v-> {
            TasksDB tasksDB = TasksDB.getInstance(holder.itemView.getContext());
            TasksDao tasksDao = tasksDB.tasksDao();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    tasksDao.deleteById(task.getId());
                }
            }).start();
        });

        /*Log.e("onBindViewHolder", "Title: " + task.getTaskTitle()+ " Desc: " +
                task.getTaskDescription() + " Visabilbity" + task.getVisibility());*/
        if(task.getVisibility() == 0) {
            holder.itemView.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
            return;
        }
        holder.title.setText(task.getTaskTitle());
        holder.description.setText(task.getTaskDescription());

        switch(task.getTaskPriority()) {
            case 0:
                holder.priority.setBackgroundColor(ContextCompat.getColor(holder.title.getContext(), R.color.light_green));
                break;
            case 1:
                holder.priority.setBackgroundColor(ContextCompat.getColor(holder.title.getContext(), R.color.green));
                break;
            case 2:
                holder.priority.setBackgroundColor(ContextCompat.getColor(holder.title.getContext(), R.color.dark_green));
                break;
            case 3:
                holder.priority.setBackgroundColor(ContextCompat.getColor(holder.title.getContext(), R.color.purple));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;
        private final TextView description;
        private final View priority;
        private final RadioButton radioButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.cardTaskTitle);
            description = itemView.findViewById(R.id.cardTaskDescription);
            priority = itemView.findViewById(R.id.priority_line);
            radioButton = itemView.findViewById(R.id.delete_task_button);


        }

    }
}
