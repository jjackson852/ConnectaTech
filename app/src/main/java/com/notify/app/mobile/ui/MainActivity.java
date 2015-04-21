
package com.notify.app.mobile.ui;

import android.accounts.OperationCanceledException;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.notify.app.mobile.BootstrapServiceProvider;
import com.notify.app.mobile.R;
import com.notify.app.mobile.bootstrapOrigin.core.BootstrapService;
import com.notify.app.mobile.bootstrapOrigin.ui.BootstrapFragmentActivity;
import com.notify.app.mobile.bootstrapOrigin.ui.BootstrapTimerActivity;
import com.notify.app.mobile.bootstrapOrigin.ui.NavigationDrawerFragment;
import com.notify.app.mobile.bootstrapOrigin.ui.TestActivity;
import com.notify.app.mobile.events.NavItemSelectedEvent;
import com.notify.app.mobile.util.Ln;
import com.notify.app.mobile.util.SafeAsyncTask;
import com.notify.app.mobile.util.UIUtils;
import com.parse.ParseACL;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.squareup.otto.Subscribe;

import java.io.ByteArrayOutputStream;

import javax.inject.Inject;

import butterknife.Views;

/**
 * Initial activity for the application.
 * <p/>
 * If you need to remove the authentication from the application please see
 * {@link com.notify.app.mobile.bootstrapOrigin.authenticator.ApiKeyProvider#getAuthKey(android.app.Activity)}
 */
public class MainActivity extends BootstrapFragmentActivity {

    public static String objID;
    @Inject
    protected BootstrapServiceProvider serviceProvider;
    private boolean userHasAuthenticated = false;
    private boolean isProvider = true;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private CharSequence drawerTitle;
    private CharSequence title;
    private NavigationDrawerFragment navigationDrawerFragment;
    private static int RESULT_LOAD_IMG = 1;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        ParseAnalytics.trackAppOpened(getIntent());

        super.onCreate(savedInstanceState);

        ParseObject.registerSubclass(Photo.class);

        ParseACL defaultACL = new ParseACL();
/*
* If you would like all objects to be private by default, remove this
* line
*/
        defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);

        if (isTablet()) {
            setContentView(R.layout.main_activity_tablet);
        } else {
            setContentView(R.layout.main_activity);
        }

        // View injection with Butterknife
        Views.inject(this);

        // Set up navigation drawer
        title = drawerTitle = getTitle();

