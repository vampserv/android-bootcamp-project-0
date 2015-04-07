package com.edward.todoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.edward.todoapp.models.Todo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by edwardyang on 4/7/15.
 */
public class TodosAdapter extends ArrayAdapter<Todo> {

    DateFormat shortDate = new SimpleDateFormat("MM/dd/yyyy");

    public TodosAdapter(Context context, List<Todo> todos) {
        super(context, 0, todos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Todo todo = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.todo_list_item, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvDate = (TextView) convertView.findViewById(R.id.tvDate);
        ProgressBar pbPriority = (ProgressBar) convertView.findViewById(R.id.pbPriority);
        // Populate the data into the template view using the data object
        tvName.setText(todo.name);
        tvDate.setText(shortDate.format(todo.dueDate));
        pbPriority.setProgress(todo.priority);
        // Return the completed view to render on screen
        return convertView;
    }
}