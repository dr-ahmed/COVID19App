package com.infectdistrack.model;

import android.os.AsyncTask;
import android.util.Log;

import com.infectdistrack.presenter.NewUserController;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import static com.infectdistrack.model.Constants.*;

public class NewUserAsyncTask extends AsyncTask<String, Integer, String> {

    private static final String TAG = "NewUserAsyncTask";
    private boolean isUserAdded = false;
    private NewUserController newUserControllerListener;
    StringBuilder result;

    public NewUserAsyncTask(NewUserController newUserControllerListener) {
        this.newUserControllerListener = newUserControllerListener;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            URL insertURL = new URL(SCRIPT_PATH + ADD_NEW_USER_SCRIPT_NAME);
            HttpURLConnection connection = (HttpURLConnection) insertURL.openConnection();
            connection.setRequestMethod(POST_METHOD);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);

            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);

            connection.connect();

            OutputStream outputStream = connection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, ENCODING));

            String post_data = URLEncoder.encode(USER_FULL_NAME_TAG, ENCODING) + "=" + URLEncoder.encode(params[0], ENCODING)
                    + "&" + URLEncoder.encode(USER_EMAIL_TAG, ENCODING) + "=" + URLEncoder.encode(params[1], ENCODING)
                    + "&" + URLEncoder.encode(USER_PASSWORD_TAG, ENCODING) + "=" + URLEncoder.encode(params[2], ENCODING)
                    + "&" + URLEncoder.encode(USER_CATEGORY_TAG, ENCODING) + "=" + URLEncoder.encode(params[3], ENCODING)
                    + "&" + URLEncoder.encode(USER_ASSOCIATE_ADMIN_TAG, ENCODING) + "=" + URLEncoder.encode(params[4], ENCODING)
                    + "&" + URLEncoder.encode(USER_WILAYA_TAG, ENCODING) + "=" + URLEncoder.encode(params[5], ENCODING)
                    + "&" + URLEncoder.encode(USER_MOUGHATAA_TAG, ENCODING) + "=" + URLEncoder.encode(params[6], ENCODING)
                    + "&" + URLEncoder.encode(USER_TYPE_TAG, ENCODING) + "=" + URLEncoder.encode(params[7], ENCODING)
                    + "&" + URLEncoder.encode(USER_ESTABLISHMENT_TYPE_TAG, ENCODING) + "=" + URLEncoder.encode(params[8], ENCODING)
                    + "&" + URLEncoder.encode(USER_ESTABLISHMENT_CATEGORY_TAG, ENCODING) + "=" + URLEncoder.encode(params[9], ENCODING)
                    + "&" + URLEncoder.encode(USER_CREATION_DATE_TAG, ENCODING) + "=" + URLEncoder.encode(params[10], ENCODING);

            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, CHAR_SET_NAME));
            result = new StringBuilder();
            String line = "";

            while ((line = bufferedReader.readLine()) != null)
                result.append(line);
            bufferedReader.close();
            inputStream.close();
            connection.disconnect();

            if (result.toString().equals(SUCCEESSFUL)) {
                isUserAdded = true;
                return "";
            }

            Log.e(TAG, "String result from doInBackground method : " + result.toString());
            return result.toString();
        } catch (Exception e) {
            Log.e(TAG, "doInBackground: " + Log.getStackTraceString(e));

            return e.getClass().getName();
            //return "Exception name : " + e.getClass().getName() + "\nException message : " + e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String exceptionInfo) {
        newUserControllerListener.onNewUserAdded(exceptionInfo, isUserAdded);
    }
}
