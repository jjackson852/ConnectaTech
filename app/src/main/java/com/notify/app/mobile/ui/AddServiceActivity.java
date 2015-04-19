package com.notify.app.mobile.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.notify.app.mobile.R;
import com.notify.app.mobile.R.id;
import com.notify.app.mobile.R.layout;
import com.notify.app.mobile.bootstrapOrigin.ui.BootstrapActivity;
import com.parse.ParseAnalytics;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Activity to create a new service on the parse database.
 */
public class AddServiceActivity extends BootstrapActivity {

    private String category;
    Spinner spinner;

    private View.OnClickListener addServiceListener = new View.OnClickListener() {
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

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /**
         * Attaches the Submit New Service button listener to the xml button.
         */
        Button submitServiceButton = (Button) findViewById(id.b_submit_new_service);
        submitServiceButton.setOnClickListener(addServiceListener);

        spinner = (Spinner) findViewById(id.spin_category);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

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
        newTechService.put("zipCode", ParseUser.getCurrentUser().getString("zipCode"));
        newTechService.put("category", spinner.getSelectedItem().toString());

        newTechService.saveInBackground();

    }

}
