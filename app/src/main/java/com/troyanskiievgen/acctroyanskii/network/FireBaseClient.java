package com.troyanskiievgen.acctroyanskii.network;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.troyanskiievgen.acctroyanskii.ui.activity.LoginActivity;

/**
 * Created by Relax on 28.06.2017.
 */

public class FireBaseClient {

    private FirebaseAuth mAuth;

    private static FireBaseClient fireBaseClient;

    private FireBaseClient() {
        mAuth = FirebaseAuth.getInstance();
    }

    public static FireBaseClient getInstance() {
        if (fireBaseClient == null) {
            fireBaseClient = new FireBaseClient();
        }
        return fireBaseClient;
    }

    public void signIn(String email, String password, OnCompleteListener<AuthResult> listener) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(listener);
    }

    public void signUp(String email, String password, OnCompleteListener<AuthResult> listener) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(listener);
    }
}
