package ru.skaridov.mynotes.interfaces;

import android.widget.TextView;

import ru.skaridov.mynotes.classes.MyRecyclerAdapter;
import ru.skaridov.mynotes.classes.TaskNote;

/**
 * Created by Asus on 04.12.2016.
 */

public interface OnItemClickListener {
    void onItemClick(TaskNote note, TextView textName);
}