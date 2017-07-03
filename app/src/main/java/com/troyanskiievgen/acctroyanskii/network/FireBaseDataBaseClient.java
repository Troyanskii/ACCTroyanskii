package com.troyanskiievgen.acctroyanskii.network;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.troyanskiievgen.acctroyanskii.manager.UserManager;
import com.troyanskiievgen.acctroyanskii.model.AccelerometerSessionModel;
import com.troyanskiievgen.acctroyanskii.model.User;

import java.util.List;

/**
 * Created by Relax on 01.07.2017.
 */

public class FireBaseDataBaseClient {

    private FirebaseDatabase database;
    private DatabaseReference myDBReference;

    private static FireBaseDataBaseClient fireBaseDBClient;

    private FireBaseDataBaseClient() {
        database = FirebaseDatabase.getInstance();
        myDBReference = database.getReference();
    }

    public static FireBaseDataBaseClient getInstance() {
        if (fireBaseDBClient == null) {
            fireBaseDBClient = new FireBaseDataBaseClient();
        }
        return fireBaseDBClient;
    }

    public void saveUser(User user) {
        myDBReference.child("users/" + user.getUid()).setValue(user);
    }

    public void updateUserSessions(List<AccelerometerSessionModel> sessionsList, String userId) {
        myDBReference.child("users/" + userId + "/sessionsList").setValue(sessionsList);
    }

    public void uploadUser(String id, final UserManager.UploadUserCallback callback) {
        DatabaseReference userReference = myDBReference.child("/users/"+id);
        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserManager.getInstance().setCurrentUser(dataSnapshot.getValue(User.class));
                Log.d("DEBUG", "User was obrained: User = " + UserManager.getInstance().getCurrentUser().toString());
                callback.onUserUploaded();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
