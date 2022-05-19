package com.example.dicetasks.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

@Dao
public interface TasksDao {
    @Insert
    Completable insert(Task task);

    @Insert
    Completable insertCompleted(CompletedTask completedTask);

    @Query("SELECT * FROM tasks WHERE id=:id")
    Observable<Task> getById(int id);

    @Query("SELECT * FROM tasks ORDER BY taskPriority DESC")
    Observable<List<Task>> getTasks();

    @Query("SELECT * FROM completed_tasks ORDER BY completedTaskPriority DESC")
    Observable<List<CompletedTask>> getCompletedTasks();

    @Query("SELECT id, taskTitle, taskDescription, taskCategory, taskPriority, visibility FROM tasks WHERE taskPriority=:taskPriority")
    Observable<List<Task>> getRandomTask(int taskPriority);

    @Query("DELETE FROM tasks WHERE id = :id")
    void deleteById(long id);
}
