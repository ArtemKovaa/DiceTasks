package com.example.dicetasks.data;

public class Statistics {
    private String userID = "";
    private int completedRandoms = 0;
    private int completedUsers = 0;

    public Statistics() {}

    public Statistics(String userID, int completedRandoms, int completedUsers) {
        this.userID = userID;
        this.completedRandoms = completedRandoms;
        this.completedUsers = completedUsers;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getCompletedRandoms() {
        return completedRandoms;
    }

    public void setCompletedRandoms(int completedRandoms) {
        this.completedRandoms = completedRandoms;
    }

    public int getCompletedUsers() {
        return completedUsers;
    }

    public void setCompletedUsers(int completedUsers) {
        this.completedUsers = completedUsers;
    }
}
