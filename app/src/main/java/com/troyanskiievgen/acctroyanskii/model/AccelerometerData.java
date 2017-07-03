package com.troyanskiievgen.acctroyanskii.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Relax on 27.06.2017.
 */

public class AccelerometerData implements Serializable{

    private Long createdTime;
    private Float x;
    private Float y;
    private Float z;

    public AccelerometerData(float x, float y, float z, long createdTime) {
        this.createdTime = createdTime;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public AccelerometerData() {
    }

    public Long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Long createdTime) {
        this.createdTime = createdTime;
    }

    public Float getX() {
        return x;
    }

    public void setX(Float x) {
        this.x = x;
    }

    public Float getY() {
        return y;
    }

    public void setY(Float y) {
        this.y = y;
    }

    public Float getZ() {
        return z;
    }

    public void setZ(Float z) {
        this.z = z;
    }

    @Override
    public String toString() {
        return "AccelerometerData{" +
                "createdTime=" + createdTime +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
