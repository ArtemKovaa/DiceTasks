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

public class CompletedTaskAdapter extends RecyclerView.Adapter<CompletedTaskAdapter.ViewHolder> {

    private List<CompletedTask> data;

    public CompletedTaskAdapter(final List<CompletedTask> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public CompletedTaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_card, parent, false);
        view.setOnClickListener(v -> {
            Toast toast = Toast.makeText(v.getContext(), "Hello",Toast.LENGTH_LONG);
            toast.show();
        });
        return new CompletedTaskAdapter.ViewHolder(view);
        /*return new ViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.task_card, parent, false));*/
    }


    @Override
    public void onBindViewHolder(@NonNull CompletedTaskAdapter.ViewHolder holder, int position) {
        CompletedTask completedTask = data.get(position);

        holder.title.setText(completedTask.getCompletedTaskTitle());
        holder.description.setText(completedTask.getCompletedTaskDescription());
        holder.cardView.setAlpha(0.6f);

        switch(completedTask.getCompletedTaskPriority()) {
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
        private final ImageButton imageButton;
        private final CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.cardTaskTitle);
            description = itemView.findViewById(R.id.cardTaskDescription);
            priority = itemView.findViewById(R.id.priority_line);
            imageButton = itemView.findViewById(R.id.complete_task_button);
            cardView = itemView.findViewById(R.id.cv);
        }

    }
}
