package com.notify.app.mobile.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.notify.app.mobile.R;
import com.notify.app.mobile.bootstrapOrigin.ui.BootstrapActivity;
import com.notify.app.mobile.core.TechService;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

import butterknife.InjectView;

import static com.notify.app.mobile.bootstrapOrigin.core.Constants.Extra.TECHSERVICE_ITEM;

public class TechServiceActivity extends BootstrapActivity {

    @InjectView(R.id.tv_title)
    protected TextView title;
    @InjectView(R.id.tv_content)
    protected TextView content;
    @InjectView(R.id.tv_price)
    protected TextView price;
    private TechService techServiceItem;
    private Boolean isProvider;
    AlertDialog.Builder alert;
    Intent techServIntent;
    int itemBeingEdited;

    private ParseObject currentTechService;

    private View.OnClickListener editServTitleListener = new View.OnClickListener() {
        public void onClick(View v) {

            itemBeingEdited = 1;
            alert.show();

        }
    };

    private View.OnClickListener editServDescListener = new View.OnClickListener() {
        public void onClick(View v) {

            itemBeingEdited = 2;
            alert.show();

        }
    };

    private View.OnClickListener editServPriceListener = new View.OnClickListener(){
        public void onClick(View v) {

            itemBeingEdited = 3;
            alert.show();

        }
    };

    private View.OnClickListener requestServListener = new View.OnClickListener() {
        public void onClick(View v) {

            //Request the service.
            navigateToRequestSubmission();

            //Go back to login activity.
            finish();

        }
    };

    private View.OnClickListener removeServListener = new View.OnClickListener() {
        public void onClick(View v) {
            AlertDialog.Builder removeAlert  = new AlertDialog.Builder(TechServiceActivity.this);

            removeAlert.setTitle("Really Remove?");

            removeAlert.setPositiveButton("Remove",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            currentTechService.deleteInBackground();
                            TechServiceActivity.this.finish();
                           
                        }
                    });

            removeAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    // what ever you want to do with No option.

                }
            });

            removeAlert.show();
        }
    };

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        isProvider = ParseUser.getCurrentUser().getBoolean("isProvider");


        if (isProvider == false) {
            setContentView(R.layout.techservice_cust);

            /**
             * Attaches the Submit Request button listener to the xml button.
             */
            Button submitRequestButton = (Button) findViewById(R.id.b_request_service);
            submitRequestButton.setOnClickListener(requestServListener);

            if (getIntent() != null && getIntent().getExtras() != null) {
                techServiceItem = (TechService) getIntent().getExtras().getSerializable(TECHSERVICE_ITEM);
            }
        } else {
            setContentView(R.layout.techservice);

            /**
             * Attaches the Edit Service Title button listener to the xml button.
             */
            Button editServTitleButton = (Button) findViewById(R.id.b_prov_edit_serv_title);
            editServTitleButton.setOnClickListener(editServTitleListener);

            /**
             * Attaches the Edit Service Description button listener to the xml button.
             */
            Button editServDescButton = (Button) findViewById(R.id.b_prov_edit_serv_desc);
            editServDescButton.setOnClickListener(editServDescListener);

            /**
             * Attaches the Edit Service Price button listener to the xml button.
             */
            Button editServPriceButton = (Button) findViewById(R.id.b_prov_edit_serv_price);
            editServPriceButton.setOnClickListener(editServPriceListener);

            /**
             * Attaches the Remove Service Description button listener to the xml button.
             */
            Button removeServButton = (Button) findViewById(R.id.b_prov_remove_serv);
            removeServButton.setOnClickListener(removeServListener);

            if (getIntent() != null && getIntent().getExtras() != null) {
                techServiceItem = (TechService) getIntent().getExtras().getSerializable(TECHSERVICE_ITEM);
            }

            generateAlertDialogs();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        setTitle(techServiceItem.getTitle());

        title.setText(techServiceItem.getTitle());
        content.setText(techServiceItem.getDescription());
        price.setText(techServiceItem.getBasePrice());

    }

    protected void navigateToRequestSubmission() {

        final Intent i = new Intent(this, RequestServiceActivity.class);
        i.putExtra("serviceRequestedId", techServiceItem.getObjectId());
        startActivity(i);

    }

    protected void generateAlertDialogs(){

        ParseQuery<ParseObject> query = ParseQuery.getQuery("TechService");
        query.whereEqualTo("objectId", techServiceItem.getObjectId());

        try {
            List<ParseObject> serviceResults = query.find();
            currentTechService = serviceResults.get(0);
        } catch (com.parse.ParseException e) {
            e.printStackTrace();
        }

        alert = new AlertDialog.Builder(this);

        final EditText edittext = new EditText(this);
        alert.setTitle("Edit Description");
//        LayoutInflater li = LayoutInflater.from(TechServiceActivity.this);
//        RelativeLayout rl = (RelativeLayout)li.inflate(R.layout.rate_provider_activity, null);
//        alert.setView(rl);
        alert.setView(edittext);

//        if(edittext.getParent() == null){
//            alert.setView(edittext);
//        } else {
//            edittext = null;
//        }


        techServIntent = new Intent(this, TechServiceActivity.class);

        alert.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //What ever you want to do with the value
                String editTextValue = String.valueOf(edittext.getText());

                switch (itemBeingEdited) {

                    case 1: currentTechService.put("title", editTextValue);
                            techServiceItem.setTitle(editTextValue);
                            break;
                    case 2: currentTechService.put("description", editTextValue);
                            techServiceItem.setDescription(editTextValue);
                            break;
                    case 3: currentTechService.put("basePrice", editTextValue);
                            techServiceItem.setBasePrice(editTextValue);
                            break;
                }

                techServIntent.putExtra(TECHSERVICE_ITEM, techServiceItem);

                currentTechService.saveInBackground();

                finish();
                startActivity(techServIntent);

            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
                
            }
        });

    }

}
