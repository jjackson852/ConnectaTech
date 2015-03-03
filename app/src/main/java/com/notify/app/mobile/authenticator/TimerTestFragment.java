package com.notify.app.mobile.authenticator;

import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.kevinsawicki.wishlist.SingleTypeAdapter;
import com.notify.app.mobile.BootstrapServiceProvider;
import com.notify.app.mobile.Injector;
import com.notify.app.mobile.R;
import com.notify.app.mobile.core.Example;
import com.notify.app.mobile.core.News;
import com.notify.app.mobile.ui.ItemListFragment;
import com.notify.app.mobile.ui.ItemListFragment2;
import com.notify.app.mobile.ui.ThrowableLoader;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class TimerTestFragment extends ItemListFragment2 {

    @Inject protected BootstrapServiceProvider serviceProvider;
    @Inject protected LogoutService logoutService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Injector.inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.bootstrap_timer, container, false);
    }

    @Override
    protected LogoutService getLogoutService() {
        return logoutService;
    }

    @Override
    protected int getErrorMessage(Exception exception) {
        return 0;
    }


    @Override
    public Loader onCreateLoader(int id, Bundle args) {
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
        }};
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {


    }
}