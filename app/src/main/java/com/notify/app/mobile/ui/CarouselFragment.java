package com.notify.app.mobile.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.notify.app.mobile.R;
import com.parse.ParseUser;
import com.viewpagerindicator.TitlePageIndicator;

import butterknife.InjectView;
import butterknife.Views;

/**
 * Fragment which houses the View pager.
 */
public class CarouselFragment extends Fragment {

    @InjectView(R.id.tpi_header)
    protected TitlePageIndicator indicator;

    @InjectView(R.id.vp_pages)
    protected ViewPager pager;

    public CarouselFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_carousel, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Views.inject(this, getView());

        Boolean isProvider = getArguments().getBoolean("isProvider");

        BootstrapPagerAdapter_Cust custLanding = new BootstrapPagerAdapter_Cust(getResources(), getChildFragmentManager());
        BootstrapPagerAdapter_Prov provLanding = new BootstrapPagerAdapter_Prov(getResources(), getChildFragmentManager());

        if (isProvider == true) {
            pager.setAdapter(provLanding);
        }
        else{
            pager.setAdapter(custLanding);
        }

        indicator.setViewPager(pager);
        if(ParseUser.getCurrentUser().getUsername().equals("demo@connectatech.org")){
            pager.setCurrentItem(0);
        }else{
            pager.setCurrentItem(1);
        }


    }
}