package com.notify.app.mobile.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;

import com.notify.app.mobile.R;
import com.notify.app.mobile.R.id;
import com.notify.app.mobile.R.layout;
import com.notify.app.mobile.bootstrapOrigin.core.User;
import com.notify.app.mobile.bootstrapOrigin.ui.BootstrapActivity;
import com.parse.ParseAnalytics;
import com.parse.ParseObject;
import com.parse.ParseUser;

import static com.notify.app.mobile.bootstrapOrigin.core.Constants.Extra.USER;

/**
 * Activity to create a new service on the parse database.
 */
public class AddProfileActivity extends BootstrapActivity {

    private String category;
    Spinner spinner;
    ParseUser serviceProvider;

    private View.OnClickListener addServiceListener = new View.OnClickListener() {
        public void onClick(View v) {

            //Create the user.
            submitProfileInfo();

            //Go back to login activity.
            finish();

        }
    };


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        setContentView(layout.add_provider_profile);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /**
         * Attaches the Submit New Service button listener to the xml button.
         */
        Button submitServiceButton = (Button) findViewById(id.update_profile);
        submitServiceButton.setOnClickListener(addServiceListener);

//        spinner = (Spinner) findViewById(id.spin_category);
//
//        // Create an ArrayAdapter using the string array and a default spinner layout
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                R.array.categories_array, android.R.layout.simple_spinner_item);
//
//        // Specify the layout to use when the list of choices appears
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        // Apply the adapter to the spinner
//        spinner.setAdapter(adapter);

    }

    /**
     * Handles onClick event on the Submit button.
     */
    public void submitProfileInfo() {
        ParseAnalytics.trackAppOpened(getIntent());

//        EditText addServTitleText;
//        EditText addServPriceText;
        MultiAutoCompleteTextView providerInfoBox = null;

//        addServTitleText = (EditText) findViewById(id.et_service_title);
//        addServPriceText = (EditText) findViewById(id.et_base_price);
        providerInfoBox = (MultiAutoCompleteTextView) findViewById(id.providerInfoBox);

//        String value = getIntent().getExtras().getString(User);

        ParseObject newProfileRating = new ParseObject("User");
//        newTechService.put("title", String.valueOf(addServTitleText.getText()));
//        newTechService.put("basePrice", String.valueOf(addServPriceText.getText()));
        newProfileRating.put("description", String.valueOf(providerInfoBox.getText()));
//        newTechService.put("createdBy", ParseUser.getCurrentUser());
//        newTechService.put("zipCode", ParseUser.getCurrentUser().getString("zipCode"));
//        newTechService.put("category", spinner.getSelectedItem().toString());

        newProfileRating.saveInBackground();

    }

}
