package com.krisantem.todolist;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.EditText;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> mainActivity =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void onCreate() throws Exception {
        if (mainActivity == null)
            throw new Exception();
    }

    @Test
    public void loadAllTasks() throws Exception {
        Runnable runner = new Runnable() {
            public void run() {
                mainActivity.getActivity().loadAllTasks();

                synchronized(this)
                {
                    this.notify();
                }
            }
        };
        synchronized( runner ) {
            mainActivity.getActivity().runOnUiThread(runner);
            runner.wait() ;
        }
    }

    @Test
    public void onResume() throws Exception {
        Runnable runner = new Runnable() {
            public void run() {
                mainActivity.getActivity().onResume();

                synchronized(this)
                {
                    this.notify();
                }
            }
        };
        synchronized( runner ) {
            mainActivity.getActivity().runOnUiThread(runner);
            runner.wait() ;
        }
    }

    @Test
    public void onCreateOptionsMenu() throws Exception {
        if (mainActivity == null)
            throw new Exception();
    }

    @Test
    public void onOptionsItemSelected() throws Exception {
        if (mainActivity == null)
            throw new Exception();
    }

    @Test
    public void onQueryTextSubmit() throws Exception {
        mainActivity.getActivity().onQueryTextSubmit("test1");
    }

    @Test
    public void onQueryTextChange() throws Exception {
        mainActivity.getActivity().onQueryTextChange("test1");
    }
}