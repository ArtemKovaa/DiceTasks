package com.example.dicetasks.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tasks")
public class Task {

    @PrimaryKey(autoGenerate = true)
    long id;
    private String taskTitle;
    private String taskDescription;
    private int taskCategory;

    public Task(String taskTitle, String taskDescription, int taskCategory) {
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.taskCategory = taskCategory;
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

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", taskTitle='" + taskTitle + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", taskCategory='" + taskCategory + '\'' +
                '}';
    }
}