package com.edward.todoapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class EditItemActivity extends ActionBarActivity {

    private EditText etEditItem;
    private int intListPosition;
    private String strOriginalFieldValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        intListPosition = getIntent().getIntExtra("position", 0);
        strOriginalFieldValue = getIntent().getStringExtra("value");

        etEditItem = (EditText) findViewById(R.id.etEditItem);
        etEditItem.setText(strOriginalFieldValue);
        etEditItem.setSelection(etEditItem.length());
        etEditItem.setFocusable(true);
        etEditItem.requestFocus();
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

    public void onEditItem(View view) {
        Intent data = new Intent();
        data.putExtra("position", intListPosition);
        data.putExtra("originalValue", strOriginalFieldValue);
        data.putExtra("value", etEditItem.getText().toString());
        setResult(RESULT_OK, data);
        this.finish();
    }
}
