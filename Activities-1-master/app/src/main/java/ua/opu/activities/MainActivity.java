package ua.opu.activities;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Uri imageUri;

    private ImageView mImageView;

    private TextView mEmailField;
    private TextView mSubjectField;
    private TextView mMessageField;

    private final Map<String, String> map = new HashMap<>();
    private static final int SECOND_ACTIVITY_REQUEST_CODE = 9999;
    private static final int REQUEST_IMAGE_CAPTURE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = findViewById(R.id.camera_image);

        mEmailField = findViewById(R.id.textview_to);
        mSubjectField = findViewById(R.id.textview_subject);
        mMessageField = findViewById(R.id.textview_message);

        Button mDetailsButton = findViewById(R.id.button_details);
        mDetailsButton.setOnClickListener(v -> {
            Intent i = new Intent(this, SecondActivity.class);
            startActivityForResult(i, SECOND_ACTIVITY_REQUEST_CODE);
        });

        Button mCameraButton = findViewById(R.id.button_camera);
        mCameraButton.setOnClickListener(v -> {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            try {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(this, "Error while trying to open camera app", Toast.LENGTH_SHORT).show();
            }
        });
        Button mSendButton = findViewById(R.id.button_send);
        mSendButton.setOnClickListener(v -> {
            try {
                Intent mail = new Intent(Intent.ACTION_SEND);
                mail.setType("message/rfc822");
                mail.putExtra(Intent.EXTRA_EMAIL, new String[]{map.get(Intent.EXTRA_EMAIL)});
                mail.putExtra(Intent.EXTRA_SUBJECT, map.get(Intent.EXTRA_SUBJECT));
                mail.putExtra(Intent.EXTRA_TEXT, map.get(Intent.EXTRA_TEXT));
                mail.putExtra(Intent.EXTRA_STREAM, imageUri);

                startActivity(Intent.createChooser(mail, "Choose email client"));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(this, "There are no email clients installed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // region Код для системы разрешений

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onStart() {
        super.onStart();

        verifyStoragePermissions();
    }

    public void verifyStoragePermissions() {
        // Проверяем наличие разрешения на запись во внешнее хранилище
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // Запрашиваем разрешение у пользователя
            ActivityCompat.requestPermissions(
                    this,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    map.put(Intent.EXTRA_EMAIL, data.getStringExtra(Intent.EXTRA_EMAIL));
                    map.put(Intent.EXTRA_SUBJECT, data.getStringExtra(Intent.EXTRA_SUBJECT));
                    map.put(Intent.EXTRA_TEXT, data.getStringExtra(Intent.EXTRA_TEXT));

                    mEmailField.setText(getString(R.string.textview_to_text, map.get(Intent.EXTRA_EMAIL)));
                    mSubjectField.setText(getString(R.string.textview_subject_text, map.get(Intent.EXTRA_SUBJECT)));
                    mMessageField.setText(getString(R.string.textview_message_text, map.get(Intent.EXTRA_TEXT)));
                }
            } else {
                Toast.makeText(this, "Action cancelled!", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageView.setImageBitmap(imageBitmap);

            File outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "screenshot.png");
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(outputFile);
                imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);

                imageUri = Uri.fromFile(outputFile);
                fileOutputStream.flush();
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // endregion
}