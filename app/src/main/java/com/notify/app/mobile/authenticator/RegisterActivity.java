package com.notify.app.mobile.authenticator;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.github.kevinsawicki.wishlist.Toaster;
import com.notify.app.mobile.Injector;
import com.notify.app.mobile.R.id;
import com.notify.app.mobile.R.layout;
import com.notify.app.mobile.R.string;
import com.notify.app.mobile.authenticator.ActionBarAccountAuthenticatorActivity;
import com.notify.app.mobile.core.BootstrapService;
import com.notify.app.mobile.core.Constants;
import com.notify.app.mobile.core.User;
import com.notify.app.mobile.events.UnAuthorizedErrorEvent;
import com.notify.app.mobile.util.Ln;
import com.notify.app.mobile.util.SafeAsyncTask;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.Views;
import retrofit.RetrofitError;

import static android.R.layout.simple_dropdown_item_1line;
import static android.accounts.AccountManager.KEY_ACCOUNT_NAME;
import static android.accounts.AccountManager.KEY_ACCOUNT_TYPE;
import static android.accounts.AccountManager.KEY_AUTHTOKEN;
import static android.accounts.AccountManager.KEY_BOOLEAN_RESULT;
import static android.view.KeyEvent.ACTION_DOWN;
import static android.view.KeyEvent.KEYCODE_ENTER;
import static android.view.inputmethod.EditorInfo.IME_ACTION_DONE;

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
