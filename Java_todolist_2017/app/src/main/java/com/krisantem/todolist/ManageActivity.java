package com.krisantem.todolist;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;

public class ManageActivity extends AppCompatActivity {

    private DbHelper    dbHelper;
    private int         editValue;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_task);
        dbHelper = new DbHelper(this);
        editValue = -1;

        Bundle b = getIntent().getExtras();
        if (b != null) {
            editValue = b.getInt("key");

            final EditText taskName = findViewById(R.id.task_title);
            final EditText taskContent = findViewById(R.id.task_content);
            final EditText taskDate = findViewById(R.id.task_date);
            final EditText taskTime = findViewById(R.id.task_time);
            final EditText taskStatus = findViewById(R.id.task_status);
            ArrayList<String> taskRow = dbHelper.getTask(editValue);

            taskName.setText(taskRow.get(0));
            taskContent.setText(taskRow.get(1));
            taskDate.setText(taskRow.get(2));
            taskTime.setText(taskRow.get(3));
            taskStatus.setText(taskRow.get(4));
        }

        final ImageView dateBtn = findViewById(R.id.task_date_btn);
        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText dateText = findViewById(R.id.task_date);
                createAddCalendar(dateText);
            }
        });

        final ImageView timeBtn = findViewById(R.id.task_time_btn);
        timeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText timeText = findViewById(R.id.task_time);
                createAddTime(timeText);
            }
        });

        final ImageView statusBtn = findViewById(R.id.task_status_btn);
        statusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText statusText = findViewById(R.id.task_status);
                createAddStatus(statusText).show();
            }
        });
    }

    /**
     * @param menu
     * @return boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_manage, menu);
        if (editValue != -1) {
            final MenuItem removeItem = menu.findItem(R.id.remove_settings);
            removeItem.setVisible(true);
        }
        return true;
    }

    /**
     * @param item
     * @return boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.validate_settings) {
            final EditText taskName = findViewById(R.id.task_title);
            final EditText taskContent = findViewById(R.id.task_content);
            final EditText taskDate = findViewById(R.id.task_date);
            final EditText taskTime = findViewById(R.id.task_time);
            final EditText taskStatus = findViewById(R.id.task_status);

            if (editValue != -1) {
                dbHelper.updateTask(editValue, taskName.getText().toString(),
                        taskContent.getText().toString(), taskDate.getText().toString(),
                        taskTime.getText().toString(), taskStatus.getText().toString());
            }
            else {
                dbHelper.insertTask(taskName.getText().toString(),
                        taskContent.getText().toString(), taskDate.getText().toString(),
                        taskTime.getText().toString(), taskStatus.getText().toString());
            }
            finish();
            return true;
        }
        else if (id == R.id.remove_settings) {
            final EditText taskName = findViewById(R.id.task_title);
            createDeleteMenu(taskName).show();
            return true;
        }
        else if (id == R.id.home_settings) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * @param taskView
     * @return dialog
     */
    public Dialog createAddStatus(final TextView taskView) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.manage_status_title)
                .setItems(R.array.manage_status_options, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String[] items = getResources().getStringArray(R.array.manage_status_options);
                        taskView.setText(items[which]);
                    }
                });
        return builder.create();
    }

    /**
     * @param taskView
     * @return dialog
     */
    public Dialog createDeleteMenu(final TextView taskView) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.delete_title)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dbHelper.deleteTask(editValue);
                        finish();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        return (builder.create());
    }

    /**
     * @param time
     */
    public void createAddTime(final EditText time) {
        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        time.setText(hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    /**
     * @param date
     */
    public void createAddCalendar(final EditText date) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        date.setText(String.format("%d/%d/%d", dayOfMonth, monthOfYear + 1, year));
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
}
