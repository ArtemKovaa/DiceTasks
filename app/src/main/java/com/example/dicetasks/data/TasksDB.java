package com.example.dicetasks.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Task.class}, version = 1)
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
                            fillDB(db);
                        }
                    })
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    private static void fillDB(@NonNull SupportSQLiteDatabase db) {
        db.execSQL("INSERT INTO tasks (taskTitle, taskDescription, taskCategory, taskPriority, visibility) " +
                "VALUES('Первый раз', 'Создайте свое задание', 1, 3, 1)");
        db.execSQL("INSERT INTO tasks (taskTitle, taskDescription, taskCategory, taskPriority, visibility) " +
                "VALUES('Защитник', 'Сдать проект по самсунгу', 2, 3, 1)");
        db.execSQL("INSERT INTO tasks (taskTitle, taskDescription, taskCategory, taskPriority, visibility) " +
                "VALUES('Покормить пса', 'Без комментариев, Вы всего лишь раб...', 3, 3, 1)");
        db.execSQL("INSERT INTO tasks (taskTitle, taskDescription, taskCategory, taskPriority, visibility) " +
                "VALUES('\uD83C\uDD71edolaga', 'Сделайте домашнее задание', 4, 3, 1)");
        db.execSQL("INSERT INTO tasks (taskTitle, taskDescription, taskCategory, taskPriority, visibility) " +
                "VALUES('Псих', 'Сдай сессию на отлично', 5, 3, 1)");
    }

}
