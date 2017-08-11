package com.example.shishir.blood.ExtraClass;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Shishir on 8/11/2017.
 */

public class MySingleton {

    private static Context context;
    private static RequestQueue requestQueue;
    private static MySingleton mInstance;

    private MySingleton(Context context) {
        this.context = context;
        requestQueue = getRequestQueue();
    }

    private RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public static synchronized MySingleton getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new MySingleton(context);
        }
        return mInstance;
    }

    public static <T> void addToRequestQueue(Request<T> request) {
        requestQueue.add(request);
    }
}
