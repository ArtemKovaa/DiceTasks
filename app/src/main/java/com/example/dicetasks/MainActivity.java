package com.example.dicetasks;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends FragmentActivity {

    public RecyclerView list;

    // TODO: remove currentFragment from code for outdated
    public Fragment currentFragment = null;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_add:
                //    if(currentFragment != null && currentFragment.toString().equals("MainFragment")){
                    if(getSupportFragmentManager().findFragmentById(R.id.fragment_container)
                            instanceof MainFragment) {
                        //View view = findViewById(R.id.main);
                        //showPopupMenu(view);
                        //return true;

                        selectedFragment = new NewTaskFragment();

                        //making the nav bar go invisible before going to NewTaskFragment
                        View navView = findViewById(R.id.nav_view);
                        navView.setVisibility(View.GONE);
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
            currentFragment = selectedFragment;

            assert selectedFragment != null;
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, selectedFragment)
                    .setReorderingAllowed(true)
                    .addToBackStack("name")
                    .commit();
            return true;
        }
    };

    // TODO: replace PopupMenu with PopupWindow
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
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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