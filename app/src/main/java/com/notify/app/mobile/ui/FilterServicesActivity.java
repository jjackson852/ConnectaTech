package com.notify.app.mobile.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.notify.app.mobile.R;
import com.notify.app.mobile.R.id;
import com.notify.app.mobile.R.layout;
import com.notify.app.mobile.bootstrapOrigin.core.BootstrapService;
import com.notify.app.mobile.bootstrapOrigin.ui.BootstrapActivity;

/**
 * Activity to create a new account on the parse database.
 */
public class FilterServicesActivity extends BootstrapActivity {

    private String compiledQuery = "{";
    private String endQuery = "}";
    private String filterByZipText;
    private String filterByCategory;
    private String zipQuery;
    private String categoryQuery;
    private boolean firstQuery = true;
    private Spinner spinner;

    private View.OnClickListener filterServicesListener = new View.OnClickListener() {
        public void onClick(View v) {

            //Filter the results.
            filterTechServices();

            //Go back to login activity.
            finish();

        }
    };

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        setContentView(layout.filter_services_activity);

        /**
         * Attaches the SubmitUser button listener to the xml button.
         */
        Button filterServicesButton = (Button) findViewById(id.b_filter_tech_services);
        filterServicesButton.setOnClickListener(filterServicesListener);

        spinner = (Spinner) findViewById(id.filter_spin_category);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.filter_categories_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }

    /**
     * Handles onClick event on the Submit button.
     */
    public void filterTechServices() {
        // Clear the previous query.
        BootstrapService.setServConstraint(null);

        filterByZipText = String.valueOf(((EditText) findViewById(id.et_searchby_zip)).getText());
        filterByCategory = spinner.getSelectedItem().toString();

        // If the value passed in is empty, then do not include it in the query.
        if (filterByZipText.equals("")) {
            zipQuery = "";
        } else {
            zipQuery = "\"zipCode\":\"" + filterByZipText + "\"";
            firstQuery = false;
        }

        if (filterByCategory.equals("")) {
            categoryQuery = "";
        } else {
            if(firstQuery == true){
                categoryQuery = "\"category\":\"" + filterByCategory + "\"";
            }
            else{
                categoryQuery = ",\"category\":\"" + filterByCategory + "\"";
            }
        }

        compiledQuery = compiledQuery + zipQuery + categoryQuery + endQuery;

        if (compiledQuery.equals("{}")) {
            // Do not apply the constraint because all EditText Fields were left empty.
        } else {
            BootstrapService.setServConstraint(compiledQuery);
        }


    }

}
