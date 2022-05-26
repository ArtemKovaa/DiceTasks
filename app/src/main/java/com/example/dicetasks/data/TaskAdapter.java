package com.example.dicetasks.data;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dicetasks.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private List<Task> data;

    public TaskAdapter(final List<Task> data) {
        this.data = data;
    }

    Disposable disposable;

    @NonNull
    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task task = data.get(position);
        CompletedTask completedTask =
                new CompletedTask(task.getTaskTitle(), task.getTaskDescription(),
                        task.getTaskCategory(), task.getTaskPriority(), task.getVisibility());
        completedTask.setUserID(task.getUserID());

        ImageButton imageButton = holder.imageButton;

        imageButton.setOnClickListener(v-> {
            TasksDB tasksDB = TasksDB.getInstance(holder.itemView.getContext());
            TasksDao tasksDao = tasksDB.tasksDao();

            disposable = tasksDao.insertCompleted(completedTask)
                    .subscribeOn(Schedulers.io()).subscribe();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                    DatabaseReference stats = FirebaseDatabase.getInstance().getReference("Statistics");
                    Query query = ref.child("Tasks").orderByChild("key").equalTo(task.getKey());

                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                                if(task.getTaskPriority() == 3) {
                                    snapshot.getRef().child("visibility").setValue(0);
                                } else {
                                    snapshot.getRef().removeValue();
                                }
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        tasksDao.deleteById(task.getId());
                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    stats.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                                Statistics statistics = snapshot.getValue(Statistics.class);
                                if (statistics != null) {
                                    if (task.getUserID().equals(statistics.getUserID())) {
                                        if(task.getTaskPriority() == 3) {
                                            snapshot.getRef().child("completedRandoms")
                                                    .setValue(statistics.getCompletedRandoms() + 1);
                                        } else {
                                            snapshot.getRef().child("completedUsers")
                                                    .setValue(statistics.getCompletedUsers() + 1);
                                        }
                                    }
                                }
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        tasksDao.deleteById(task.getId());
                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }).start();



            new Thread(new Runnable() {
                @Override
                public void run() {
                    tasksDao.deleteById(task.getId());
                }
            }).start();
        });

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
        private final ImageButton imageButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.cardTaskTitle);
            description = itemView.findViewById(R.id.cardTaskDescription);
            priority = itemView.findViewById(R.id.priority_line);
            imageButton = itemView.findViewById(R.id.complete_task_button);


        }

    }
}
