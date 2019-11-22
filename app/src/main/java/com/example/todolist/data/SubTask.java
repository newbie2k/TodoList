package com.example.todolist.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "subtask_table")
public class SubTask {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    public int taskId;

    @NonNull
    public String subTask;
}
