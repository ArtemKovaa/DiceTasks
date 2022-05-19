package com.example.dicetasks.data;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dicetasks.R;

import java.util.List;
import java.util.Stack;

public class CompletedTaskAdapter extends RecyclerView.Adapter<CompletedTaskAdapter.ViewHolder> {

    private List<CompletedTask> data;

    public CompletedTaskAdapter(final List<CompletedTask> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public CompletedTaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.completed_task_card, parent, false);
        view.setOnClickListener(v -> {
            Toast toast = Toast.makeText(v.getContext(), "Hello",Toast.LENGTH_LONG);
            toast.show();
        });
        return new CompletedTaskAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull CompletedTaskAdapter.ViewHolder holder, int position) {
        CompletedTask completedTask = data.get(position);

        holder.title.setText(completedTask.getCompletedTaskTitle());
        holder.description.setText(completedTask.getCompletedTaskDescription());
        holder.cardView.setAlpha(0.6f);

        ImageButton imageButton = holder.imageButton;

        imageButton.setOnClickListener(v-> {
            TasksDB tasksDB = TasksDB.getInstance(holder.itemView.getContext());
            TasksDao tasksDao = tasksDB.tasksDao();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    tasksDao.deleteCompletedById(completedTask.getId());
                }
            }).start();
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;
        private final TextView description;
        private final ImageButton imageButton;
        private final CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.completed_card_task_title);
            description = itemView.findViewById(R.id.completed_card_task_description);
            imageButton = itemView.findViewById(R.id.delete_task_button);
            cardView = itemView.findViewById(R.id.completed_cv);
        }

    }
}
