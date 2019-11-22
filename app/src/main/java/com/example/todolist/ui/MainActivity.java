package com.example.todolist.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Toast;

import com.example.todolist.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements TaskListFragment.GetIdListener {
    private FragmentManager manager;
    private int taskId;
    private boolean onTablet;
    private  AlertDialog onTabletChoiceDialog;
    private  AlertDialog welcomeDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(findViewById(R.id.tablet_layout_main_activity) != null)
            onTablet = true;

        manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        TaskListFragment taskListFragment = new TaskListFragment();
        transaction.add(R.id.frag_container, taskListFragment , "TaskList")
                .commit();

        FloatingActionButton addTaskFab = findViewById(R.id.add_fab);


            addTaskFab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!onTablet) {
                            AddTaskDialog addTaskDialog = new AddTaskDialog("Add Task", 0);
                            addTaskDialog.setCancelable(false);
                            addTaskDialog.show(getSupportFragmentManager(), "Task Add Dialog");

                    } else {
                        final String[] s = new String[]{"Add Task", "Add SubTask"};
                        AlertDialog.Builder builder =
                                new AlertDialog.Builder(MainActivity.this);
                        builder.setSingleChoiceItems(s, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(s[which].equals("Add Task")) {
                                    AddTaskDialog addTaskDialog = new AddTaskDialog("Add Task", 0);
                                    addTaskDialog.setCancelable(false);
                                    addTaskDialog.show(getSupportFragmentManager(), "Task Add Dialog");
                                    onTabletChoiceDialog.dismiss();
                                }
                                else {
                                    if(taskId == 0) {
                                        Toast.makeText(MainActivity.this, "No Task Selected",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        AddTaskDialog addTaskDialog = new AddTaskDialog("Add SubTask", taskId);
                                        addTaskDialog.setCancelable(false);
                                        addTaskDialog.show(getSupportFragmentManager(), "SubTask Add Dialog");
                                        onTabletChoiceDialog.dismiss();
                                    }
                                }
                            }
                        });
                        onTabletChoiceDialog = builder.create();
                        onTabletChoiceDialog.show();
                    }
                }
            });

            // Welcome Screen(Tips) Logic
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        if((sharedPreferences.getInt("welcome", 0)) == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this).setPositiveButton("Got " +
                    "It", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    welcomeDialog.dismiss();
                }
            }).setMessage("1. Swipe Right/Left to Delete Task\n2. Long Press to Edit Task \n3. " +
                    "Tap to open SubTask List").setTitle("Welcome !!!").setCancelable(false);
            welcomeDialog = builder.create();
            welcomeDialog.show();
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("welcome", 1);
        editor.apply();
    }

    @Override
    public void getTaskInfo(int taskId, String taskName) {
        this.taskId = taskId;
        if(!onTablet) {
            Intent i = new Intent(this, SubTaskActivity.class);
            i.putExtra("TASKID", taskId);
            i.putExtra("TASKNAME", taskName);
            startActivity(i);
        }
        else {
            SubTaskListFragment subTaskListFragment = new SubTaskListFragment(taskId, taskName);
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.frag_container1, subTaskListFragment, "SubTaskList");
            transaction.commit();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }
}