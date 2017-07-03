package com.troyanskiievgen.acctroyanskii.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.troyanskiievgen.acctroyanskii.manager.UserManager;
import com.troyanskiievgen.acctroyanskii.view.MainActivityView;

/**
 * Created by Relax on 29.06.2017.
 */

@InjectViewState
public class MainActivityPresenter extends MvpPresenter<MainActivityView> {

    private final static int DEFAULT_FRAGMENT_POSITION = 0;

    private int currentFragmentSelected = DEFAULT_FRAGMENT_POSITION;
    private int intervalSelected;
    private Integer[] intervalsArray = {1, 2, 5, 10};

    public MainActivityPresenter() {
        getViewState().showProgressBar();
        uploadUser();
    }

    private void setupView() {
        getViewState().setupMainActivity();
        getViewState().setupIntervalsPicker(intervalsArray);
        getViewState().setIntervalSelected(intervalSelected);
        getViewState().setupButtons(true);
        getViewState().setSelectedFragmentPager(currentFragmentSelected);
        getViewState().hideProgressBar();
    }

    public void updateIntervalSelected(int position){
        intervalSelected = position;
        getViewState().setIntervalSelected(intervalSelected);
    }

    public long getIntervalSelected() {
        return (long)intervalsArray[intervalSelected] * 1000;
    }

    public void setSelectedFragmentPager(int position) {
        currentFragmentSelected = position;
        getViewState().setSelectedFragmentPager(position);
    }

    public void setupButtonsAvailability(boolean isStartEnabled) {
        getViewState().setupButtons(isStartEnabled);
    }

    private void uploadUser(){
        UserManager.getInstance().uploadUser(uploadCallback);
    }

    private UserManager.UploadUserCallback uploadCallback = new UserManager.UploadUserCallback() {
        @Override
        public void onUserUploaded() {
            setupView();
        }
    };
}
