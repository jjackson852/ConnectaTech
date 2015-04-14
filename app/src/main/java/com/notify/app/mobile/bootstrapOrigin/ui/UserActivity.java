package com.notify.app.mobile.bootstrapOrigin.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.notify.app.mobile.R;
import com.notify.app.mobile.bootstrapOrigin.core.User;
import com.notify.app.mobile.ui.RateUserActivity;
import com.parse.FunctionCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;

import static com.notify.app.mobile.bootstrapOrigin.core.Constants.Extra.USER;

public class UserActivity extends BootstrapActivity {

    @InjectView(R.id.iv_avatar)
    protected ImageView avatar;
    @InjectView(R.id.tv_name)
    protected TextView name;

    private User user;
    private ParseUser parseProvider;
    private ParseObject newRating;
    private Float avgRating;
    private Button rateActivity;
    private Intent intent;
    private TextView currentProviderRating;
    private Bundle extras;

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
//        intent = new Intent(this, RateUserActivity.class);
//        intent.putExtra("providerID", user.getObjectId());
//        intent.putExtra("provider",parseProvider);

        intent = new Intent(this, RateUserActivity.class);
        intent.getExtras();
        extras = new Bundle();
        extras.putString("providerID",user.getObjectId());
        extras.putString("provider", String.valueOf(parseProvider));
        intent.putExtras(extras);

        Button rateActivity = ((Button) findViewById(R.id.rateProviderActivity));

        rateActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(intent);

            }
        });


//        /**
//         * Attaches the Submit New Service button listener to the xml button.
//         */
//        Button submitRatingButton = (Button) findViewById(R.id.b_submitRating_cust);
//        submitRatingButton.setOnClickListener(addRatingListener);
//
//        ratingBarSubmittable = (RatingBar) findViewById(R.id.ratingBar_cust_view);
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
                    }catch (NumberFormatException ex){
                        avgRating = 0.0f;
                    }


                    Toast.makeText(UserActivity.this, avgRating.toString(), Toast.LENGTH_LONG).show();
                    RatingBar ratingBarViewOnly = (RatingBar) findViewById(R.id.ratingBar_cust_view);

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

        Picasso.with(this).load(user.getAvatarUrl())
                .placeholder(R.drawable.gravatar_icon)
                .into(avatar);

        name.setText(String.format("%s %s", user.getFirstName(), user.getLastName()));




    }
//
//    /**
//     * Handles onClick event on the Submit button.
//     */
//    public void submitRating() {
//        ParseAnalytics.trackAppOpened(getIntent());
//
//        newRating = new ParseObject("Rating");
//        newRating.put("provider",parseProvider);
//        newRating.put("submittedBy", ParseUser.getCurrentUser());
//        newRating.put("rating", ratingBarSubmittable.getRating());
//        newRating.put("providerID", user.getObjectId());
//        newRating.saveInBackground();
//
//    }

}
