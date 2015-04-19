package com.notify.app.mobile.bootstrapOrigin.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.notify.app.mobile.R;
import com.notify.app.mobile.bootstrapOrigin.core.User;
import com.notify.app.mobile.ui.RateUserActivity;
import com.notify.app.mobile.ui.TechServiceActivity;
import com.parse.FunctionCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;

import static com.notify.app.mobile.bootstrapOrigin.core.Constants.Extra.TECHSERVICE_ITEM;
import static com.notify.app.mobile.bootstrapOrigin.core.Constants.Extra.USER;

public class UserActivity extends BootstrapActivity {

    @InjectView(R.id.iv_avatar)
    protected ImageView avatar;
    @InjectView(R.id.tv_name)
    protected TextView name;

    @InjectView(R.id.currentJoinDate)
    protected TextView currentJoinDate;

    private User user;
    private ParseUser parseProvider;
    private ParseObject newRating;
    private Float avgRating;
    private String ratingCount;
    private Button rateActivity;
    private RatingBar ratingBarSubmittable;
    private Intent intent;
    private TextView currentProviderRating;
    private Bundle extras;
    RelativeLayout rl;
    AlertDialog.Builder alert;
    //private TextView currentJoinDate;

    private View.OnClickListener addRatingListener = new View.OnClickListener() {
        public void onClick(View v) {

            //Submit the rating.
          //  submitRating();

            //Go back to login activity.
        //    finish();

        }
    };

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.user_view);

        if (getIntent() != null && getIntent().getExtras() != null) {
            user = (User) getIntent().getExtras().getSerializable(USER);
        }

//        currentJoinDate.setText(user.getCreateAt());



        Button rateActivity = ((Button) findViewById(R.id.rateProviderActivity));

        rateActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alert.show();

            }
        });


        ratingBarSubmittable = (RatingBar) findViewById(R.id.ratingBar_alert_view);
        alert = new AlertDialog.Builder(this);

        alert.setTitle("Review Provider");
        LayoutInflater li = LayoutInflater.from(UserActivity.this);
        rl = (RelativeLayout)li.inflate(R.layout.rate_provider_activity, null);
        alert.setView(rl);

        final Intent userIntent = new Intent(this, UserActivity.class);
        alert.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //What ever you want to do with the value
                float rating = ((RatingBar)rl.findViewById(R.id.ratingBar_alert_view)).getRating();
                newRating = new ParseObject("Rating");
                newRating.put("provider", parseProvider);
                newRating.put("submittedBy", ParseUser.getCurrentUser());
                newRating.put("rating", rating);
                newRating.put("providerID", user.getObjectId());
                newRating.saveInBackground();
                //submitRating();

                userIntent.putExtra(USER, user);
                finish();
                startActivity(userIntent);
                ((ViewGroup)rl.getParent()).removeView(rl);
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
                ((ViewGroup)rl.getParent()).removeView(rl);
            }
        });



//        /**
//         * Attaches the Submit New Service button listener to the xml button.
//         */
//        Button submitRatingButton = (Button) findViewById(R.id.b_submitRating_cust);
//        submitRatingButton.setOnClickListener(addRatingListener);
//
//
        ParseQuery<ParseUser> userQuery = ParseUser.getQuery();
        userQuery.whereEqualTo("objectId", user.getObjectId());

        try {
            List<ParseUser> results = userQuery.find();
            parseProvider = results.get(0);
        } catch (com.parse.ParseException e) {
            e.printStackTrace();
        }

        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("providerID", user.getObjectId());
        ParseCloud.callFunctionInBackground("averageRating", params, new FunctionCallback<Map<String,Object>>() {
            public void done(Map<String, Object>  ratings, ParseException e) {
                if (e == null) {
                    // ratings is 4.5 or such


                    try{
                        avgRating = Float.valueOf(ratings.get("avgRating").toString());
                        ratingCount = ratings.get("ratingCount").toString();
                        currentProviderRating  = (TextView) findViewById(R.id.currentProviderRating);
                        currentProviderRating.setText("Based on " + ratingCount + " Review(s).");
                    }catch (NumberFormatException ex){
                        avgRating = 0.0f;
                    }


                    Toast.makeText(UserActivity.this, avgRating.toString(), Toast.LENGTH_LONG).show();
                    RatingBar ratingBarViewOnly = (RatingBar) findViewById(R.id.ratingBar_cust_view);
                    ratingBarViewOnly.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_UP) {
                                // TODO perform your action here
                                alert.show();
                            }
                            return true;
                        }});

                    ratingBarViewOnly.setIsIndicator(true);
                    ratingBarViewOnly.setRating(avgRating);
                    ratingBarViewOnly.invalidate();
                }
                else{

                }
            }
        });



        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ParseFile photo = (ParseFile) parseProvider.get("ImageFile");

        try{
            Picasso.with(this).load(photo.getUrl())
                    .placeholder(R.drawable.gravatar_icon)
                    .into(avatar);
        }catch (NullPointerException ex){
            Picasso.with(this).load(user.getAvatarUrl())
                    .placeholder(R.drawable.gravatar_icon)
                    .into(avatar);
        }

        name.setText(String.format("%s %s", user.getFirstName(), user.getLastName()));
        TextView currentJoinDate = ((TextView) findViewById(R.id.currentJoinDate));
        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
        currentJoinDate.setText(formatter.format( user.getCreateAt()));



    }


    public void submitRating() {
        ParseAnalytics.trackAppOpened(getIntent());

        newRating = new ParseObject("Rating");
        newRating.put("provider", parseProvider);
        newRating.put("submittedBy", ParseUser.getCurrentUser());

        newRating.put("providerID", user.getObjectId());
        newRating.saveInBackground();

    }


}
