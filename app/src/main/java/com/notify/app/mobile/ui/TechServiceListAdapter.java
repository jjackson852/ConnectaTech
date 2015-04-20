package com.notify.app.mobile.ui;

import android.view.LayoutInflater;

import com.notify.app.mobile.R;
import com.notify.app.mobile.bootstrapOrigin.ui.AlternatingColorListAdapter;
import com.notify.app.mobile.core.TechService;

import java.util.List;

public class TechServiceListAdapter extends AlternatingColorListAdapter<TechService> {
    /**
     * @param inflater
     * @param items
     * @param selectable
     */
    public TechServiceListAdapter(final LayoutInflater inflater, final List<TechService> items,
                                  final boolean selectable) {
        super(R.layout.techservice_list_item, inflater, items, selectable);
    }

    /**
     * @param inflater
     * @param items
     */
    public TechServiceListAdapter(final LayoutInflater inflater, final List<TechService> items) {
        super(R.layout.techservice_list_item, inflater, items);
    }

    @Override
    protected int[] getChildViewIds() {
        return new int[]{R.id.tv_title, R.id.tv_summary,
                R.id.tv_zip, R.id.tv_category, R.id.tv_price};
    }

    @Override
    protected void update(final int position, final TechService item) {
        super.update(position, item);

        setText(0, item.getTitle());
        setText(1, item.getDescription());
        setText(2, item.getZipCode());
        setText(3, item.getCategory());
        setText(4, item.getBasePrice());
        //setNumber(R.id.tv_date, item.getCreatedAt());
    }
}
