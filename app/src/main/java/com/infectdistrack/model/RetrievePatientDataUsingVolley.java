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
import static com.infectdistrack.model.Constants.PATIENT_REQUEST_TYPE;
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
    private String exceptionInfo = "", patientPhoneNumber = "";
    private String selectedItem;

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

                        // Log.e(TAG, "response = " + response);
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
                    exceptionInfo = NO_INTERNET_CONNECTION;
                } else
                    exceptionInfo = error.getMessage();

                phoneNumberCheckoutFragment.getPatientPhoneNumberFromVolley(exceptionInfo, isAlreadyRegistrated, patientPhoneNumber);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put(PHONE_NUMBER_PK_TAG, phoneNumberCheckoutFragment.getPhoneNumber());
                params.put(PATIENT_REQUEST_TYPE, selectedItem);

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
                exceptionInfo = response;
            else {
                String doesPatientExist = result.getString(DOES_PATIENT_EXIST);


                if (doesPatientExist.equals(NO))
                    isAlreadyRegistrated = false;
                else if (doesPatientExist.equals(YES)) {
                    patientPhoneNumber = result.getString(PATIENT_PHONE_NUMBER);
                    isAlreadyRegistrated = !patientPhoneNumber.equals("null"); // Le doesPatientExist serait "null" si l'id n'existe pas en mode associé
                }
            }
        } catch (JSONException e) {
            Log.e(TAG, "JSONException : " + Log.getStackTraceString(e));
            Log.e(TAG, "response from PHP script : " + response);
        } finally {
            phoneNumberCheckoutFragment.getPatientPhoneNumberFromVolley(exceptionInfo, isAlreadyRegistrated, patientPhoneNumber);
        }
    }

    public void setRequestType(String selectedItem) {
        this.selectedItem = selectedItem;
    }
}
