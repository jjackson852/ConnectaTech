package com.notify.app.mobile.bootstrapOrigin.ui;

import android.os.Bundle;
import android.widget.TextView;

import com.notify.app.mobile.R;
import com.notify.app.mobile.bootstrapOrigin.core.News;

import butterknife.InjectView;

import static com.notify.app.mobile.bootstrapOrigin.core.Constants.Extra.NEWS_ITEM;

public class NewsActivity extends BootstrapActivity {

    @InjectView(R.id.tv_title)
    protected TextView title;
    @InjectView(R.id.tv_content)
    protected TextView content;
    private News newsItem;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.news);

        if (getIntent() != null && getIntent().getExtras() != null) {
            newsItem = (News) getIntent().getExtras().getSerializable(NEWS_ITEM);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        setTitle(newsItem.getTitle());
        // setTitle(R.string.trial_news);

        title.setText(newsItem.getTitle());
        content.setText(newsItem.getContent());

    }

}
