package com.infectdistrack.model;

import android.os.AsyncTask;
import android.util.Log;

import com.infectdistrack.presenter.NewUserController;

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
import static com.infectdistrack.model.Constants.SCRIPT_PATH;
import static com.infectdistrack.model.Constants.USER_EMAIL_TAG;
import static com.infectdistrack.model.Constants.USER_PASSWORD_TAG;

public class CheckDuplicateUserAsyncTask extends AsyncTask<String, Integer, String> {

    private static final String TAG = "CheckDuplicUsrAsyncTask";
    private boolean userIsUnique = false;
    private NewUserController newUserControllerListener;
    private User user;
    StringBuilder result;

    public CheckDuplicateUserAsyncTask(NewUserController newUserControllerListener) {
        this.newUserControllerListener = newUserControllerListener;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            URL insertURL = new URL(SCRIPT_PATH + CHECKING_DUPLICATE_USER_SCRIPT_NAME);
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

            String post_data = URLEncoder.encode(USER_EMAIL_TAG, ENCODING) + "=" + URLEncoder.encode(params[0], ENCODING);
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

            if (result.toString().equals("[]")) // Si le retour du document JSON est vide, càd que l'email du user n'existe pas encore dans la BD
                userIsUnique = true;
            else { // Sinon, on oeut avoir deux cas
                JSONObject response = new JSONObject(result.toString());
                if (!response.isNull(JSON_HEADER_TAG)) // soit, il existe many users with such email
                    userIsUnique = false;
                else { // soit, une exception a eu lieu lors de l'exécution de la requete de check duplicate
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
        newUserControllerListener.onCheckDuplicateUserResponse(exceptionInfo, userIsUnique);
    }
}
