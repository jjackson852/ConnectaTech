package com.notify.app.mobile.ui;

import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.notify.app.mobile.BootstrapServiceProvider;
import com.notify.app.mobile.Injector;
import com.notify.app.mobile.R;
import com.notify.app.mobile.authenticator.LogoutService;
import com.notify.app.mobile.core.Experiment;
import com.github.kevinsawicki.wishlist.SingleTypeAdapter;
import com.notify.app.mobile.core.User;
import com.parse.ParseFile;
import com.parse.ParseObject;


import java.io.ByteArrayOutputStream;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class ExperimentListFragment extends Activity{
//        extends ItemListFragment<Experiment>{

//    @Inject protected BootstrapServiceProvider serviceProvider;
//    @Inject protected LogoutService logoutService;

//    @Override
//    protected SingleTypeAdapter<Experiment> createAdapter(final List<Experiment> items) {
//        return new ExperimentListAdapter(getActivity().getLayoutInflater(), items);
//    }
//
//    @Override
//    protected int getErrorMessage(final Exception exception) {
//        return R.string.error_loading_experiment;
//    }
//
//    @Override
//    protected LogoutService getLogoutService() {
//        return logoutService;
//    }
//
//    @Override
//    public Loader<List<Experiment>> onCreateLoader(final int id, final Bundle args) {
//        final List<Experiment> initialItems = items;
//        return new ThrowableLoader<List<Experiment>>(getActivity(), items) {
//
//            @Override
//            public List<Experiment> loadData() throws Exception {
//
//                try {
//                    List<Experiment> latest = null;
//
//                    if (getActivity() != null) {
//                        latest = serviceProvider.getService(getActivity()).getExperiment();
//                    }
//
//                    if (latest != null) {
//                        return latest;
//                    } else {
//                        return Collections.emptyList();
//                    }
//                }catch (final OperationCanceledException e){
//                    final Activity activity = getActivity();
//                    if (activity != null)
//                        activity.finish();
//                }
//                    return initialItems;
//            }
//        };
//    }

//    @Override
//    protected void configureList(final Activity activity, final ListView listView) {
//        super.configureList(activity, listView);
//
//        listView.setFastScrollEnabled(true);
//        listView.setDividerHeight(0);
//
//        getListAdapter().addHeader(activity.getLayoutInflater()
//                .inflate(R.layout.experiment_list_item_labels, null));
//    }

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from main.xml
        setContentView(R.layout.experiment_list_item_labels);

        // Locate the button in main.xml
        button = (Button) findViewById(R.id.stupidbtn);

        // Capture button clicks
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                // Locate the image from internal storage
                ImageView imageView = (ImageView) findViewById(R.id.imgView);
                Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
                imageView.setImageBitmap(bitmap);
                // Convert it to byte
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                // Compress image to lower quality scale 1 - 100
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] image = stream.toByteArray();

                // Create the ParseFile
                ParseFile file = new ParseFile("androidbegin.png", image);
                // Upload the image into Parse Cloud
                file.saveInBackground();

                // Create a New Class called "ImageUpload" in Parse
                ParseObject imgupload = new ParseObject("ImageUpload");

                // Create a column named "ImageName" and set the string
                imgupload.put("ImageName", "AndroidBegin Logo");

                // Create a column named "ImageFile" and insert the image
                imgupload.put("ImageFile", file);

                // Create the class and the columns
                imgupload.saveInBackground();

                // Show a simple toast message
                Toast.makeText(ExperimentListFragment.this, "Image Uploaded",
                        Toast.LENGTH_SHORT).show();
            }
        });
}
