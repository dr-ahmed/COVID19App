package com.infectdistrack.model;

import android.os.AsyncTask;
import android.util.Log;

import com.infectdistrack.presenter.LoginController;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.*;
import java.util.concurrent.TimeoutException;

import static com.infectdistrack.model.Constants.*;
import static com.infectdistrack.model.Utilities.removeApostrophe;

public class LoginAsyncTask extends AsyncTask<String, Integer, String> {

    private static final String TAG = "LoginAsyncTask";
    private boolean userIsConfirmed = true, isFirstLogin;
    private LoginController loginControllerListener;
    private User user;
    StringBuilder result;

    public LoginAsyncTask(LoginController loginControllerListener) {
        this.loginControllerListener = loginControllerListener;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            URL insertURL = new URL(SCRIPT_PATH + LOGIN_SCRIPT_NAME);
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

            String post_data = URLEncoder.encode(USER_EMAIL_TAG, ENCODING) + "=" + URLEncoder.encode(params[0], ENCODING)
                    + "&" + URLEncoder.encode(USER_PASSWORD_TAG, ENCODING) + "=" + URLEncoder.encode(params[1], ENCODING);
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
                userIsConfirmed = false;
            else {
                Log.e(TAG, "resut : " + result.toString());
                JSONObject response = new JSONObject(result.toString());
                if (!response.isNull(JSON_HEADER_TAG)) {
                    JSONArray userData = response.getJSONArray(JSON_HEADER_TAG);
                    userIsConfirmed = true;

                    isFirstLogin = userData.getJSONObject(0).getBoolean(USER_FIRST_LOGIN_TAG);

                    Integer id = Integer.parseInt(userData.getJSONObject(0).getString(USER_ID_TAG));
                    String full_name = removeApostrophe(userData.getJSONObject(0).getString(USER_FULL_NAME_TAG)),
                            email = userData.getJSONObject(0).getString(USER_EMAIL_TAG),
                            password = userData.getJSONObject(0).getString(USER_PASSWORD_TAG),
                            category = userData.getJSONObject(0).getString(USER_CATEGORY_TAG),
                            associate_admin = userData.getJSONObject(0).getString(USER_ASSOCIATE_ADMIN_TAG),
                            wilaya = userData.getJSONObject(0).getString(USER_WILAYA_TAG),
                            moughataa = userData.getJSONObject(0).getString(USER_MOUGHATAA_TAG),
                            userType = userData.getJSONObject(0).getString(USER_TYPE_TAG),
                            establishmentType = removeApostrophe(userData.getJSONObject(0).getString(USER_ESTABLISHMENT_TYPE_TAG)),
                            establishmentCategory = removeApostrophe(userData.getJSONObject(0).getString(USER_ESTABLISHMENT_CATEGORY_TAG));

                    user = new User(id, full_name, email, password, category, Integer.parseInt(associate_admin), wilaya, moughataa, userType, establishmentType, establishmentCategory);
                } else {
                    Log.e(TAG, "String result from doInBackground method : " + result.toString());
                    return result.toString();
                }
            }
            return "";
        } catch (Exception e) {
            Log.e(TAG, "doInBackground: " + Log.getStackTraceString(e));
            return "Exception name : " + e.getClass().getName() + "\nException message : " + e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String exceptionInfo) {
        loginControllerListener.onLoginResponse(user, exceptionInfo, userIsConfirmed, isFirstLogin);
    }
}
