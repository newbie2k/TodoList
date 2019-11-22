package com.example.todolist.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.todolist.data.Task;
import com.example.todolist.repository.TaskRepository;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {
    private TaskRepository repository;

    public TaskViewModel(@NonNull Application application) {
        super(application);
        repository = TaskRepository.getEventRepository(application);
    }

    public LiveData<List<Task>> getAllTaskList() {
        return repository.getAllTask();
    }

    public void removeTask(Task task) {
        repository.removeTask(task);
    }
}