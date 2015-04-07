package com.notify.app.mobile.ui;

import android.view.LayoutInflater;

import com.notify.app.mobile.R;
import com.notify.app.mobile.bootstrapOrigin.ui.AlternatingColorListAdapter;
import com.notify.app.mobile.core.Example;

import java.util.List;

public class ExampleListAdapter extends AlternatingColorListAdapter<Example> {
    /**
     * @param inflater
     * @param items
     * @param selectable
     */
    public ExampleListAdapter(final LayoutInflater inflater, final List<Example> items,
                              final boolean selectable) {
        super(R.layout.example_list_item, inflater, items, selectable);
    }

    /**
     * @param inflater
     * @param items
     */
    public ExampleListAdapter(final LayoutInflater inflater, final List<Example> items) {
        super(R.layout.example_list_item, inflater, items);
    }

    @Override
    protected int[] getChildViewIds() {
        return new int[]{R.id.tv_title, R.id.tv_summary,
                R.id.tv_date};
    }

    @Override
    protected void update(final int position, final Example item) {
        super.update(position, item);

        setText(0, item.getTitle());
        setText(1, item.getContent());
        //setNumber(R.id.tv_date, item.getCreatedAt());
    }
}
