package com.notify.app.mobile.ui;

import android.view.LayoutInflater;
import android.widget.RatingBar;

import com.notify.app.mobile.R;
import com.notify.app.mobile.bootstrapOrigin.ui.AlternatingColorListAdapter;
import com.notify.app.mobile.core.Example;
import com.notify.app.mobile.core.Rating;

import java.util.List;

public class RatingListAdapter extends AlternatingColorListAdapter<Rating> {
    /**
     * @param inflater
     * @param items
     * @param selectable
     */
    public RatingListAdapter(final LayoutInflater inflater, final List<Rating> items,
                             final boolean selectable) {
        super(R.layout.rating_list_item, inflater, items, selectable);
    }

    /**
     * @param inflater
     * @param items
     */
    public RatingListAdapter(final LayoutInflater inflater, final List<Rating> items) {
        super(R.layout.rating_list_item, inflater, items);
    }

    @Override
    protected int[] getChildViewIds() {
        return new int[]{R.id.tv_title, R.id.tv_summary,
                R.id.tv_list_rating};
    }

    @Override
    protected void update(final int position, final Rating item) {
        super.update(position, item);

        setText(0, item.getTitle());
        setText(1, item.getDescription());
        setText(2, item.getRating() + " out of 5");
    }
}
