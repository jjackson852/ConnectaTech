package com.notify.app.mobile.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.notify.app.mobile.Injector;
import com.notify.app.mobile.R;
import com.notify.app.mobile.bootstrapOrigin.ui.BootstrapActivity;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by theblackfu on 2/25/2015.
 */
public class RatingActivity extends BootstrapActivity {

    Button button;
    private RatingBar ratingBar;
    private TextView txtRatingValue;
    private Button btnSubmit;
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rating_actvity);

        Injector.inject(this);
        setTitle(R.string.rating_title);
        addListenerOnRatingBar();
        addListenerOnButton();

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Button for Parse Submit
        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {

                progressDialog = ProgressDialog.show(RatingActivity.this, "",
                        "Downloading Image...", true);

                // Locate the class table named "ImageUpload" in Parse.com
                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                        "ImageUpload");

                // Locate the objectId from the class
                query.getInBackground("KDKWQRTwRE",
                        new GetCallback<ParseObject>() {

                            public void done(ParseObject object,
                                             ParseException e) {
                                // TODO Auto-generated method stub

                                // Locate the column named "ImageName" and set
                                // the string
                                ParseFile fileObject = (ParseFile) object
                                        .get("ImageFile");
                                fileObject
                                        .getDataInBackground(new GetDataCallback() {

                                            public void done(byte[] data,
                                                             ParseException e) {
                                                if (e == null) {
                                                    Log.d("test",
                                                            "We've got data in data.");
                                                    // Decode the Byte[] into
                                                    // Bitmap
                                                    Bitmap bmp = BitmapFactory
                                                            .decodeByteArray(data, 0, data.length);
                                                    // Get the ImageView from
                                                    // main.xml
                                                    ImageView image = (ImageView) findViewById(R.id.image);
                                                    // Set the Bitmap into the
                                                    // ImageView
                                                    image.setImageBitmap(bmp);
                                                    // Close progress dialog
                                                    progressDialog.dismiss();

                                                } else {
                                                    Log.d("test",
                                                            "There was a problem downloading the data.");
                                                }
                                            }
                                        });
                            }
                        });
            }

        });
    }


    public void addListenerOnRatingBar() {

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        txtRatingValue = (TextView) findViewById(R.id.txtRatingValue);

        //if rating value is changed,
        //display the current rating value in the result (textview) automatically
        ratingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {

                txtRatingValue.setText(String.valueOf(rating));

            }
        });
    }

    public void addListenerOnButton() {

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        //if click on me, then display the current rating value.
        btnSubmit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Toast.makeText(RatingActivity.this,
                        String.valueOf(ratingBar.getRating()),
                        Toast.LENGTH_SHORT).show();

            }

        });

    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            // Source:
            // http://developer.android.com/training/implementing-navigation/ancestral.html
            // This is the home button in the top left corner of the screen.
            case android.R.id.home:
                final Intent upIntent = NavUtils.getParentActivityIntent(this);
                // If parent is not properly defined in AndroidManifest.xml upIntent will be null
                // TODO hanlde upIntent == null
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    // This activity is NOT part of this app's task, so create a new task
                    // when navigating up, with a synthesized back stack.
                    TaskStackBuilder.create(this)
                            // Add all of this activity's parents to the back stack
                            .addNextIntentWithParentStack(upIntent)
                                    // Navigate up to the closest parent
                            .startActivities();
                } else {
                    // This activity is part of this app's task, so simply
                    // navigate up to the logical parent activity.
                    NavUtils.navigateUpTo(this, upIntent);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
