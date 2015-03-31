

package com.notify.app.mobile.ui;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.notify.app.mobile.R;

//import dagger.Module;

/**
 * Pager adapter
 */
public class BootstrapPagerAdapter_Prov extends FragmentPagerAdapter {

    private final Resources resources;

    /**
     * Create pager adapter
     *
     * @param resources
     * @param fragmentManager
     */
    public BootstrapPagerAdapter_Prov(final Resources resources, final FragmentManager fragmentManager) {
        super(fragmentManager);
        this.resources = resources;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Fragment getItem(final int position) {
        final Fragment result;
        switch (position) {
            case 0:
                result = new RequestListFragment();
                break;
            case 1:
                result = new ProviderProfileFragment();
                break;
            case 2:
                result = new TechServiceListFragment();
                break;
//            case 3:
//                result = new UserListFragment();
//                break;
//            case 4:
//                result = new CheckInsListFragment();
//                break;
            default:
                result = null;
                break;
        }
        if (result != null) {
            result.setArguments(new Bundle()); //TODO do we need this?
        }
        return result;
    }

    @Override
    public CharSequence getPageTitle(final int position) {
        switch (position) {
            case 0:
                return resources.getString(R.string.provider_requests);
            case 1:
                return resources.getString(R.string.title_activity_provider);
            case 2:
                return resources.getString(R.string.title_techservices);
//            case 3:
//                return resources.getString(R.string.page_users);
//            case 4:
//                return resources.getString(R.string.page_checkins);
            default:
                return null;
        }
    }
}
