package com.notify.app.mobile.ui;

import android.view.LayoutInflater;
import com.notify.app.mobile.R;
import com.notify.app.mobile.core.Experiment;
import java.util.List;

/**
 * Created by Martinez on 2/18/2015.
 */
public class ExperimentListAdapter  extends AlternatingColorListAdapter<Experiment> {

    public ExperimentListAdapter(final LayoutInflater inflater, final List<Experiment> items) {
        super(R.layout.experiment_list_item, inflater, items);
    }

    @Override
    protected int[] getChildViewIds() {
        return new int[]{R.id.tv_name, R.id.tv_date};
    }
}
