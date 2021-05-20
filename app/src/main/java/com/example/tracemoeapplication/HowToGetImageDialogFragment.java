package com.example.tracemoeapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class HowToGetImageDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.how_to_get_image_dialog_title)
                .setItems(R.array.how_to_get_image_options, (dialog, which) -> {
                    // The 'which' argument contains the index position
                    // of the selected item
                })
                .setNegativeButton(R.string.how_to_get_image_cancel_button_text, (dialog, id) -> {
                    // User cancelled the dialog
                });
        return builder.create();
    }
}
