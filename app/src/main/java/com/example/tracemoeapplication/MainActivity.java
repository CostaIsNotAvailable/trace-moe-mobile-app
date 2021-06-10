package com.example.tracemoeapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.tracemoeapplication.dtos.MatchListDto;
import com.example.tracemoeapplication.enums.HowToGetImageDialogOptionEnum;
import com.example.tracemoeapplication.interfaces.HowToGetImageDialogListener;
import com.example.tracemoeapplication.interfaces.RequestManagerListener;
import com.example.tracemoeapplication.requestmanager.RequestManager;
import com.example.tracemoeapplication.screenslide.ScreenSlidePagerActivity;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, HowToGetImageDialogListener, RequestManagerListener {
    private Button selectImageButton;
    private ProgressBar progressBar;

    private static final int TAKE_PHOTO_REQUEST_CODE = 100;
    private static final int IMPORT_FROM_GALLERY_REQUEST_CODE = 101;
    public static final String EXTRA_MATCH_LIST = "EXTRA_MATCH_LIST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instances
        selectImageButton = findViewById(R.id.selectImageButton);
        progressBar = findViewById(R.id.progressBar);

        // Listeners
        selectImageButton.setOnClickListener(this);

        // Set default visibilities
        progressBar.setVisibility(View.GONE);
        selectImageButton.setVisibility(View.VISIBLE);
    }

    // Open image selection options dialog
    @Override
    public void onClick(View v) {
        if(v == selectImageButton){
            DialogFragment howToGetImageDialogFragment = new HowToGetImageDialogFragment();
            howToGetImageDialogFragment.show(getSupportFragmentManager(), "HowToGetImageDialog");
        }
    }

    // Process the option chose in the dialog
    public void onHowToGetImageDialogDialogSelectOption(HowToGetImageDialogOptionEnum option) {
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

    // Get the image and post it
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode != RESULT_OK){
        return;
        }

        Bitmap imageBitmap = null;
        switch (requestCode){
            case TAKE_PHOTO_REQUEST_CODE:
                if (data != null) {
                    imageBitmap = (Bitmap) data.getExtras().get("data");
                }
                break;
            case IMPORT_FROM_GALLERY_REQUEST_CODE:
                try {
                    if (data != null) {
                        imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            default: break;
        }

        if(imageBitmap != null){
            loading();
            RequestManager.getInstance(this).postImage(imageBitmap);
        } else {
            Toast.makeText(this, R.string.image_selection_error_text, Toast.LENGTH_LONG).show();
        }
    }

    // Image post response
    @Override
    public void onPostImageResponse(MatchListDto matchList) {
        if(matchList != null && matchList.getResult() != null){
            displayMatchList(matchList);
        } else {
            Toast.makeText(this, R.string.response_error_text, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onPostImageResponseError(Exception exception) {
        stopLoading();
        Toast.makeText(this, R.string.response_error_text, Toast.LENGTH_LONG).show();
    }

    // Image post response error

    // Launch the activity that will display the list
    public void displayMatchList(MatchListDto matchList) {
        Intent intent = new Intent(this, ScreenSlidePagerActivity.class);
        intent.putExtra(EXTRA_MATCH_LIST, matchList);
        startActivity(intent);
        stopLoading();
    }

    private void loading() {
        selectImageButton.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void stopLoading() {
        progressBar.setVisibility(View.GONE);
        selectImageButton.setVisibility(View.VISIBLE);
    }
}