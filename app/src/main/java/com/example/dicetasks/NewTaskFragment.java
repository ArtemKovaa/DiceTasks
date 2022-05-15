package com.example.dicetasks;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.dicetasks.data.Task;
import com.example.dicetasks.data.TasksDB;
import com.example.dicetasks.data.TasksDao;

import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NewTaskFragment extends Fragment {

    Button returnButton;
    Button addTaskButton;
    EditText taskHeaderView;
    EditText taskDescriptionView;
    RadioButton highPriorityButton;
    RadioButton mediumPriorityButton;
    RadioButton lowPriorityButton;

    Integer taskPriority;

    Disposable disposable;

    private FragmentActivity activityContext;

    @Override
    public void onAttach(@NonNull Activity activity) {
        activityContext = (FragmentActivity) activity;
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_new_task, container, false);

        TasksDB tasksDB = TasksDB.getInstance(getActivity());
        TasksDao tasksDao = tasksDB.tasksDao();

        returnButton = view.findViewById(R.id.returnButton);
        addTaskButton = view.findViewById(R.id.addTaskButton);

        taskHeaderView = view.findViewById(R.id.taskHeaderEditText);
        taskDescriptionView = view.findViewById(R.id.taskDescEditText);

        highPriorityButton = view.findViewById(R.id.high_priority_button);
        mediumPriorityButton = view.findViewById(R.id.medium_priority_button);
        lowPriorityButton = view.findViewById(R.id.low_priority_button);

        returnButton.setOnClickListener(v -> {
            returnToHomeScreen();
        });

        addTaskButton.setOnClickListener(v -> {
            Task task = readFromUI();

            if (task == null) {
                Toast
                        .makeText(getActivity(), "Получены не все данные!", Toast.LENGTH_SHORT)
                        .show();
                return;
            }

            disposable = tasksDao.insert(task)
                    .subscribeOn(Schedulers.io()).subscribe(this::returnToHomeScreen);
        });

        highPriorityButton.setOnClickListener(v -> {
            taskPriority = 2;
        });

        mediumPriorityButton.setOnClickListener(v -> {
            taskPriority = 1;
        });

        lowPriorityButton.setOnClickListener(v -> {
            taskPriority = 0;
        });

        return view;
    }

    private Task readFromUI() {
        String taskTitle = taskHeaderView.getText().toString();
        String taskDescription = taskDescriptionView.getText().toString();
        int taskCategory = 5;

        if (taskTitle.isEmpty() || taskDescription.isEmpty() || taskPriority == null)
            return null;

        return new Task(taskTitle, taskDescription, taskCategory, taskPriority, 1);
    }

    private void returnToHomeScreen() {
        activityContext.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, MainFragment.class, null)
                .commit();

    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (disposable != null) disposable.dispose();
    }

    @NonNull
    @Override
    public String toString() {
        return "NewTaskFragment";
    }
}
