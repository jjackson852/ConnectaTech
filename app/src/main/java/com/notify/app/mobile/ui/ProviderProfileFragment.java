package com.notify.app.mobile.ui;

import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.notify.app.mobile.BootstrapServiceProvider;
import com.notify.app.mobile.Injector;
import com.notify.app.mobile.R;
import com.notify.app.mobile.authenticator.LogoutService;
import com.notify.app.mobile.core.Example;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;


public class ProviderProfileFragment extends ItemListFragment2 {

    private View view;
    private Button edit_photo;

    @Inject protected LogoutService logoutService;
    @Inject protected BootstrapServiceProvider serviceProvider;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Injector.inject(this);
    }

    @Override
    protected int getErrorMessage(Exception exception) {
        return 0;
    }

    @Override
    protected LogoutService getLogoutService() {
        return logoutService;
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        final List<Example> initialItems = items;
        return new ThrowableLoader<List<Example>>(getActivity(), items) {
            @Override
            public List<Example> loadData() throws Exception {
                try {
                    if (getActivity() != null) {
                        return serviceProvider.getService(getActivity()).getExample();
                    } else {
                        return Collections.emptyList();
                    }

                } catch (OperationCanceledException e) {
                    Activity activity = getActivity();
                    if (activity != null)
                        activity.finish();
                    return initialItems;
                }
            }};
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.provider_profile_activity, container, false);

        final ParseFile providerPhoto = (ParseFile) ParseUser.getCurrentUser()
                .get("ImageFile");

        try {
            providerPhoto.getDataInBackground(new GetDataCallback() {

                public void done(byte[] data,
                                 ParseException e) {
                    if (e == null) {
                        Log.d("test",
                                "We've got data in data.");
                        // Decode the Byte[] into
                        // Bitmap
                        Bitmap bmp = BitmapFactory
                                .decodeByteArray(
                                        data, 0,
                                        data.length);

                        // Get the ImageView from
                        // main.xml
                        ImageView providerPic = (ImageView) view.findViewById(R.id.provider_profile_image);

                        // Set the Bitmap into the
                        // ImageView
                        providerPic.setImageBitmap(bmp);

                        // Close progress dialog
                        //   progressDialog.dismiss();

                    } else {
                        Log.d("test",
                                "There was a problem downloading the data.");
                    }
                }
            });
        }catch(NullPointerException ex){
            if(ex != null){

                TextView test = (TextView) view.findViewById(R.id.no_photo);
                test.setText("Photo Not Yet Uploaded.");
            }
        }

        edit_photo = ((Button) view.findViewById(R.id.edit_photo_btn));

        edit_photo.setOnClickListener(new View.OnClickListener(){
            @Override
        public void onClick(View v){
                Intent intent = new Intent(getActivity(), EditPhotoActivity.class);
                startActivity(intent);
            }
        });
         // Inflate the layout for this fragment
         return view;
     }
}

