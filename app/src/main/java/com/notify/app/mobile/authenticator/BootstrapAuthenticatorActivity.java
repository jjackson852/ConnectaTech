package com.notify.app.mobile.authenticator;

import static android.R.layout.simple_dropdown_item_1line;
import static android.accounts.AccountManager.KEY_ACCOUNT_NAME;
import static android.accounts.AccountManager.KEY_ACCOUNT_TYPE;
import static android.accounts.AccountManager.KEY_AUTHTOKEN;
import static android.accounts.AccountManager.KEY_BOOLEAN_RESULT;
import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;
import static android.view.KeyEvent.ACTION_DOWN;
import static android.view.KeyEvent.KEYCODE_ENTER;
import static android.view.inputmethod.EditorInfo.IME_ACTION_DONE;
import android.accounts.Account;
import android.accounts.AccountManager;
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
import android.widget.Toast;

import com.notify.app.mobile.Injector;
import com.notify.app.mobile.R;
import com.notify.app.mobile.R.id;
import com.notify.app.mobile.R.layout;
import com.notify.app.mobile.R.string;
import com.notify.app.mobile.core.BootstrapService;
import com.notify.app.mobile.core.Constants;
import com.notify.app.mobile.core.User;
import com.notify.app.mobile.events.UnAuthorizedErrorEvent;
import com.notify.app.mobile.ui.MainActivity;
import com.notify.app.mobile.ui.TextWatcherAdapter;
import com.notify.app.mobile.util.Ln;
import com.notify.app.mobile.util.SafeAsyncTask;
import com.github.kevinsawicki.wishlist.Toaster;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.PushService;
import com.parse.SaveCallback;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.Views;
import retrofit.RetrofitError;

/**
 * Activity to authenticate the user against an API (example API on Parse.com)
 */
public class BootstrapAuthenticatorActivity extends ActionBarAccountAuthenticatorActivity {

    /**
     * PARAM_CONFIRM_CREDENTIALS
     */
    public static final String PARAM_CONFIRM_CREDENTIALS = "confirmCredentials";

    /**
     * PARAM_PASSWORD
     */
    public static final String PARAM_PASSWORD = "password";

    /**
     * PARAM_USERNAME
     */
    public static final String PARAM_USERNAME = "username";

    /**
     * PARAM_AUTHTOKEN_TYPE
     */
    public static final String PARAM_AUTHTOKEN_TYPE = "authtokenType";
    public static ParseUser user2 = null;

    private AccountManager accountManager;
    //private ParseUser emailUser;

    private Boolean datastoreIsEnabled = false;
    private Boolean isProvider = false;

    @Inject BootstrapService bootstrapService;
    @Inject Bus bus;

    @InjectView(id.et_login_email_or_username) protected AutoCompleteTextView emailOrUsernameText;
    @InjectView(id.et_password) protected EditText passwordText;


    @InjectView(id.b_signin) protected Button signInButton;


    private final TextWatcher watcher = validationTextWatcher();

    private SafeAsyncTask<Boolean> authenticationTask;
    private String authToken;
    private String authTokenType;

    /**
     * If set we are just checking that the user knows their credentials; this
     * doesn't cause the user's password to be changed on the device.
     */
    private Boolean confirmCredentials = false;

    public static String emailOrUsername;

    private String password;


    /**
     * In this instance the token is simply the sessionId returned from Parse.com. This could be a
     * oauth token or some other type of timed token that expires/etc. We're just using the parse.com
     * sessionId to prove the example of how to utilize a token.
     */
    private String token;

    /**
     * Was the original caller asking for an entirely new account?
     */
    protected boolean requestNewAccount = false;

    /**
     * Button listener for the Register button.
     */
    private View.OnClickListener regCustButtonListener = new View.OnClickListener() {
        public void onClick(View v) {

            isProvider = false;
            navigateToRegister();

        }
    };

    private View.OnClickListener regProvButtonListener = new View.OnClickListener() {
        public void onClick(View v) {

            isProvider = true;
            navigateToRegister();

        }
    };

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        Injector.inject(this);

        accountManager = AccountManager.get(this);



        final Intent intent = getIntent();
        emailOrUsername = intent.getStringExtra(PARAM_USERNAME);
        authTokenType = intent.getStringExtra(PARAM_AUTHTOKEN_TYPE);
        confirmCredentials = intent.getBooleanExtra(PARAM_CONFIRM_CREDENTIALS, false);

