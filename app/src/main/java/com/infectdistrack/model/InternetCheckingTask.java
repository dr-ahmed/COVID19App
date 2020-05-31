package com.infectdistrack.model;

import android.os.AsyncTask;
import android.util.Log;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class InternetCheckingTask extends AsyncTask<String, Integer, String> {

    private static final String TAG = "InternetCheckingTask";

    @Override
    protected String doInBackground(String... params) {
        InetAddress address = null;
        try {
            address = InetAddress.getByName(params[0]);
        } catch (UnknownHostException e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }

        return address != null ? address.getHostAddress() : null;
    }
}