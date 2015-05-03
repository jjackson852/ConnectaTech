package com.notify.app.mobile.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.notify.app.mobile.R;
import com.notify.app.mobile.bootstrapOrigin.core.User;
import com.notify.app.mobile.bootstrapOrigin.ui.BootstrapActivity;
import com.notify.app.mobile.core.Request;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.InjectView;

import static com.notify.app.mobile.bootstrapOrigin.core.Constants.Extra.REQUEST_ITEM;
import static com.notify.app.mobile.bootstrapOrigin.core.Constants.Extra.TECHSERVICE_ITEM;
import static com.notify.app.mobile.bootstrapOrigin.core.Constants.Extra.USER;

public class RequestActivity extends BootstrapActivity {

    @InjectView(R.id.tv_title)
    protected TextView title;
    @InjectView(R.id.tv_content)
    protected TextView content;
    @InjectView(R.id.date_submitted)
    protected TextView date;
    @InjectView(R.id.submitted_by)
    protected TextView submitted;

    private Request requestItem;
    private Boolean isProvider;
    ParseObject currentRequest;
    //    ParseObject parseResults;
    AlertDialog.Builder alert;
    Intent requestIntent;
    int itemBeingEdited;
    EditText edittext;


    private View.OnClickListener editReqAddlInfoListener = new View.OnClickListener() {
        public void onClick(View v) {

            itemBeingEdited = 1;
            alert.show();

        }
    };

    private View.OnClickListener removeReqListener = new View.OnClickListener() {
        public void onClick(View v) {
            AlertDialog.Builder removeAlert = new AlertDialog.Builder(RequestActivity.this);

            removeAlert.setTitle("Really Delete Request?");

            removeAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    // what ever you want to do with No option.

                }
            });

            removeAlert.setPositiveButton("Remove",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            currentRequest.deleteInBackground();
                            RequestActivity.this.finish();

                        }
                    });

            removeAlert.show();
        }
    };

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        isProvider = ParseUser.getCurrentUser().getBoolean("isProvider");


        if (!isProvider) {
            setContentView(R.layout.request_cust);

            if (getIntent() != null && getIntent().getExtras() != null) {
                requestItem = (Request) getIntent().getExtras().getSerializable(REQUEST_ITEM);
            }
            final String titleString = requestItem.getServiceTitle();
     //       submitted.setText(requestItem.getCustEmail());
            setTitle(titleString);

            /**
             * Attaches the Remove Request button listener to the xml button.
             */
            Button removeReqButton = (Button) findViewById(R.id.b_cust_remove_req);
            removeReqButton.setOnClickListener(removeReqListener);

            /**
             * Attaches the Edit Request Description button listener to the xml button.
             */
            Button editReqAddInfoButton = (Button) findViewById(R.id.b_cust_edit_req_desc);
            editReqAddInfoButton.setOnClickListener(editReqAddlInfoListener);

            ParseQuery<ParseObject> query = ParseQuery.getQuery("Request");
            query.whereEqualTo("objectId", requestItem.getObjectId());

            try {
                List<ParseObject> serviceResults = query.find();
                currentRequest = serviceResults.get(0);
            } catch (com.parse.ParseException e) {
                e.printStackTrace();
            }
            generateAlertDialogs();


        } else {
            setContentView(R.layout.request);

            if (getIntent() != null && getIntent().getExtras() != null) {
                requestItem = (Request) getIntent().getExtras().getSerializable(REQUEST_ITEM);
            }

            final String custPhone = requestItem.getCustPhoneNumber();
            final String custEmail = requestItem.getCustEmail();

            final String titleString = requestItem.getServiceTitle();

//            setTitle(titleString);

            Button callCustomerButton = (Button) findViewById(R.id.b_cust_phone_number_req);
            callCustomerButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    String phone_no = custPhone;
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + phone_no));
                    startActivity(callIntent);
                }
            });

            Button emailCustomerButton = (Button) findViewById(R.id.b_cust_email_req);

            emailCustomerButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    String email_addr = custEmail;

                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                            "mailto", custEmail, null));
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Response to Request for: " + titleString);
                    startActivity(Intent.createChooser(emailIntent, "Send via..."));
                }
            });

            ParseQuery<ParseObject> query = ParseQuery.getQuery("Request");
            query.whereEqualTo("objectId", requestItem.getObjectId());

            try {
                List<ParseObject> serviceResults = query.find();
                currentRequest = serviceResults.get(0);
            } catch (com.parse.ParseException e) {
                e.printStackTrace();
            }

//            Button denyRequsetButton = (Button) findViewById(R.id.b_cust_deny_req);
//            denyRequsetButton.setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(View arg0) {
//
//                    AlertDialog.Builder removeAlert  = new AlertDialog.Builder(RequestActivity.this);
//
//                    removeAlert.setTitle("Are you sure?");
//
//                    removeAlert.setPositiveButton("Yes",
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    ParsePush push = new ParsePush();
//                                    push.setChannel(currentRequest.getParseUser("submittedBy").getObjectId());
//                                    push.setMessage(ParseUser.getCurrentUser().getUsername() + " was unable to accept your request.");
//                                    push.sendInBackground();
//                                    currentRequest.deleteInBackground();
//                                    RequestActivity.this.finish();
//
//                                }
//                            });
//
//                    removeAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int whichButton) {
//                            // what ever you want to do with No option.
//
//                        }
//                    });
//
//                    removeAlert.show();
//                }
//            });
            submitted.setText(requestItem.getCustEmail());
//            ParseQuery<ParseObject> query = ParseQuery.getQuery("Request");
//            query.whereEqualTo("submittedBy", requestItem.getObjectId());

//            try {
//                List<ParseObject> results = query.find();
//                parseResults = results.get(0);
//            } catch (com.parse.ParseException e) {
//                e.printStackTrace();
//            }


        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        title.setText(requestItem.getServiceTitle());
        content.setText(requestItem.getAddlInfo());
        Format formatter = new SimpleDateFormat("MM-dd-yyyy");
        date.setText(formatter.format(requestItem.getCreatedAt()));


    }

    protected void generateAlertDialogs() {


        alert = new AlertDialog.Builder(this);

        edittext = new EditText(this);
        alert.setTitle("Edit Specifics");

        alert.setView(edittext);

        requestIntent = new Intent(this, RequestActivity.class);
        alert.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //What ever you want to do with the value
                String editTextValue = String.valueOf(edittext.getText());

                switch (itemBeingEdited) {

                    case 1:
                        currentRequest.put("addlInfo", editTextValue);
                        requestItem.setAddlInfo(editTextValue);
                        break;

                }

                requestIntent.putExtra(REQUEST_ITEM, requestItem);

                currentRequest.saveInBackground();

                finish();
                startActivity(requestIntent);
                ((ViewGroup)edittext.getParent()).removeView(edittext);

            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
                ((ViewGroup)edittext.getParent()).removeView(edittext);

            }
        });

    }

}
