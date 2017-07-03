package com.troyanskiievgen.acctroyanskii.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.troyanskiievgen.acctroyanskii.R;
import com.troyanskiievgen.acctroyanskii.model.User;
import com.troyanskiievgen.acctroyanskii.network.FireBaseDataBaseClient;
import com.troyanskiievgen.acctroyanskii.presenter.LoginPresenter;
import com.troyanskiievgen.acctroyanskii.utils.Animator;
import com.troyanskiievgen.acctroyanskii.view.LoginActivityView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.troyanskiievgen.acctroyanskii.utils.Animator.ALPHA_INVISIBLE;
import static com.troyanskiievgen.acctroyanskii.utils.Animator.ALPHA_VISIBLE;


/**
 * Created by Relax on 27.06.2017.
 */

public class LoginActivity extends MvpAppCompatActivity implements LoginActivityView{

    public final static String USER_ID_KEY = "user_id_key";

    @BindView(R.id.login_title) TextView tvLoginTitle;
    @BindView(R.id.login_email) EditText etLogin;
    @BindView(R.id.login_repeat_password) EditText etRepeatPassword;
    @BindView(R.id.login_password) EditText etPassword;
    @BindView(R.id.login_signin_button) Button btnSignIn;
    @BindView(R.id.login_signup_button) Button btnSignUp;
    @BindView(R.id.login_mode_changer) Button btnModeChanger;

    @InjectPresenter
    LoginPresenter loginPresenter;

    private Animator animator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        animator = new Animator();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            loginPresenter.onBackPress();
        }
        return false;
    }

    @OnClick(R.id.login_signin_button)
    public void onSignIn() {
        loginPresenter.signIn(etLogin.getText().toString(), etPassword.getText().toString());
    }

    @OnClick(R.id.login_signup_button)
    public void onSignUp() {
        loginPresenter.signUp(etLogin.getText().toString(), etPassword.getText().toString(),
                etRepeatPassword.getText().toString());
    }

    @OnClick(R.id.login_mode_changer)
    public void onChangeMode() {
        loginPresenter.changeMode();
    }


    @Override
    public void onSignInSuccess(String userId) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(USER_ID_KEY, userId);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onSignUpSuccess() {
        Toast.makeText(this, R.string.login_signup_successfully, Toast.LENGTH_SHORT).show();
        onSignIn();
    }

    @Override
    public void setupAsSignIn() {
        btnModeChanger.setText(getResources().getString(R.string.login_go_to_sign_up));
        tvLoginTitle.setText(getResources().getString(R.string.login_sign_in));
        etRepeatPassword.setVisibility(View.GONE);
        etRepeatPassword.setAlpha(ALPHA_INVISIBLE);
        btnSignUp.setAlpha(ALPHA_INVISIBLE);
        btnSignUp.setVisibility(View.GONE);
        btnSignIn.setVisibility(View.VISIBLE);
        animator.animateVisibility(ALPHA_VISIBLE, btnSignIn, 1000);
    }

    @Override
    public void setupAsSignUp() {
        btnModeChanger.setText(getResources().getString(R.string.login_go_to_sign_in));
        tvLoginTitle.setText(getResources().getString(R.string.login_sign_up));
        btnSignIn.setAlpha(ALPHA_INVISIBLE);
        btnSignIn.setVisibility(View.GONE);
        etRepeatPassword.setVisibility(View.VISIBLE);
        animator.animateVisibility(ALPHA_VISIBLE, etRepeatPassword, 1000);
        btnSignUp.setVisibility(View.VISIBLE);
        animator.animateVisibility(ALPHA_VISIBLE, btnSignUp, 1000);
    }


    @Override
    public void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}
