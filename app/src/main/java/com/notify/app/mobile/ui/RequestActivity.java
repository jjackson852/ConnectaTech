package com.notify.app.mobile.ui;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.notify.app.mobile.R;
import com.notify.app.mobile.core.Request;
import com.parse.ParseUser;

import butterknife.InjectView;

import static com.notify.app.mobile.core.Constants.Extra.REQUEST_ITEM;

public class RequestActivity extends BootstrapActivity {

    private Request requestItem;

    @InjectView(R.id.tv_title) protected TextView title;
    @InjectView(R.id.tv_content) protected TextView content;
//    @InjectView(R.id.tv_cust_phone_number_req) protected TextView custPhoneTV;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.request);

        if (getIntent() != null && getIntent().getExtras() != null) {
            requestItem = (Request) getIntent().getExtras().getSerializable(REQUEST_ITEM);
        }

        final String custPhone = requestItem.getCustPhoneNumber();
        final String custEmail = requestItem.getCustEmail();
        final String titleString = requestItem.getServiceTitle();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        setTitle(titleString);

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
                        "mailto",custEmail, null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Response to Request for: " + titleString);
                startActivity(Intent.createChooser(emailIntent, "Send via..."));
            }
        });


        title.setText(requestItem.getServiceTitle());
        content.setText(requestItem.getAddlInfo());
//        custPhoneTV.setText("Call: "+ custPhone);
//        custPhoneTV.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
//
//
//        custPhoneTV.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//
//                String phone_no = custPhone;
//                Intent callIntent = new Intent(Intent.ACTION_CALL);
//                callIntent.setData(Uri.parse("tel:" + phone_no));
//                startActivity(callIntent);
//            }
//        });
    }

}
