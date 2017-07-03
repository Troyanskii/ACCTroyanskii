package com.troyanskiievgen.acctroyanskii.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.troyanskiievgen.acctroyanskii.view.AcceletometerDataGraphView;

/**
 * Created by Relax on 02.07.2017.
 */

@InjectViewState
public class GraphFragmentPresenter extends MvpPresenter<AcceletometerDataGraphView> {

    public GraphFragmentPresenter() {
        setupView();
    }

    public void setupView() {
        getViewState().setupGraph();
    }
}
