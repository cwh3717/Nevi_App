package com.example.Nevi_App;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import com.bitnpulse.beacon.scan.BeaconScanManager;
import com.bitnpulse.beacon.scan.ListenerBeaconScan;

import android.app.Activity;
import android.os.Bundle;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.PrivateKey;
import java.util.ArrayList;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //Intent intent = new Intent(this, MyService.class);
        //startService(intent);

        DownloadTask downloadTask = new DownloadTask();
        downloadTask.execute();

    }

    private JSONObject downloadJSONObject() throws IOException {

        JSONObject jsonObject = null;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            URL url = new URL("http://health.drvisions.com/smartparking/basic/parkinginfotoapp.php");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            if (conn != null) {

                conn.setConnectTimeout(2000);
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
                    Toast.makeText(this, "http not", Toast.LENGTH_LONG).show();
                }
                conn.disconnect();
            }
        } catch (Exception e) {
            Toast.makeText(this, "error", Toast.LENGTH_LONG).show();
        }

        String jsonString = stringBuilder.toString();

        try {

            JSONArray ja = new JSONArray(jsonString);

            for (int i = 0; i < ja.length(); i++) {
                jsonObject = ja.getJSONObject(i);

            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            Toast.makeText(this, "error", Toast.LENGTH_LONG).show();
        }

        //결과 출력
        return jsonObject;

    }

    private class DownloadTask extends AsyncTask<String, Integer, JSONObject>{
        JSONObject jsonObject = null;

        @Override
        protected JSONObject doInBackground(String... url) {
            try {
                jsonObject = downloadJSONObject();
            } catch (Exception e){
                Log.d("Background Task", e.toString());
            }

            return jsonObject;
        }

        @Override
        protected void onPostExcute(JSONObject jsonObject) {



            for(int i=0; i<jsonObject.length(); i++){


            }

        }


    }

    public void BluetoothCheck(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
    }


}
