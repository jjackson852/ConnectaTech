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

/**
 * Created by theblackfu on 3/1/2015.
 */
public class EditImageActivity extends BootstrapActivity {

    private Photo photo;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onCreate(Bundle savedInstanceState) {

        final int RESULT_LOAD_IMAGE = 1;
        photo = new Photo();
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
        }


    public Photo getCurrentPhoto() {
        return photo;
    }

}
