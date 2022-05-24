package com.example.dicetasks.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tasks")
public class Task extends TaskPattern{

    @PrimaryKey(autoGenerate = true)
    long id;

    public Task(){};

    public Task(String taskTitle, String taskDescription, int taskCategory, int taskPriority, int visibility) {
        super( taskTitle, taskDescription, taskCategory, taskPriority, visibility);
    }

    public long getId() {
        return id;
    }
}