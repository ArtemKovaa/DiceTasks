package com.example.dicetasks.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "completed_tasks")
public class CompletedTask {

    @PrimaryKey(autoGenerate = true)
    long id;
    private String userID = "NULL";
    private String completedTaskTitle;
    private String completedTaskDescription;
    private int completedTaskCategory;
    private int completedTaskPriority;
    private int visibility;

    public CompletedTask(String completedTaskTitle, String completedTaskDescription, int completedTaskCategory, int completedTaskPriority, int visibility) {
        this.completedTaskTitle = completedTaskTitle;
        this.completedTaskDescription = completedTaskDescription;
        this.completedTaskCategory = completedTaskCategory;
        this.completedTaskPriority = completedTaskPriority;
        this.visibility = visibility;
    }

    public long getId() {
        return id;
    }

    public String getCompletedTaskTitle() {
        return completedTaskTitle;
    }

    public void setCompletedTaskTitle(String completedTaskTitle) {
        this.completedTaskTitle = completedTaskTitle;
    }

    public String getCompletedTaskDescription() {
        return completedTaskDescription;
    }

    public void setCompletedTaskDescription(String completedTaskDescription) {
        this.completedTaskDescription = completedTaskDescription;
    }

    public int getCompletedTaskCategory() {
        return completedTaskCategory;
    }

    public void setCompletedTaskCategory(int completedTaskCategory) {
        this.completedTaskCategory = completedTaskCategory;
    }

    public int getCompletedTaskPriority() {
        return completedTaskPriority;
    }

    public void setCompletedTaskPriority(int completedTaskPriority) {
        this.completedTaskPriority = completedTaskPriority;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}