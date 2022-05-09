package com.example.dicetasks;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class TaskMenuFragment extends Fragment {

    private FirstViewModel mViewModel;

    public static TaskMenuFragment newInstance() {
        return new TaskMenuFragment();
    }

    private TaskAdapter taskAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // TODO: check the view usage. Scroll of RecycleView is locked

        View fragmentView = inflater.inflate(R.layout.task_menu_fragment, container, false);
        initRecyclerView(fragmentView);
        return fragmentView;
        //return inflater.inflate(R.layout.task_menu_fragment, container, false);
    }

    public void initRecyclerView(View v) {
        RecyclerView recyclerView = v.findViewById(R.id.recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getParent());
        recyclerView.setLayoutManager(layoutManager);
        taskAdapter = new TaskAdapter(generateData());
        recyclerView.setAdapter(taskAdapter);
    }

    private final String name[] = {
            "Clouds",
            "Sun",
            "Partial clouds",
            "Snow",
            "Sleet",
            "Mist",
            "Clear",
            "Rain",
            "Rain thunder",
            "Fog"
    };

    private final String description[] = {
            "it's when it's sunny but there're some clouds partially restricting the sun rays",
            "sunny",
            "Idk, what's this",
            "White solid water",
            "Nasty stuff outside home. Stay, don't leave. It'd be great if you have a fireplace by your side",
            "Mystery or something, idk",
            "sunny but no sun probably",
            "droplets slap slap",
            "droplets go brrtt",
            "Hazy"
    };

    class Block {
        private String name;
        private String description;

        public Block(String name, String description) {
            this.name = name;
            this.description = description;
        }

        public String getDescription() {
            return description;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    public ArrayList<Block> generateData() {
        ArrayList<Block> list = new ArrayList<>();
        for(int i = 0; i< description.length; i++) {
            list.add(new Block(name[i], description[i]));
        }
        return list;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(FirstViewModel.class);
        // TODO: Use the ViewModel
    }

}