package com.krisantem.todolist;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "localhost";
    private static final int    DB_VER = 1;
    private static final String DB_TABLE = "tasks";
    private static final String DB_COLUMN_TASK = "task_name";
    private static final String DB_COLUMN_CONTENT = "task_content";
    private static final String DB_COLUMN_DATE = "task_date";
    private static final String DB_COLUMN_TIME = "task_time";
    private static final String DB_COLUMN_STATUS = "task_status";

    DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    /**
     * @param sqLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = String.format(
                "CREATE TABLE %s (ID INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT NOT NULL," +
                        " %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL);",
                DB_TABLE, DB_COLUMN_TASK, DB_COLUMN_CONTENT, DB_COLUMN_DATE,
                DB_COLUMN_TIME, DB_COLUMN_STATUS);
        sqLiteDatabase.execSQL(query);
    }

    /**
     * @param sqLiteDatabase
     * @param i
     * @param i1
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String query = String.format("CREATE TABLE IF EXISTS %s", DB_TABLE);
        sqLiteDatabase.execSQL(query);
        onCreate(sqLiteDatabase);
    }

    /**
     * @param task
     * @param content
     * @param date
     * @param time
     * @param status
     */
    void insertTask(String task, String content, String date, String time, String status) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DB_COLUMN_TASK, task);
        values.put(DB_COLUMN_CONTENT, content);
        values.put(DB_COLUMN_DATE, date);
        values.put(DB_COLUMN_TIME, time);
        values.put(DB_COLUMN_STATUS, status);
        sqLiteDatabase.insertWithOnConflict(DB_TABLE, null, values,
                SQLiteDatabase.CONFLICT_REPLACE);
        sqLiteDatabase.close();
    }

    /**
     * @param id
     * @param task
     * @param content
     * @param date
     * @param time
     * @param status
     */
    void updateTask(int id, String task, String content, String date, String time, String status) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DB_COLUMN_TASK, task);
        values.put(DB_COLUMN_CONTENT, content);
        values.put(DB_COLUMN_DATE, date);
        values.put(DB_COLUMN_TIME, time);
        values.put(DB_COLUMN_STATUS, status);
        sqLiteDatabase.update(DB_TABLE, values, "ID="+id, null);
        sqLiteDatabase.close();
    }

    /**
     * @param id
     */
    void deleteTask(int id) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        sqLiteDatabase.delete(DB_TABLE, "ID="+id, null);
        sqLiteDatabase.close();
    }

    /**
     * @param id
     * @return list of a specific task
     */
    ArrayList<String>   getTask(int id) {
        ArrayList<String> taskTarget = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        @SuppressLint("DefaultLocale") String query =
                String.format("SELECT %s, %s, %s, %s, %s FROM %s WHERE %s = %d",
                DB_COLUMN_TASK, DB_COLUMN_CONTENT, DB_COLUMN_DATE, DB_COLUMN_TIME, DB_COLUMN_STATUS,
                        DB_TABLE, "ID", id);
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor.moveToFirst()){
            taskTarget.add(cursor.getString(cursor.getColumnIndex(DB_COLUMN_TASK)));
            taskTarget.add(cursor.getString(cursor.getColumnIndex(DB_COLUMN_CONTENT)));
            taskTarget.add(cursor.getString(cursor.getColumnIndex(DB_COLUMN_DATE)));
            taskTarget.add(cursor.getString(cursor.getColumnIndex(DB_COLUMN_TIME)));
            taskTarget.add(cursor.getString(cursor.getColumnIndex(DB_COLUMN_STATUS)));
        }
        cursor.close();
        sqLiteDatabase.close();
        return taskTarget;
    }

    /**
     * @return list of all tasks
     */
    ArrayList<String[]> getAllTasks() {
        ArrayList<String[]> allTasks = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query(DB_TABLE, new String[]{"ID", DB_COLUMN_TASK,
                        DB_COLUMN_CONTENT, DB_COLUMN_DATE, DB_COLUMN_TIME, DB_COLUMN_STATUS},
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            String[] stringList = new String[6];
            stringList[0] = Integer.toString(cursor.getInt(cursor.getColumnIndex("ID")));
            stringList[1] = cursor.getString(cursor.getColumnIndex(DB_COLUMN_TASK));
            stringList[2] = cursor.getString(cursor.getColumnIndex(DB_COLUMN_CONTENT));
            stringList[3] = cursor.getString(cursor.getColumnIndex(DB_COLUMN_DATE));
            stringList[4] = cursor.getString(cursor.getColumnIndex(DB_COLUMN_TIME));
            stringList[5] = cursor.getString(cursor.getColumnIndex(DB_COLUMN_STATUS));
            allTasks.add(stringList);
        }
        cursor.close();
        sqLiteDatabase.close();
        return (allTasks);
    }
}
