package ru.skaridov.mynotes.classes;

import java.util.Calendar;

/**
 * Created by Asus on 20.11.2016.
 */

public class TaskNote {
    private int id;
    private String name;
    private Calendar finishDate, reminderDate;
    private boolean haveReminder;
    private int done;
    private int cycle;

    // Конструктор нового Note созданного пользователем без напоминания
    public TaskNote(String name, Calendar finishDate, int cycle) {
        this.name = name;
        this.finishDate = finishDate;
        this.haveReminder = false;
        this.cycle = cycle;
        this.done = DBContract.TasksTable.UNDONE;
    }

    // Конструктор нового Note созданного пользователем с напоминанием
    public TaskNote(String name, Calendar finishDate, Calendar reminderDate, int cycle) {
        this.name = name;
        this.finishDate = finishDate;
        this.haveReminder = true;
        this.reminderDate = reminderDate;
        this.cycle = cycle;
        this.done = DBContract.TasksTable.UNDONE;
    }

    // Конструктор нового Note полученного из БД с напоминанием
    public TaskNote(int id, String name, Calendar finishDate, Calendar reminderDate, int done, int cycle) {
        this.id = id;
        this.name = name;
        this.finishDate = finishDate;
        this.reminderDate = reminderDate;
        this.haveReminder = true;
        this.done = done;
        this.cycle = cycle;
    }

    // Конструктор нового Note полученного из БД без напоминания
    public TaskNote(int id, String name, Calendar finishDate, int done, int cycle) {
        this.id = id;
        this.name = name;
        this.finishDate = finishDate;
        this.haveReminder = false;
        this.done = done;
        this.cycle = cycle;
    }

    public int getDone() {
        return done;
    }

    public void setDone(int done) {
        this.done = done;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Calendar getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Calendar finishDate) {
        this.finishDate = finishDate;
    }

    public Calendar getReminderDate() {
        return reminderDate;
    }

    public void setReminderDate(Calendar reminderDate) {
        this.reminderDate = reminderDate;
    }

    public int getCycle() {
        return cycle;
    }

    public void setCycle(int cycle) {
        this.cycle = cycle;
    }

    public boolean isHaveReminder() {
        return haveReminder;
    }

    public void setHaveReminder(boolean haveReminder) {
        this.haveReminder = haveReminder;
    }
}