        requestNewAccount = emailOrUsername == null;

        setContentView(layout.login_activity);

       // if (datastoreIsEnabled == false) {


          //  datastoreIsEnabled = true;
       // }

        /**
         * Attaches the Register button listener to the xml button
         */
        Button regNewCustButton = (Button)findViewById(id.b_regAsNewCust);
        Button regNewProvButton = (Button)findViewById(id.b_regAsNewProv);
        regNewCustButton.setOnClickListener(regCustButtonListener);
        regNewProvButton.setOnClickListener(regProvButtonListener);


        Views.inject(this);

        emailOrUsernameText.setAdapter(new ArrayAdapter<String>(this,
                simple_dropdown_item_1line, userEmailAccounts()));

        passwordText.setOnKeyListener(new OnKeyListener() {

            public boolean onKey(final View v, final int keyCode, final KeyEvent event) {
                if (event != null && ACTION_DOWN == event.getAction()
                        && keyCode == KEYCODE_ENTER && signInButton.isEnabled()) {
                    handleLogin(signInButton);
                    return true;
                }
                return false;
            }
        });

        passwordText.setOnEditorActionListener(new OnEditorActionListener() {

            public boolean onEditorAction(final TextView v, final int actionId,
                                          final KeyEvent event) {
                if (actionId == IME_ACTION_DONE && signInButton.isEnabled()) {
                    handleLogin(signInButton);
                    return true;
                }
                return false;
            }
        });

