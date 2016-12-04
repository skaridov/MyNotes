package ru.skaridov.mynotes.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import ru.skaridov.mynotes.MainActivity;
import ru.skaridov.mynotes.R;
import ru.skaridov.mynotes.classes.TaskNote;

/**
 * Created by Asus on 04.12.2016.
 */

public class NoteOptionsDialogFragment extends DialogFragment {
    Context context;
    EditText etName;
    TaskNote note;

    public void show(FragmentManager manager, String tag, TaskNote note) {
        super.show(manager, tag);
        this.note = note;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_note_options, null);
        builder.setView(view);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                etName = (EditText) getDialog().findViewById(R.id.dialog_note_options_name);
                ((MainActivity) getActivity()).okClickedNoteOptions(etName.getText().toString(), note);
                //Toast.makeText(null, et.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        //context = getContext();

        return builder.create();
    }
}
