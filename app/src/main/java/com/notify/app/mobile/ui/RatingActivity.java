package com.notify.app.mobile.ui;

import android.os.Bundle;
import android.widget.TextView;

import com.notify.app.mobile.R;
import com.notify.app.mobile.bootstrapOrigin.ui.BootstrapActivity;
import com.notify.app.mobile.core.Example;
import com.notify.app.mobile.core.Rating;

import butterknife.InjectView;

import static com.notify.app.mobile.bootstrapOrigin.core.Constants.Extra.RATING_ITEM;

public class RatingActivity extends BootstrapActivity {

    @InjectView(R.id.tv_title)
    protected TextView title;
    @InjectView(R.id.tv_content)
    protected TextView content;
    @InjectView(R.id.tv_date)
    protected TextView date;
    private Rating ratingItem;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.rating);

        if (getIntent() != null && getIntent().getExtras() != null) {
            ratingItem = (Rating) getIntent().getExtras().getSerializable(RATING_ITEM);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        setTitle(ratingItem.getTitle());

        title.setText(ratingItem.getTitle());
       // date.setText(ratingItem.getCreatedAt().toString());
        content.setText(ratingItem.getDescription());

    }

}
