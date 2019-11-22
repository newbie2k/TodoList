package com.example.todolist.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.todolist.R;
import com.example.todolist.data.SubTask;
import com.example.todolist.data.Task;
import com.example.todolist.viewmodel.DialogTaskViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class AddTaskDialog extends DialogFragment {
    private View view;
    private TextInputEditText editText;
    private String title;

    private String taskName;
    private int taskId;
    private String subTaskName;
    private int subTaskId;
    private int sub_TaskId;
    private DialogTaskViewModel viewModel;
    /**
     * flag >= 0 ---> insert
     * flag = -1 ---> update
     */
    private int flag = -1;      /* Here using flag for multiple purpose
                                   1. One for knowing the visual fragment
                                   2. Passing taskId
                                   3. Differentiating between update and insert request
                                 */

    public AddTaskDialog(String title, int flag) {
        this.title = title;
        this.flag = flag;
    }

    public AddTaskDialog(String title, Task task, SubTask subTask) {
        this.title = title;
        taskName = task.taskName;
        taskId = task.taskId;
        subTaskName = subTask.subTask;
        sub_TaskId = subTask.taskId;
        subTaskId = subTask.id;
    }

    public AddTaskDialog(){
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if(savedInstanceState != null) {
            title = savedInstanceState.getString("TITLE");
            flag = savedInstanceState.getInt("FLAG");
            if(flag == -1) {
            if(savedInstanceState.getString("TASKNAME", null) != null) {
                taskName = savedInstanceState.getString("TASKNAME");
                taskId = savedInstanceState.getInt("TASKID");
            }
            else {
                subTaskName = savedInstanceState.getString("SUBTASKNAME");
                subTaskId = savedInstanceState.getInt("SUBTASKID");
                sub_TaskId = savedInstanceState.getInt("SUB_TASKID");
            }
            }
        }

        final AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        builder.setTitle(title);
        Log.v("Dialog", " : " + title);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.add_task_dialog, null);
        editText = (TextInputEditText) view.findViewById(R.id.add_task);

        if(flag == -1) {
            if(subTaskName != null) {
                editText.setText(subTaskName);
                editText.setHint("");
            }
            else {
                editText.setText((taskName));
                editText.setHint("");
            }
        }



        builder.setView(view);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String newTaskName = editText.getText().toString().trim();
                if(flag == 0 || flag >0) {
                    if (flag == 0) {
                        Task newTask = new Task();
                        newTask.taskName = newTaskName;
                        viewModel.insertTask(newTask);
                    } else {
                        SubTask newTask = new SubTask();
                        newTask.subTask = newTaskName;
                        newTask.taskId = flag;
                        viewModel.insertSubTask(newTask);
                    }
                }
                else {
                    if(subTaskName != null) {
                        SubTask subTask = new SubTask();
                        subTask.subTask = subTaskName;
                        subTask.taskId = sub_TaskId;
                        subTask.id = subTaskId;
                        viewModel.updateSubTask(subTask);
                    }
                    else {
                        Task task = new Task();
                        task.taskName = taskName;
                        task.taskId = taskId;
                        viewModel.updateTask(task);
                    }

                }

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });
        return builder.create();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(DialogTaskViewModel.class);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.v("OnSaveInstance", "OnSave CAlled");
        outState.putInt("FLAG" , flag);
        outState.putString("TITLE", title);
        if(taskName != null) {
            outState.putInt("TASKID", taskId);
            outState.putString("TASKNAME", taskName);
        }
        if(subTaskName != null) {
            outState.putInt("SUBTASKID", subTaskId);
            outState.putString("SUBTASKNAME",subTaskName);
            outState.putInt("SUB_TASKID", sub_TaskId);
        }
    }
}