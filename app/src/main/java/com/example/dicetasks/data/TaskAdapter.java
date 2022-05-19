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
        view.setOnClickListener(v -> {
            Toast toast = Toast.makeText(v.getContext(), "Вы нажали на карточку. Зачем?",Toast.LENGTH_LONG);
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
        CompletedTask completedTask =
                new CompletedTask(task.getTaskTitle(), task.getTaskDescription(),
                        task.getTaskCategory(), task.getTaskPriority(), task.getVisibility());
        ImageButton imageButton = holder.imageButton;

        imageButton.setOnClickListener(v-> {
            TasksDB tasksDB = TasksDB.getInstance(holder.itemView.getContext());
            TasksDao tasksDao = tasksDB.tasksDao();

            /*int countOfRandom = 0;

            for(Task elem: data){
                if(elem.getTaskPriority() == 3 && elem.getVisibility() != 0)
                    countOfRandom++;
            }
            if(countOfRandom == 3)
            {
                for(Task elem: data){
                    if(elem.getTaskPriority() == 3 && elem.getVisibility() == 0) {
                        Task toVisible = elem;
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                tasksDao.deleteById(elem.getId());
                            }
                        }).start();

                        toVisible.setVisibility(1);
                        disposable = tasksDao.insert(toVisible)
                                .subscribeOn(Schedulers.io()).subscribe();

                        //THE WORST PART OF OUR PROJECT TODO : remove break
                        break;
                    }
                }
            }*/

            new Thread(new Runnable() {
                @Override
                public void run() {
                    disposable = tasksDao.insertCompleted(completedTask)
                            .subscribeOn(Schedulers.io()).subscribe();
                }
            }).start();

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
