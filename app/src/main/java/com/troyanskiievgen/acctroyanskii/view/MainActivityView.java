package com.troyanskiievgen.acctroyanskii.view;

import com.arellomobile.mvp.MvpView;

/**
 * Created by Relax on 28.06.2017.
 */

public interface MainActivityView extends MvpView {

    void setupMainActivity();

    void setupIntervalsPicker(Integer[] intervals);

    void setIntervalSelected(int position);

    void setSelectedFragmentPager(int position);

    void setupButtons(boolean isStartEnabled);

    void showProgressBar();

    void hideProgressBar();
}
