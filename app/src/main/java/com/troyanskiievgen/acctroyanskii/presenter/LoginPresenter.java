package com.troyanskiievgen.acctroyanskii.presenter;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.troyanskiievgen.acctroyanskii.manager.UserManager;
import com.troyanskiievgen.acctroyanskii.model.User;
import com.troyanskiievgen.acctroyanskii.network.FireBaseClient;
import com.troyanskiievgen.acctroyanskii.network.FireBaseDataBaseClient;
import com.troyanskiievgen.acctroyanskii.view.LoginActivityView;

/**
 * Created by Relax on 28.06.2017.
 */

@InjectViewState
public class LoginPresenter extends MvpPresenter<LoginActivityView> {


    private boolean isSignInMode;

    public LoginPresenter() {
        this.isSignInMode = true;
        setupView();
    }

    private void setupView() {
        if (isSignInMode) {
            getViewState().setupAsSignIn();
        } else {
            getViewState().setupAsSignUp();
        }
    }

    public void changeMode() {
        if(isSignInMode) {
            setupAsSignUp();
        } else {
            setupAsSignIn();
        }
    }

    private void setupAsSignIn() {
        getViewState().setupAsSignIn();
        isSignInMode = true;
    }

    private void setupAsSignUp() {
        getViewState().setupAsSignUp();
        isSignInMode = false;
    }

    public void onBackPress() {
        if (isSignInMode) {
            getViewState().onBackPressed();
        } else {
            isSignInMode = true;
            getViewState().setupAsSignIn();
        }
    }

    public void signIn(String email, String password) {
        if (isValidDataForSignIn(email, password)) {
            FireBaseClient.getInstance().signIn(email, password, signInListener);
        } else {
            getViewState().showError("Please input valid email and password.");
        }
    }


    public void signUp(String email, String password, String repeatPassword) {
        if (isValidDataForRegistration(email, password, repeatPassword)) {
            FireBaseClient.getInstance().signUp(email, password, signUpListener);
        } else {
            getViewState().showError("Please input valid email, password can't be empty and have to be the same as repeat password.");
        }
    }

    private boolean isValidDataForSignIn(String email, String password) {
        boolean isValid = isEmailValid(email);
        if (isValid) {
            isValid = isValidPasswordLength(password);
        }
        return isValid;
    }

    private boolean isValidDataForRegistration(String email, String password, String repeatPassword) {
        boolean isValid = isEmailValid(email);
        if (isValid) {
            isValid = isValidPasswordLength(password);
        }
        if (isValid) {
            isValid = password.equals(repeatPassword);
        }
        return isValid;
    }

    private boolean isValidPasswordLength(String password) {
        return password.trim().length() > 0;
    }

    private boolean isEmailValid(String email) {
        return email != null && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private OnCompleteListener signUpListener = new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()) {
                FireBaseDataBaseClient.getInstance().saveUser(new User(task.getResult().getUser().getUid()));
                getViewState().onSignUpSuccess();
            } else {
                getViewState().showError("Internal server error.");
            }
        }
    };
    private OnCompleteListener signInListener = new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()) {
                UserManager.getInstance().setCurrentUserId(task.getResult().getUser().getUid());
                getViewState().onSignInSuccess(task.getResult().getUser().getUid());
            } else {
                getViewState().showError("Invalid user credentials for login.");
            }
        }
    };
}
