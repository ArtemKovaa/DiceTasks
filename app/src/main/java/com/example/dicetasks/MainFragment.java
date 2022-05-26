package com.example.dicetasks;

import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Objects;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainFragment extends Fragment {

    RecyclerView recyclerView;
    List<Task> loadedTasks;
    final String TABlE = "Tasks";
    DatabaseReference dataBase;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.main_fragment, container, false);

        dataBase = FirebaseDatabase.getInstance().getReference(TABlE);

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

    private void getDataFromDB() {
        TasksDB tasksDB = TasksDB.getInstance(getActivity());
        TasksDao tasksDao = tasksDB.tasksDao();

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                tasksDao.deleteAllTasks().subscribeOn(Schedulers.single()).subscribe();
                for (DataSnapshot ds : snapshot.getChildren()) {

                    Task task = ds.getValue(Task.class);
                    if (task != null) {
                        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                        if (currentUser != null && task.getUserID().equals(currentUser.getUid())) {
                            tasksDao.insert(task)
                                    .subscribeOn(Schedulers.single()).subscribe();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        dataBase.addValueEventListener(valueEventListener);
    }

    private void initRecyclerView(View v) {
        RecyclerView recyclerView = v.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getParent());
        recyclerView.setLayoutManager(layoutManager);
    }

    private void firstStartRandomLoad() {

    }

    @NonNull
    @Override
    public String toString() {
        return "MainFragment";
    }

}