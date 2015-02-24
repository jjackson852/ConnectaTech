package com.notify.app.mobile.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.notify.app.mobile.R;
import com.parse.ParseFile;
import com.parse.ParseObject;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Date;

public class ExperimentListFragment extends Fragment{
//    private static final int PICK_IMAGE = 1;
//    Button button;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        View rootView = inflater.inflate(R.layout.experiment_items, container, false);
//        button = (Button) rootView.findViewById(R.id.experimentbtn);
//        button.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View arg0) {
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
//                startActivity(intent);
//                Uri selectedImage = intent.getData();
//                String[] filePathColumn = {MediaStore.Images.Media.DATA};
//                Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
//                cursor.moveToFirst();
//                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                String filePath = cursor.getString(columnIndex);
//                cursor.close();
//                //ImageView imageView = (ImageView) findViewById(R.id.imgView);
//                Bitmap bitmap = BitmapFactory.decodeFile(filePath);
//                //imageView.setImageBitmap(bitmap);
//                // Convert it to byte
//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                // Compress image to lower quality scale 1 - 100
//                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//                byte[] image = stream.toByteArray();
//
//                // Create the ParseFile
//                ParseFile file = new ParseFile("androidbegin.png", image);
//                // Upload the image into Parse Cloud
//                file.saveInBackground();
//
//                // Create a New Class called "ImageUpload" in Parse
//                ParseObject imgupload = new ParseObject("ImageUpload");
//
//                // Create a column named "ImageName" and set the string
//                imgupload.put("ImageName", "AndroidBegin Logo");
//
//                // Create a column named "ImageFile" and insert the image
//                imgupload.put("ImageFile", file);
//
//                // Create the class and the columns
//                imgupload.saveInBackground();
//
//            }
//        });
//        return rootView;
//    }
//        private String selectedImagePath = "";
//        private ImageView imgUser;
//        public String imgPath;
//        final private int PICK_IMAGE = 1;
//        final private int CAPTURE_IMAGE = 2;
//        Button button;
//        @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//            View rootView = inflater.inflate(R.layout.experiment_items, container, false);
//            button = (Button) rootView.findViewById(R.id.experimentbtn);
//
//
//    public Uri setImageUri() {
//        // Store image in dcim
//        File file = new File(Environment.getExternalStorageDirectory() + "/DCIM/", "image" + new Date().getTime() + ".png");
//        Uri imgUri = Uri.fromFile(file);
//        this.imgPath = file.getAbsolutePath();
//        return imgUri;
//    }
//
//
//    public String getImagePath() {
//        return imgPath;
//    }
//
//    button.setOnClickListener(new View.OnClickListener()
//
//    {
////        @Override
//        public void onClick (View v){
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent, ""), PICK_IMAGE);
//    }
//    }
//
//    );
//
//    btnCapture.setOnClickListener(new View.OnClickListener()
//
//    {
////        @Override
//        public void onClick (View v){
//        final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, setImageUri());
//        startActivityForResult(intent, CAPTURE_IMAGE);
//    }
//    }
//
//    );
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode != Activity.RESULT_CANCELED) {
//            if (requestCode == PICK_IMAGE) {
//                selectedImagePath = getAbsolutePath(data.getData());
//                imgUser.setImageBitmap(decodeFile(selectedImagePath));
//            } else if (requestCode == CAPTURE_IMAGE) {
//                selectedImagePath = getImagePath();
//                imgUser.setImageBitmap(decodeFile(selectedImagePath));
//            } else {
//                super.onActivityResult(requestCode, resultCode, data);
//            }
//        }
//
//    }
//
//
//    public Bitmap decodeFile(String path) {
//        try {
//            // Decode image size
//            BitmapFactory.Options o = new BitmapFactory.Options();
//            o.inJustDecodeBounds = true;
//            BitmapFactory.decodeFile(path, o);
//            // The new size we want to scale to
//            final int REQUIRED_SIZE = 70;
//
//            // Find the correct scale value. It should be the power of 2.
//            int scale = 1;
//            while (o.outWidth / scale / 2 >= REQUIRED_SIZE && o.outHeight / scale / 2 >= REQUIRED_SIZE)
//                scale *= 2;
//
//            // Decode with inSampleSize
//            BitmapFactory.Options o2 = new BitmapFactory.Options();
//            o2.inSampleSize = scale;
//            return BitmapFactory.decodeFile(path, o2);
//        } catch (Throwable e) {
//            e.printStackTrace();
//        }
//        return null;
//
//    }
//
//    public String getAbsolutePath(Uri uri) {
//        String[] projection = {MediaStore.MediaColumns.DATA};
//        @SuppressWarnings("deprecation")
//        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
//        if (cursor != null) {
//            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
//            cursor.moveToFirst();
//            return cursor.getString(column_index);
//        } else
//            return null;
//    }
//}
Button button;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) getS(Context.LAYOUT_INFLATER_SERVICE);
        View theInflatedView = inflater.inflate(R.layout.experiment_items);
        setContentView(R.layout.experiment_list_item);
        btnClick();
    }
    public void btnClick() {
        button = (Button) findViewById(R.id.experimentbtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent browserIntent =
                        new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.howtosolvenow.com"));
                startActivity(browserIntent);
            }
        });
    }
}
