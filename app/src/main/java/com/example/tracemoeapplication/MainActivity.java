package com.example.tracemoeapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button selectImageButton;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instances
        selectImageButton = findViewById(R.id.selectImageButton);
        imageView = findViewById(R.id.imageView);

        // Listeners
        selectImageButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == selectImageButton){
            DialogFragment howToGetImageDialogFragment = new HowToGetImageDialogFragment();
            FragmentManager fragmentManager = this.getSupportFragmentManager();
            howToGetImageDialogFragment.show(fragmentManager, "Dialog");
        }
    }
}