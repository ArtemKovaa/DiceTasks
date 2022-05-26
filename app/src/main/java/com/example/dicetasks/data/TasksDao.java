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

    @Query("SELECT * FROM tasks WHERE id=:id")
    Task getByTaskId(int id);

    @Query("SELECT * FROM tasks ORDER BY taskPriority DESC")
    Observable<List<Task>> getTasks();

    @Query("SELECT * FROM completed_tasks")
    Observable<List<CompletedTask>> getCompletedTasks();

    @Query("SELECT id, taskTitle, taskDescription, taskCategory, taskPriority, visibility FROM tasks WHERE taskPriority=:taskPriority")
    Observable<List<Task>> getRandomTask(int taskPriority);

    @Query("DELETE FROM tasks WHERE id = :id")
    void deleteById(long id);

    @Query("DELETE FROM completed_tasks WHERE id = :id")
    void deleteCompletedById(long id);

    @Query("DELETE FROM tasks")
    Completable deleteAllTasks();

    @Query("DELETE FROM completed_tasks")
    Completable deleteAllCompletedTasks();

    //Thing that gets tasks in db
    @Query("SELECT COUNT(*) FROM tasks WHERE taskPriority = 3 AND visibility != 0")
    int countRands();

    @Query("UPDATE tasks SET visibility = :visibility WHERE id= :id")
    void setVisibilityByID(int id, int visibility);

    @Query("SELECT id FROM tasks WHERE taskCategory = :category")
    int getFirstInvisibleByCategory(int category);


}
