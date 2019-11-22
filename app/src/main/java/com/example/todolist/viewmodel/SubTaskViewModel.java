package com.example.todolist.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;

import com.example.todolist.data.SubTask;
import com.example.todolist.repository.TaskRepository;

import java.util.List;

public class SubTaskViewModel extends AndroidViewModel implements TaskRepository.FilteredSubTask {
    private MediatorLiveData<List<SubTask>> subTaskMutableLiveData;
    private TaskRepository repository;

    public SubTaskViewModel(@NonNull Application application) {
        super(application);
        repository = TaskRepository.getEventRepository(application);
        subTaskMutableLiveData = new MediatorLiveData<>();
    }

    public LiveData<List<SubTask>> getSubTaskList(final int taskId) {
        repository.getFilteredSubTask(taskId, SubTaskViewModel.this);

        subTaskMutableLiveData.addSource(repository.getAllSubTask(), new Observer<List<SubTask>>() {
            @Override
            public void onChanged(List<SubTask> subTasks) {
                repository.getFilteredSubTask(taskId, SubTaskViewModel.this);
            }
        });
        return subTaskMutableLiveData;
    }

    // Needed to getFiltered Result
    @Override
    public void filteredSubTaskListner(List<SubTask> subTasks) {
        subTaskMutableLiveData.setValue(subTasks);
    }

    public void removeSubTask(SubTask subTask) {
        repository.removeSubTask(subTask);
    }
}
