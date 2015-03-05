package com.notify.app.mobile.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.notify.app.mobile.R;
import com.notify.app.mobile.authenticator.TimerTestFragment;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseUser;

import java.io.File;


public class ProviderProfileFragment extends TimerTestFragment implements View.OnClickListener {


    private View view;

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
                        ImageView providerPic = (ImageView) getActivity().findViewById(R.id.provider_profile_image);

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


        // Inflate the layout for this fragment
        return view;
    }


    @Override
    public void onClick(View view) {

        this.view = view;
    }


}

