package com.example.dicetasks;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.dicetasks.data.Statistics;
import com.example.dicetasks.data.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.reactivex.schedulers.Schedulers;

public class ProfileFragment extends Fragment {

    Button logoutButton;
    FirebaseAuth mAuth;
    DatabaseReference database;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);
        getActivity().findViewById(R.id.toolbar).setVisibility(View.VISIBLE);

        TextView textView = getActivity().findViewById(R.id.active_tasks_text);
        textView.setText("Пользовательская статистика");

        TextView randomsNumber = view.findViewById(R.id.completed_randoms_number);
        TextView usersNumber = view.findViewById(R.id.completed_users_number);

        database = FirebaseDatabase.getInstance().getReference("Statistics");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Statistics statistics = ds.getValue(Statistics.class);
                    if (statistics != null && statistics.getUserID()
                            .equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                        randomsNumber.setText(Integer.toString(statistics.getCompletedRandoms()));
                        usersNumber.setText(Integer.toString(statistics.getCompletedUsers()));
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        database.addValueEventListener(valueEventListener);


        logoutButton = view.findViewById(R.id.logout_button);
        mAuth = FirebaseAuth.getInstance();

        logoutButton.setOnClickListener(v -> {
            mAuth.signOut();
            startActivity(new Intent(getContext(), LoginActivity.class));
        });

        return view;
    }
}
