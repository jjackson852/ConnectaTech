

package com.notify.app.mobile.ui;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.notify.app.mobile.R;
import com.notify.app.mobile.bootstrapOrigin.ui.CheckInsListFragment;
import com.notify.app.mobile.bootstrapOrigin.ui.NewsListFragment;
import com.notify.app.mobile.bootstrapOrigin.ui.UserListFragment;
import com.parse.ParseUser;

//import dagger.Module;

/**
 * Pager adapter
 */
public class BootstrapPagerAdapter_Cust extends FragmentPagerAdapter {

    private final Resources resources;
    private Boolean isGuest = false;

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
        if(ParseUser.getCurrentUser().getUsername().equals("demo@connectatech.org")){
            isGuest = true;
            return 2;
        }
        else{
            return 3;
        }
    }

    @Override
    public Fragment getItem(final int position) {
        final Fragment result;
        if(isGuest == true){
            switch (position) {
                case 0:
                    result = new UserListFragment();
                    break;
                case 1:
                    result = new TechServiceListFragment();
                    break;
                default:
                    result = null;
                    break;
            }
        }
        else{
            switch (position) {
                case 0:
                    result = new RequestListFragment();
                    break;
                case 1:
                    result = new UserListFragment();
                    break;
                case 2:
                    result = new TechServiceListFragment();
                    break;
                default:
                    result = null;
                    break;
            }
        }

        if (result != null) {
            result.setArguments(new Bundle()); //TODO do we need this?
        }
        return result;
    }

    @Override
    public CharSequence getPageTitle(final int position) {
        if(isGuest == true){
            switch (position) {
                case 0:
                    return resources.getString(R.string.page_users);
                case 1:
                    return resources.getString(R.string.title_browse_techservices);
                default:
                    return null;
            }
        }else{
            switch (position) {
                case 0:
                    return resources.getString(R.string.active_requests);
                case 1:
                    return resources.getString(R.string.page_users);
                case 2:
                    return resources.getString(R.string.title_browse_techservices);
                default:
                    return null;
            }
        }

    }
}
