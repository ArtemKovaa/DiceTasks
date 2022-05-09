package com.example.dicetasks;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewRow> {

    private ArrayList<TaskMenuFragment.Block> arrayList;

    public TaskAdapter(ArrayList<TaskMenuFragment.Block> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewRow onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_row,parent,false);
        return new ViewRow(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewRow holder, int position) {

        holder.task.setText(arrayList.get(position).getName());
        holder.desc.setText(arrayList.get(position).getDescription());
        //Picasso.get().load(arrayList.get(position).getUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class ViewRow extends RecyclerView.ViewHolder {

        TextView task;
        TextView desc;

        public ViewRow(@NonNull View view) {
            super(view);

            view.setOnClickListener(v -> {
                task.setText("*функционал*");
            });

            task = itemView.findViewById(R.id.task);
            desc = itemView.findViewById(R.id.desc);
        }
    }
}
