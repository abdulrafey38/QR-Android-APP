package com.example.qr;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MySingleton {
    private static MySingleton mInstance;
    private RequestQueue requestQueue;
    private static Context mContext;
    private static final String SHARED_PREF_NAME="mysharedpref12";


    private MySingleton(Context context)
    {
        mContext = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized MySingleton getInstance(Context context)
    {

        if(mInstance == null)
        {
            mInstance = new MySingleton(context);
        }
        return mInstance;
    }


    public boolean logout()
    {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }


    public RequestQueue getRequestQueue() {
        if (requestQueue == null)
        {
            requestQueue = Volley.newRequestQueue(mContext.getApplicationContext());

        }
        return requestQueue;
    }

    public <T>void addTorequestque(Request<T> request)
    {
        requestQueue.add(request);
    }
}

