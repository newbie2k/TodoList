package com.example.todolist.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.todolist.data.SubTask;
import com.example.todolist.data.SubTaskDao;
import com.example.todolist.data.Task;
import com.example.todolist.data.TaskDao;
import com.example.todolist.data.TaskDatabase;

import java.util.List;

public class TaskRepository {

    private static volatile TaskRepository INSTANCE;
    private static Application application;
    private TaskDao taskDao;
    private SubTaskDao subTaskDao;

    private TaskRepository() {
        TaskDatabase database = TaskDatabase.getDatabase(application);
        taskDao = database.taskDao();
        subTaskDao = database.subTaskDao();
    }

    public static TaskRepository getEventRepository(Application application) {
        if (INSTANCE == null) {
            synchronized (TaskRepository.class) {
                if (INSTANCE == null) {
                    TaskRepository.application = application;
                    INSTANCE = new TaskRepository();
                }
            }
        }
        return INSTANCE;
    }

    public void updateTask(final Task task) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                taskDao.updateTask(task);
                return null;
            }
        }.execute();
    }

    public void updateSubTask(final SubTask subTask) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                subTaskDao.updateSubTask(subTask);
                return null;
            }
        }.execute();
    }

    public void insertTask(final Task task) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                taskDao.insertTask(task);
                return null;
            }
        }.execute();
    }

    public void insertSubTask(final SubTask subTask) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                subTaskDao.insertSubTask(subTask);
                return null;
            }
        }.execute();
    }

    // Needed to observe the SubTask DataBase
    public LiveData<List<SubTask>> getAllSubTask() {
        return subTaskDao.getAllSubTask();
    }

    // Needed to get filtered SubTask
    public void getFilteredSubTask(final int taskId, final FilteredSubTask filteredSubTask) {
        new AsyncTask<Void, Void, List<SubTask>>() {
            @Override
            protected List<SubTask> doInBackground(Void... voids) {

                return subTaskDao.getFilteredSubTask(taskId);
            }

            @Override
            protected void onPostExecute(List<SubTask> subTasks) {
                filteredSubTask.filteredSubTaskListner(subTasks);
            }
        }.execute();
    }

    public LiveData<List<Task>> getAllTask() {
        return taskDao.getAllTask();
    }


    public void removeSubTask(final SubTask subTask) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                subTaskDao.removeSubTask(subTask);
                return null;
            }
        }.execute();
    }

    public void removeTask(final Task task) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                subTaskDao.removeAllSubTask(task.taskId);
                taskDao.removeTask(task);
                return null;
            }
        }.execute();

    }

    public interface FilteredSubTask {
        void filteredSubTaskListner(List<SubTask> subTasks);
    }
}