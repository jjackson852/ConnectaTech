package com.notify.app.mobile.ui;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import com.notify.app.mobile.Injector;
import com.notify.app.mobile.R;
import com.notify.app.mobile.bootstrapOrigin.ui.BootstrapActivity;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

/**
 * Created by theblackfu on 3/1/2015.
 */
public class EditImageActivity extends BootstrapActivity {

    private static final int RESULT_LOAD_IMAGE = 1;
    private Photo photo;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onCreate(Bundle savedInstanceState) {


       // photo = new Photo();
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        // getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        Injector.inject(this);

        setContentView(R.layout.activity_edit_image);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent i = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i, RESULT_LOAD_IMAGE);

//        FragmentManager manager = getFragmentManager();
//        Fragment fragment = manager.findFragmentById(R.id.fragmentContainer);
//
//        if (fragment == null) {
//            fragment = new EditImageActivity();
//            manager.beginTransaction().add(R.id.fragmentContainer, fragment)
//                    .commit();
        }    @Override
             protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            //test

            // String picturePath contains the path of selected Image


        }
    }

    public Photo getCurrentPhoto() {
        return photo;
    }


//    private void addPhotoAndReturn(ParseFile photoFile) {
////        ((EditPhotoActivity) getActivity()).getCurrentPhoto().setPhotoFile(
////                photoFile);
//        ParseUser cameraCurrentUser = ParseUser.getCurrentUser();
//        cameraCurrentUser.put("picturePath", photoFile);
//        cameraCurrentUser.saveInBackground();
//
//    }
}
