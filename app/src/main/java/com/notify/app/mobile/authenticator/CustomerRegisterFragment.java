package com.notify.app.mobile.authenticator;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.telephony.TelephonyManager;
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

    private String regUsernameText;
    private String regPasswordText;
    private String regEmailText;
    private String regFNameText;
    private String regLNameText;
    private String regPhoneNumberText;
    private EditText phoneET;
//    private String regCountry;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.register_activity_cust, container, false);

        phoneET = (EditText) view.findViewById(R.id.et_phone_number);

        phoneET.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

//        regCountry = getCountry(getActivity());

        Button submitUserButton = (Button) view.findViewById(R.id.b_registerSub_cust);
        submitUserButton.setOnClickListener(this);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onClick(View v) {
        //do what you want to do when button is clicked
        switch (v.getId()) {
            case R.id.b_registerSub_cust:
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
        regPhoneNumberText = String.valueOf(phoneET.getText());

        regFNameText = Character.toUpperCase(regFNameText.charAt(0)) + regFNameText.substring(1);
        regLNameText = Character.toUpperCase(regLNameText.charAt(0)) + regLNameText.substring(1);


        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(getActivity());
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
        if (regUsernameText.matches("^[a-zA-Z0-9]{8,16}$")){

            if (isEmailValid(regEmailText)) {

                if (regFNameText.matches("^[a-zA-Z]{1,25}$") && regLNameText.matches("^[a-zA-Z]{1,30}$")){

                    if (regPhoneNumberText.matches("^[0-9]{10}")) {

                        completeRegistration();

                        //Go back to login activity.
                        getActivity().finish();
                    }
                    else{

                        // Present Dialog Box and do NOT register.
                        dlgAlert.setMessage("Phone numbers must contain 10 digits.");
                        dlgAlert.create().show();

                    }

                }
                else{
                    // Present Dialog Box and do NOT register.
                    dlgAlert.setMessage("Please revise your First or Last name entry.");
                    dlgAlert.create().show();
                }

            }
            else{
                // Present Dialog Box and do NOT register.
                dlgAlert.setMessage("Please enter a valid email address.");
                dlgAlert.create().show();
            }

        }
        else{
            // Present Dialog Box and do NOT register.
            dlgAlert.setMessage("Please choose a username with 8-16 characters which contains" +
                    " only numbers and letters.");
            dlgAlert.create().show();
        }
    }

    public void completeRegistration(){

        ParseUser user = new ParseUser();
        user.setUsername(regUsernameText);
        user.setPassword(regPasswordText);
        user.setEmail(regEmailText);

        user.put("firstName", regFNameText);
        user.put("lastName", regLNameText);
        user.put("isProvider", false);
        user.put("phoneNumber", regPhoneNumberText);
//        user.put("country", regCountry);

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

    public final static boolean isEmailValid(CharSequence emailAddr) {
        if (emailAddr == null)
            return false;

        return android.util.Patterns.EMAIL_ADDRESS.matcher(emailAddr).matches();
    }

//    public String getCountry(Context c){
//
//        String country;
//
//        TelephonyManager tManager = (TelephonyManager)c.getSystemService(Context.TELEPHONY_SERVICE);
//
//        country = tManager.getNetworkCountryIso();
//
//        return country;
//    }
}