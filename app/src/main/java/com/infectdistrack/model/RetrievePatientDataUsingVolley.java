package com.infectdistrack.model;

import android.util.Log;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.infectdistrack.view.PhoneNumberCheckoutFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.infectdistrack.model.Constants.NO_INTERNET_CONNECTION;
import static com.infectdistrack.model.Constants.PHONE_NUMBER_PK_TAG;
import static com.infectdistrack.model.Constants.RETRIEVE_PATIENT_DATA_SCRIPT_NAME;
import static com.infectdistrack.model.Constants.SCRIPT_PATH;

public class RetrievePatientDataUsingVolley {

    private PhoneNumberCheckoutFragment phoneNumberCheckoutFragment;
    private RequestQueue requestQueue;

    private static final String TAG = "RetrievePatientData",
            VOLLEY_REQUEST_TAG = "VolleyRequest",
            NO = "NO",
            YES = "YES",
            DOES_PATIENT_EXIST = "does_patient_exist",
            PATIENT_PHONE_NUMBER = "patient_phone_number";

    private boolean isAlreadyRegistrated;
    private String exceptionOccured = "", patientPhoneNumber = "";

    public RetrievePatientDataUsingVolley(PhoneNumberCheckoutFragment phoneNumberCheckoutFragment) {
        this.phoneNumberCheckoutFragment = phoneNumberCheckoutFragment;
        requestQueue = Volley.newRequestQueue(phoneNumberCheckoutFragment.getActivity());
    }

    public void getDataFromServer() {
        String url = SCRIPT_PATH + RETRIEVE_PATIENT_DATA_SCRIPT_NAME;
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e(TAG, "response = " + response);
                        getDataFromJSONDocument(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // ici on trouve les erreurs d'exécution du coté client ou serveur.
                // Client : timout ou erreur dans getParams par exemple
                // serveur : erreur dans le script PHP. Exemple : Unexpected response code 500 (com.android.volley.ServerError)

                Log.e(TAG, Log.getStackTraceString(error));

                if (error instanceof NoConnectionError || error instanceof TimeoutError) {
                    exceptionOccured = NO_INTERNET_CONNECTION;
                } else
                    exceptionOccured = error.getMessage();

                phoneNumberCheckoutFragment.getPatientPhoneNumberFromVolley(exceptionOccured == null ? "NullPointerException" : exceptionOccured,
                        isAlreadyRegistrated, patientPhoneNumber);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put(PHONE_NUMBER_PK_TAG, phoneNumberCheckoutFragment.getPhoneNumber());

                return params;
            }
        };
        request.setTag(VOLLEY_REQUEST_TAG);
        requestQueue.add(request);
    }

    private void getDataFromJSONDocument(String response) {
        try {
            JSONObject result = new JSONObject(response);

            if (!result.has(DOES_PATIENT_EXIST))
                exceptionOccured = response;
            else {
                String authorized_user = result.getString(DOES_PATIENT_EXIST);

                if (authorized_user.equals(NO))
                    isAlreadyRegistrated = false;
                else if (authorized_user.equals(YES)) {
                    isAlreadyRegistrated = true;
                    patientPhoneNumber = result.getString(PATIENT_PHONE_NUMBER);
                }
            }
        } catch (JSONException e) {
            Log.e(TAG, "JSONException : " + Log.getStackTraceString(e));
            Log.e(TAG, "response from PHP script : " + response);
        } finally {
            phoneNumberCheckoutFragment.getPatientPhoneNumberFromVolley(exceptionOccured, isAlreadyRegistrated, patientPhoneNumber);
        }
    }
}
