package com.edward.todoapp;

import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import com.edward.todoapp.models.Todo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class EditItemActivity extends ActionBarActivity {

    private EditText etEditItem;
    private EditText etEditDate;
    private SeekBar sbEditPriority;

    private int intListPosition;
    private Todo todo;
    private String strOriginalFieldValue;

    DateDialogFragment frag;
    Calendar dueDate;

    DateFormat shortDate = new SimpleDateFormat("MM/dd/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        intListPosition = getIntent().getIntExtra("position", 0);
        todo = getIntent().getParcelableExtra("Todo");

        strOriginalFieldValue = todo.name;

        etEditItem = (EditText) findViewById(R.id.etEditItem);
        etEditItem.setText(todo.name);
        etEditItem.setSelection(etEditItem.length());
        etEditItem.setFocusable(true);
        etEditItem.requestFocus();

        etEditDate = (EditText) findViewById(R.id.etEditDate);
        etEditDate.setText(shortDate.format(todo.dueDate));
        dueDate = Calendar.getInstance();
        dueDate.setTime(todo.dueDate);

        sbEditPriority = (SeekBar) findViewById(R.id.sbEditPriority);
        sbEditPriority.setProgress(todo.priority);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_item, menu);
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

    public void onEditDate(View view) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction(); //get the fragment
        frag = DateDialogFragment.newInstance(this, new DateDialogFragmentListener(){
            public void updateChangedDate(int year, int month, int day){
                etEditDate.setText(String.valueOf(month+1) + "/" + String.valueOf(day) + "/" + String.valueOf(year));
                dueDate.set(year, month, day);
            }
        }, dueDate);
        frag.show(ft, "DateDialogFragment");
    }

    public void onEditItem(View view) {
        Intent data = new Intent();
        try {
            todo.name = etEditItem.getText().toString();
            todo.dueDate = shortDate.parse(etEditDate.getText().toString());
            todo.priority = sbEditPriority.getProgress();

            data.putExtra("position", intListPosition);
            data.putExtra("originalValue", strOriginalFieldValue);
            data.putExtra("Todo", todo);
            setResult(RESULT_OK, data);
            this.finish();

        } catch(ParseException e){
            Toast.makeText(this, "parsing error!", Toast.LENGTH_SHORT).show();
        } catch(Exception e){
            Toast.makeText(this, "some other error!", Toast.LENGTH_SHORT).show();
        }
    }

    public interface DateDialogFragmentListener{
        //this interface is a listener between the Date Dialog fragment and the activity to update the buttons date
        public void updateChangedDate(int year, int month, int day);
    }

}
