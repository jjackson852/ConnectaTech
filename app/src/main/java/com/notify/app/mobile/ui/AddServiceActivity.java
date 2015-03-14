package com.notify.app.mobile.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.notify.app.mobile.R.id;
import com.notify.app.mobile.R.layout;
import com.notify.app.mobile.authenticator.ActionBarAccountAuthenticatorActivity;
import com.parse.ParseAnalytics;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

/**
 * Activity to create a new account on the parse database.
 */
public class AddServiceActivity extends BootstrapActivity {


    private View.OnClickListener submitUserListener = new View.OnClickListener() {
        public void onClick(View v) {

            //Create the user.
            submitTechService();

            //Go back to login activity.
            finish();

        }
    };

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        setContentView(layout.add_tech_service);

        /**
         * Attaches the SubmitUser button listener to the xml button.
         */
        Button submitServiceButton = (Button)findViewById(id.b_submit_new_service);
        submitServiceButton.setOnClickListener(submitUserListener);

    }

    /**
     * Handles onClick event on the Submit button.
     */
    public void submitTechService() {
        ParseAnalytics.trackAppOpened(getIntent());

        EditText addServTitleText;
        EditText addServPriceText;
        EditText addServDescriptionText;

        addServTitleText = (EditText) findViewById(id.et_service_title);
        addServPriceText = (EditText) findViewById(id.et_base_price);
        addServDescriptionText = (EditText) findViewById(id.et_service_description);


        ParseObject newTechService = new ParseObject("TechService");
        newTechService.put("title", String.valueOf(addServTitleText.getText()));
        newTechService.put("basePrice", String.valueOf(addServPriceText.getText()));
        newTechService.put("description", String.valueOf(addServDescriptionText.getText()));
        newTechService.put("createdBy", ParseUser.getCurrentUser());

        newTechService.saveInBackground();

    }

}
