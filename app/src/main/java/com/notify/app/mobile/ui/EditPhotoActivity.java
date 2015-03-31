package com.notify.app.mobile.ui;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Build;
import android.os.Bundle;

import com.notify.app.mobile.Injector;
import com.notify.app.mobile.R;
import com.notify.app.mobile.bootstrapOrigin.ui.BootstrapActivity;

/**
 * Created by theblackfu on 3/1/2015.
 */
public class EditPhotoActivity extends BootstrapActivity {

    private Photo photo;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onCreate(Bundle savedInstanceState) {


        photo = new Photo();
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        // getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        Injector.inject(this);

        setContentView(R.layout.activity_edit_photo);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        FragmentManager manager = getFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragmentContainer);

        if (fragment == null) {
            fragment = new EditPhotoFragment();
            manager.beginTransaction().add(R.id.fragmentContainer, fragment)
                    .commit();
        }
    }

    public Photo getCurrentPhoto() {
        return photo;
    }

}
