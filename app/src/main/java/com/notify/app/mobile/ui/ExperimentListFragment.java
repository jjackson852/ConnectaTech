package com.notify.app.mobile.ui;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.notify.app.mobile.R;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.io.ByteArrayOutputStream;

public class ExperimentListFragment extends Fragment {

    Button button;
    Bitmap thumbnail;
    private static int RESULT_LOAD_IMAGE = 1;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.experiment_items, container, false);
        button = (Button) rootView.findViewById(R.id.experimentbtn);
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                Intent in = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(in, RESULT_LOAD_IMAGE);
            }
        });
        return rootView;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == getActivity().RESULT_OK && null != data) {

            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            thumbnail = (BitmapFactory.decodeFile(picturePath));
            ByteArrayOutputStream stream = new ByteArrayOutputStream();

            // Compress image to lower quality scale 1 - 100
            thumbnail.compress(Bitmap.CompressFormat.PNG, 100, stream);

//            byte[] image = stream.toByteArray();
            ParseFile file = new ParseFile("androidbegin.png", stream.toByteArray());

            file.saveInBackground();

            ParseObject imgupload = new ParseObject("ImageUpload");

            imgupload.put("ImageName", "AndroidBegin Logo");

            imgupload.put("ImageFile", file);

            imgupload.saveInBackground();
        }
    }
}

