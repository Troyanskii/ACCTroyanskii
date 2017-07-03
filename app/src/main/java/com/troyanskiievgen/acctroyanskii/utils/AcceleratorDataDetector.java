package com.troyanskiievgen.acctroyanskii.utils;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

/**
 * Created by Relax on 30.06.2017.
 */

public class AcceleratorDataDetector implements SensorEventListener {


    private AcceleratorListener mListener;
    private long timeLastIteration;
    private long timeDelay;
    private long finishTime = Long.MAX_VALUE;
    private long startDelay;

    public AcceleratorDataDetector(long timeDelay, long workDuration, long startDelay) {
        this.timeDelay = timeDelay;
        if (workDuration > 0) {
            this.finishTime = workDuration + System.currentTimeMillis();
        }
        this.startDelay = startDelay;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (mListener != null) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            final long now = System.currentTimeMillis();
            if (timeDelay + timeLastIteration < now) {
                timeLastIteration = now;
                mListener.onCoordinateObtained(x, y, z, now, timeLastIteration > finishTime);
            }
        }
    }


    public void setOnObtainDataListener(AcceleratorListener listener) {
        this.mListener = listener;
    }

    public interface AcceleratorListener {
        void onCoordinateObtained(float x, float y, float z, long obtainedTime, boolean isExpired);
    }
}
