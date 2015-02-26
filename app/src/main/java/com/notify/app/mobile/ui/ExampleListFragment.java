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
import com.notify.app.mobile.core.Example;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import static com.notify.app.mobile.core.Constants.Extra.NEWS_ITEM;

public class ExampleListFragment extends ItemListFragment<Example> {

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

        setEmptyText(R.string.no_news);
    }

    @Override
    protected void configureList(Activity activity, ListView listView) {
        super.configureList(activity, listView);

        listView.setFastScrollEnabled(true);
        listView.setDividerHeight(0);

        getListAdapter()
                .addHeader(activity.getLayoutInflater()
                        .inflate(R.layout.example_list_label, null));
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
    public Loader<List<Example>> onCreateLoader(int id, Bundle args) {
        final List<Example> initialItems = items;
        return new ThrowableLoader<List<Example>>(getActivity(), items) {

            @Override
            public List<Example> loadData() throws Exception {
                try {
                    if (getActivity() != null) {
                        return serviceProvider.getService(getActivity()).getExample();
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
    protected SingleTypeAdapter<Example> createAdapter(List<Example> items) {
        return new ExampleListAdapter(getActivity().getLayoutInflater(), items);
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        Example news = ((Example) l.getItemAtPosition(position));

        startActivity(new Intent(getActivity(), NewsActivity.class).putExtra(NEWS_ITEM, news));
    }

    @Override
    protected int getErrorMessage(Exception exception) {
        return R.string.error_loading_news;
    }
}
