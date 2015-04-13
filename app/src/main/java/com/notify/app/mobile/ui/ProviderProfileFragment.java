package com.notify.app.mobile.ui;

import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.notify.app.mobile.BootstrapServiceProvider;
import com.notify.app.mobile.Injector;
import com.notify.app.mobile.R;
import com.notify.app.mobile.authenticator.LogoutService;
import com.notify.app.mobile.bootstrapOrigin.ui.ThrowableLoader;
import com.notify.app.mobile.core.Example;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;


public class ProviderProfileFragment extends ItemListFragment2 {

    @Inject
    protected LogoutService logoutService;
    @Inject
    protected BootstrapServiceProvider serviceProvider;

    @InjectView(R.id.tv_name)
    protected TextView name;
    private View view;
    private ImageView edit_photo;
    private Button edit_photo_button;
    private TextView txtRatingValue;
    //Rating
    private Button btnSubmit;
    private RatingBar ratingBar;
    private ProgressDialog progressDialog;
    //Toast Test Button
    private Button edit_rating;

    //Parse Object for Rating Value
    ParseObject ratingtxt;
    TextView totalRating;

    private Button selectImage;

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
            }
        };
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
        } catch (NullPointerException ex) {
            if (ex != null) {

                Button noPhotoButton = (Button) view.findViewById(R.id.edit_photo_btn);
                noPhotoButton.setText("Photo Not Yet Uploaded.\nClick here to upload one.");

                //     Button noRatingButton = (Button) view.findViewById(R.id.edit_rating_btn);
                //      noPhotoButton.setText("Rating Not Yet Uploaded.\nClick here to upload one.");
            }
        }

        edit_photo = ((ImageView) view.findViewById(R.id.provider_profile_image));
        edit_photo_button = ((Button) view.findViewById(R.id.edit_photo_btn));

        edit_photo_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Add A Photo", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), EditPhotoActivity.class);
                startActivity(intent);
            }
        });


        edit_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Add A Photo", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), EditPhotoActivity.class);
                startActivity(intent);
            }
        });
//-------------------------------------------------------------------------------------------------
        selectImage = (Button) view.findViewById(R.id.select_image_button);
        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Add A Photo", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), EditImageActivity.class);
                startActivity(intent);
            }
        });



//        Rating Button Listener
//        btnSubmit = ((Button) view.findViewById(R.id.edit_rating_btn));
//
//        btnSubmit.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                progressDialog = ProgressDialog.show(getActivity(), "",
//                        "Downloading Image...", true);
//
//                // Locate the class table named "ImageUpload" in Parse.com
//                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
//                        "ImageUpload");
//
//                // Locate the objectId from the class
//                query.getInBackground("KDKWQRTwRE",
//                        new GetCallback<ParseObject>() {
//
//                            public void done(ParseObject object,
//                                             ParseException e) {
//                                // TODO Auto-generated method stub
//
//                                // Locate the column named "ImageName" and set
//                                // the string
//                                ParseFile fileObject = (ParseFile) object
//                                        .get("ImageFile");
//                                fileObject
//                                        .getDataInBackground(new GetDataCallback() {
//
//                                            public void done(byte[] data,
//                                                             ParseException e) {
//                                                if (e == null) {
//                                                    Log.d("test",
//                                                            "We've got data in data.");
//                                                    // Decode the Byte[] into
//                                                    // Bitmap
//                                                    Bitmap bmp = BitmapFactory
//                                                            .decodeByteArray(data, 0, data.length);
//                                                    // Get the ImageView from
//                                                    // main.xml
//                                                    ImageView image = (ImageView) view.findViewById(R.id.image);
//                                                    // Set the Bitmap into the
//                                                    // ImageView
//                                                    image.setImageBitmap(bmp);
//                                                    // Close progress dialog
//                                                    progressDialog.dismiss();
//
//                                                } else {
//                                                    Log.d("test",
//                                                            "There was a problem downloading the data.");
//                                                }
//                                            }
//                                        });
//
//                            }
//                        });
//            }
//
//        });

//--------------------------------------------------------------------------------------------------

//
//        //Rating Button Listener
//        btnSubmit = ((Button) view.findViewById(R.id.edit_rating_btn));
//
//        btnSubmit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Toast.makeText(getActivity(), String.valueOf(ratingBar.getRating(), Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(getActivity(), RatingActivity.class);
//                startActivity(intent);
//            }
//        });

//--------------------------------------------------------------------------------------------------

        //Rating Button Listener
        ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
        txtRatingValue = (TextView) view.findViewById(R.id.txtRatingValue);

        //if rating value is changed,
        //display the current rating value in the result (textview) automatically
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {

                txtRatingValue.setText(String.valueOf(rating));

            }
        });

//--------------------------------------------------------------------------------------------------
//        //Rating Button Listener
//        ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
//        btnSubmit = (Button) view.findViewById(R.id.btnSubmit);
//
//        //if click on me, then display the current rating value.
//        btnSubmit.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                // ParseUser ratingId = new ParseUser();
//                ratingtxt = new ParseObject("Rating");
//
//                ratingtxt.put("rating", Float.valueOf(ratingBar.getRating()));
//                ratingtxt.put("submittedBy", ParseUser.getCurrentUser());
//                ratingtxt.add("ratingTotal", String.valueOf(ratingBar.getRating()));
//
//
//                Toast.makeText(getActivity(),
//                        String.valueOf(ratingBar.getRating()),
//                        Toast.LENGTH_SHORT).show();
//                ratingtxt.saveInBackground();
//
//            }
//
//        });
//--------------------------------------------------------------------------------------------------


        // Inflate the layout for this fragment
        return view;
    }

//    @Override
//    public void onResume(){
//        super.onResume();
//        refresh();
//    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


}

