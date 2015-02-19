package com.notify.app.mobile.ui;

import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ListView;

import com.notify.app.mobile.BootstrapServiceProvider;
import com.notify.app.mobile.Injector;
import com.notify.app.mobile.R;
import com.notify.app.mobile.authenticator.LogoutService;
import com.notify.app.mobile.core.Experiment;
import com.github.kevinsawicki.wishlist.SingleTypeAdapter;
import com.notify.app.mobile.core.User;


import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class ExperimentListFragment extends ItemListFragment<Experiment>{

    @Inject protected BootstrapServiceProvider serviceProvider;
    @Inject protected LogoutService logoutService;

    @Override
    protected SingleTypeAdapter<Experiment> createAdapter(final List<Experiment> items) {
        return new ExperimentListAdapter(getActivity().getLayoutInflater(), items);
    }

    @Override
    protected int getErrorMessage(final Exception exception) {
        return R.string.error_loading_experiment;
    }

    @Override
    protected LogoutService getLogoutService() {
        return logoutService;
    }

    @Override
    public Loader<List<Experiment>> onCreateLoader(final int id, final Bundle args) {
        final List<Experiment> initialItems = items;
        return new ThrowableLoader<List<Experiment>>(getActivity(), items) {

            @Override
            public List<Experiment> loadData() throws Exception {

                try {
                    List<Experiment> latest = null;

                    if (getActivity() != null) {
                        latest = serviceProvider.getService(getActivity()).getExperiment();
                    }

                    if (latest != null) {
                        return latest;
                    } else {
                        return Collections.emptyList();
                    }
                }catch (final OperationCanceledException e){
                    final Activity activity = getActivity();
                    if (activity != null)
                        activity.finish();
                }
                    return initialItems;
            }
        };
    }

    @Override
    protected void configureList(final Activity activity, final ListView listView) {
        super.configureList(activity, listView);

        listView.setFastScrollEnabled(true);
        listView.setDividerHeight(0);

        getListAdapter().addHeader(activity.getLayoutInflater()
                .inflate(R.layout.experiment_list_item_labels, null));
    }
}
