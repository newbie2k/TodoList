package com.example.todolist.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.todolist.data.SubTask;
import com.example.todolist.data.Task;
import com.example.todolist.repository.TaskRepository;

public class DialogTaskViewModel extends AndroidViewModel {
    private TaskRepository repository;

    public DialogTaskViewModel(@NonNull Application application) {
        super(application);
        repository = TaskRepository.getEventRepository(application);
    }

    public void insertTask(Task task) {
        repository.insertTask(task);
    }

    public void insertSubTask(SubTask subTask) {
        repository.insertSubTask(subTask);
    }

    public void updateTask(Task newTask) {
        repository.updateTask(newTask);
    }

    public void updateSubTask(SubTask newSubTask) {
        repository.updateSubTask(newSubTask);
    }

}
