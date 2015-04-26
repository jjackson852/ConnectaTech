package com.notify.app.mobile.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.notify.app.mobile.R;
import com.notify.app.mobile.bootstrapOrigin.core.User;
import com.notify.app.mobile.bootstrapOrigin.core.UserService;
import com.notify.app.mobile.bootstrapOrigin.ui.BootstrapActivity;
import com.notify.app.mobile.bootstrapOrigin.ui.UserActivity;
import com.notify.app.mobile.core.TechService;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.DecimalFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.InjectView;

import static com.notify.app.mobile.bootstrapOrigin.core.Constants.Extra.TECHSERVICE_ITEM;
import static com.notify.app.mobile.bootstrapOrigin.core.Constants.Extra.USER;

public class TechServiceActivity extends BootstrapActivity {

    @InjectView(R.id.tv_title)
    protected TextView title;
    @InjectView(R.id.tv_content)
    protected TextView content;
    @InjectView(R.id.tv_price)
    protected TextView price;
    @InjectView(R.id.tv_category)
        protected TextView category;
    @InjectView(R.id.tv_submitted)
    protected TextView submitted;
    private TechService techServiceItem;
    private Boolean isProvider;
    private EditText edittext;
    AlertDialog.Builder alert;
    Intent techServIntent;
    Intent userServIntent;
    int itemBeingEdited;
    Spinner spinner;
    private RelativeLayout rl;

    private ParseObject currentTechService;
    private ParseUser parseProvider;

    private View.OnClickListener editServTitleListener = new View.OnClickListener() {
        public void onClick(View v) {

            itemBeingEdited = 1;
            alert.setView(edittext);
            alert.show();
//            ((ViewGroup)edittext.getParent()).removeView(edittext);

        }
    };

    private View.OnClickListener editServDescListener = new View.OnClickListener() {
        public void onClick(View v) {

            itemBeingEdited = 2;
            alert.setView(edittext);
            alert.show();
//            ((ViewGroup)edittext.getParent()).removeView(edittext);

        }
    };

    private View.OnClickListener editServPriceListener = new View.OnClickListener(){
        public void onClick(View v) {

            itemBeingEdited = 3;
            edittext.setInputType(InputType.TYPE_CLASS_NUMBER);
            alert.setView(edittext);
            alert.show();
//            ((ViewGroup)edittext.getParent()).removeView(edittext);

        }
    };


