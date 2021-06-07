package com.example.tracemoeapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.tracemoeapplication.enums.HowToGetImageDialogOptionEnum;
import com.example.tracemoeapplication.interfaces.HowToGetImageDialogListener;

public class HowToGetImageDialogFragment extends DialogFragment {

    HowToGetImageDialogListener listener;

   @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (HowToGetImageDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement HowToGetImageDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.how_to_get_image_dialog_title)
                .setItems(R.array.how_to_get_image_options, (dialog, which) -> {
                    selectOption(which);
                })
                .setNegativeButton(R.string.how_to_get_image_cancel_button_text, (dialog, id) -> {
                });
        return builder.create();
    }

    private void selectOption(int optionIndex){
        HowToGetImageDialogOptionEnum option;
        switch (optionIndex){
            case 0:
                option = HowToGetImageDialogOptionEnum.TAKE_PHOTO;
                break;
            case 1:
                option = HowToGetImageDialogOptionEnum.IMPORT_FROM_GALLERY;
                break;
            default:
                throw new UnsupportedOperationException("Not implemented option selection");
        }
        listener.onHowToGetImageDialogDialogSelectOption(option);
    }
}
