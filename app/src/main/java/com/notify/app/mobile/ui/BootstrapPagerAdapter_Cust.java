

package com.notify.app.mobile.ui;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.notify.app.mobile.R;
import com.notify.app.mobile.authenticator.CustomerRegisterFragment;
import com.notify.app.mobile.authenticator.TimerTestFragment;

//import dagger.Module;

/**
 * Pager adapter
 */
public class BootstrapPagerAdapter_Cust extends FragmentPagerAdapter {

    private final Resources resources;

    /**
     * Create pager adapter
     *
     * @param resources
     * @param fragmentManager
     */
    public BootstrapPagerAdapter_Cust(final Resources resources, final FragmentManager fragmentManager) {
        super(fragmentManager);
        this.resources = resources;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Fragment getItem(final int position) {
        final Fragment result;
        switch (position) {
            case 0:
                result = new NewsListFragment();
                break;
            case 1:
                result = new UserListFragment();
                break;
            case 2:
                result = new CheckInsListFragment();
                break;
            case 3:
                result = new ExampleListFragment();
                break;
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
                return resources.getString(R.string.page_news);
            case 1:
                return resources.getString(R.string.page_users);
            case 2:
                return resources.getString(R.string.page_checkins);
            case 3:
                return resources.getString(R.string.title_example);
            default:
                return null;
        }
    }
}
