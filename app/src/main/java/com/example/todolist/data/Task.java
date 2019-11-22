package com.example.todolist.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "task_table")
public class Task {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int taskId;

    @NonNull
    @ColumnInfo(name = "task")
    public String taskName;
}
