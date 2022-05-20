package com.example.dicetasks;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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

import java.util.List;
import java.util.Random;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class MainActivity extends FragmentActivity {

    PopupWindow popupWindow;
    CoordinatorLayout parent;
    BottomNavigationView navigation;
    View addBut;
    private FirebaseAuth mAuth;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_add:
                    if(getSupportFragmentManager().findFragmentById(R.id.fragment_container)
                            instanceof MainFragment) {
                        if(popupWindow == null || !popupWindow.isShowing())
                            showPopup();

                        //old code
                        //selectedFragment = new NewTaskFragment();
                        /*
                        //making the nav bar go invisible before going to NewTaskFragment
                        View navView = findViewById(R.id.nav_view);
                        navView.setVisibility(View.GONE);*/
                    }
                    else {
                        selectedFragment = new MainFragment();
                    }
                    break;
                case R.id.navigation_settings:
                    selectedFragment = new SettingsFragment();
                    break;
                case R.id.navigation_profile:
                    selectedFragment = new ProfileFragment();
                    break;
            }

            //assert selectedFragment != null;
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

    private void showPopup () {
        View view = LayoutInflater.from(getBaseContext())
                .inflate(R.layout.task_choice_popup_window, null, true);
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;

        popupWindow = new PopupWindow(view,width,height,true);
        popupWindow.showAtLocation(parent, Gravity.TOP, 0, (int)navigation.getY() - (int)popupWindow.getHeight()*2);
        //popupWindow.showAsDropDown(addBut,0,0, Gravity.CENTER);


        Button addRandom = view.findViewById(R.id.add_random);
        Button createNew = view.findViewById(R.id.create_new);

        // TODO: logic for random task
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
                        tasksDao.setVisibilityByID(random.nextInt() % 6 + 1, 1);
                    } else {
                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast toast = Toast.makeText(getBaseContext(),
                                        "Случайных заданий не может быть больше" +
                                                " 3", Toast.LENGTH_SHORT);
                                ((TextView)((LinearLayout)toast.getView()).getChildAt(0))
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

        //This code should cancel the popup menu
        /*View overallView = findViewById(R.id.main);
        overallView.setOnClickListener(v -> {
            popupWindow.dismiss();
            popupWindow = null;
            Toast.makeText(this,"I should disappear",Toast.LENGTH_SHORT).show();
            overallView.setOnClickListener(null);
        });*/
    }

   /* // TODO: replace PopupMenu with PopupWindow
    private void showPopupMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(this,v);
        popupMenu.inflate(R.menu.popup_menu);

        popupMenu
                .setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.random:
                                Toast.makeText(getApplicationContext(),
                                        "Вы выбрали random",
                                        Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.personal:
                                Toast.makeText(getApplicationContext(),
                                        "Вы выбрали personal",
                                        Toast.LENGTH_SHORT).show();
                                return true;
                            default:
                                return false;
                        }
                    }
                });

        //Remove when you're mentally prepared
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
                Toast.makeText(getApplicationContext(), "onDismiss (test)",
                        Toast.LENGTH_SHORT).show();
            }
        });

        popupMenu.show();
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        parent = findViewById(R.id.main);
        navigation = findViewById(R.id.nav_view);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        /*getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new MainFragment())
                .commit();*/

        //Summoning the main activity
        navigation.setSelectedItemId(R.id.navigation_add);
        addBut = findViewById(R.id.navigation_add);
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
    }
}