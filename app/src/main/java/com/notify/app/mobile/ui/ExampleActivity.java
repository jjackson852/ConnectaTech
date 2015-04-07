package com.notify.app.mobile.ui;

import android.os.Bundle;
import android.widget.TextView;

import com.notify.app.mobile.R;
import com.notify.app.mobile.bootstrapOrigin.ui.BootstrapActivity;
import com.notify.app.mobile.core.Example;

import butterknife.InjectView;

import static com.notify.app.mobile.bootstrapOrigin.core.Constants.Extra.EXAMPLE_ITEM;

public class ExampleActivity extends BootstrapActivity {

    @InjectView(R.id.tv_title)
    protected TextView title;
    @InjectView(R.id.tv_content)
    protected TextView content;
    private Example exampleItem;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.example);

        if (getIntent() != null && getIntent().getExtras() != null) {
            exampleItem = (Example) getIntent().getExtras().getSerializable(EXAMPLE_ITEM);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        setTitle(exampleItem.getTitle());
        setTitle(R.string.example_news);

        title.setText(exampleItem.getTitle());
        content.setText(exampleItem.getContent());

    }

}
