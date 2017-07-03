package com.troyanskiievgen.acctroyanskii.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Relax on 27.06.2017.
 */

public class User {

    private String uid;
    private List<AccelerometerSessionModel> sessionsList = new ArrayList<>();

    public User(String uid) {
        this.uid = uid;
    }

    public User() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public List<AccelerometerSessionModel> getSessionsList() {
        return sessionsList != null ? sessionsList : new ArrayList<AccelerometerSessionModel>();
    }

    public void setSessionsList(List<AccelerometerSessionModel> sessionsList) {
        this.sessionsList = sessionsList;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", sessionsList=" + sessionsList +
                '}';
    }
}
