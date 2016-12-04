package ru.skaridov.mynotes;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

import ru.skaridov.mynotes.classes.DBContract;
import ru.skaridov.mynotes.classes.DBHelper;
import ru.skaridov.mynotes.classes.MyRecyclerAdapter;
import ru.skaridov.mynotes.classes.TaskNote;
import ru.skaridov.mynotes.dialogs.NewNoteDialogFragment;
import ru.skaridov.mynotes.dialogs.NoteOptionsDialogFragment;
import ru.skaridov.mynotes.interfaces.OnItemClickListener;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnItemClickListener {

    DBHelper dbHelper;
    ArrayList<TaskNote> notesToShowArray;
    RecyclerView recyclerView;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewNote();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        context = this;
        dbHelper = new DBHelper(this);
        notesToShowArray = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    }

    private void createNewNote() {
        FragmentManager manager = getSupportFragmentManager();
        NewNoteDialogFragment newNoteDialogFragment = new NewNoteDialogFragment();
        newNoteDialogFragment.show(manager, "new note");
    }

    public void onItemClick(TaskNote note, TextView textName) {
        FragmentManager manager = getSupportFragmentManager();
        NoteOptionsDialogFragment noteOptionsDialogFragment = new NoteOptionsDialogFragment();
        noteOptionsDialogFragment.show(manager, "note options", note);
    }

    @Override
    protected void onStart() {
        ArrayList<TaskNote> tn = dbHelper.getTasksFromDatabase();
        if (tn != null) notesToShowArray = tn;
        super.onStart();
    }

    @Override
    protected void onStop() {
        for (int i = 0; i < notesToShowArray.size(); i++) {
            dbHelper.updateNoteInDatabase(notesToShowArray.get(i));
        }
        dbHelper.close();
        super.onStop();
    }

    @Override
    protected void onResume() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyRecyclerAdapter adapter = new MyRecyclerAdapter(notesToShowArray, this);
        recyclerView.setAdapter(adapter);
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void okClickedNewNote(String s) {
        TaskNote newNote = new TaskNote(s, Calendar.getInstance(), DBContract.CyclesTable.NON_CYCLE);
        notesToShowArray.add(newNote);
        dbHelper.saveNewNoteToDatabase(newNote);
    }

    public void okClickedNoteOptions(String s, TaskNote note) {
        note.setName(s);
        dbHelper.updateNoteInDatabase(note);
    }
}
