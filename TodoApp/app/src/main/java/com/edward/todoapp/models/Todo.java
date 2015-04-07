package com.edward.todoapp.models;

import android.content.Context;

import com.orm.SugarRecord;

/**
 * Created by edwardyang on 4/6/15.
 */
public class Todo extends SugarRecord<Todo> {
    public String name;

    public Todo(){
    }

    public Todo(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

}