package com.example.todolist.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertTask(Task task);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    public void updateTask(Task task);

    @Delete
    public void removeTask(Task task);

    @Query("SELECT * from task_table")
    public LiveData<List<Task>> getAllTask();
}
