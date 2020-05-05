package com.krisantem.todolist;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.EditText;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ManageActivityTest {
    @Rule
    public ActivityTestRule<ManageActivity> manageActivity =
            new ActivityTestRule<>(ManageActivity.class);

    @Test
    public void onCreateOptionsMenu() throws Exception {
        if (manageActivity == null)
            throw new Exception();
    }

    @Test
    public void onOptionsItemSelected() throws Exception {
        if (manageActivity == null)
            throw new Exception();
    }

    @Test
    public void createDeleteMenu() throws Exception {
        Runnable runner = new Runnable() {
            public void run() {
                final EditText editor = manageActivity.getActivity().findViewById(R.id.task_title);
                manageActivity.getActivity().createDeleteMenu(editor).show();

                synchronized(this)
                {
                    this.notify();
                }
            }
        };
        synchronized( runner ) {
            manageActivity.getActivity().runOnUiThread(runner);
            runner.wait() ;
        }

    }

    @Test
    public void createAddCalendar() throws Exception {
        Runnable runner = new Runnable() {
            public void run() {
                final EditText editor = manageActivity.getActivity().findViewById(R.id.task_date);
                manageActivity.getActivity().createAddCalendar(editor);
                synchronized(this)
                {
                    this.notify();
                }
            }
        };
        synchronized( runner ) {
            manageActivity.getActivity().runOnUiThread(runner);
            runner.wait() ;
        }

    }

    @Test
    public void createAddTime() throws Exception {
        Runnable runner = new Runnable() {
            public void run() {
                final EditText editor = manageActivity.getActivity().findViewById(R.id.task_time);
                manageActivity.getActivity().createAddTime(editor);
                synchronized(this)
                {
                    this.notify();
                }
            }
        };
        synchronized( runner ) {
            manageActivity.getActivity().runOnUiThread(runner);
            runner.wait() ;
        }

    }

    @Test
    public void createAddStatus() throws Exception {
        Runnable runner = new Runnable() {
            public void run() {
                final EditText editor = manageActivity.getActivity().findViewById(R.id.task_status);
                manageActivity.getActivity().createAddStatus(editor).show();
                synchronized(this)
                {
                    this.notify();
                }
            }
        };
        synchronized( runner ) {
            manageActivity.getActivity().runOnUiThread(runner);
            runner.wait() ;
        }

    }
}