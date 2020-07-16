package com.infectdistrack.model;

import android.os.AsyncTask;
import android.util.Log;

import com.infectdistrack.presenter.LoginController;
import com.infectdistrack.presenter.NewUserController;
import com.infectdistrack.view.DialogFragmentToResetPasswordDuringFirstLogin;

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
import static com.infectdistrack.model.Constants.CHECKING_DUPLICATE_USER_SCRIPT_NAME;
import static com.infectdistrack.model.Constants.CONNECTION_TIMEOUT;
import static com.infectdistrack.model.Constants.ENCODING;
import static com.infectdistrack.model.Constants.JSON_HEADER_TAG;
import static com.infectdistrack.model.Constants.POST_METHOD;
import static com.infectdistrack.model.Constants.READ_TIMEOUT;
import static com.infectdistrack.model.Constants.RESET_PASSWORD_DURING_FIRST_LOGIN_SCRIPT_NAME;
import static com.infectdistrack.model.Constants.SCRIPT_PATH;
import static com.infectdistrack.model.Constants.SUCCEESSFUL;
import static com.infectdistrack.model.Constants.USER_EMAIL_TAG;
import static com.infectdistrack.model.Constants.USER_ID_TAG;
import static com.infectdistrack.model.Constants.USER_PASSWORD_TAG;

public class ResetPasswordDuringFirstLoginAsyncTask extends AsyncTask<String, Integer, String> {

    private static final String TAG = "ResetPasswordFirstLogin";
    private boolean isCommited = false;
    private DialogFragmentToResetPasswordDuringFirstLogin dialogFragmentToResetPasswordDuringFirstLogin;
    StringBuilder result;

    public ResetPasswordDuringFirstLoginAsyncTask(DialogFragmentToResetPasswordDuringFirstLogin dialogFragmentToResetPasswordDuringFirstLogin) {
        this.dialogFragmentToResetPasswordDuringFirstLogin = dialogFragmentToResetPasswordDuringFirstLogin;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            URL insertURL = new URL(SCRIPT_PATH + RESET_PASSWORD_DURING_FIRST_LOGIN_SCRIPT_NAME);
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

            String post_data = URLEncoder.encode(USER_ID_TAG, ENCODING) + "=" + URLEncoder.encode(params[0], ENCODING)
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

            if (result.toString().equals(SUCCEESSFUL)) {
                isCommited = true;
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
        dialogFragmentToResetPasswordDuringFirstLogin.onResetPassword(exceptionInfo, isCommited);
    }
}
