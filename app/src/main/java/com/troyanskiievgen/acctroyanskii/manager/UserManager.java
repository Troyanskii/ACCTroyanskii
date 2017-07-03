package com.troyanskiievgen.acctroyanskii.manager;

import com.troyanskiievgen.acctroyanskii.model.AccelerometerSessionModel;
import com.troyanskiievgen.acctroyanskii.model.User;
import com.troyanskiievgen.acctroyanskii.network.FireBaseDataBaseClient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Relax on 01.07.2017.
 */

public class UserManager {

    private static UserManager instance;
    private User currentUser;
    private String currentUserId;

    private UserManager() {
    }

    public static UserManager getInstance() {
        if(instance == null) {
           instance = new UserManager();
        }
        return instance;
    }

    public String getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(String currentUserId) {
        this.currentUserId = currentUserId;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;

        // TODO: 02.07.2017 fix data obtain logic to prevent appearing empty sessions and remove this hack
        filterInvalidData();
    }

    public void uploadUser(UploadUserCallback callback) {
        FireBaseDataBaseClient.getInstance().uploadUser(currentUserId, callback);
    }

    public interface UploadUserCallback {

        void onUserUploaded();

    }

    private void filterInvalidData() {
        List<AccelerometerSessionModel> validSessionsList = new ArrayList<>();
        for (AccelerometerSessionModel session : currentUser.getSessionsList()) {
            if (session != null && session.getDate() != null && session.getAccelerometerDataList() != null && session.getAccelerometerDataList().size() > 0) {
                validSessionsList.add(session);
            }
        }
        currentUser.setSessionsList(validSessionsList);
    }
}
