package com.krisantem.todolist;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

@RunWith(AndroidJUnit4.class)
public class DbHelperTest {
    private Context context;

    @Before
    public void setup() {
        this.context = InstrumentationRegistry.getTargetContext();
    }
    @Test
    public void onCreate() throws Exception {
        DbHelper dbHelper = new DbHelper(this.context);
    }

    @Test
    public void insertTask() throws Exception {
        DbHelper dbHelper = new DbHelper(this.context);
        dbHelper.insertTask("test1", "test1", "10/12/2020", "10:20",
                "In progress");
    }

    @Test
    public void updateTask() throws Exception {
        DbHelper dbHelper = new DbHelper(this.context);
        dbHelper.insertTask("test1", "test1", "10/12/2020", "10:20",
                "In progress");
        dbHelper.updateTask(0,"test1", "test1", "10/12/2020", "10:20",
                "In progress");
    }

    @Test
    public void deleteTask() throws Exception {
        DbHelper dbHelper = new DbHelper(this.context);
        dbHelper.insertTask("test1", "test1", "10/12/2020", "10:20",
                "In progress");
        dbHelper.deleteTask(0);
    }

    @Test
    public void getTask() throws Exception {
        DbHelper dbHelper = new DbHelper(this.context);
        dbHelper.insertTask("test1", "test1", "10/12/2020", "10:20",
                "In progress");
        ArrayList<String> taskTest = dbHelper.getTask(0);
    }

    @Test
    public void getAllTasks() throws Exception {
        DbHelper dbHelper = new DbHelper(this.context);
        dbHelper.insertTask("test1", "test1", "10/12/2020", "10:20",
                "In progress");
        dbHelper.insertTask("test2", "test1", "10/12/2020", "10:20",
                "Done");
        ArrayList<String[]> allTasks = dbHelper.getAllTasks();
    }

}