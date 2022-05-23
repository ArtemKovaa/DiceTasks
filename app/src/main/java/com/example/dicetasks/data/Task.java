package com.example.dicetasks.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tasks")
public class Task {

    @PrimaryKey(autoGenerate = true)
    long id;
    private String userID = "NULL";
    private String taskTitle;
    private String taskDescription;
    private int taskCategory;
    private int taskPriority;
    private int visibility;
    private String key = "NULL";

    public Task(){};

    public Task(String taskTitle, String taskDescription, int taskCategory, int taskPriority, int visibility) {
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.taskCategory = taskCategory;
        this.taskPriority = taskPriority;
        this.visibility = visibility;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }



    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public void setTaskCategory(int taskCategory) {
        this.taskCategory = taskCategory;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public int getTaskCategory() {
        return taskCategory;
    }

    public long getId() {
        return id;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }


    public int getTaskPriority() {
        return taskPriority;
    }

    public void setTaskPriority(int taskPriority) {
        this.taskPriority = taskPriority;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}