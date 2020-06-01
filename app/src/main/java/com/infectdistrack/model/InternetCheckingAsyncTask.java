package com.infectdistrack.model;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.InetAddress;

public class InternetCheckingAsyncTask extends AsyncTask<String, Integer, String> {

    private static final String TAG = "InternetCheckingTask";

    @Override
    protected String doInBackground(String... params) {
        InetAddress address = null;
        try {
            address = InetAddress.getByName(params[0]);
        } catch (IOException e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }

        return address != null ? address.getHostAddress() : null;
    }
}