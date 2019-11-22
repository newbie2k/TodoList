package com.example.todolist.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.R;
import com.example.todolist.data.SubTask;
import com.example.todolist.data.Task;
import com.example.todolist.ui.adapter.TaskAdapter;
import com.example.todolist.viewmodel.TaskViewModel;

import java.util.List;

public class TaskListFragment extends Fragment implements TaskAdapter.OnTaskSelectedListner, TaskAdapter.UpdateTaskListener {

    private View view;
    private TaskViewModel viewModel;
    private TaskAdapter adapter;
    private GetIdListener listener;
    private List<Task> taskList;
    private AlertDialog deleteDialog;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.listener = (GetIdListener) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new TaskAdapter(getContext(), this, this);
        viewModel = ViewModelProviders.of(this).get(TaskViewModel.class);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.task_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.task_fragment_rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        viewModel.getAllTaskList().observe(getViewLifecycleOwner(), new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> taskList) {
                TaskListFragment.this.taskList = taskList;
                adapter.updateTaskData(taskList);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.END | ItemTouchHelper.START) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {
                if(taskList != null) {
                    final RecyclerView.ViewHolder LocalViewHolder = viewHolder;
                    AlertDialog.Builder builder =
                            new AlertDialog.Builder(getActivity()).setPositiveButton("Delete",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            viewModel.removeTask(taskList.get(LocalViewHolder.getAdapterPosition()));
                                        }
                                    }).setCancelable(false).setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    deleteDialog.dismiss();
                                    adapter.notifyDataSetChanged();
                                }
                            }).setTitle("Delete \"" +taskList.get(viewHolder.getAdapterPosition()).taskName + "\"").setMessage("All SubTask also be DELETED, Are You Sure?");
                            deleteDialog = builder.create();
                            deleteDialog.show();

                }
            }
        }).attachToRecyclerView(recyclerView);
    }

    @Override
    public void onTaskSelected(int taskId, String taskName) {
        listener.getTaskInfo(taskId, taskName);
    }

    public interface GetIdListener {
        public void getTaskInfo(int taskId, String taskString);
    }

    @Override
    public void onUpdateTaskLongPressListener(Task task) {
        AddTaskDialog addTaskDialog = new AddTaskDialog("Update Task", task, null);
        addTaskDialog.setCancelable(false);
        addTaskDialog.show(getActivity().getSupportFragmentManager(), "Task Update Dialog");
    }
}