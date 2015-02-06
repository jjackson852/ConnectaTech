package com.notify.app.mobile.authenticator;

import android.os.Bundle;

import com.notify.app.mobile.R;
import com.notify.app.mobile.R.layout;

/**
 * Activity to create a new account on the parse database.
 */
public class RegisterActivity extends ActionBarAccountAuthenticatorActivity {


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        setContentView(layout.register_pages);

        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (bundle != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            CustomerRegisterFragment firstFragment = new CustomerRegisterFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            firstFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();
        }

    }

}
