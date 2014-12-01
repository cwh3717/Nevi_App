package com.example.Nevi_App;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2014-12-01.
 */
public class MyThread extends Thread {

    public void run() {


        StringBuilder sb = new StringBuilder();

        try {
            URL url = new URL("http://192.168.52.77/test.php");
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
                        sb.append(line + "\n");
                    }
                    br.close();

                } else {
                    tv.setText("http_not");
                }
                conn.disconnect();
            }
        } catch (Exception e) {
            tv.setText(e.toString());
        }

        String jsonString = sb.toString();

        try {

            JSONArray ja = new JSONArray(jsonString);

            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo = ja.getJSONObject(i);

            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            tv.setText(e.toString());
        }

        //결과 출력
        return jo;

    }

    }

}
