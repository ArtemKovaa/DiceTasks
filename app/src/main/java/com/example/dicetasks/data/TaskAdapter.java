package com.example.dicetasks.data;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dicetasks.MainActivity;
import com.example.dicetasks.MainFragment;
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
        Log.e("onBindViewHolder", "Title: " + task.getTaskTitle()+ " Desc: " +
                task.getTaskDescription() + " Visabilbity" + task.getVisibility());
        if(task.getVisibility() == 0) {
            holder.itemView.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
            return;
        }
        holder.title.setText(task.getTaskTitle());
        holder.description.setText(task.getTaskDescription());

        switch(task.getTaskPriority()) {
            case 0:
                holder.priority.setBackgroundColor(ContextCompat.getColor(holder.title.getContext(), R.color.yellow));
                break;
            case 1:
                holder.priority.setBackgroundColor(ContextCompat.getColor(holder.title.getContext(), R.color.orange));
                break;
            case 2:
                holder.priority.setBackgroundColor(ContextCompat.getColor(holder.title.getContext(), R.color.red));
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.cardTaskTitle);
            description = itemView.findViewById(R.id.cardTaskDescription);
            priority = itemView.findViewById(R.id.priority_line);
        }
    }
}
