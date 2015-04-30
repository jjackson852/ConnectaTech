package com.notify.app.mobile.ui;

import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.provider.MediaStore;
import android.net.Uri;
import android.database.Cursor;

import com.notify.app.mobile.BootstrapApplication;
import com.notify.app.mobile.BootstrapServiceProvider;
import com.notify.app.mobile.Injector;
import com.notify.app.mobile.R;
import com.notify.app.mobile.authenticator.LogoutService;
import com.notify.app.mobile.bootstrapOrigin.core.BootstrapService;
import com.notify.app.mobile.bootstrapOrigin.core.User;
import com.notify.app.mobile.bootstrapOrigin.ui.ThrowableLoader;
import com.notify.app.mobile.bootstrapOrigin.ui.UserActivity;
import com.notify.app.mobile.core.Example;
import com.notify.app.mobile.core.Request;
import com.parse.FunctionCallback;
import com.parse.GetDataCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;


import java.io.ByteArrayOutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.InjectView;

import static com.notify.app.mobile.bootstrapOrigin.core.Constants.Extra.USER;


public class ProviderProfileFragment extends ItemListFragment2 {

    @Inject
    protected LogoutService logoutService;
    @Inject
    protected BootstrapServiceProvider serviceProvider;

    @InjectView(R.id.tv_name)
    protected TextView name;

//    @InjectView(R.id.providerInfo)
//    protected TextView providerInfoTV;
    private View view;
    private ImageView edit_photo;
    private Button edit_photo_button;
    private ParseUser currentUser;
    private TextView txtRatingValue;
    //Rating
    private Button btnSubmit;
    private Button providerInfoBtn;
   // private TextView providerInfo;
    private ProgressDialog progressDialog;
    private Float avgRating;
    //Toast Test Button
    private Button edit_rating;
    private int RESULT_LOAD_IMAGE;
    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;
    AlertDialog.Builder alert;
    private ImageView providerPic;
    //Parse Object for Rating Value
    ParseObject ratingtxt;
    TextView totalRating;
    private String provBioStr;
    private TextView providerInfoTV;
    private ParseFile providerPhoto;

    private Button selectImage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Injector.inject(this);

        BootstrapService.setRatingConstraint("{\"provider\":{\"__type\":\"Pointer\",\"className\":\"_User\",\"objectId\":\"" + ParseUser.getCurrentUser().getObjectId() + "\"}}");
        RatingListFragment ratingFragment = new RatingListFragment();

        // Add the fragment to the 'fragment_container' FrameLayout
        getChildFragmentManager().beginTransaction()
                .add(R.id.rating_fragment_container_prov, ratingFragment).commit();
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

        currentUser = ParseUser.getCurrentUser();
        providerInfoTV = (TextView) view.findViewById(R.id.providerInfo);
        String provBioStr = currentUser.getString("bio");
        if (provBioStr == null){
            providerInfoTV.setText("No bio given.");
        }
        else{
            providerInfoTV.setText(provBioStr);
        }


        alert = new AlertDialog.Builder(getActivity());

        alert.setTitle("Edit Bio");

        final EditText bioEditText = new EditText(getActivity());
        bioEditText.setText(provBioStr);
        alert.setView(bioEditText);


        final Intent profileIntent = new Intent(getActivity(), MainActivity.class);
        alert.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //What ever you want to do with the value
                String bioText = String.valueOf(bioEditText.getText());

                currentUser.put("bio", bioText);
                currentUser.saveInBackground();

//                getActivity().finish();
//                startActivity(profileIntent);
                providerInfoTV.setText(bioText);
                ((ViewGroup)bioEditText.getParent()).removeView(bioEditText);
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
                ((ViewGroup)bioEditText.getParent()).removeView(bioEditText);
            }
        });

        providerPhoto = (ParseFile) ParseUser.getCurrentUser()
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
                        providerPic = (ImageView) view.findViewById(R.id.provider_profile_image);

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
                providerPic = (ImageView) view.findViewById(R.id.provider_profile_image);
                Drawable myDrawable = getResources().getDrawable(R.drawable.gravatar_icon);
                providerPic.setImageDrawable(myDrawable);

//                Button noPhotoButton = (Button) view.findViewById(R.id.edit_photo_btn);
//                noPhotoButton.setText("Photo Not Yet Uploaded.\nClick here to upload one.");

                //     Button noRatingButton = (Button) view.findViewById(R.id.edit_rating_btn);
                //      noPhotoButton.setText("Rating Not Yet Uploaded.\nClick here to upload one.");
            }
        }

