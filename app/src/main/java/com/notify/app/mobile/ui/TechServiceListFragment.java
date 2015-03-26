package com.notify.app.mobile.ui;

import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.github.kevinsawicki.wishlist.SingleTypeAdapter;
import com.notify.app.mobile.BootstrapServiceProvider;
import com.notify.app.mobile.Injector;
import com.notify.app.mobile.R;
import com.notify.app.mobile.authenticator.LogoutService;
import com.notify.app.mobile.authenticator.RegisterActivity;
import com.notify.app.mobile.core.Example;
import com.notify.app.mobile.core.TechService;
import com.parse.ParseUser;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;

import static com.notify.app.mobile.core.Constants.Extra.TECHSERVICE_ITEM;

public class TechServiceListFragment extends ItemListFragment<TechService> {

    @Inject protected BootstrapServiceProvider serviceProvider;
    @Inject protected LogoutService logoutService;


    private View.OnClickListener noOfferedServicesListener = new View.OnClickListener() {
        public void onClick(View v) {

           navigateToAddService();

        }
    };

    private View.OnClickListener noServicesBrowseableListener = new View.OnClickListener() {
        public void onClick(View v) {

            navigateFilterServices();

        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Injector.inject(this);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (ParseUser.getCurrentUser().getBoolean("isProvider") == true) {
            setEmptyText(R.string.no_tech_services);
            setEmptyTextIsClickable(true);
            setEmptyTextOnClickMethod(noOfferedServicesListener);
        }
        else{
            setEmptyText(R.string.no_tech_services_browse);
            setEmptyTextIsClickable(true);
            setEmptyTextOnClickMethod(noServicesBrowseableListener);
        }

    }

    @Override
    protected void configureList(Activity activity, ListView listView) {
        super.configureList(activity, listView);

        listView.setFastScrollEnabled(true);
        listView.setDividerHeight(0);


        if (ParseUser.getCurrentUser().getBoolean("isProvider") == true) {
            getListAdapter()
                    .addHeader(activity.getLayoutInflater()
                            .inflate(R.layout.techservice_list_label, null));
        }
        else{
            getListAdapter()
                    .addHeader(activity.getLayoutInflater()
                            .inflate(R.layout.search_techservice_list_label, null));
        }

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

//    @Override
//    public void onStop() {
//        setListAdapter(null);
//
//        super.onStop();
//    }

    @Override
    public Loader<List<TechService>> onCreateLoader(int id, Bundle args) {
        final List<TechService> initialItems = items;
        return new ThrowableLoader<List<TechService>>(getActivity(), items) {

            @Override
            public List<TechService> loadData() throws Exception {
                try {
                    if (getActivity() != null) {
                        return serviceProvider.getService(getActivity()).getTechService();
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
    protected SingleTypeAdapter<TechService> createAdapter(List<TechService> items) {
        return new TechServiceListAdapter(getActivity().getLayoutInflater(), items);
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        TechService techService = ((TechService) l.getItemAtPosition(position));

        startActivity(new Intent(getActivity(), TechServiceActivity.class).putExtra(TECHSERVICE_ITEM, techService));
    }

    @Override
    protected int getErrorMessage(Exception exception) {
        return R.string.error_loading_news;
    }

    @Override
    public void onResume(){
        super.onResume();
        refresh();
    }

    public void navigateToAddService() {
        final Intent i = new Intent(getActivity(), AddServiceActivity.class);
        startActivity(i);
    }

    public void navigateFilterServices() {
        final Intent i = new Intent(getActivity(), FilterServicesActivity.class);
        startActivity(i);
    }

}
