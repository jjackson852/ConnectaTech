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
import com.notify.app.mobile.bootstrapOrigin.ui.ThrowableLoader;
import com.notify.app.mobile.core.Example;
import com.notify.app.mobile.core.Rating;
import com.parse.ParseUser;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import static com.notify.app.mobile.bootstrapOrigin.core.Constants.Extra.RATING_ITEM;

public class RatingListFragment extends ItemListFragment<Rating> {

    @Inject
    protected BootstrapServiceProvider serviceProvider;
    @Inject
    protected LogoutService logoutService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Injector.inject(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (ParseUser.getCurrentUser().getBoolean("isProvider")) {
            setEmptyText("You have no reviews.");
        }
        else{
            setEmptyText("This provider has no reviews.");
        }
    }

    @Override
    protected void configureList(Activity activity, ListView listView) {
        super.configureList(activity, listView);

        listView.setFastScrollEnabled(true);
        listView.setDividerHeight(0);

        getListAdapter()
                .addHeader(activity.getLayoutInflater()
                        .inflate(R.layout.rating_list_label, null));
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
    public Loader<List<Rating>> onCreateLoader(int id, Bundle args) {
        final List<Rating> initialItems = items;
        return new ThrowableLoader<List<Rating>>(getActivity(), items) {

            @Override
            public List<Rating> loadData() throws Exception {
                try {
                    if (getActivity() != null) {
                        return serviceProvider.getService(getActivity()).getRating();
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
    protected SingleTypeAdapter<Rating> createAdapter(List<Rating> items) {
        return new RatingListAdapter(getActivity().getLayoutInflater(), items);
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        Rating rating = ((Rating) l.getItemAtPosition(position));

        startActivity(new Intent(getActivity(), RatingActivity.class).putExtra(RATING_ITEM, rating));
    }

    @Override
    protected int getErrorMessage(Exception exception) {
        return R.string.error_loading_news;
    }
}
