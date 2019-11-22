package com.example.todolist.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.R;
import com.example.todolist.data.SubTask;
import com.example.todolist.data.Task;
import com.example.todolist.ui.AddTaskDialog;

import java.util.List;

/**
 * Using same Adapter for both Fragments(RecyclerView)
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private LayoutInflater inflater;
    private List<Task> taskList;
    private List<SubTask> subTaskList;
    private OnTaskSelectedListner listener;
    private  UpdateSubTaskListener updateSubTaskListener;
    private UpdateTaskListener updateTaskListener;
    Context context;

    // TaskAdapter Constructor
    public TaskAdapter(Context context,UpdateSubTaskListener updateSubTaskListener ) {
        inflater = LayoutInflater.from(context);

        this.updateSubTaskListener = updateSubTaskListener;
    }

    public TaskAdapter(Context context, OnTaskSelectedListner listener, UpdateTaskListener updateTaskListener
                       ) {
        inflater = LayoutInflater.from(context);
        this.listener = listener;
        this.updateTaskListener = updateTaskListener;
    }


    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.rv_item, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, final int position) {
        // Checking if Its called from TaskList OR SubTaskList
        if(taskList != null) {
            holder.taskTextView.setText(taskList.get(position).taskName);
            holder.itemViewRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onTaskSelected(taskList.get(position).taskId,
                            taskList.get(position).taskName);
                    Log.v("TaskAdapter","Clicked : "  + taskList.get(position).taskId);
                }
            });
            holder.itemViewRoot.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    updateTaskListener.onUpdateTaskLongPressListener(taskList.get(position));
                    return true;
                }
            });
        }
        else {
            holder.taskTextView.setText(subTaskList.get(position).subTask);
            holder.itemViewRoot.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    updateSubTaskListener.onUpdateSubTaskLongPressListener(subTaskList.get(position));
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        // At initial state (When no data)
        if(taskList == null && subTaskList == null)
            return 0;
        else if(taskList == null)
            return subTaskList.size();
        else
            return taskList.size();
    }

    // ViewHolder Class
    class TaskViewHolder extends RecyclerView.ViewHolder{
        private TextView taskTextView;
        private LinearLayout itemViewRoot;

        private TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            taskTextView = itemView.findViewById(R.id.task_textview);
            itemViewRoot = itemView.findViewById(R.id.itemview_root);
        }
    }

    /**
     * Changes the DataSet of Adapter
     */
    public void updateTaskData(List<Task> taskList) {
        this.taskList = taskList;
        notifyDataSetChanged();
    }

    public void updateSubTaskData(List<SubTask> subTaskList) {
        this.subTaskList = subTaskList;
        notifyDataSetChanged();
    }

    // Listener for getting taskId
    public interface OnTaskSelectedListner {
        void onTaskSelected(int taskId, String taskName);
    }

    // Listener for Long Press on Task
    public interface UpdateTaskListener {
        void onUpdateTaskLongPressListener(Task task);
    }

    // Listener for Long Press on SubTask
    public interface UpdateSubTaskListener {
        void onUpdateSubTaskLongPressListener(SubTask subTask);
    }
}