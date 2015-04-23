package com.notify.app.mobile.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.notify.app.mobile.R.id;
import com.notify.app.mobile.R.layout;
import com.notify.app.mobile.bootstrapOrigin.ui.BootstrapActivity;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

/**
 * Activity to create a new account on the parse database.
 */
public class RequestServiceActivity extends BootstrapActivity {

    EditText addlInfoText;
    ParseObject serviceRequested;
    ParseUser serviceProvider;
    String serviceRequestedId;

    private View.OnClickListener requestSubListener = new View.OnClickListener() {
        public void onClick(View v) {


            ParsePush push = new ParsePush();
//               ParsePush.subscribeInBackground(serviceRequested.getParseUser("createdBy").getObjectId());
////                                PushService.setDefaultPushCallback(BootstrapAuthenticatorActivity.this, MainActivity.class);
////            push.setChannel(String.valueOf(serviceRequested.getParseUser("createdBy")));
            push.setChannel(serviceRequested.getParseUser("createdBy").getObjectId());
            push.setMessage("You have a new Request");
            push.sendInBackground();
            Toast.makeText(
                    getApplicationContext(),
                    serviceRequested.getParseUser("createdBy").getObjectId(),
                    Toast.LENGTH_LONG).show();
//        }
            //Request the service.
            submitRequest();


            //Go back to login activity.
            finish();
//        if (ParseUser.getCurrentUser().getBoolean("isProvider")) {
//            ParsePush push = new ParsePush();
//////            ParsePush.subscribeInBackground(ParseUser.getCurrentUser().getObjectId());
//////                                PushService.setDefaultPushCallback(BootstrapAuthenticatorActivity.this, MainActivity.class);
//            push.setChannel(serviceRequested.getObjectId());
//                                push.setMessage(ParseUser.getCurrentUser().getUsername());
//            push.sendInBackground();
//        }
//        else {


        }
    };

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        setContentView(layout.request_service_activity);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        /**
         * Attaches the SubmitUser button listener to the xml button.
         //         */
        Button filterServicesButton = (Button) findViewById(id.b_submit_request);
        filterServicesButton.setOnClickListener(requestSubListener);

        serviceRequestedId = getIntent().getExtras().getString("serviceRequestedId");

        ParseQuery<ParseObject> query = ParseQuery.getQuery("TechService");
        query.whereEqualTo("objectId", serviceRequestedId);

        try {
            List<ParseObject> serviceResults = query.find();
            serviceRequested = serviceResults.get(0);
        } catch (com.parse.ParseException e) {
            e.printStackTrace();
        }

    }

    /**
     * Handles onClick event on the Submit button.
     */
    public void submitRequest() {

        addlInfoText = (EditText) findViewById(id.et_addl_request_info);

        ParseObject newRequest = new ParseObject("Request");

        newRequest.put("addlInfo", String.valueOf(addlInfoText.getText()));
        newRequest.put("serviceTitle", serviceRequested.getString("title"));
        newRequest.put("requestedService", serviceRequested);
        newRequest.put("submittedBy", ParseUser.getCurrentUser());
        newRequest.put("provider", serviceRequested.getParseUser("createdBy"));
        newRequest.put("custPhoneNumber", ParseUser.getCurrentUser().getString("phoneNumber"));
        newRequest.put("custEmail", ParseUser.getCurrentUser().getEmail());
        //newRequest.put("category", serviceRequested.getCategory()); For Later Use

        newRequest.saveInBackground();

    }

}
