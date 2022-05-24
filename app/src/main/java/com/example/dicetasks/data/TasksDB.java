package com.example.dicetasks.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Task.class, CompletedTask.class}, version = 1)
public abstract class TasksDB extends RoomDatabase {

    private static TasksDB instance;

    public abstract TasksDao tasksDao();

    public static TasksDB getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            TasksDB.class, "tasks_database")
                    .addCallback(new RoomDatabase.Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                        }
                    })
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
