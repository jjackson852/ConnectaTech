package com.notify.app.mobile.ui;

import android.view.LayoutInflater;

import com.notify.app.mobile.R;
import com.notify.app.mobile.bootstrapOrigin.ui.AlternatingColorListAdapter;
import com.notify.app.mobile.core.Request;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.List;

public class RequestListAdapter extends AlternatingColorListAdapter<Request> {
    /**
     * @param inflater
     * @param items
     * @param selectable
     */
    public RequestListAdapter(final LayoutInflater inflater, final List<Request> items,
                              final boolean selectable) {
        super(R.layout.request_list_item, inflater, items, selectable);
    }

    /**
     * @param inflater
     * @param items
     */
    public RequestListAdapter(final LayoutInflater inflater, final List<Request> items) {
        super(R.layout.request_list_item, inflater, items);
    }

    @Override
    protected int[] getChildViewIds() {
        return new int[]{R.id.tv_title, R.id.tv_summary,
                R.id.tv_date};
    }

    @Override
    protected void update(final int position, final Request item) {
        super.update(position, item);

        Format formatter = new SimpleDateFormat("MM-dd-yyyy");
        setText(0, item.getServiceTitle());
        setText(2, item.getAddlInfo());
        setText(1, formatter.format(item.getCreatedAt()).toString());
   //     setText(3, item.getCustEmail());
    }
}
