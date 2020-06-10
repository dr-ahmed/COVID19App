package com.infectdistrack.model;

import android.os.AsyncTask;
import android.util.Log;

import com.infectdistrack.presenter.Covid19NewFormController;

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

public class Covid19NewFormAsyncTask extends AsyncTask<String, Integer, String> {

    private static final String TAG = "NewFormAsyncTask";
    private boolean isFormAdded = false;
    private Covid19NewFormController covid19NewFormController;
    StringBuilder result;

    public Covid19NewFormAsyncTask(Covid19NewFormController covid19NewFormController) {
        this.covid19NewFormController = covid19NewFormController;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            URL insertURL = new URL(SCRIPT_PATH + ADD_NEW_FORM_SCRIPT_NAME);
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

            String post_data = URLEncoder.encode(PARENT_USER_ID, ENCODING) + "=" + URLEncoder.encode(params[0], ENCODING)
                    + "&" + URLEncoder.encode(PATIENT_NAME, ENCODING) + "=" + URLEncoder.encode(params[1], ENCODING)
                    + "&" + URLEncoder.encode(PATIENT_PHONE_NUMBER, ENCODING) + "=" + URLEncoder.encode(params[2], ENCODING)
                    + "&" + URLEncoder.encode(PATIENT_GENDER, ENCODING) + "=" + URLEncoder.encode(params[3], ENCODING)
                    + "&" + URLEncoder.encode(PATIENT_AGE, ENCODING) + "=" + URLEncoder.encode(params[4], ENCODING)
                    + "&" + URLEncoder.encode(PATIENT_WILAYA, ENCODING) + "=" + URLEncoder.encode(params[5], ENCODING)
                    + "&" + URLEncoder.encode(PATIENT_SUSPECTED_CASES, ENCODING) + "=" + URLEncoder.encode(params[6], ENCODING)
                    + "&" + URLEncoder.encode(PATIENT_SYMPTOMS, ENCODING) + "=" + URLEncoder.encode(params[7], ENCODING)
                    + "&" + URLEncoder.encode(PATIENT_TERRAIN, ENCODING) + "=" + URLEncoder.encode(params[8], ENCODING)
                    + "&" + URLEncoder.encode(PATIENT_COVID19_CONFIRMED, ENCODING) + "=" + URLEncoder.encode(params[9], ENCODING)
                    + "&" + URLEncoder.encode(PATIENT_EVOLUTION, ENCODING) + "=" + URLEncoder.encode(params[10], ENCODING)
                    + "&" + URLEncoder.encode(FORM_ADDITION_DATE, ENCODING) + "=" + URLEncoder.encode(params[11], ENCODING);

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
                isFormAdded = true;
                return "";
            }

            Log.e(TAG, "String result from doInBackground method : " + result.toString());
            return result.toString();
        } catch (Exception e) {
            Log.e(TAG, "doInBackground: " + Log.getStackTraceString(e));
            return "Exception name : " + e.getClass().getName() + "\nException message : " + e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String exceptionInfo) {
        covid19NewFormController.onFormAdded(exceptionInfo, isFormAdded);
    }
}
