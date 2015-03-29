package com.notify.app.mobile.ui;

import android.os.Bundle;
import android.widget.TextView;

import com.notify.app.mobile.R;
import com.notify.app.mobile.core.Request;

import butterknife.InjectView;

import static com.notify.app.mobile.core.Constants.Extra.REQUEST_ITEM;

public class RequestActivity extends BootstrapActivity {

    private Request requestItem;

    @InjectView(R.id.tv_title) protected TextView title;
    @InjectView(R.id.tv_content) protected TextView content;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.request);

        if (getIntent() != null && getIntent().getExtras() != null) {
            requestItem = (Request) getIntent().getExtras().getSerializable(REQUEST_ITEM);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        setTitle(requestItem.getServiceTitle());


        title.setText(requestItem.getServiceTitle());
        content.setText(requestItem.getAddlInfo());

    }

}
