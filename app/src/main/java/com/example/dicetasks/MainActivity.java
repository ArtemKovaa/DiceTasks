package com.example.dicetasks;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dicetasks.data.Task;
import com.example.dicetasks.data.TasksDB;
import com.example.dicetasks.data.TasksDao;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Random;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends FragmentActivity {

    PopupWindow popupWindow;
    CoordinatorLayout parent;
    BottomNavigationView navigation;
    View addBut;
    private FirebaseAuth mAuth;
    private String TABlE = "Tasks";

    DatabaseReference dataBase = FirebaseDatabase.getInstance().getReference("Tasks");

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_add:
                    if (getSupportFragmentManager().findFragmentById(R.id.fragment_container)
                            instanceof MainFragment) {
                        if (popupWindow == null || !popupWindow.isShowing())
                            showPopup();
                    } else {
                        selectedFragment = new MainFragment();
                    }
                    break;
                case R.id.navigation_settings:
                    selectedFragment = new ProfileFragment();
                    break;
                case R.id.navigation_profile:
                    selectedFragment = new CompletedFragment();
                    break;
            }

            if (selectedFragment == null)
                return false;
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, selectedFragment)
                    .setReorderingAllowed(true)
                    .addToBackStack("name")
                    .commit();
            return true;
        }
    };

    private void showPopup() {
        View view = LayoutInflater.from(getBaseContext())
                .inflate(R.layout.task_choice_popup_window, null, true);
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;

        popupWindow = new PopupWindow(view, width, height, true);
        popupWindow.showAtLocation(parent, Gravity.TOP, 0, (int) navigation.getY() - (int) popupWindow.getHeight() * 2);


        Button addRandom = view.findViewById(R.id.add_random);
        Button createNew = view.findViewById(R.id.create_new);

        addRandom.setOnClickListener(v -> {
            popupWindow.dismiss();
            popupWindow = null;

            TasksDB tasksDB = TasksDB.getInstance(getBaseContext());
            TasksDao tasksDao = tasksDB.tasksDao();

            Observable<List<Task>> list = tasksDao.getTasks();

            Disposable disposable;

            Random random = new Random();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (tasksDao.countRands() < 3) {
                        int randId = tasksDao.getFirstInvisibleByCategory
                                (random.nextInt() % 6 + 1);
                        tasksDao.setVisibilityByID(randId, 1);
                        Task task = tasksDao.getByTaskId(randId);
                        if (task != null) {
                            String key = task.getKey();
                            dataBase.child(key).child("visibility").setValue(1);

                        }
                    } else {
                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast toast = Toast.makeText(getBaseContext(),
                                        "Случайных заданий не может быть больше" +
                                                " 3", Toast.LENGTH_SHORT);
                                ((TextView) ((LinearLayout) toast.getView()).getChildAt(0))
                                        .setGravity(Gravity.CENTER_HORIZONTAL);
                                toast.show();
                            }
                        });
                    }
                }
            }).start();


        });

        createNew.setOnClickListener(v -> {
            popupWindow.dismiss();
            popupWindow = null;
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new NewTaskFragment())
                    .setReorderingAllowed(true)
                    .addToBackStack("name")
                    .commit();
            findViewById(R.id.toolbar).setVisibility(View.GONE);
            View navView = findViewById(R.id.nav_view);
            navView.setVisibility(View.GONE);
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        parent = findViewById(R.id.main);
        navigation = findViewById(R.id.nav_view);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //Summoning the main activity
        navigation.setSelectedItemId(R.id.navigation_add);
        addBut = findViewById(R.id.navigation_add);


        getDataFromDB();
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }

    }

    private void getDataFromDB() {
        TasksDB tasksDB = TasksDB.getInstance(getBaseContext());
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
}