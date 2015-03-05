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


    private View view;

    @Override
    public void onClick(View view) {

        this.view = view;
    }


}

