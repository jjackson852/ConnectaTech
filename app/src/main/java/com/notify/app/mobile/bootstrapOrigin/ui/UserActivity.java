package com.notify.app.mobile.bootstrapOrigin.ui;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.notify.app.mobile.R;
import com.notify.app.mobile.bootstrapOrigin.core.User;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.InjectView;

import static com.notify.app.mobile.bootstrapOrigin.core.Constants.Extra.USER;

public class UserActivity extends BootstrapActivity {

    @InjectView(R.id.iv_avatar)
    protected ImageView avatar;
    @InjectView(R.id.tv_name)
    protected TextView name;

    private User user;
    private String email;
    private String counter;
    private TextView text;
    private String ave;
    private ParseUser parseProvider;
    private ParseObject newRating;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.user_view);

        if (getIntent() != null && getIntent().getExtras() != null) {
            user = (User) getIntent().getExtras().getSerializable(USER);
        }

        ParseQuery<ParseUser> userQuery = ParseUser.getQuery();
        userQuery.whereEqualTo("objectId", user.getObjectId());

        try {
            List<ParseUser> results = userQuery.find();
            parseProvider = results.get(0);
        } catch (com.parse.ParseException e) {
            e.printStackTrace();
        }

//        try {
//
//            ParseQuery<ParseObject> count = ParseQuery.getQuery("Rating");
//            count.whereEqualTo("provider", user.getObjectId());
//            counter = String.valueOf(count);
//
//            ParseQuery<ParseObject> ave_rating = ParseQuery.getQuery("rate");
//            ave_rating.whereEqualTo("ave_rating", email);
//            ave = String.valueOf(ave_rating);
//
//            text = (TextView) findViewById(R.id.textView3);
//            text.setText(counter);
//        } catch(Exception e) {
//            text = (TextView) findViewById(R.id.textView3);
//            text.setText("N/a");
//        }


        newRating = new ParseObject("Rating");
        newRating.put("provider",parseProvider);
        newRating.saveInBackground();

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Picasso.with(this).load(user.getAvatarUrl())
                .placeholder(R.drawable.gravatar_icon)
                .into(avatar);

        name.setText(String.format("%s %s", user.getFirstName(), user.getLastName()));


    }


}
