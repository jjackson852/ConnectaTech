package com.notify.app.mobile.authenticator;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.notify.app.mobile.R;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class ProviderRegisterFragment extends Fragment implements View.OnClickListener {

    View view;

    private String regUsernameText;
    private String regPasswordText;
    private String regEmailText;
    private String regFNameText;
    private String regLNameText;
    private String regZipCodeText;
    private Spinner spinner;

    public final static boolean isEmailValid(CharSequence emailAddr) {
        if (emailAddr == null)
            return false;

        return android.util.Patterns.EMAIL_ADDRESS.matcher(emailAddr).matches();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.register_activity_prov, container, false);

        Button submitUserButton = (Button) view.findViewById(R.id.b_registerSub_prov);
        submitUserButton.setOnClickListener(this);
        spinner = (Spinner) view.findViewById(R.id.spin_states);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.states, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onClick(View v) {
        //do what you want to do when button is clicked
        switch (v.getId()) {
            case R.id.b_registerSub_prov:
                //Create the user.
                handleRegister();

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

        regUsernameText = String.valueOf(((EditText) view.findViewById(R.id.et_reg_username)).getText()).toLowerCase();
        regPasswordText = String.valueOf(((EditText) view.findViewById(R.id.et_reg_password)).getText()).toLowerCase();
        regEmailText = String.valueOf(((EditText) view.findViewById(R.id.et_reg_email)).getText()).toLowerCase();
        regFNameText = String.valueOf(((EditText) view.findViewById(R.id.et_reg_first_name)).getText()).toLowerCase();
        regLNameText = String.valueOf(((EditText) view.findViewById(R.id.et_reg_last_name)).getText()).toLowerCase();
        regZipCodeText = String.valueOf(((EditText) view.findViewById(R.id.et_reg_zipcode)).getText());

        regZipCodeText = Character.toUpperCase(regZipCodeText.charAt(0)) + regZipCodeText.substring(1);
        regFNameText = Character.toUpperCase(regFNameText.charAt(0)) + regFNameText.substring(1);
        regLNameText = Character.toUpperCase(regLNameText.charAt(0)) + regLNameText.substring(1);

        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(getActivity());
        dlgAlert.setTitle("Invalid Input");
        dlgAlert.setCancelable(true);

        dlgAlert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //dismiss the dialog
                    }
                });

        /**
         * Checks to ensure the Username contains only letters
         * and numbers and is between 8 and 16 characters.
         */
        if (regUsernameText.matches("^[a-zA-Z0-9]{8,16}$")) {

            if (isEmailValid(regEmailText)) {

                if (regFNameText.matches("^[a-zA-Z]{1,25}$") && regLNameText.matches("^[a-zA-Z]{1,30}$")) {

                    if (regZipCodeText.matches(/*"^[0-9]{5}$"*/"^[a-zA-Z\\s]*$")) {
                        completeRegistration();

                        //Go back to login activity.
                        getActivity().finish();
                    } else {
                        // Present Dialog Box and do NOT register.
                        dlgAlert.setMessage("Please revise your city name.");
                        dlgAlert.create().show();
                    }

                } else {
                    // Present Dialog Box and do NOT register.
                    dlgAlert.setMessage("Please revise your First or Last name entry.");
                    dlgAlert.create().show();
                }

            } else {
                // Present Dialog Box and do NOT register.
                dlgAlert.setMessage("Please enter a valid email address.");
                dlgAlert.create().show();
            }

        } else {
            // Present Dialog Box and do NOT register.
            dlgAlert.setMessage("Please choose a username with 8-16 characters which contains" +
                    " only numbers and letters.");
            dlgAlert.create().show();
        }

    }

    public void completeRegistration() {

        ParseUser user = new ParseUser();
        user.setUsername(regUsernameText);
        user.setPassword(regPasswordText);
        user.setEmail(regEmailText);

        user.put("firstName", regFNameText);
        user.put("lastName", regLNameText);
        user.put("zipCode", regZipCodeText);
        user.put("state", spinner.getSelectedItem().toString());
        user.put("isProvider", true);


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