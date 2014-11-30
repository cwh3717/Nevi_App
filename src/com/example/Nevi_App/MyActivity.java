package com.example.Nevi_App;

import android.content.ContentValues;
import android.content.Intent;
import android.widget.Toast;
import com.bitnpulse.beacon.scan.BeaconScanManager;
import com.bitnpulse.beacon.scan.ListenerBeaconScan;

import android.app.Activity;
import android.os.Bundle;

import java.util.ArrayList;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    Intent intent = new Intent(this, MyService.class);
    startService(intent);


    }


    public void BluetoothCheck(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
    }


}
