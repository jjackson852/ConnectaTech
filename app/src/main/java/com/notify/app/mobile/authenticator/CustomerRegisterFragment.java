package com.notify.app.mobile.authenticator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.notify.app.mobile.R;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class CustomerRegisterFragment extends Fragment implements View.OnClickListener {

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.register_activity_new, container, false);

        Button submitUserButton = (Button) view.findViewById(R.id.b_registerSub);
        submitUserButton.setOnClickListener(this);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onClick(View v) {
        //do what you want to do when button is clicked
        switch (v.getId()) {
            case R.id.b_registerSub:
                //Create the user.
                handleRegister();

                //Go back to login activity.
                getActivity().finish();
                break;

            // Different Button Pressed
//            case R.id.differentButton:
//                Do something else.
//                break;
        }

    }

    /**
     * Handles onClick event on the Submit button. Sends username/password to
     * the server for account creation.
     */
    public void handleRegister() {

        EditText regUserText;
        EditText regPasswordText;
        EditText regFNameText;
        EditText regLNameText;

        regUserText = (EditText) view.findViewById(R.id.et_reg_email);
        regPasswordText = (EditText) view.findViewById(R.id.et_reg_password);
        regFNameText = (EditText) view.findViewById(R.id.et_reg_first_name);
        regLNameText = (EditText) view.findViewById(R.id.et_reg_last_name);

        ParseUser user = new ParseUser();
        user.setUsername(String.valueOf(regUserText.getText()));
        user.setPassword(String.valueOf(regPasswordText.getText()));

        user.put("firstName", String.valueOf(regFNameText.getText()));
        user.put("lastName", String.valueOf(regLNameText.getText()));

        user.signUpInBackground(new SignUpCallback() {

            public void done(com.parse.ParseException e) {
                if (e == null) {
                    // Hooray! Let them use the app now.
                } else {
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                }
            }

        });
    }
}