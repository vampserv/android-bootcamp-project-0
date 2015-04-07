package com.edward.todoapp.models;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;

import java.util.Date;

/**
 * Created by edwardyang on 4/6/15.
 */
public class Todo extends SugarRecord<Todo> implements Parcelable {
    public String name;
    public int priority;
    public Date dueDate;

    public Todo(){
    }

    public Todo(String name, int priority, Date dueDate){
        this.name = name;
        this.priority = priority;
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return this.name;
    }


    protected Todo(Parcel in) {
        name = in.readString();
        priority = in.readInt();
        long tmpDueDate = in.readLong();
        dueDate = tmpDueDate != -1 ? new Date(tmpDueDate) : null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(priority);
        dest.writeLong(dueDate != null ? dueDate.getTime() : -1L);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Todo> CREATOR = new Parcelable.Creator<Todo>() {
        @Override
        public Todo createFromParcel(Parcel in) {
            return new Todo(in);
        }

        @Override
        public Todo[] newArray(int size) {
            return new Todo[size];
        }
    };
}