//        edit_photo = ((ImageView) view.findViewById(R.id.provider_profile_image));
//        edit_photo_button = ((Button) view.findViewById(R.id.edit_photo_btn));
//
//        edit_photo_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getActivity(), "Add A Photo", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(getActivity(), EditPhotoActivity.class);
//                startActivity(intent);
//            }
//        });
//
//
//        edit_photo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getActivity(), "Add A Photo", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(getActivity(), EditImageActivity.class);
//                startActivity(intent);
//            }
//        });
//-------------------------------------------------------------------------------------------------
        selectImage = (Button) view.findViewById(R.id.select_image_button);
        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create intent to Open Image applications like Gallery, Google Photos
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // Start the Intent
                getActivity().startActivityForResult(galleryIntent, RESULT_LOAD_IMG);

//                Toast.makeText(getActivity(), "Add A Photo", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(getActivity(), EditImageActivity.class);
//                startActivity(intent);


            }
        });
        providerInfoBtn = (Button) view.findViewById(R.id.providerInfoBtn);
        providerInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              alert.show();
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


        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("providerID", ParseUser.getCurrentUser().getObjectId());
        ParseCloud.callFunctionInBackground("averageRating", params, new FunctionCallback<Map<String, Object>>() {
            public void done(Map<String, Object> ratings, ParseException e) {
                if (e == null) {
                    // ratings is 4.5 or such


                    try {
                        avgRating = Float.valueOf(ratings.get("avgRating").toString());
                    } catch (NumberFormatException ex) {
                        avgRating = 0.0f;
                    }


                    RatingBar ratingBarViewOnly = (RatingBar) getActivity().findViewById(R.id.ratingBar);

                    ratingBarViewOnly.setRating(avgRating);
                    ratingBarViewOnly.setIsIndicator(true);
                    ratingBarViewOnly.invalidate();


                } else {

                }

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

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        try {
//            // When an Image is picked
//            if (requestCode == RESULT_LOAD_IMG && resultCode == getActivity().RESULT_OK
//                    && null != data) {
//                // Get the Image from data
//
//                Uri selectedImage = data.getData();
//                String[] filePathColumn = { MediaStore.Images.Media.DATA };
//
//                // Get the cursor
//                Cursor cursor = getActivity().getContentResolver().query(selectedImage,
//                        filePathColumn, null, null, null);
//                // Move to first row
//                cursor.moveToFirst();
//
//                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                imgDecodableString = cursor.getString(columnIndex);
//                cursor.close();
//                ///ImageView imgView = (ImageView) findViewById(R.id.imgView);
//                // Set the Image in ImageView after decoding the String
////                imgView.setImageBitmap(BitmapFactory
////                        .decodeFile(imgDecodableString));
//
//                //-------------begin gallery
//                Bitmap newPhoto = BitmapFactory.decodeFile(imgDecodableString);
//                Toast.makeText(getActivity(), "Photos!",
//                        Toast.LENGTH_LONG).show();
//                Bitmap imageScaled = Bitmap.createScaledBitmap(newPhoto, 200, 200
//                        * newPhoto.getHeight() / newPhoto.getWidth(), false);
//// Override Android default landscape orientation and save portrait
//                Matrix matrix = new Matrix();
//                matrix.postRotate(0);
//                Bitmap rotatedScaledImage = Bitmap.createBitmap(imageScaled, 0,
//                        0, imageScaled.getWidth(), imageScaled.getHeight(),
//                        matrix, true);
//                ByteArrayOutputStream bos = new ByteArrayOutputStream();
//                rotatedScaledImage.compress(Bitmap.CompressFormat.JPEG, 100, bos);
//                byte[] scaledData = bos.toByteArray();
//// Save the scaled image to Parse
//                ParseFile newPhotoFile = new ParseFile("profile_photoV2.jpg", scaledData);
//                ParseUser.getCurrentUser().put("ImageFile", newPhotoFile);
//                ParseUser.getCurrentUser().saveEventually();
//
//
//            } else {
//                Toast.makeText(getActivity(), "You haven't picked Image",
//                        Toast.LENGTH_LONG).show();
//            }
//        } catch (Exception e) {
//            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG)
//                    .show();
//        }
//
//    }


}

