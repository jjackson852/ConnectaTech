package com.notify.app.mobile.util;

import android.content.Context;
import android.content.Intent;

import com.notify.app.mobile.authenticator.RegisterActivity;
import com.notify.app.mobile.ui.MainActivity;
import com.parse.ParsePushBroadcastReceiver;

/**
 * Created by AppleJacks on 3/15/2015.
 */
public class Receiver extends ParsePushBroadcastReceiver {

    @Override
    public void onPushOpen(Context context, Intent intent) {
        Intent i = new Intent(context, RegisterActivity.class);
        i.putExtras(intent.getExtras());
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}