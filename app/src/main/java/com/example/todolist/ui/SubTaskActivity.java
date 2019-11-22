package com.example.todolist.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.example.todolist.R;
import com.example.todolist.ui.AddTaskDialog;
import com.example.todolist.ui.SubTaskListFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SubTaskActivity extends AppCompatActivity {
    private int taskId;
    private String taskName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_task);

        taskId = getIntent().getExtras().getInt("TASKID");
        taskName = getIntent().getExtras().getString("TASKNAME");

        FloatingActionButton addSubTaskFab = findViewById(R.id.add_subtask_fab);


        addSubTaskFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddTaskDialog addTaskDialog = new AddTaskDialog("Add SubTask", taskId);
                addTaskDialog.setCancelable(false);
                addTaskDialog.show(getSupportFragmentManager(), "SubTask Add Dialog");
            }
        });


        SubTaskListFragment subTaskListFragment = new SubTaskListFragment(taskId, taskName);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.subtask_fragment_container, subTaskListFragment, "SubTaskList");
        transaction.commit();

    }
}