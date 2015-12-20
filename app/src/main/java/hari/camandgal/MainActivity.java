package hari.camandgal;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_IMAGE_CAMERA = 102;
    private static final int REQUEST_CODE_PICK_FILE_GALLERY = 103;
    private String filePath, mCurrentPhotoPath;
    private ImageView image_iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image_iv = (ImageView) findViewById(R.id.image_iv);
        //set listeners
        findViewById(R.id.select_from_gal).setOnClickListener(this);
        findViewById(R.id.select_with_cam).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.select_from_gal:
                Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, REQUEST_CODE_PICK_FILE_GALLERY);
                break;
            case R.id.select_with_cam:
                dispatchTakePictureIntent(REQUEST_CODE_IMAGE_CAMERA);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bitmap bitmap;
            if (requestCode == REQUEST_CODE_PICK_FILE_GALLERY) {
                filePath = getFilePath(this, data.getData());
                bitmap = createBitMap(filePath);
                image_iv.setImageBitmap(bitmap);
            } else if (requestCode == REQUEST_CODE_IMAGE_CAMERA) {
                filePath = mCurrentPhotoPath;
                bitmap = createBitMap(filePath);
                image_iv.setImageBitmap(bitmap);
            }
        }
    }

    public static Bitmap createBitMap(String capturingImageURl) {
        File file = new File(capturingImageURl);
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        return bitmap;
    }

    public static String getFilePath(Context context, Uri contentUri) {
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(contentUri, filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String filePath = cursor.getString(columnIndex);
        cursor.close();
        return filePath;
    }

    private void dispatchTakePictureIntent(int requestCode) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Create the File where the photo should go
        File photoFile = createImageFile();
        // Continue only if the File was successfully created
        if (photoFile != null) {
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
            startActivityForResult(takePictureIntent, requestCode);
        }

    }

    private File createImageFile() {
        // Create an image file name
        String imageFileName = "Me" + System.currentTimeMillis();
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/" + "Me");
        if (!storageDir.exists())
            storageDir.mkdir();
        File image = null;
        try {
            image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );

        } catch (IOException e) {
            e.printStackTrace();
        }
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;


    }
}