    private View.OnClickListener editServCategoryListener = new View.OnClickListener(){
        public void onClick(View v) {

//            currentTechService.put("category", spinner.getSelectedItem().toString());
//            techServiceItem.setCategory(spinner.getSelectedItem().toString());
//            techServIntent.putExtra(TECHSERVICE_ITEM, techServiceItem);
//            currentTechService.saveInBackground();
//            finish();
            itemBeingEdited = 4;
//            ((ViewGroup)edittext.getParent()).removeView(edittext);
            alert.setView(rl);
            alert.show();

//            ((ViewGroup)spinner.getParent()).removeView(spinner);

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

    private View.OnClickListener viewProviderListener = new View.OnClickListener() {
        public void onClick(View v) {

            //Request the service.
            navigateToProviderProfile();

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


        if (getIntent() != null && getIntent().getExtras() != null) {
            techServiceItem = (TechService) getIntent().getExtras().getSerializable(TECHSERVICE_ITEM);
        }


        ParseQuery<ParseObject> query = ParseQuery.getQuery("TechService");
        query.whereEqualTo("objectId", techServiceItem.getObjectId());

        try {
            List<ParseObject> serviceResults = query.find();
            currentTechService = serviceResults.get(0).fetchIfNeeded();
        } catch (com.parse.ParseException e) {
            e.printStackTrace();
        }


        if (isProvider == false) {
            setContentView(R.layout.techservice_cust);

            /**
             * Attaches the Submit Request button listener to the xml button.
             */
            Button submitRequestButton = (Button) findViewById(R.id.b_request_service);
            submitRequestButton.setOnClickListener(requestServListener);

            TextView viewProviderView = (TextView) findViewById(R.id.tv_navtoprovider);
            viewProviderView.setOnClickListener(viewProviderListener);

            TextView viewProviderName = (TextView) findViewById(R.id.tv_providername);

            try{

                parseProvider = currentTechService.getParseUser("createdBy").fetchIfNeeded();
            }catch(com.parse.ParseException ex){
                ex.printStackTrace();
            }
            viewProviderName.setText(parseProvider.getUsername());

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
             * Attaches the Edit Service Price button listener to the xml button.
             */
            Button editServCategoryButton = (Button) findViewById(R.id.b_prov_edit_serv_category);
            editServCategoryButton.setOnClickListener(editServCategoryListener);

            /**
             * Attaches the Remove Service Description button listener to the xml button.
             */
            Button removeServButton = (Button) findViewById(R.id.b_prov_remove_serv);
            removeServButton.setOnClickListener(removeServListener);

            LayoutInflater li = LayoutInflater.from(TechServiceActivity.this);
            rl = (RelativeLayout)li.inflate(R.layout.spinner_layout, null);
            spinner = (Spinner) rl.findViewById(R.id.spin_edit_category);

            // Create an ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.categories_array, android.R.layout.simple_spinner_item);

            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // Apply the adapter to the spinner
            spinner.setAdapter(adapter);

            if (getIntent() != null && getIntent().getExtras() != null) {
                techServiceItem = (TechService) getIntent().getExtras().getSerializable(TECHSERVICE_ITEM);
            }
//            if (getIntent() != null && getIntent().getExtras() != null) {
//                name = (ParseUser) getIntent().getExtras().getSerializable(USER);
//            }

            alert = new AlertDialog.Builder(this);
            edittext = new EditText(this);
            generateAlertDialogs();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        setTitle(techServiceItem.getTitle());

        title.setText(techServiceItem.getTitle());
        content.setText(techServiceItem.getDescription());



        category.setText(techServiceItem.getCategory());
        price.setText("$" + techServiceItem.getBasePrice() + " " + techServiceItem.getChargeType());
        Format formatter = new SimpleDateFormat("MM-dd-yyyy");
        submitted.setText(formatter.format( techServiceItem.getCreatedAt()));

    }



    protected void navigateToRequestSubmission() {

        final Intent i = new Intent(this, RequestServiceActivity.class);
        i.putExtra("serviceRequestedId", techServiceItem.getObjectId());
        startActivity(i);

    }

    protected void navigateToProviderProfile() {

        User provider = new User();

        provider.setObjectId(parseProvider.getObjectId());
        provider.setBio(parseProvider.getString("bio"));
        provider.setCreatedAt(parseProvider.getCreatedAt());
        provider.setFirstName(parseProvider.getString("firstName"));
        provider.setLastName(parseProvider.getString("lastName"));
        provider.setUsername(parseProvider.getUsername());
        final Intent i = new Intent(this, UserActivity.class);
        i.putExtra(USER, provider);
        startActivity(i);

    }

    protected void generateAlertDialogs(){

        alert.setTitle("Edit");
//        if(itemBeingEdited == 4){
//            alert.setView(spinner);
//        }
//        else{
//            alert.setView(edittext);
//        }


        techServIntent = new Intent(this, TechServiceActivity.class);

        alert.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //What ever you want to do with the value
                String editTextValue = "";
                String spinnerTextValue = "";
                if(itemBeingEdited == 4){
                    spinnerTextValue = spinner.getSelectedItem().toString();
                }
                else{
                    editTextValue = String.valueOf(edittext.getText());
                }


                switch (itemBeingEdited) {

                    case 1: currentTechService.put("title", editTextValue);
                            techServiceItem.setTitle(editTextValue);
                            break;
                    case 2: currentTechService.put("description", editTextValue);
                            techServiceItem.setDescription(editTextValue);
                            break;
                    case 3: if(editTextValue.equals("")){
                                editTextValue = "0.00";
                            }
                            currentTechService.put("basePrice", editTextValue);
                            techServiceItem.setBasePrice(editTextValue);
                            break;
                    case 4: currentTechService.put("category", spinnerTextValue);
                            techServiceItem.setCategory(spinnerTextValue);
                            break;
                }

                techServIntent.putExtra(TECHSERVICE_ITEM, techServiceItem);

                currentTechService.saveInBackground();

                finish();
                if(itemBeingEdited == 4){
                    ((ViewGroup)rl.getParent()).removeView(rl);
                }
                if(itemBeingEdited == 3){
                    edittext.setInputType(InputType.TYPE_CLASS_TEXT);
                }
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
