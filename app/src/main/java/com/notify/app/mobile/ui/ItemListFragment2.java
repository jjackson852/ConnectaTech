
package com.notify.app.mobile.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.github.kevinsawicki.wishlist.Toaster;
import com.notify.app.mobile.R;
import com.notify.app.mobile.R.id;
import com.notify.app.mobile.R.layout;
import com.notify.app.mobile.authenticator.LogoutService;
import com.notify.app.mobile.bootstrapOrigin.ui.ThrowableLoader;

import java.util.Collections;
import java.util.List;


/**
 * Base fragment for displaying a list of items that loads with a progress bar
 * visible
 *
 * @param <E>
 */
public abstract class ItemListFragment2<E> extends Fragment
        implements LoaderCallbacks<List<E>> {

    private static final String FORCE_REFRESH = "forceRefresh";
    protected List<E> items = Collections.emptyList();
    /**
     * @param args bundle passed to the loader by the LoaderManager
     * @return true if the bundle indicates a requested forced refresh of the
     * items
     */
    protected static boolean isForceRefresh(final Bundle args) {
        return args != null && args.getBoolean(FORCE_REFRESH, false);
    }


    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        return inflater.inflate(layout.item_list, null);
    }

    /**
     * Detach from list view.
     */
    @Override
    public void onDestroyView() {


        super.onDestroyView();
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(final Menu optionsMenu, final MenuInflater inflater) {
        inflater.inflate(R.menu.bootstrap, optionsMenu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (!isUsable()) {
            return false;
        }
        switch (item.getItemId()) {
            case id.refresh:
                forceRefresh();
                return true;
            case id.logout:
                logout();
                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected abstract LogoutService getLogoutService();

    private void logout() {

        getLogoutService().logout(new Runnable() {
            @Override
            public void run() {
                // Calling a refresh will force the service to look for a logged in user
                // and when it finds none the user will be requested to log in again.
                forceRefresh();
            }

        });


    }

    /**
     * Force a refresh of the items displayed ignoring any cached items
     */
    protected void forceRefresh() {
        final Bundle bundle = new Bundle();
        bundle.putBoolean(FORCE_REFRESH, true);
        refresh(bundle);
    }

    /**
     * Refresh the fragment's list
     */
    public void refresh() {
        refresh(null);
    }

    private void refresh(final Bundle args) {
        if (!isUsable()) {
            return;
        }

        getActionBarActivity().setSupportProgressBarIndeterminateVisibility(true);

        getLoaderManager().restartLoader(0, args, this);
    }

    private ActionBarActivity getActionBarActivity() {
        return ((ActionBarActivity) getActivity());
    }

    /**
     * Get error message to display for exception
     *
     * @param exception
     * @return string resource id
     */
    protected abstract int getErrorMessage(final Exception exception);

    protected Exception getException(final Loader<List<E>> loader) {
        if (loader instanceof ThrowableLoader) {
            return ((ThrowableLoader<List<E>>) loader).clearException();
        } else {
            return null;
        }
    }

    public ItemListFragment<E> setListShown(final boolean shown) {
        return setListShown(true);
    }


    protected void showList() {
        setListShown(true);
    }

    protected void showError(final int message) {
        Toaster.showLong(getActivity(), message);
    }

    public void onLoadFinished(final Loader<List<E>> loader, final List<E> items) {

        getActionBarActivity().setSupportProgressBarIndeterminateVisibility(false);

        final Exception exception = getException(loader);
        if (exception != null) {
            showError(getErrorMessage(exception));
            showList();
            return;
        }
    }

    /**
     * Is this fragment still part of an activity and usable from the UI-thread?
     *
     * @return true if usable on the UI-thread, false otherwise
     */
    protected boolean isUsable() {
        return getActivity() != null;
    }


    @Override
    public void onLoaderReset(final Loader<List<E>> loader) {
        // Intentionally left blank
    }
}
