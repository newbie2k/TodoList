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
public interface SubTaskDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertSubTask(SubTask subTask);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    public void updateSubTask(SubTask subTask);

    @Delete
    public void removeSubTask(SubTask subTask);

    @Query("DELETE from subtask_table WHERE taskId = :taskId")
    public void removeAllSubTask(int taskId);


    @Query("SELECT * from subtask_table WHERE taskId = :taskId")
    public List<SubTask> getFilteredSubTask(int taskId);

    @Query("SELECT * from subtask_table")
    public LiveData<List<SubTask>> getAllSubTask();
}
