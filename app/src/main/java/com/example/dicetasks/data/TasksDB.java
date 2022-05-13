package com.example.dicetasks.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Task.class}, version = 1)
public abstract class TasksDB extends RoomDatabase {

    private static TasksDB instance;

    public abstract TasksDao tasksDao();

    public static TasksDB getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            TasksDB.class, "tasks_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}
