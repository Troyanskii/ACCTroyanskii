package com.troyanskiievgen.acctroyanskii.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.arellomobile.mvp.MvpFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.troyanskiievgen.acctroyanskii.R;
import com.troyanskiievgen.acctroyanskii.manager.UserManager;
import com.troyanskiievgen.acctroyanskii.presenter.AccelerometerDataListPresenter;
import com.troyanskiievgen.acctroyanskii.ui.adapter.ExpandableListAdapter;
import com.troyanskiievgen.acctroyanskii.view.AccelerometerDataListView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Relax on 28.06.2017.
 */

public class AccelerometerDataListFragment extends MvpFragment implements AccelerometerDataListView {

    @BindView(R.id.exp_list_view)
    ExpandableListView expandableListView;

    @InjectPresenter
    AccelerometerDataListPresenter accelerometerDataListPresenter;

    private ExpandableListAdapter adapter;

    public static AccelerometerDataListFragment newInstance() {
        return new AccelerometerDataListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_accelerometer_data_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @Override
    public void setupList() {
        adapter = new ExpandableListAdapter(getActivity(), UserManager.getInstance().getCurrentUser().getSessionsList());
        expandableListView.setAdapter(adapter);
    }
}
