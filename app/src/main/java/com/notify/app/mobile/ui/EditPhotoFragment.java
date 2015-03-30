package com.notify.app.mobile.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.notify.app.mobile.R;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseUser;
import com.parse.SaveCallback;

/**
 * Created by theblackfu on 3/1/2015.
 */
public class EditPhotoFragment extends Fragment {

    private ImageButton photoButton;
    private Button saveButton;
    private Button cancelButton;
    private ParseImageView photoPreview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle SavedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_edit_photo, parent, false);
        photoButton = ((ImageButton) v.findViewById(R.id.photo_button));

        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCamera();
            }
        });

        saveButton = ((Button) v.findViewById(R.id.save_button));

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Photo photo = ((EditPhotoActivity) getActivity()).getCurrentPhoto();
                photo.setAuthor(ParseUser.getCurrentUser());

                photo.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            getActivity().setResult(Activity.RESULT_OK);
//                            getActivity().finish();
                            Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        } else {
                            Toast.makeText(
                                    getActivity().getApplicationContext(),
                                    "Error saving: " + e.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        cancelButton = ((Button) v.findViewById(R.id.cancel_button));

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().setResult(Activity.RESULT_CANCELED);
                getActivity().finish();
            }
        });
// Until the user has taken a photo, hide the preview
        photoPreview = (ParseImageView) v.findViewById(R.id.edit_photo_preview);
        photoPreview.setVisibility(View.INVISIBLE);
        return v;
    }
    /*
    * All data entry about a Photo object is managed from the EditPhotoActivity.
    * When the user wants to add a photo, we'll start up a custom
    * CameraFragment that will let them take the photo and save it to the Photo
    * object owned by the EditPhotoActivity. Create a new CameraFragment, swap
    * the contents of the fragmentContainer (see activity_edit_photo.xml), then
    * add the EditPhotoFragment to the back stack so we can return to it when the
    * camera is finished.
    */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void startCamera() {
        Fragment cameraFragment = new CameraFragment();
        FragmentTransaction transaction = getActivity().getFragmentManager()
                .beginTransaction();
        transaction.replace(R.id.fragmentContainer, cameraFragment);
        transaction.addToBackStack("EditPhotoFragment");
        transaction.commit();
    }
    /*
    * On resume, check and see if a photo has been set from the
    * CameraFragment. If it has, load the image in this fragment and make the
    * preview image visible.
    */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onResume() {
        super.onResume();
        ParseFile photoFile = ((EditPhotoActivity) getActivity())
                .getCurrentPhoto().getPhotoFile();
        if (photoFile != null) {
            photoPreview.setParseFile(photoFile);
            photoPreview.loadInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] data, ParseException e) {
                    photoPreview.setVisibility(View.VISIBLE);
                }
            });
        }
    }
}
