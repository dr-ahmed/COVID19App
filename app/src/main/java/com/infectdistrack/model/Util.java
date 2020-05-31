package com.infectdistrack.model;

import android.util.Log;

import java.util.concurrent.TimeUnit;

public class Util {
    private static final String TAG = "Util";

    static String POST_METHOD = "POST",
            SCRIPT_PATH = "http://192.168.1.100/infectdisdb/",
            LOGIN_SCRIPT_NAME = "login.php",
            ENCODING = "UTF-8",
            USER_NAME_TAG = "name",
            USER_EMAIL_TAG = "email",
            USER_PASSWORD_TAG = "password",
            USER_CATEGORY_TAG = "category",
            USER_ASSOCIATE_ADMIN_TAG = "associate_admin",
            USER_LOCATION_TAG = "location",
            USER_ESTABLISHMENT_TAG = "establishment",
            CHAR_SET_NAME = "iso-8859-1",
            JSON_HEADER_TAG = "User";

    static final int READ_TIMEOUT = 15000,
            CONNECTION_TIMEOUT = 15000;

    public static boolean isInternetAvailable() {
        String netAddress;
        try {
            netAddress = new InternetCheckingTask().execute("www.google.com").get(100, TimeUnit.MILLISECONDS);
            return !netAddress.equals("");
        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
            return false;
        }
    }
}
