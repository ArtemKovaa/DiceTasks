package com.example.dicetasks;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.dicetasks.data.Task;
import com.example.dicetasks.data.TasksDB;
import com.example.dicetasks.data.TasksDao;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NewTaskFragment extends Fragment {

    ImageButton returnButton;
    ImageButton addTaskButton;
    EditText taskHeaderView;
    EditText taskDescriptionView;
    RadioButton highPriorityButton;
    RadioButton mediumPriorityButton;
    RadioButton lowPriorityButton;

    DatabaseReference dataBase;
    private String TABlE = "Tasks";

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

        dataBase = FirebaseDatabase.getInstance().getReference(TABlE);

        returnButton = view.findViewById(R.id.returnButton);
        addTaskButton = view.findViewById(R.id.addTaskButton);

        taskHeaderView = view.findViewById(R.id.taskHeaderEditText);
        taskDescriptionView = view.findViewById(R.id.taskDescEditText);

        highPriorityButton = view.findViewById(R.id.high_priority_button);
        mediumPriorityButton = view.findViewById(R.id.medium_priority_button);
        lowPriorityButton = view.findViewById(R.id.low_priority_button);

        returnButton.setOnClickListener(v -> {
            returnToHomeScreen();

            //Makes the navView appear back
            View navView = requireActivity().findViewById(R.id.nav_view);
            navView.setVisibility(View.VISIBLE);
        });

        addTaskButton.setOnClickListener(v -> {
            Task task = readFromUI();

            if (task == null) {
                Toast
                        .makeText(getActivity(), "Получены не все данные!", Toast.LENGTH_SHORT)
                        .show();
                return;
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    task.setUserID(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    String key = dataBase.push().getKey();
                    task.setKey(key);
                    dataBase.child(key).setValue(task);
                }
            }).start();

            returnToHomeScreen();
            /*disposable = tasksDao.insert(task)
                    .subscribeOn(Schedulers.io()).subscribe(this::returnToHomeScreen);*/

            //Makes the navView appear back
            View navView = requireActivity().findViewById(R.id.nav_view);
            navView.setVisibility(View.VISIBLE);
        });

        highPriorityButton.setOnClickListener(v -> {
            highPriorityButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.high_priority_outline));
            highPriorityButton.setTextColor(ContextCompat.getColor(getContext(), R.color.black));

            mediumPriorityButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.default_outline));
            mediumPriorityButton.setTextColor(ContextCompat.getColor(getContext(), R.color.grey));

            lowPriorityButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.default_outline));
            lowPriorityButton.setTextColor(ContextCompat.getColor(getContext(), R.color.grey));
            taskPriority = 2;
        });

        mediumPriorityButton.setOnClickListener(v -> {
            highPriorityButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.default_outline));
            highPriorityButton.setTextColor(ContextCompat.getColor(getContext(), R.color.grey));

            mediumPriorityButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.medium_priority_outline));
            mediumPriorityButton.setTextColor(ContextCompat.getColor(getContext(), R.color.black));

            lowPriorityButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.default_outline));
            lowPriorityButton.setTextColor(ContextCompat.getColor(getContext(), R.color.grey));

            taskPriority = 1;
        });

        lowPriorityButton.setOnClickListener(v -> {
            highPriorityButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.default_outline));
            highPriorityButton.setTextColor(ContextCompat.getColor(getContext(), R.color.grey));

            mediumPriorityButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.default_outline));
            mediumPriorityButton.setTextColor(ContextCompat.getColor(getContext(), R.color.grey));

            lowPriorityButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.low_priority_outline));
            lowPriorityButton.setTextColor(ContextCompat.getColor(getContext(), R.color.black));

            taskPriority = 0;
        });

        //Replaces the event of pressing down the system back button
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                View navView = requireActivity().findViewById(R.id.nav_view);
                navView.setVisibility(View.VISIBLE);
                returnToHomeScreen();
            }
        };
        // TODO: check if getViewLifecycleOwner is not in very high SDK (21 required)
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);

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
