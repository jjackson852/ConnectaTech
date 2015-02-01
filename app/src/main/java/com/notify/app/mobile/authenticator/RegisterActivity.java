package com.notify.app.mobile.authenticator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.notify.app.mobile.R.id;
import com.notify.app.mobile.R.layout;
import com.parse.ParseAnalytics;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

/**
 * Activity to create a new account on the parse database.
 */
public class RegisterActivity extends ActionBarAccountAuthenticatorActivity {


    private View.OnClickListener submitUserListener = new View.OnClickListener() {
        public void onClick(View v) {

            //Create the user.
            handleRegister();

            //Go back to login activity.
            finish();

        }
    };

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        setContentView(layout.register_activity_new);

        /**
         * Attaches the SubmitUser button listener to the xml button.
         */
        Button submitUserButton = (Button)findViewById(id.b_registerSub);
        submitUserButton.setOnClickListener(submitUserListener);

    }

    /**
     * Handles onClick event on the Submit button. Sends username/password to
     * the server for account creation.
     */
    public void handleRegister() {
        ParseAnalytics.trackAppOpened(getIntent());

        EditText regUserText;
        EditText regPasswordText;
        EditText regFNameText;
        EditText regLNameText;

        regUserText = (EditText) findViewById(id.et_reg_email);
        regPasswordText = (EditText) findViewById(id.et_reg_password);
        regFNameText = (EditText) findViewById(id.et_reg_first_name);
        regLNameText = (EditText) findViewById(id.et_reg_last_name);

        ParseUser user = new ParseUser();
        user.setUsername(String.valueOf(regUserText.getText()));
        user.setPassword(String.valueOf(regPasswordText.getText()));

        user.put("firstName", String.valueOf(regFNameText.getText()));
        user.put("lastName", String.valueOf(regLNameText.getText()));

        user.signUpInBackground(new SignUpCallback() {

            public void done(com.parse.ParseException e) {
                if (e == null) {
                    // Hooray! Let them use the app now.
                } else {
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                }
            }

        });
    }

}
