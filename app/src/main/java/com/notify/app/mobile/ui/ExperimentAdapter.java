package com.notify.app.mobile.ui;

import android.view.LayoutInflater;

import com.notify.app.mobile.R;
import com.notify.app.mobile.core.Experiment;

import java.util.List;

/**
 * Created by Martinez on 2/28/2015.
 */
public class ExperimentAdapter extends AlternatingColorListAdapter<Experiment> {

    public ExperimentAdapter(final LayoutInflater inflater, final List<Experiment> items) {
        super(R.layout.experiment_items, inflater, items);
    }

    @Override
    protected int[] getChildViewIds() {
        return new int[]{R.id.experimentbtn};
    }
}
