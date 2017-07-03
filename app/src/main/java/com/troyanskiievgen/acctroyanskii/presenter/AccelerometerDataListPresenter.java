package com.troyanskiievgen.acctroyanskii.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.troyanskiievgen.acctroyanskii.view.AccelerometerDataListView;

/**
 * Created by Relax on 02.07.2017.
 */

@InjectViewState
public class AccelerometerDataListPresenter extends MvpPresenter<AccelerometerDataListView> {

    public AccelerometerDataListPresenter() {
        setupView();
    }

    public void setupView() {
        getViewState().setupList();
    }
}
