package com.example.shishir.blood;

import android.app.Application;
import android.content.Intent;

import com.example.shishir.blood.Activity.NotificationActivity;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Shishir on 8/10/2017.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        OneSignal.startInit(this).setNotificationOpenedHandler(new OneSignal.NotificationOpenedHandler() {
            @Override
            public void notificationOpened(OSNotificationOpenResult result) {
                JSONObject data=result.notification.payload.additionalData;

                Intent intent=new Intent(getApplicationContext(),NotificationActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                try {
                    intent.putExtra("dataM",data.getString("H"));
                }catch (JSONException e){
                    e.printStackTrace();
                }startActivity(intent);
            }
        }).autoPromptLocation(true).init();

    }
}
