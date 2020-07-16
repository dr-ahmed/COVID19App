package com.infectdistrack.model;

import android.os.AsyncTask;
import android.util.Log;

import com.infectdistrack.presenter.LoginController;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import static com.infectdistrack.model.Constants.CHAR_SET_NAME;
import static com.infectdistrack.model.Constants.CHECKING_USER_SESSION_DATA_VALIDITY_SCRIPT_NAME;
import static com.infectdistrack.model.Constants.CONNECTION_TIMEOUT;
import static com.infectdistrack.model.Constants.DOES_USER_EXIST_TAG;
import static com.infectdistrack.model.Constants.ENCODING;
import static com.infectdistrack.model.Constants.POST_METHOD;
import static com.infectdistrack.model.Constants.READ_TIMEOUT;
import static com.infectdistrack.model.Constants.SCRIPT_PATH;
import static com.infectdistrack.model.Constants.USER_ASSOCIATE_ADMIN_TAG;
import static com.infectdistrack.model.Constants.USER_CATEGORY_TAG;
import static com.infectdistrack.model.Constants.USER_EMAIL_TAG;
import static com.infectdistrack.model.Constants.USER_ESTABLISHMENT_CATEGORY_TAG;
import static com.infectdistrack.model.Constants.USER_ESTABLISHMENT_TYPE_TAG;
import static com.infectdistrack.model.Constants.USER_FULL_NAME_TAG;
import static com.infectdistrack.model.Constants.USER_MOUGHATAA_TAG;
import static com.infectdistrack.model.Constants.USER_PASSWORD_TAG;
import static com.infectdistrack.model.Constants.USER_TYPE_TAG;
import static com.infectdistrack.model.Constants.USER_WILAYA_TAG;
import static com.infectdistrack.model.Constants.YES;

public class CheckUserSessionDataValidityAsyncTask extends AsyncTask<String, Integer, String> {

    private static final String TAG = "CheckSessDataValidity";
    private boolean sessionDataIsStillValid = false;
    private LoginController loginControllerListener;
    StringBuilder result;

    public CheckUserSessionDataValidityAsyncTask(LoginController loginControllerListener) {
        this.loginControllerListener = loginControllerListener;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            URL insertURL = new URL(SCRIPT_PATH + CHECKING_USER_SESSION_DATA_VALIDITY_SCRIPT_NAME);
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
                    + "&" + URLEncoder.encode(USER_ESTABLISHMENT_CATEGORY_TAG, ENCODING) + "=" + URLEncoder.encode(params[9], ENCODING);

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

            if (result.toString().equals("[]"))
                sessionDataIsStillValid = false;
            else {
                JSONObject response = new JSONObject(result.toString());

                if (response.has(DOES_USER_EXIST_TAG))
                    sessionDataIsStillValid = response.getString(DOES_USER_EXIST_TAG).equals(YES);
                else {
                    Log.e(TAG, "String result from doInBackground method : " + result.toString());
                    return result.toString();
                }
            }
            return "";
        } catch (Exception e) {
            Log.e(TAG, "doInBackground: " + Log.getStackTraceString(e));
            return e.getClass().getName();
            //return "Exception name : " + e.getClass().getName() + "\nException message : " + e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String exceptionInfo) {
        loginControllerListener.whenUserSessionDataIsSavedInSharedPrefs(exceptionInfo, sessionDataIsStillValid);
    }
}
