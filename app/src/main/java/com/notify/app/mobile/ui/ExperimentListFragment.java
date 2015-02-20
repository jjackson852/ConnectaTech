package com.notify.app.mobile.ui;

import android.support.v4.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.notify.app.mobile.R;
import com.parse.ParseFile;
import com.parse.ParseObject;
import java.io.ByteArrayOutputStream;

public class ExperimentListFragment extends Fragment {

    Button button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
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

            }
        });
}
