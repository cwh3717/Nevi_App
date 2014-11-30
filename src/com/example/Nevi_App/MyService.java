package com.example.Nevi_App;


import android.bluetooth.BluetoothAdapter;
import android.content.ContentValues;
import android.os.Bundle;
import com.bitnpulse.beacon.scan.BeaconScanManager;
import com.bitnpulse.beacon.scan.ListenerBeaconScan;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Administrator on 2014-12-01.
 */
public class MyService extends Service implements ListenerBeaconScan {

    private MyActivity myActivity = null;

    private static final String TAG = "BeaconService";

    private BeaconScanManager beaconScanManager = null;

    private static final int nRSSI = -90;
    private static final int nScanTime = 2000;
    private static final int nCheckTime = 6;
    private static final int INTENT_ENABLE_BT_REQ = 200;


    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate()");

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

        Toast.makeText(this, "Beacon Service가 중지되었습니다.", Toast.LENGTH_LONG).show();
        Log.d(TAG, "onDestroy()");
        if (beaconScanManager != null) {
            beaconScanManager.stop();
            beaconScanManager = null;
        }
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Toast.makeText(this, "Beacon Service가 시작되었습니다.", Toast.LENGTH_LONG).show();
        Log.d(TAG, "onStart()");

        while (true) {

            if (beaconScanManager != null) {
                if (!beaconScanManager.isScanning()) {
                    if (beaconScanManager.start()) {
                        Bundle savedInstanceState = null;
                        myActivity.onCreate(savedInstanceState);
                        break;
                    } else {
                        Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        myActivity.BluetoothCheck(enableIntent, INTENT_ENABLE_BT_REQ);
                    }
                } else {
                    beaconScanManager.start();
                }
            }
        }

        return START_STICKY;
        //return super.onStartCommand(intent, flags, startId);
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
