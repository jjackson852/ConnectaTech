package com.notify.app.mobile.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.notify.app.mobile.R;


public class TestActivityFragment extends Fragment implements View.OnClickListener {

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.test_activity, container, false);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onClick(View v) {

                getActivity().finish();

        }

    }

