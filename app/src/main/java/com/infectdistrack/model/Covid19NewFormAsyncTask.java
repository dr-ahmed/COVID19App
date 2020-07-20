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

            String post_data = URLEncoder.encode("patient_id", ENCODING) + "=" + URLEncoder.encode(params[0], ENCODING)
                    + "&" + URLEncoder.encode("name", ENCODING) + "=" + URLEncoder.encode(params[1], ENCODING)
                    + "&" + URLEncoder.encode("gender", ENCODING) + "=" + URLEncoder.encode(params[2], ENCODING)
                    + "&" + URLEncoder.encode("date_of_birth", ENCODING) + "=" + URLEncoder.encode(params[3], ENCODING)
                    + "&" + URLEncoder.encode("wilaya", ENCODING) + "=" + URLEncoder.encode(params[4], ENCODING)
                    + "&" + URLEncoder.encode("moughataa", ENCODING) + "=" + URLEncoder.encode(params[5], ENCODING)
                    + "&" + URLEncoder.encode("form_id", ENCODING) + "=" + URLEncoder.encode(params[6], ENCODING)
                    + "&" + URLEncoder.encode("parent_id", ENCODING) + "=" + URLEncoder.encode(params[7], ENCODING)
                    + "&" + URLEncoder.encode("symptoms", ENCODING) + "=" + URLEncoder.encode(params[8], ENCODING)
                    + "&" + URLEncoder.encode("consulter_medecin", ENCODING) + "=" + URLEncoder.encode(params[9], ENCODING)
                    + "&" + URLEncoder.encode("structure_medecin", ENCODING) + "=" + URLEncoder.encode(params[10], ENCODING)
                    + "&" + URLEncoder.encode("raison_absence", ENCODING) + "=" + URLEncoder.encode(params[11], ENCODING)
                    + "&" + URLEncoder.encode("sabsenter_du_travail", ENCODING) + "=" + URLEncoder.encode(params[12], ENCODING)
                    + "&" + URLEncoder.encode("combien_de_jours", ENCODING) + "=" + URLEncoder.encode(params[13], ENCODING)
                    + "&" + URLEncoder.encode("contact_personne_suspecte", ENCODING) + "=" + URLEncoder.encode(params[14], ENCODING)
                    + "&" + URLEncoder.encode("tel_personne_suspecte", ENCODING) + "=" + URLEncoder.encode(params[15], ENCODING)
                    + "&" + URLEncoder.encode("dernier_contact", ENCODING) + "=" + URLEncoder.encode(params[16], ENCODING)
                    + "&" + URLEncoder.encode("niveau_socio_economique", ENCODING) + "=" + URLEncoder.encode(params[17], ENCODING)
                    + "&" + URLEncoder.encode("condition_pre_dispo", ENCODING) + "=" + URLEncoder.encode(params[18], ENCODING)
                    + "&" + URLEncoder.encode("liste_condition_pre_dispo", ENCODING) + "=" + URLEncoder.encode(params[19], ENCODING)
                    + "&" + URLEncoder.encode("test_covid", ENCODING) + "=" + URLEncoder.encode(params[20], ENCODING)
                    + "&" + URLEncoder.encode("type_test", ENCODING) + "=" + URLEncoder.encode(params[21], ENCODING)
                    + "&" + URLEncoder.encode("date_test", ENCODING) + "=" + URLEncoder.encode(params[22], ENCODING)
                    + "&" + URLEncoder.encode("tdr", ENCODING) + "=" + URLEncoder.encode(params[23], ENCODING)
                    + "&" + URLEncoder.encode("pcr", ENCODING) + "=" + URLEncoder.encode(params[24], ENCODING)
                    + "&" + URLEncoder.encode("scanner", ENCODING) + "=" + URLEncoder.encode(params[25], ENCODING)
                    + "&" + URLEncoder.encode("tdr_reponse", ENCODING) + "=" + URLEncoder.encode(params[26], ENCODING)
                    + "&" + URLEncoder.encode("tdr_details", ENCODING) + "=" + URLEncoder.encode(params[27], ENCODING)
                    + "&" + URLEncoder.encode("pcr_reponse", ENCODING) + "=" + URLEncoder.encode(params[28], ENCODING)
                    + "&" + URLEncoder.encode("scanner_reponse", ENCODING) + "=" + URLEncoder.encode(params[29], ENCODING)
                    + "&" + URLEncoder.encode("statut_actuel", ENCODING) + "=" + URLEncoder.encode(params[30], ENCODING)
                    + "&" + URLEncoder.encode("detail_vivant", ENCODING) + "=" + URLEncoder.encode(params[31], ENCODING)
                    + "&" + URLEncoder.encode("date_admission", ENCODING) + "=" + URLEncoder.encode(params[32], ENCODING)
                    + "&" + URLEncoder.encode("struct_sani_hospi", ENCODING) + "=" + URLEncoder.encode(params[33], ENCODING)
                    + "&" + URLEncoder.encode("date_deces", ENCODING) + "=" + URLEncoder.encode(params[34], ENCODING)
                    + "&" + URLEncoder.encode("lieu_deces", ENCODING) + "=" + URLEncoder.encode(params[35], ENCODING)
                    + "&" + URLEncoder.encode("duree_hospi", ENCODING) + "=" + URLEncoder.encode(params[36], ENCODING)
                    + "&" + URLEncoder.encode("struct_sani_deces", ENCODING) + "=" + URLEncoder.encode(params[37], ENCODING)
                    + "&" + URLEncoder.encode("addition_date", ENCODING) + "=" + URLEncoder.encode(params[38], ENCODING);

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
            return e.getClass().getName();
            //return "Exception name : " + e.getClass().getName() + "\nException message : " + e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String exceptionInfo) {
        covid19NewFormController.onFormAdded(exceptionInfo, isFormAdded);
    }
}
