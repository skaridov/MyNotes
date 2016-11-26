package ru.skaridov.mynotes.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import ru.skaridov.mynotes.MainActivity;
import ru.skaridov.mynotes.R;

/**
 * Created by Asus on 20.11.2016.
 */

public class NewNoteDialogFragment extends DialogFragment {

    EditText et;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_new_note, null);
        builder.setView(view);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                et = (EditText) getDialog().findViewById(R.id.edittext_new_note_name);
                ((MainActivity) getActivity()).okClicked(et.getText().toString());
                //Toast.makeText(null, et.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        return builder.create();
    }
}
