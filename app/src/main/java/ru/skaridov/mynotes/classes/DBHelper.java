package ru.skaridov.mynotes.classes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Asus on 20.11.2016.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = DBHelper.class.getSimpleName();

    /**
     * Имя файла базы данных
     */
    private static final String DATABASE_NAME = "dbnotes.db";

    /**
     * Версия базы данных. При изменении схемы увеличить на единицу
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Конструктор {@link DBHelper}.
     *
     * @param context Контекст приложения
     */
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d(LOG_TAG, "--- onCreate database ---");
        String SQL_CREATE_TASKS_TABLE = "CREATE TABLE " + DBContract.TasksTable.TABLE_NAME + " ( " +
                DBContract.TasksTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DBContract.TasksTable.COLUMN_NAME + " STRING NOT NULL, " +
                DBContract.TasksTable.COLUMN_DONE + " INTEGER DEFAULT 0, " +
                DBContract.TasksTable.COLUMN_FINISHDATE + " DATETIME, " +
                DBContract.TasksTable.COLUMN_REMINDERDATE + " DATETIME, " +
                DBContract.TasksTable.COLUMN_CYCLE_ID + " INTEGER NOT NULL);";
        String SQL_CREATE_CYCLES_TABLE = "CREATE TABLE " + DBContract.CyclesTable.TABLE_NAME + " ( " +
                DBContract.CyclesTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DBContract.CyclesTable.COLUMN_DEFINITION + " STRING);";
        String SQL_INSERT_CYCLES1 = "INSERT INTO " + DBContract.CyclesTable.TABLE_NAME + " ( " + DBContract.CyclesTable.COLUMN_DEFINITION + ") VALUES ('non_cycle');";

        String SQL_INSERT_CYCLES2 = "INSERT INTO " + DBContract.CyclesTable.TABLE_NAME + " ( " + DBContract.CyclesTable.COLUMN_DEFINITION + ") VALUES ('every_day');";
        String SQL_INSERT_CYCLES3 = "INSERT INTO " + DBContract.CyclesTable.TABLE_NAME + " ( " + DBContract.CyclesTable.COLUMN_DEFINITION + ") VALUES ('every_week');";
        String SQL_INSERT_CYCLES4 = "INSERT INTO " + DBContract.CyclesTable.TABLE_NAME + " ( " + DBContract.CyclesTable.COLUMN_DEFINITION + ") VALUES ('every_month');";
        String SQL_INSERT_CYCLES5 = "INSERT INTO " + DBContract.CyclesTable.TABLE_NAME + " ( " + DBContract.CyclesTable.COLUMN_DEFINITION + ") VALUES ('every_year');";

        sqLiteDatabase.execSQL(SQL_CREATE_TASKS_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_CYCLES_TABLE);
        sqLiteDatabase.execSQL(SQL_INSERT_CYCLES1);
        sqLiteDatabase.execSQL(SQL_INSERT_CYCLES2);
        sqLiteDatabase.execSQL(SQL_INSERT_CYCLES3);
        sqLiteDatabase.execSQL(SQL_INSERT_CYCLES4);
        sqLiteDatabase.execSQL(SQL_INSERT_CYCLES5);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void saveNewNoteToDatabase(TaskNote note) {
        if (note == null) return;
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();
        cv.put(DBContract.TasksTable.COLUMN_NAME, note.getName());
        cv.put(DBContract.TasksTable.COLUMN_DONE, note.getDone());
        cv.put(DBContract.TasksTable.COLUMN_FINISHDATE, note.getFinishDate().toString());
        if (note.isHaveReminder()) cv.put(DBContract.TasksTable.COLUMN_REMINDERDATE, note.getReminderDate().toString());
        cv.put(DBContract.TasksTable.COLUMN_CYCLE_ID, note.getCycle());
        db.insert(DBContract.TasksTable.TABLE_NAME, null, cv);
    }

    public void updateNoteInDatabase(TaskNote note) {
        if (note == null) return;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DBContract.TasksTable._ID, note.getId());
        cv.put(DBContract.TasksTable.COLUMN_NAME, note.getName());
        cv.put(DBContract.TasksTable.COLUMN_DONE, note.getDone());
        cv.put(DBContract.TasksTable.COLUMN_FINISHDATE, note.getFinishDate().toString());
        if (note.isHaveReminder()) cv.put(DBContract.TasksTable.COLUMN_REMINDERDATE, note.getReminderDate().toString());
        cv.put(DBContract.TasksTable.COLUMN_CYCLE_ID, note.getCycle());
        db.update(DBContract.TasksTable.TABLE_NAME, cv, "_id = " + note.getId(), null);
    }

    public ArrayList<TaskNote> getTasksFromDatabase() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<TaskNote> taskNotesArray = new ArrayList<>();
        Cursor c = db.query(DBContract.TasksTable.TABLE_NAME, null, null, null, null, null, null);
        if (c.moveToFirst()) {
            int idIndex = c.getColumnIndex(DBContract.TasksTable._ID);
            int nameIndex = c.getColumnIndex(DBContract.TasksTable.COLUMN_NAME);
            int doneIndex = c.getColumnIndex(DBContract.TasksTable.COLUMN_DONE);
            int finishdateIndex = c.getColumnIndex(DBContract.TasksTable.COLUMN_FINISHDATE);
            int reminderdateIndex = c.getColumnIndex(DBContract.TasksTable.COLUMN_REMINDERDATE);
            int cycleidIndex = c.getColumnIndex(DBContract.TasksTable.COLUMN_CYCLE_ID);
            do {
                int id = c.getInt(idIndex);
                String name = c.getString(nameIndex);
                int done = c.getInt(doneIndex);
                Calendar finishdate = stringToCalendar(c.getString(finishdateIndex));
                int cycleid = c.getInt(cycleidIndex);
                String str = c.getString(reminderdateIndex);
                if (str == null) {   // без напоминания
                    TaskNote note = new TaskNote(id, name, finishdate, done, cycleid);
                    taskNotesArray.add(note);
                } else {   // с напоминанием
                    Calendar reminderdate = stringToCalendar(str);
                    TaskNote note = new TaskNote(id, name, finishdate, reminderdate, done, cycleid);
                    taskNotesArray.add(note);
                }
            } while (c.moveToNext());
        } else return null;
        c.close();
        return taskNotesArray;
    }

    private Calendar stringToCalendar(String s) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        try {
            cal.setTime(sdf.parse(s));
        } catch (ParseException e) {
            Log.d("MyLog", "parsing problem, string: " + s);
        }
        return cal;
    }
}
