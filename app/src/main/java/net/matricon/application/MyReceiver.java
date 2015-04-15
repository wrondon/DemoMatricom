package net.matricon.application;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "BroadcastReceiver";
    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        Log.e(TAG, "on Receive BroadcastReceiver for everything !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            Log.e(TAG, "on Receive BroadcastReceiver BOOT COMPLETED !!!!!!!!!");
            Toast.makeText(context, "on Receive BroadcastReceiver BOOT COMPLETED !!!!!!!!!",
                    Toast.LENGTH_LONG).show();
        } else if(intent.getAction().equals("net.matricom.application.MATRICOM.EMPLOYMENT")) {
            Log.e(TAG, "on Receive BroadcastReceiver MATRICOM EMPLOYMENT !!!!!!!");
            Toast.makeText(context, "on Receive BroadcastReceiver MATRICOM EMPLOYMENT",
                    Toast.LENGTH_LONG).show();
        }


    }
}
