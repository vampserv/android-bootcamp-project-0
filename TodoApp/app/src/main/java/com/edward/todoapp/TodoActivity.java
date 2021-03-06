package com.edward.todoapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.edward.todoapp.models.Todo;
import com.orm.query.Condition;
import com.orm.query.Select;

import org.apache.commons.io.FileUtils;

public class TodoActivity extends ActionBarActivity {

    private EditText etNewItem;
    private ListView lvItems;
//    ArrayList<Todo> items;
    List<Todo> items;
    TodosAdapter itemsAdapter;
//    ArrayAdapter<Todo> itemsAdapter;

    private final int REQUEST_CODE = 200;
    private String todoFilename = "todo.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_app);

        etNewItem = (EditText) findViewById(R.id.etNewItem);
        lvItems = (ListView) findViewById(R.id.lvItems);

//        readItems();
        items = Todo.listAll(Todo.class);

        itemsAdapter = new TodosAdapter(this, items);
        lvItems.setAdapter(itemsAdapter);

        setupListViewListener();

    }

    private void setupListViewListener() {
        // listen for long click and remove item by the index
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        Todo todo = (Todo)parent.getAdapter().getItem(position);
                        todo.delete();
                        items.remove(position);
                        itemsAdapter.notifyDataSetChanged();
//                        writeItems();
                        return true;
                    }
                }
        );
        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        launchEditItemActivity(parent.getAdapter().getItem(position).toString(), position);
                    }
                }
        );
    }

    private void launchEditItemActivity(String value, int position) {
        Intent i = new Intent(TodoActivity.this, EditItemActivity.class);
        i.putExtra("position", position);
        i.putExtra("Todo", (Todo)lvItems.getAdapter().getItem(position));
        startActivityForResult(i, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            int position = data.getExtras().getInt("position");
            String originalFieldValue = data.getExtras().getString("originalValue");
//            String fieldValue = data.getExtras().getString("value");
//            todo.name = fieldValue;
            Todo editedTodo = data.getParcelableExtra("Todo");
            Todo todo = (Todo)lvItems.getAdapter().getItem(position);
            // TODO: figure out how to pass todo into activity, make changes directly to db, and pass changed todo back to onActivityResult and set into the List
            todo.name = editedTodo.name;
            todo.dueDate = editedTodo.dueDate;
            todo.priority = editedTodo.priority;
            todo.save();
            items.set(position, todo);
            itemsAdapter.notifyDataSetChanged();
//            writeItems();
            Toast.makeText(this, originalFieldValue + " changed to " + todo.name, Toast.LENGTH_SHORT).show();
        }
    }

//    private void readItems() {
//        File filesDir = getFilesDir();
//        File todoFile = new File(filesDir, todoFilename);
//        try {
//            items = new ArrayList<String>(FileUtils.readLines(todoFile));
//        } catch (IOException e) {
//            items = new ArrayList<String>();
//        }
//    }
//
//    private void writeItems() {
//        File filesDir = getFilesDir();
//        File todoFile = new File(filesDir, todoFilename);
//        try {
//            FileUtils.writeLines(todoFile, items);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_todo_app, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onAddItem(View view) {
        String fieldValue = etNewItem.getText().toString();
        Todo todo = new Todo(fieldValue, 10, new Date());
        todo.save();
        itemsAdapter.add(todo);
        etNewItem.setText("");
//        writeItems();
    }
}
