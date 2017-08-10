package com.example.shishir.blood;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;

/**
 * Created by Shishir on 8/11/2017.
 */

public class Network {

    public static boolean isNetAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null) {

            if (networkInfo.isAvailable() && networkInfo.isConnected()) {
                return true;
            }
        }
        return false;
    }

    public static void showInternetAlertDialog(Context context) {
        new AlertDialog.Builder(context).setTitle("Alert !")
                .setIcon(R.drawable.warning_icon)
                .setMessage("No Internet Connection !")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }
}
