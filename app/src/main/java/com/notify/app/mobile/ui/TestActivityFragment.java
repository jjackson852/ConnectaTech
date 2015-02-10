package com.notify.app.mobile.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.notify.app.mobile.R;
import com.notify.app.mobile.authenticator.BootstrapAuthenticatorActivity;
import com.notify.app.mobile.core.Constants;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;


public class TestActivityFragment extends Fragment implements View.OnClickListener {

    View view;
    TextView text;

//    ParseUser currentUser;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.test_activity, container, false);

        text = (TextView) view.findViewById(R.id.hello_world_to_fullname);


        /**
         * James' Attempts at listing the current user's username.(Work in progress)
         */
//        ParseQuery<ParseUser> userQuery = ParseUser.getQuery();
//            userQuery.whereEqualTo("username", BootstrapAuthenticatorActivity.emailOrUsername);
//
//            userQuery.getFirstInBackground(new GetCallback<ParseUser>() {
//                @Override
//                public void done(ParseUser user, com.parse.ParseException e) {
//                    if (user == null) {
//                        Log.d("score", "The getFirst request failed.");
//                        text.setText(BootstrapAuthenticatorActivity.emailOrUsername + " null for some reason");
//                    } else {
//                        Log.d("score", "Retrieved the object.");
//                        text.setText(user.getUsername());
//                    }
//
//
//                }
//            });

//            String fname = currentUser.getString("firstName");
//            String lname = currentUser.getString("lastName");
//
//            text.setText(fname + " " + lname);
//            try {
//                Thread.sleep(3000,0);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }



        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onClick(View v) {

                getActivity().finish();

        }

    }

