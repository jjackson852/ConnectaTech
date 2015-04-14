package com.notify.app.mobile.ui;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.notify.app.mobile.R;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;


public class ImageFragment extends Fragment {

    public static final String TAG = "ImageFragment";
    private static final int RESULT_OK = 1 ;
    private Camera camera;
    private SurfaceView surfaceView;
    private ParseFile photoFile;
    private ImageButton imageButton;
    private Intent data;
    private static int LOAD_IMAGE_RESULTS = 1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_image, parent, false);
        imageButton = (ImageButton) v.findViewById(R.id.image_button);
        // Set button's onClick listener object.




//        if (camera == null) {
//            try {
//                camera = Camera.open();
//                imageButton.setEnabled(true);
//            } catch (Exception e) {
//                Log.e(TAG, "No camera with exception: " + e.getMessage());
//                imageButton.setEnabled(false);
//                Toast.makeText(getActivity(), "No camera detected",
//                        Toast.LENGTH_LONG).show();
//            }
//        }
//        imageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (camera == null)
//                    return;
//                camera.takePicture(new Camera.ShutterCallback() {
//                    @Override
//                    public void onShutter() {
//// nothing to do
//                    }
//                }, null, new Camera.PictureCallback() {
//                    @Override
//                    public void onPictureTaken(byte[] data, Camera camera) {
//                        saveScaledPhoto(data);
//                    }
//                });
//            }
//        });
        //saveScaledPhoto();
//        @Override
//        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//            super.onActivityResult(requestCode, resultCode, data);
//
//            // Here we need to check if the activity that was triggers was the Image Gallery.
//            // If it is the requestCode will match the LOAD_IMAGE_RESULTS value.
//            // If the resultCode is RESULT_OK and there is some data we know that an image was picked.
//            if (requestCode == LOAD_IMAGE_RESULTS && resultCode == RESULT_OK && data != null) {
//                // Let's read picked image data - its URI
//                Uri pickedImage = data.getData();
//                // Let's read picked image path using content resolver
//                String[] filePath = { MediaStore.Images.Media.DATA };
//                Cursor cursor = getContentResolver().query(pickedImage, filePath, null, null, null);
//                cursor.moveToFirst();
//                String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));
//
//                // Now we need to set the GUI ImageView data with data read from the picked file.
//                imageButton.setImageBitmap(BitmapFactory.decodeFile(imagePath));
//
//                // At the end remember to close the cursor or you will end with the RuntimeException!
//                cursor.close();
//            }
//        }
//Intent i  = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//// Start new activity with the LOAD_IMAGE_RESULTS to handle back the results when image is picked from the Image Gallery.
//        startActivityForResult(i, LOAD_IMAGE_RESULTS);



        surfaceView = (SurfaceView) v.findViewById(R.id.camera_surface_view);
        SurfaceHolder holder = surfaceView.getHolder();
        holder.addCallback(new Callback() {
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (camera != null) {
                        camera.setDisplayOrientation(90);
                        camera.setPreviewDisplay(holder);
                        camera.startPreview();
                    }
                } catch (IOException e) {
                    Log.e(TAG, "Error setting up preview", e);
                }
            }

            public void surfaceChanged(SurfaceHolder holder, int format,
                                       int width, int height) {
// nothing to do here
            }

            public void surfaceDestroyed(SurfaceHolder holder) {
// nothing here
            }
        });
        return v;
    }

    /*
    * ParseQueryAdapter loads ParseFiles into a ParseImageView at whatever size
    * they are saved. Since we never need a full-size image in our app, we'll
    * save a scaled one right away.
    */
    private void saveScaledPhoto(byte[] data) {
// Resize photo from camera byte array
        Bitmap image = BitmapFactory.decodeByteArray(data, 0, data.length);
        Bitmap imageScaled = Bitmap.createScaledBitmap(image, 200, 200
                * image.getHeight() / image.getWidth(), false);
// Override Android default landscape orientation and save portrait
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        Bitmap rotatedScaledImage = Bitmap.createBitmap(imageScaled, 0,
                0, imageScaled.getWidth(), imageScaled.getHeight(),
                matrix, true);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        rotatedScaledImage.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        byte[] scaledData = bos.toByteArray();
// Save the scaled image to Parse
        photoFile = new ParseFile("profile_photo.jpg", scaledData);
        photoFile.saveInBackground(new SaveCallback() {
            public void done(ParseException e) {
                if (e != null) {
                    Toast.makeText(getActivity(),
                            "Error saving: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                } else {
                    addPhotoAndReturn(photoFile);
                }
            }
        });
    }

    /*
    * Once the photo has saved successfully, we're ready to return to the
    * EditPhotoFragment. When we added the CameraFragment to the back stack, we
    * named it "EditPhotoFragment". Now we'll pop fragments off the back stack
    * until we reach that Fragment.
    */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void addPhotoAndReturn(ParseFile photoFile) {
//        ((EditPhotoActivity) getActivity()).getCurrentPhoto().setPhotoFile(
//                photoFile);
        ParseUser cameraCurrentUser = ParseUser.getCurrentUser();
        cameraCurrentUser.put("ImageFile", photoFile);
        cameraCurrentUser.saveInBackground();
        FragmentManager fm = getActivity().getFragmentManager();
        fm.popBackStack("EditImageFragment",
                FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (camera == null) {
            try {
                camera = Camera.open();
                imageButton.setEnabled(true);
            } catch (Exception e) {
                Log.i(TAG, "No camera: " + e.getMessage());
                imageButton.setEnabled(false);
                Toast.makeText(getActivity(), "No camera detected",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onPause() {
        if (camera != null) {
            camera.stopPreview();
            camera.release();
        }
        super.onPause();
    }

    public void setImage(Bitmap map) {


        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        // Compress image to lower quality scale 1 - 100
        map.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] image = stream.toByteArray();

        // Create the ParseFile
        ParseFile file = new ParseFile("picture_1.jpeg", image);
        // Upload the image into Parse Cloud
        ParseUser user = ParseUser.getCurrentUser();
        user.put("profilePic", file);

        user.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

            }
        });
    }
}