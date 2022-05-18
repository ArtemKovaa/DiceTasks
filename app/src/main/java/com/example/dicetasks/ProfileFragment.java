package com.example.dicetasks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.achievements_fragment, container, false);
        getActivity().findViewById(R.id.toolbar).setVisibility(View.VISIBLE);
        TextView textView = getActivity().findViewById(R.id.active_tasks_text);
        textView.setText("Выполненные задания");
        return view;
    }

    @NonNull
    @Override
    public String toString() {
        return "ProfileFragment";
    }
}
