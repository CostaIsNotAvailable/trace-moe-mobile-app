package com.example.tracemoeapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.tracemoeapplication.enums.HowToGetImageDialogOptionEnum;
import com.example.tracemoeapplication.interfaces.HowToGetImageDialogListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, HowToGetImageDialogListener{
    private Button selectImageButton;
    private ImageView imageView;
    private static final int TAKE_PHOTO_REQUEST_CODE = 100;
    private static final int IMPORT_FROM_GALLERY_REQUEST_CODE = 101;

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
            howToGetImageDialogFragment.show(getSupportFragmentManager(), "HowToGetImageDialog");
        }
    }

    public void onHowToGetImageDialogDialogSelectOption(HowToGetImageDialogOptionEnum option) {
        Uri image;
        switch (option){
            case TAKE_PHOTO:
                Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePhotoIntent, TAKE_PHOTO_REQUEST_CODE);
                break;
            case IMPORT_FROM_GALLERY:
                Intent imagePicker = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(imagePicker, IMPORT_FROM_GALLERY_REQUEST_CODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == TAKE_PHOTO_REQUEST_CODE){
            imageView.setImageBitmap((Bitmap) data.getExtras().get("data"));
        }

        if(resultCode == RESULT_OK && requestCode == IMPORT_FROM_GALLERY_REQUEST_CODE){
            imageView.setImageURI(data.getData());
        }
    }
}