package com.notify.app.mobile.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.notify.app.mobile.R;
import com.notify.app.mobile.bootstrapOrigin.ui.BootstrapActivity;
import com.notify.app.mobile.core.TechService;
import com.parse.ParseUser;

import butterknife.InjectView;

import static com.notify.app.mobile.bootstrapOrigin.core.Constants.Extra.TECHSERVICE_ITEM;

public class TechServiceActivity extends BootstrapActivity {

    @InjectView(R.id.tv_title)
    protected TextView title;
    @InjectView(R.id.tv_content)
    protected TextView content;
    private TechService techServiceItem;
    private Boolean isProvider;
    private View.OnClickListener requestServListener = new View.OnClickListener() {
        public void onClick(View v) {

            //Request the service.
            navigateToRequestSubmission();

            //Go back to login activity.
            finish();

        }
    };

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        isProvider = ParseUser.getCurrentUser().getBoolean("isProvider");

        if (isProvider == false) {
            setContentView(R.layout.techservice_cust);

            /**
             * Attaches the Submit Request button listener to the xml button.
             */
            Button submitRequestButton = (Button) findViewById(R.id.b_request_service);
            submitRequestButton.setOnClickListener(requestServListener);
        } else {
            setContentView(R.layout.techservice);
        }


        if (getIntent() != null && getIntent().getExtras() != null) {
            techServiceItem = (TechService) getIntent().getExtras().getSerializable(TECHSERVICE_ITEM);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        setTitle(techServiceItem.getTitle());
        ///setTitle(R.string.techservice_news);

        title.setText(techServiceItem.getTitle());
        content.setText(techServiceItem.getDescription());

    }

    protected void navigateToRequestSubmission() {

        final Intent i = new Intent(this, RequestServiceActivity.class);
        i.putExtra("serviceRequestedId", techServiceItem.getObjectId());
        startActivity(i);

    }

}