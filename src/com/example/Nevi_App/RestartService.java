package com.example.Nevi_App;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by wonheumchoi on 14. 12. 2..
 */


public class RestartService extends BroadcastReceiver {

    public static final String ACTION_RESTART_MYSERVICE
            = "ACTION.Restart.PersistentService";


    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("MYSERVICE", "RestartService called!!!!!!!!!!!!!!!!!!!!!!!");


        /* 서비스 죽일때 알람으로 다시 서비스 등록 */
        if (intent.getAction().equals(ACTION_RESTART_MYSERVICE)) {

            Log.d("MYSERVICE", "Service dead, but resurrection");

            Intent i = new Intent(context, MyService.class);
            context.startService(i);
        }

        /* 폰 재부팅할때 서비스 등록 */
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {

            Log.d("MYSERVICE", "ACTION_BOOT_COMPLETED");

            Intent i = new Intent(context, MyService.class);
            context.startService(i);
        }
    }
}