        emailOrUsernameText.addTextChangedListener(watcher);
        passwordText.addTextChangedListener(watcher);

    }

    private List<String> userEmailAccounts() {
        final Account[] accounts = accountManager.getAccountsByType("com.google");
        final List<String> emailAddresses = new ArrayList<String>(accounts.length);
        for (final Account account : accounts) {
            emailAddresses.add(account.name);
        }
        return emailAddresses;
    }

    private TextWatcher validationTextWatcher() {
        return new TextWatcherAdapter() {
            public void afterTextChanged(final Editable gitDirEditText) {
                updateUIWithValidation();
            }

        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        bus.register(this);
        updateUIWithValidation();
    }

    @Override
    protected void onPause() {
        super.onPause();
        bus.unregister(this);
    }

    private void updateUIWithValidation() {
        final boolean populated = populated(emailOrUsernameText) && populated(passwordText);
        signInButton.setEnabled(populated);
    }

    private boolean populated(final EditText editText) {
        return editText.length() > 0;
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage(getText(string.message_signing_in));
        dialog.setIndeterminate(true);
        dialog.setCancelable(true);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(final DialogInterface dialog) {
                if (authenticationTask != null) {
                    authenticationTask.cancel(true);
                }
            }
        });
        return dialog;
    }

    @Subscribe
    public void onUnAuthorizedErrorEvent(UnAuthorizedErrorEvent unAuthorizedErrorEvent) {
        // Could not authorize for some reason.
        Toaster.showLong(BootstrapAuthenticatorActivity.this, R.string.message_bad_credentials);
    }

    /**
     * Handles onClick event on the Submit button. Sends username/password to
     * the server for authentication.
     * <p/>
     * Specified by android:onClick="handleLogin" in the layout xml
     *
     * @param view
     */
    public void handleLogin(final View view) {
        if (authenticationTask != null) {
            return;
        }

        if (requestNewAccount) {
            emailOrUsername = emailOrUsernameText.getText().toString().toLowerCase();

            /**
             * Checks to see if the account name entered is an email address or username.
             */
            if (emailOrUsername.contains("@")){
                ParseQuery<ParseUser> userQuery = ParseUser.getQuery();
                userQuery.whereEqualTo("email", emailOrUsername);

                try {
                    List<ParseUser> results = userQuery.find();
                    emailOrUsername = results.get(0).getUsername();
                } catch (com.parse.ParseException e) {
                    e.printStackTrace();
                }

            }

        }



        password = passwordText.getText().toString();
        showProgress();

        authenticationTask = new SafeAsyncTask<Boolean>() {
            public Boolean call() throws Exception {

                final String query = String.format("%s=%s&%s=%s",
                        PARAM_USERNAME, emailOrUsername, PARAM_PASSWORD, password);

                User loginResponse = bootstrapService.authenticate(emailOrUsername, password);
                token = loginResponse.getSessionToken();

                return true;
            }

            @Override
            protected void onException(final Exception e) throws RuntimeException {
                // Retrofit Errors are handled inside of the {
                if(!(e instanceof RetrofitError)) {
                    final Throwable cause = e.getCause() != null ? e.getCause() : e;
                    if(cause != null) {
                        Toaster.showLong(BootstrapAuthenticatorActivity.this, cause.getMessage());
                    }
                }
            }

            @Override
            public void onSuccess(final Boolean authSuccess) {
                onAuthenticationResult(authSuccess);
            }

            @Override
            protected void onFinally() throws RuntimeException {
                hideProgress();
                authenticationTask = null;
            }
        };
        authenticationTask.execute();
    }

    public void handleLoginAsGuest(final View view) {
        if (authenticationTask != null) {
            return;
        }

//        if (requestNewAccount) {
//            emailOrUsername = emailOrUsernameText.getText().toString().toLowerCase();
//        }

        emailOrUsername = Constants.Auth.GUEST_USERNAME.toLowerCase();

//        password = passwordText.getText().toString();

        password = Constants.Auth.GUEST_PASSWORD;

        showProgress();

        authenticationTask = new SafeAsyncTask<Boolean>() {
            public Boolean call() throws Exception {

                final String query = String.format("%s=%s&%s=%s",
                        PARAM_USERNAME, emailOrUsername, PARAM_PASSWORD, password);

                User loginResponse = bootstrapService.authenticate(emailOrUsername, password);
                token = loginResponse.getSessionToken();

                return true;
            }

            @Override
            protected void onException(final Exception e) throws RuntimeException {
                // Retrofit Errors are handled inside of the {
                if(!(e instanceof RetrofitError)) {
                    final Throwable cause = e.getCause() != null ? e.getCause() : e;
                    if(cause != null) {
                        Toaster.showLong(BootstrapAuthenticatorActivity.this, cause.getMessage());
                    }
                }
            }

            @Override
            public void onSuccess(final Boolean authSuccess) {
                onAuthenticationResult(authSuccess);
            }

            @Override
            protected void onFinally() throws RuntimeException {
                hideProgress();
                authenticationTask = null;
            }
        };
        authenticationTask.execute();
    }

    public void handleLoginAsProvider(final View view) {
        if (authenticationTask != null) {
            return;
        }

//        if (requestNewAccount) {
//            emailOrUsername = emailOrUsernameText.getText().toString().toLowerCase();
//        }

        emailOrUsername = Constants.Auth.PROVIDER_USERNAME.toLowerCase();

//        password = passwordText.getText().toString();

        password = Constants.Auth.PROVIDER_PASSWORD;

        showProgress();

        authenticationTask = new SafeAsyncTask<Boolean>() {
            public Boolean call() throws Exception {

                final String query = String.format("%s=%s&%s=%s",
                        PARAM_USERNAME, emailOrUsername, PARAM_PASSWORD, password);

                User loginResponse = bootstrapService.authenticate(emailOrUsername, password);
                token = loginResponse.getSessionToken();

                return true;
            }

            @Override
            protected void onException(final Exception e) throws RuntimeException {
                // Retrofit Errors are handled inside of the {
                if(!(e instanceof RetrofitError)) {
                    final Throwable cause = e.getCause() != null ? e.getCause() : e;
                    if(cause != null) {
                        Toaster.showLong(BootstrapAuthenticatorActivity.this, cause.getMessage());
                    }
                }
            }

            @Override
            public void onSuccess(final Boolean authSuccess) {
                onAuthenticationResult(authSuccess);
            }

            @Override
            protected void onFinally() throws RuntimeException {
                hideProgress();
                authenticationTask = null;
            }
        };
        authenticationTask.execute();
    }
    /**
     * Called when response is received from the server for confirm credentials
     * request. See onAuthenticationResult(). Sets the
     * AccountAuthenticatorResult which is sent back to the caller.
     *
     * @param result
     */
    protected void finishConfirmCredentials(final boolean result) {
        final Account account = new Account(emailOrUsername, Constants.Auth.BOOTSTRAP_ACCOUNT_TYPE);
        accountManager.setPassword(account, password);

        final Intent intent = new Intent();
        intent.putExtra(KEY_BOOLEAN_RESULT, result);
        setAccountAuthenticatorResult(intent.getExtras());
        setResult(RESULT_OK, intent);

        final Intent homeIntent = new Intent(this, MainActivity.class);
        finish();

        homeIntent.addFlags(FLAG_ACTIVITY_CLEAR_TOP | FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(homeIntent);


    }

    /**
     * Called when response is received from the server for authentication
     * request. See onAuthenticationResult(). Sets the
     * AccountAuthenticatorResult which is sent back to the caller. Also sets
     * the authToken in AccountManager for this account.
     */

    protected void finishLogin() {
        final Account account = new Account(emailOrUsername, Constants.Auth.BOOTSTRAP_ACCOUNT_TYPE);

        if (requestNewAccount) {
            accountManager.addAccountExplicitly(account, password, null);
        } else {
            accountManager.setPassword(account, password);
        }

        authToken = token;

        final Intent intent = new Intent();
        intent.putExtra(KEY_ACCOUNT_NAME, emailOrUsername);
        intent.putExtra(KEY_ACCOUNT_TYPE, Constants.Auth.BOOTSTRAP_ACCOUNT_TYPE);

        if (authTokenType != null
                && authTokenType.equals(Constants.Auth.AUTHTOKEN_TYPE)) {
            intent.putExtra(KEY_AUTHTOKEN, authToken);
        }

        setAccountAuthenticatorResult(intent.getExtras());
        setResult(RESULT_OK, intent);

        final Intent homeIntent = new Intent(this, MainActivity.class);//

        ParseUser.logInInBackground(emailOrUsername, password,
                new LogInCallback() {
                    public void done(ParseUser user, ParseException e) {
                        if (user != null) {
                            // If user exist and authenticated, send user to Welcome.class

                            Toast.makeText(getApplicationContext(),
                                    "Successfully Logged in",
                                    Toast.LENGTH_LONG).show();
                                    user2 = user;

                                    finish();

//                            if (ParseUser.getCurrentUser().getBoolean("isProvider")) {
//                                ParsePush push = new ParsePush();
//                                push.subscribeInBackground("Provider");
////                                PushService.setDefaultPushCallback(BootstrapAuthenticatorActivity.this, MainActivity.class);
//                                push.setChannel("Provider");
//                                push.setMessage("You have 0 new Requests");
//                                push.sendInBackground();
//                            }
//                            else {
//
//                                ParsePush push = new ParsePush();
//                                push.subscribeInBackground("Customer");
////                                PushService.setDefaultPushCallback(BootstrapAuthenticatorActivity.this, MainActivity.class);
//                                push.setChannel("Customer");
//                                push.setMessage("You have 0 new Services");
//                                push.sendInBackground();
//                            }

                                    homeIntent.addFlags(FLAG_ACTIVITY_CLEAR_TOP | FLAG_ACTIVITY_SINGLE_TOP);
                                    startActivity(homeIntent);
                        } else {
                            Toast.makeText(
                                    getApplicationContext(),
                                    "No such user exist, please signup",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });

        //user = ParseUser.getCurrentUser();
        user2.pinInBackground();
//        //if (user == null){
        //final Intent homeIntent = new Intent(this, MainActivity.class);
       // homeIntent.addFlags(FLAG_ACTIVITY_CLEAR_TOP | FLAG_ACTIVITY_SINGLE_TOP);
         //   finish();

       // startActivity(homeIntent);
//        //}

    }

    /**
     * Hide progress dialog
     */
    @SuppressWarnings("deprecation")
    protected void hideProgress() {
        dismissDialog(0);
    }

    /**
     * Show progress dialog
     */
    @SuppressWarnings("deprecation")
    protected void showProgress() {
        showDialog(0);
    }

    /**
     * Called when the authentication process completes (see attemptLogin()).
     *
     * @param result
     */
    public void onAuthenticationResult(final boolean result) {
        if (result) {
            if (!confirmCredentials) {
                finishLogin();
            } else {
                finishConfirmCredentials(true);
            }
        } else {
            Ln.d("onAuthenticationResult: failed to authenticate");
            if (requestNewAccount) {
                Toaster.showLong(BootstrapAuthenticatorActivity.this,
                        string.message_auth_failed_new_account);
            } else {
                Toaster.showLong(BootstrapAuthenticatorActivity.this,
                        string.message_auth_failed);
            }
        }
    }

    private void navigateToRegister() {
        final Intent i = new Intent(this, RegisterActivity.class);
        i.putExtra("isProvider", isProvider);
        startActivity(i);
    }

}
