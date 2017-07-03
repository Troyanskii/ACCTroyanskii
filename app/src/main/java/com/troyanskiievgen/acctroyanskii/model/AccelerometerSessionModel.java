package com.troyanskiievgen.acctroyanskii.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Relax on 01.07.2017.
 */

public class AccelerometerSessionModel implements Serializable{

    private Long date;
    private List<AccelerometerData> accelerometerDataList;

    public AccelerometerSessionModel() {
    }

    public Long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public List<AccelerometerData> getAccelerometerDataList() {
        return accelerometerDataList;
    }

    public void setAccelerometerDataList(List<AccelerometerData> accelerometerDataList) {
        this.accelerometerDataList = accelerometerDataList;
    }

    @Override
    public String toString() {
        return "AccelerometerSessionModel{" +
                "date=" + date +
                ", accelerometerDataList=" + accelerometerDataList +
                '}';
    }
}