//        ParsePush push = new ParsePush();
//        push.setChannel("Provider");
//        push.setMessage("You have 0 new Requests");
//        push.sendInBackground();
        checkAuth();
        if (!isTablet()) {
            drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawerToggle = new ActionBarDrawerToggle(
                    this,                    /* Host activity */
                    drawerLayout,           /* DrawerLayout object */
                    R.drawable.ic_drawer,    /* nav drawer icon to replace 'Up' caret */
                    R.string.navigation_drawer_open,    /* "open drawer" description */
                    R.string.navigation_drawer_close) { /* "close drawer" description */

                /**
                 * Called when a drawer has settled in a completely closed state.
                 */
                public void onDrawerClosed(View view) {
                    getSupportActionBar().setTitle(title);
                    supportInvalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                }

                /**
                 * Called when a drawer has settled in a completely open state.
                 */
                public void onDrawerOpened(View drawerView) {
                    getSupportActionBar().setTitle(drawerTitle);
                    supportInvalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                }
            };

            // Set the drawer toggle as the DrawerListener
            drawerLayout.setDrawerListener(drawerToggle);

            navigationDrawerFragment = (NavigationDrawerFragment)
                    getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

            // Set up the drawer.
            navigationDrawerFragment.setUp(
                    R.id.navigation_drawer,
                    (DrawerLayout) findViewById(R.id.drawer_layout));
        }

        drawerLayout.closeDrawer(Gravity.LEFT);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


    }

    private boolean isTablet() {
        return UIUtils.isTablet(this);
    }

    @Override
    protected void onPostCreate(final Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        if (!isTablet()) {
            // Sync the toggle state after onRestoreInstanceState has occurred.
            drawerToggle.syncState();
        }
    }

    @Override
    public void onConfigurationChanged(final Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (!isTablet()) {
            drawerToggle.onConfigurationChanged(newConfig);
        }
    }

    private void initScreen() {
        if (userHasAuthenticated) {
            // try {
            ParseUser currentUser = ParseUser.getCurrentUser();
            isProvider = currentUser.getBoolean("isProvider");
            //ParseUser.unpinAllInBackground();
            // } catch (com.parse.ParseException e) {
            //      e.printStackTrace();
            //}

            //String isProviderstr = BootstrapAuthenticatorActivity.user.fetch().;

            Bundle carouselArgs = new Bundle();

            carouselArgs.putBoolean("isProvider", isProvider);

            CarouselFragment carousel = new CarouselFragment();
            carousel.setArguments(carouselArgs);

            Ln.d("Foo");
            final FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, carousel)
                    .commit();
        }

    }

    private void checkAuth() {
        new SafeAsyncTask<Boolean>() {

            @Override
            public Boolean call() throws Exception {
                final BootstrapService svc = serviceProvider.getService(MainActivity.this);
                return svc != null;
            }

            @Override
            protected void onException(final Exception e) throws RuntimeException {
                super.onException(e);
                if (e instanceof OperationCanceledException) {
                    // User cancelled the authentication process (back button, etc).
                    // Since auth could not take place, lets finish this activity.
                    finish();
                }
            }

            @Override
            protected void onSuccess(final Boolean hasAuthenticated) throws Exception {
                super.onSuccess(hasAuthenticated);
                userHasAuthenticated = true;
                initScreen();
            }
        }.execute();
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        if (!isTablet() && drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
            case android.R.id.home:
                //menuDrawer.toggleMenu();
                return true;
//            case R.id.timer:
//                //navigateToTimer();
//                startActivity(new Intent(this, BootstrapTimerActivity.class));
//                return true;
            case R.id.test:
                startActivity(new Intent(this, TestActivity.class));
                //navigateToTest();
                return true;
            case R.id.rating:
                startActivity(new Intent(this, OldRatingActivity.class));
                //navigateToTest();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void navigateToTimer() {
        // final Intent i = new Intent(this, BootstrapTimerActivity.class);
        //startActivity(i);
        //startActivity(new Intent(this, BootstrapTimerActivity.class));
    }

    private void navigateToTest() {
        //     final Intent i = new Intent(this, Test_Activity.class);
        //     startActivity(i);
        //     startActivity(new Intent(this, Test_Activity.class));
    }


    @Subscribe
    public void onNavigationItemSelected(NavItemSelectedEvent event) {

        Ln.d("Selected: %1$s", event.getItemPosition());

        switch (event.getItemPosition()) {
            case 0:
                // Home
                // do nothing as we're already on the home screen.
                break;
            case 1:
                // Timer
                startActivity(new Intent(this, BootstrapTimerActivity.class));
                //navigateToTimer();
                break;
            case 2:
                // Test
                // navigateToTest();
                startActivity(new Intent(this, TestActivity.class));
                break;
            case 3:
                // Test
                // navigateToTest();
                startActivity(new Intent(this, OldRatingActivity.class));
                break;
        }
    }

    public void navigateToAddService(final View view) {
        final Intent i = new Intent(this, AddServiceActivity.class);
        startActivity(i);
    }


    public void navigateToFilterServices(final View view) {
        final Intent i = new Intent(this, FilterServicesActivity.class);
        startActivity(i);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                ///ImageView imgView = (ImageView) findViewById(R.id.imgView);
                // Set the Image in ImageView after decoding the String
//                imgView.setImageBitmap(BitmapFactory
//                        .decodeFile(imgDecodableString));

                //-------------begin gallery
                Bitmap newPhoto = BitmapFactory.decodeFile(imgDecodableString);

                Bitmap imageScaled = Bitmap.createScaledBitmap(newPhoto, 200, 200
                        * newPhoto.getHeight() / newPhoto.getWidth(), false);
// Override Android default landscape orientation and save portrait
                Matrix matrix = new Matrix();
                matrix.postRotate(0);
                Bitmap rotatedScaledImage = Bitmap.createBitmap(imageScaled, 0,
                        0, imageScaled.getWidth(), imageScaled.getHeight(),
                        matrix, true);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                rotatedScaledImage.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                byte[] scaledData = bos.toByteArray();
// Save the scaled image to Parse
                ParseFile newPhotoFile = new ParseFile("profile_photoV2.jpg", scaledData);
                newPhotoFile.saveInBackground(new SaveCallback() {
                    public void done(ParseException e) {
                        if (e != null) {
                            Toast.makeText(MainActivity.this,
                                    "Error saving: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });

                ParseUser cameraCurrentUser = ParseUser.getCurrentUser();
                cameraCurrentUser.put("ImageFile", newPhotoFile);
                cameraCurrentUser.saveInBackground();

                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);


            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG)
                    .show();
        }

    }


}

