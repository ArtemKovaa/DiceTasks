package com.example.dicetasks.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "random_tasks")
public class RandomTask extends TaskPattern{

    @PrimaryKey(autoGenerate = true)
    long id;

    public RandomTask(String taskTitle, String taskDescription, int taskCategory, int taskPriority, int visibility) {
        super( taskTitle, taskDescription, taskCategory, taskPriority, visibility);
    }
}
