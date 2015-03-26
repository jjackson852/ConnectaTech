package com.notify.app.mobile.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.notify.app.mobile.R;
import com.notify.app.mobile.R.id;
import com.notify.app.mobile.R.layout;
import com.notify.app.mobile.core.BootstrapService;
import com.parse.ParseAnalytics;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Activity to create a new account on the parse database.
 */
public class FilterServicesActivity extends BootstrapActivity {

    private String compiledQuery = "{";
    private String endQuery = "}";
    private String filterByZipText;
    private String zipQuery;

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
        Button filterServicesButton = (Button)findViewById(id.b_filter_tech_services);
        filterServicesButton.setOnClickListener(filterServicesListener);

    }

    /**
     * Handles onClick event on the Submit button.
     */
    public void filterTechServices() {
        // Clear the previous query.
        BootstrapService.setServConstraint(null);

        filterByZipText = String.valueOf(((EditText) findViewById(id.et_searchby_zip)).getText());

        // If the value passed in is empty, then do not include it in the query.
        if (filterByZipText.equals("")){
            zipQuery = "";
        }
        else{
            zipQuery = "\"zipCode\":\""+ filterByZipText +"\"";
        }

        compiledQuery = compiledQuery + zipQuery + endQuery;

       if (compiledQuery.equals("{}")){
            // Do not apply the constraint because all EditText Fields were left empty.
       }
        else{
            BootstrapService.setServConstraint(compiledQuery);
       }


    }

}
