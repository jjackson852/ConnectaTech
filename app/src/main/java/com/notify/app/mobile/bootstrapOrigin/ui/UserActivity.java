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

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.user_view);

        try {
            email = ParseUser.getCurrentUser().getEmail();

            ParseQuery<ParseObject> count = ParseQuery.getQuery("rate");
            count.whereEqualTo("count", email);
            counter = String.valueOf(count);

            ParseQuery<ParseObject> ave_rating = ParseQuery.getQuery("rate");
            ave_rating.whereEqualTo("ave_rating", email);
            ave = String.valueOf(ave_rating);

            text = (TextView) findViewById(R.id.textView3);
            text.setText(ave);
        } catch(Exception e) {
            text = (TextView) findViewById(R.id.textView3);
            text.setText("N/a");
        }

        if (getIntent() != null && getIntent().getExtras() != null) {
            user = (User) getIntent().getExtras().getSerializable(USER);
        }

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Picasso.with(this).load(user.getAvatarUrl())
                .placeholder(R.drawable.gravatar_icon)
                .into(avatar);

        name.setText(String.format("%s %s", user.getFirstName(), user.getLastName()));


    }


}
