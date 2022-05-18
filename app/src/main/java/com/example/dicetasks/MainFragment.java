package com.example.dicetasks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dicetasks.data.Task;
import com.example.dicetasks.data.TaskAdapter;
import com.example.dicetasks.data.TasksDB;
import com.example.dicetasks.data.TasksDao;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainFragment extends Fragment {

    FloatingActionButton addTaskButton;
    private FragmentActivity activityContext;
    private TaskAdapter taskAdapter;
    RecyclerView recyclerView;
    List<Task> loadedTasks;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.main_fragment, container, false);

        getActivity().findViewById(R.id.toolbar).setVisibility(View.VISIBLE);
        TextView textView = getActivity().findViewById(R.id.active_tasks_text);
        textView.setText("Активные задания");

        TasksDB tasksDB = TasksDB.getInstance(getActivity());
        TasksDao tasksDao = tasksDB.tasksDao();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        //addTaskButton = view.findViewById(R.id.add_task_button);
        /*addTaskButton.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, NewTaskFragment.class, null)
                    .setReorderingAllowed(true)
                    .addToBackStack("name")
                    .commit();
        });*/

        tasksDao.getTasks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Task>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull List<Task> tasks) {
                        loadedTasks = tasks;
                        TaskAdapter adapter = new TaskAdapter(loadedTasks);
                        recyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        initRecyclerView(view);
        return view;
    }

    public void initRecyclerView(View v) {
        RecyclerView recyclerView = v.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getParent());
        recyclerView.setLayoutManager(layoutManager);
    }

    @NonNull
    @Override
    public String toString() {
        return "MainFragment";
    }

}