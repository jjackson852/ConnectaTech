package com.notify.app.mobile.ui;

import android.os.Bundle;
import android.widget.TextView;

import com.notify.app.mobile.R;
import com.notify.app.mobile.core.Example;

import butterknife.InjectView;

import static com.notify.app.mobile.core.Constants.Extra.NEWS_ITEM;

public class ExampleActivity extends BootstrapActivity {

    private Example newsItem;

    @InjectView(R.id.tv_title) protected TextView title;
    @InjectView(R.id.tv_content) protected TextView content;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.example);

        if (getIntent() != null && getIntent().getExtras() != null) {
            newsItem = (Example) getIntent().getExtras().getSerializable(NEWS_ITEM);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        setTitle(newsItem.getTitle());
        setTitle(R.string.example_news);

        title.setText(newsItem.getTitle());
        content.setText(newsItem.getContent());

    }

}
