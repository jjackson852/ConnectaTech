package com.notify.app.mobile.ui;

import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ListView;

import com.github.kevinsawicki.wishlist.SingleTypeAdapter;
import com.notify.app.mobile.BootstrapServiceProvider;
import com.notify.app.mobile.Injector;
import com.notify.app.mobile.R;
import com.notify.app.mobile.authenticator.LogoutService;
import com.notify.app.mobile.core.Request;
import com.notify.app.mobile.core.Request;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import static com.notify.app.mobile.core.Constants.Extra.REQUEST_ITEM;

public class RequestListFragment extends ItemListFragment<Request> {

    @Inject protected BootstrapServiceProvider serviceProvider;
    @Inject protected LogoutService logoutService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Injector.inject(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setEmptyText(R.string.no_requests);
    }

    @Override
    protected void configureList(Activity activity, ListView listView) {
        super.configureList(activity, listView);

        listView.setFastScrollEnabled(true);
        listView.setDividerHeight(0);

        getListAdapter()
                .addHeader(activity.getLayoutInflater()
                        .inflate(R.layout.request_list_label, null));
    }

    @Override
    protected LogoutService getLogoutService() {
        return logoutService;
    }

    @Override
    public void onDestroyView() {
        setListAdapter(null);

        super.onDestroyView();
    }

    @Override
    public Loader<List<Request>> onCreateLoader(int id, Bundle args) {
        final List<Request> initialItems = items;
        return new ThrowableLoader<List<Request>>(getActivity(), items) {

            @Override
            public List<Request> loadData() throws Exception {
                try {
                    if (getActivity() != null) {
                        return serviceProvider.getService(getActivity()).getRequest();
                    } else {
                        return Collections.emptyList();
                    }

                } catch (OperationCanceledException e) {
                    Activity activity = getActivity();
                    if (activity != null)
                        activity.finish();
                    return initialItems;
                }
            }
        };
    }

    @Override
    protected SingleTypeAdapter<Request> createAdapter(List<Request> items) {
        return new RequestListAdapter(getActivity().getLayoutInflater(), items);
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        Request request = ((Request) l.getItemAtPosition(position));

        startActivity(new Intent(getActivity(), RequestActivity.class).putExtra(REQUEST_ITEM, request));
    }

    @Override
    protected int getErrorMessage(Exception exception) {
        return R.string.error_loading_news;
    }
}
