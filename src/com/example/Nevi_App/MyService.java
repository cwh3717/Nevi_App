package com.example.Nevi_App;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;


import android.os.SystemClock;
import com.bitnpulse.beacon.scan.BeaconScanManager;
import com.bitnpulse.beacon.scan.ListenerBeaconScan;

import android.os.Handler;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * Created by Administrator on 2014-12-01.
 */
public class MyService extends Service implements Runnable, ListenerBeaconScan {


    private static final String TAG = "BeaconService";

    private BeaconScanManager beaconScanManager = null;

    private static final int nRSSI = -90;
    private static final int nScanTime = 2000;
    private static final int nCheckTime = 6;

    private Handler mHandler;
    private boolean mIsRunnig;
    private int mStartId = 0;

    private static final int DELAY_TIME = 10 * 1000;

    private static final int REBOOT_DELAY_TIMER = 10 * 1000;


    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind()");
        return null;
    }

    @Override
    public void onCreate() {

        Log.d(TAG, "onCreate()");
        unregisterRestartAlarm();

        super.onCreate();
        mIsRunnig = false;

        try {
            beaconScanManager = new BeaconScanManager(getApplicationContext(), this, nRSSI, nScanTime, nCheckTime);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            onDestroy();
        }

    }

    @Override
    public void onDestroy() {

        Log.d(TAG, "onDestroy()");
        registerRestartAlarm();

        super.onDestroy();

        mIsRunnig = false;
    }

    @Override
    public void onStart(Intent intent, int startId) {


        Log.d(TAG, "onStart()");
        super.onStart(intent, startId);

        mStartId = startId;

        mHandler = new Handler();
        mHandler.postDelayed(this, DELAY_TIME);
        mIsRunnig = true;

    }


    @Override
    public void run() {
        Log.e(TAG, "run()");

        if(!mIsRunnig){
            Log.d(TAG, "run(), mIsRunning is false");
            Log.d(TAG, "run(), alarm service end");
            return;
        } else {
            Log.d(TAG, "run(), mIsRunning is true");
            Log.d(TAG, "run(), alarm repeat after one minutes");

            function();

            mHandler.postDelayed(this, DELAY_TIME);
            mIsRunnig = true;
        }
    }

    private void function() {
        Log.d(TAG, "function()");

    }

    private void registerRestartAlarm() {
        Log.d(TAG, "registerRestartAlarm()");

        Intent intent = new Intent(MyService.this, RestartService.class);
        intent.setAction(RestartService.ACTION_RESTART_MYSERVICE);
        PendingIntent sender = PendingIntent.getBroadcast(MyService.this, 0, intent, 0);

        long firstTime = SystemClock.elapsedRealtime();
        firstTime += REBOOT_DELAY_TIMER;

        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, firstTime, REBOOT_DELAY_TIMER, sender);

    }

    private void unregisterRestartAlarm(){

        Log.d(TAG, "unregisterRestartAlarm()");

        Intent intent = new Intent(MyService.this, RestartService.class);
        intent.setAction(RestartService.ACTION_RESTART_MYSERVICE);
        PendingIntent sender = PendingIntent.getBroadcast(MyService.this, 0, intent, 0);

        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        am.cancel(sender);
    }

    @Override
    public boolean onBeaconScanned(ArrayList<ContentValues> mResultArray) {
        // TODO Auto-generated method stub

        return false;
    }

    @Override
    public void onBeaconScanError(Exception e) {
        // TODO Auto-generated method stub
        e.printStackTrace();
        Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        beaconScanManager.stop();
    }

}
