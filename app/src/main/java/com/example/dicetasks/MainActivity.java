package com.example.dicetasks;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.dicetasks.data.Task;
import com.example.dicetasks.data.TasksDB;
import com.example.dicetasks.data.TasksDao;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import io.reactivex.disposables.Disposable;

public class MainActivity extends FragmentActivity {

    PopupWindow popupWindow;
    CoordinatorLayout parent;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_add:
                    if(getSupportFragmentManager().findFragmentById(R.id.fragment_container)
                            instanceof MainFragment) {
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
                .inflate(R.layout.task_choice_popup_window, null, false);
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;



        PopupWindow popupWindow = new PopupWindow(view,width,height,false);
        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);


        Button addRandom = view.findViewById(R.id.add_random);
        Button createNew = view.findViewById(R.id.create_new);

        // TODO: logic for random task
        addRandom.setOnClickListener(v -> {
            popupWindow.dismiss();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new NewTaskFragment())
                    .setReorderingAllowed(true)
                    .addToBackStack("name")
                    .commit();
            View navView = findViewById(R.id.nav_view);
            navView.setVisibility(View.GONE);
        });

        createNew.setOnClickListener(v -> {
            popupWindow.dismiss();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new NewTaskFragment())
                    .setReorderingAllowed(true)
                    .addToBackStack("name")
                    .commit();
            View navView = findViewById(R.id.nav_view);
            navView.setVisibility(View.GONE);
        });


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

        parent = findViewById(R.id.main);
        BottomNavigationView navigation = findViewById(R.id.nav_view);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        /*getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new MainFragment())
                .commit();*/

        //Summoning the main activity
        navigation.setSelectedItemId(R.id.navigation_add);
    }

}