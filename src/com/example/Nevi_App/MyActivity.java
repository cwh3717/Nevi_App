package com.example.Nevi_App;


import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.*;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;

import com.bitnpulse.beacon.scan.BeaconScanManager;
import com.bitnpulse.beacon.scan.ListenerBeaconScan;

import android.app.Activity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */

    BroadcastReceiver receiver;
    Intent intentMyService;

    MyService myService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        MyThread myThread = new MyThread();
        Thread t = new Thread(myThread);
        t.setDaemon(true);
        t.start();


        Log.d("MainActivity", "service start!!!!!!");

        intentMyService = new Intent(this, MyService.class);

        try
        {

            IntentFilter mainFilter = new IntentFilter();
            registerReceiver(receiver, mainFilter);
            startService(intentMyService);

        } catch (Exception e) {

            Log.d("MainActivity", e.getMessage()+"");
            e.printStackTrace();
        }

    }

    class MyThread implements Runnable {


        GridLayout B1 = (GridLayout) findViewById(R.id.B1);
        GridLayout B2 = (GridLayout) findViewById(R.id.B2);
        GridLayout B3 = (GridLayout) findViewById(R.id.B3);
        GridLayout B4 = (GridLayout) findViewById(R.id.B4);
        GridLayout B5 = (GridLayout) findViewById(R.id.B5);
        GridLayout B6 = (GridLayout) findViewById(R.id.B6);
        GridLayout B7 = (GridLayout) findViewById(R.id.B7);
        GridLayout B8 = (GridLayout) findViewById(R.id.B8);
        GridLayout B9 = (GridLayout) findViewById(R.id.B9);
        GridLayout B10 = (GridLayout) findViewById(R.id.B10);


        @Override
        public void run() {

            while (true) {
                StringBuilder stringBuilder = new StringBuilder();

                try {
                    URL url = new URL("http://health.drvisions.com/smartparking/basic/parkinginfotoapp.php");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    if (conn != null) {

                        conn.setConnectTimeout(1000);
                        conn.setUseCaches(false);

                        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                            BufferedReader br = new BufferedReader(
                                    new InputStreamReader(conn.getInputStream()));

                            while (true) {
                                String line = br.readLine();
                                if (line == null)
                                    break;
                                stringBuilder.append(line + "\n");
                            }
                            br.close();

                        } else {
                            Log.d("Http_Error", "MyThread");
                        }
                        conn.disconnect();
                    }
                } catch (Exception e) {
                    Log.d("Error", e.toString());
                }

                String jsonString = stringBuilder.toString();
                String[] array = new String[10];

                try {

                    JSONArray jsonArray = new JSONArray(jsonString);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        array[i] = jsonObject.getString("info");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (!array[0].equals("0")) {
                    B1_handler.sendEmptyMessage(0);
                } else B1_handler.sendEmptyMessage(1);

                if (!array[1].equals("0")) {
                    B2_handler.sendEmptyMessage(0);
                } else B2_handler.sendEmptyMessage(1);

                if (!array[2].equals("0")) {
                    B3_handler.sendEmptyMessage(0);
                } else B3_handler.sendEmptyMessage(1);

                if (!array[3].equals("0")) {
                    B4_handler.sendEmptyMessage(0);
                } else B4_handler.sendEmptyMessage(1);

                if (!array[4].equals("0")) {
                    B5_handler.sendEmptyMessage(0);
                } else B5_handler.sendEmptyMessage(1);

                if (!array[5].equals("0")) {
                    B6_handler.sendEmptyMessage(0);
                } else B6_handler.sendEmptyMessage(1);

                if (!array[6].equals("0")) {
                    B7_handler.sendEmptyMessage(0);
                } else B7_handler.sendEmptyMessage(1);

                if (!array[7].equals("0")) {
                    B8_handler.sendEmptyMessage(0);
                } else B8_handler.sendEmptyMessage(1);

                if (!array[8].equals("0")) {
                    B9_handler.sendEmptyMessage(0);
                } else B9_handler.sendEmptyMessage(1);

                if (!array[9].equals("0")) {
                    B10_handler.sendEmptyMessage(0);
                } else B10_handler.sendEmptyMessage(1);

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }

        Handler B1_handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0) {
                    B1.setBackgroundColor(Color.RED);
                } else B1.setBackgroundColor(0);
            }
        };

        Handler B2_handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0) {
                    B2.setBackgroundColor(Color.RED);
                } else B2.setBackgroundColor(0);
            }
        };

        Handler B3_handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0) {
                    B3.setBackgroundColor(Color.RED);
                } else B3.setBackgroundColor(0);
            }
        };

        Handler B4_handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0) {
                    B4.setBackgroundColor(Color.RED);
                } else B4.setBackgroundColor(0);
            }
        };

        Handler B5_handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0) {
                    B5.setBackgroundColor(Color.RED);
                } else B5.setBackgroundColor(0);
            }
        };

        Handler B6_handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0) {
                    B6.setBackgroundColor(Color.RED);
                } else B6.setBackgroundColor(0);
            }
        };

        Handler B7_handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0) {
                    B7.setBackgroundColor(Color.RED);
                } else B7.setBackgroundColor(0);
            }
        };

        Handler B8_handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0) {
                    B8.setBackgroundColor(Color.RED);
                } else B8.setBackgroundColor(0);
            }
        };

        Handler B9_handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0) {
                    B9.setBackgroundColor(Color.RED);
                } else B9.setBackgroundColor(0);
            }
        };

        Handler B10_handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0) {
                    B10.setBackgroundColor(Color.RED);
                } else B10.setBackgroundColor(0);
            }
        };
    }

}
