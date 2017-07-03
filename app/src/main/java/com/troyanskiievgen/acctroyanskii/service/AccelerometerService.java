package com.troyanskiievgen.acctroyanskii.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.troyanskiievgen.acctroyanskii.manager.UserManager;
import com.troyanskiievgen.acctroyanskii.model.AccelerometerData;
import com.troyanskiievgen.acctroyanskii.model.AccelerometerSessionModel;
import com.troyanskiievgen.acctroyanskii.network.FireBaseDataBaseClient;
import com.troyanskiievgen.acctroyanskii.utils.AcceleratorDataDetector;

import java.util.ArrayList;
import java.util.List;

import static com.troyanskiievgen.acctroyanskii.ui.activity.LoginActivity.USER_ID_KEY;
import static java.lang.Thread.sleep;

/**
 * Created by Relax on 30.06.2017.
 */

public class AccelerometerService extends Service {

    public final static String WORk_DURATION = "work_duration";
    public final static String START_DATE = "start_delay";
    public final static String INTERVAL = "interval";

    private long accelerometerWorkDuration;
    private long accelerometerWorkDelay;
    private long accelerometerStartDate;

    private String currentUserId;
    private long startSession;
    private List<AccelerometerData> accelerometerDataList;

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private AcceleratorDataDetector acceleratorDataDetector;
    private Thread acceleratorThread;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        currentUserId = intent.getExtras().getString(USER_ID_KEY);
        accelerometerWorkDuration = intent.getExtras().getLong(WORk_DURATION);
        accelerometerWorkDelay = intent.getExtras().getLong(INTERVAL);
        accelerometerStartDate = intent.getExtras().getLong(START_DATE);
        setupAccelerator();
        return super.onStartCommand(intent, flags, startId);
    }

    private void initSessionData() {
        startSession = System.currentTimeMillis();
        accelerometerDataList = new ArrayList<>();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(acceleratorDataDetector, accelerometer);
        saveResult();
    }

    private void setupAccelerator() {
        // TODO: 01.07.2017 implement in new thread
        acceleratorThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if(accelerometerStartDate > 0) {
                        sleep(accelerometerStartDate);
                    }
                    initSessionData();
                    acceleratorDataDetector = new AcceleratorDataDetector(accelerometerWorkDelay, accelerometerWorkDuration, accelerometerStartDate);
                    sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
                    accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                    acceleratorDataDetector.setOnObtainDataListener(new AcceleratorDataDetector.AcceleratorListener() {
                        @Override
                        public void onCoordinateObtained(float x, float y, float z, long time, boolean isExpired) {
                            AccelerometerData data = new AccelerometerData(x, y, z, time);
                            accelerometerDataList.add(data);
                            if (isExpired) {
                                stopSelf();
                            }
                            Log.d("DEBUG", data.toString());
                        }
                    });
                    sensorManager.registerListener(acceleratorDataDetector, accelerometer, SensorManager.SENSOR_DELAY_UI);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        acceleratorThread.start();
    }

    private void saveResult() {
        AccelerometerSessionModel session = new AccelerometerSessionModel();
        session.setDate(startSession);
        session.setAccelerometerDataList(accelerometerDataList);
        UserManager.getInstance().getCurrentUser().getSessionsList().add(session);
        FireBaseDataBaseClient.getInstance().updateUserSessions(UserManager.getInstance().getCurrentUser().getSessionsList(),currentUserId);
    }
}
