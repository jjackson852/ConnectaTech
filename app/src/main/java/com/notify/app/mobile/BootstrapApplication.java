

package com.notify.app.mobile;

import android.app.Application;
import android.app.Instrumentation;
import android.content.Context;

import com.notify.app.mobile.core.Constants;
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

    }

    private Object getRootModule() {
        return new RootModule();
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
}
