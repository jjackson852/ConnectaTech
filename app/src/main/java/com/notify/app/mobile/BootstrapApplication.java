

package com.notify.app.mobile;

import android.app.Application;
import android.app.Instrumentation;
import android.content.Context;

import com.notify.app.mobile.bootstrapOrigin.core.Constants;
import com.parse.Parse;

/**
 * connectatech application
 */
public class BootstrapApplication extends Application {

    private static BootstrapApplication instance;

    /**
     * Create main application
     */
    public BootstrapApplication() {
    }

    /**
     * Create main application
     *
     * @param context
     */
    public BootstrapApplication(final Context context) {
        this();
        attachBaseContext(context);
    }

    /**
     * Create main application
     *
     * @param instrumentation
     */
    public BootstrapApplication(final Instrumentation instrumentation) {
        this();
        attachBaseContext(instrumentation.getTargetContext());
    }

    public static BootstrapApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        // Perform injection
        Injector.init(getRootModule(), this);

        //Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        //Initialize the connection to the parse database.
        Parse.initialize(this, Constants.Http.PARSE_APP_ID, Constants.Http.PARSE_CLIENT_KEY);

//            ParsePush.subscribeInBackground("", new SaveCallback() {
//                @Override
//                public void done(ParseException e) {
//                    if (e == null) {
//                        Log.d("com.parse.push", "successfully subscribed to the broadcast channel.");
//                    } else {
//                        Log.e("com.parse.push", "failed to subscribe for push", e);
//                    }
//                }
//            });
//
////        // Associate the device with a user
////        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
////        installation.put("user", ParseUser.getCurrentUser());
////        installation.saveInBackground();
//
//            ParseInstallation.getCurrentInstallation().saveInBackground(new SaveCallback() {
//                @Override
//                public void done(ParseException e) {
//                    PushService.setDefaultPushCallback(BootstrapApplication.this, MainActivity.class);
//                }
//            });


    }

    private Object getRootModule() {
        return new RootModule();
    }
}
