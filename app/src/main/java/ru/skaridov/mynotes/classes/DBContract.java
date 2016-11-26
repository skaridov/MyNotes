package ru.skaridov.mynotes.classes;

import android.provider.BaseColumns;

/**
 * Created by Asus on 20.11.2016.
 */

public final class DBContract {
    private DBContract() {};

    public static final class TasksTable implements BaseColumns {
        public final static String TABLE_NAME = "tasks";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_NAME = "name";
        public final static String COLUMN_DONE = "done";
        public final static String COLUMN_FINISHDATE = "finishdate";
        public final static String COLUMN_REMINDERDATE = "reminderdate";
        public final static String COLUMN_CYCLE_ID = "cycle_id";

        public static final int DONE = 1;
        public static final int UNDONE = 0;
    }

    public static final class CyclesTable implements BaseColumns {
        public final static String TABLE_NAME = "cycles";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_DEFINITION = "definition";

        public static final int NON_CYCLE = 1;
        public static final int EVERY_DAY = 2;
        public static final int EVERY_WEEK = 3;
        public static final int EVERY_MONTH = 4;
        public static final int EVERY_YEAR = 5;
    }
}
