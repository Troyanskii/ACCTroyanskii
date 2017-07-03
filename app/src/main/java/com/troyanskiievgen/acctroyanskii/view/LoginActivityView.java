package com.troyanskiievgen.acctroyanskii.view;

import com.arellomobile.mvp.MvpView;

/**
 * Created by Relax on 28.06.2017.
 */

public interface LoginActivityView extends MvpView {

    void setupAsSignUp();

    void setupAsSignIn();

    void onBackPressed();

    void onSignInSuccess(String userId);

    void onSignUpSuccess();

    void showError(String error);
}
