package com.example.dicetasks.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "completed_tasks")
public class CompletedTask extends TaskPattern {

    @PrimaryKey(autoGenerate = true)
    long id;

    public CompletedTask(String taskTitle, String taskDescription, int taskCategory, int taskPriority, int visibility) {
        super( taskTitle, taskDescription, taskCategory, taskPriority, visibility);
    }
}