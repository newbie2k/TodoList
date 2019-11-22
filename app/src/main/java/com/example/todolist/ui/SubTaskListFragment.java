package com.example.todolist.ui;

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
import com.example.todolist.viewmodel.SubTaskViewModel;
import com.example.todolist.viewmodel.TaskViewModel;

import java.util.List;

public class SubTaskListFragment extends Fragment implements TaskAdapter.UpdateSubTaskListener {

    private View view;
    private SubTaskViewModel viewModel;
    private TaskAdapter adapter;
    private int taskId;
    private List<SubTask> subTaskList;
    private String taskName;


    public SubTaskListFragment(int taskId, String taskName) {
        this.taskId = taskId;
        this.taskName = taskName;
    }

    public SubTaskListFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new TaskAdapter(getContext(), this);
        viewModel = ViewModelProviders.of(this).get(SubTaskViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.subtask_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.subtask_fragment_rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        viewModel.getSubTaskList(taskId).observe(getViewLifecycleOwner(),
                new Observer<List<SubTask>>() {
            @Override
            public void onChanged(List<SubTask> subTasks) {
                SubTaskListFragment.this.subTaskList = subTasks;
                adapter.updateSubTaskData(subTasks);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.END | ItemTouchHelper.START) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                if(subTaskList != null) {

                    viewModel.removeSubTask(subTaskList.get(viewHolder.getAdapterPosition()));
                }
            }
        }).attachToRecyclerView(recyclerView);

        getActivity().setTitle(taskName);
    }

    @Override
    public void onUpdateSubTaskLongPressListener(SubTask subTask) {
        AddTaskDialog addTaskDialog = new AddTaskDialog("Update SubTask", null, subTask);
        addTaskDialog.setCancelable(false);
        addTaskDialog.show(getActivity().getSupportFragmentManager(), "SubTask Update Dialog");
    }

}
