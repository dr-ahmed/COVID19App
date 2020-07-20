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
import com.infectdistrack.view.PhoneNumberCheckoutActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.infectdistrack.model.Constants.NO_INTERNET_CONNECTION;
import static com.infectdistrack.model.Constants.PARENT_ID_TAG;
import static com.infectdistrack.model.Constants.PATIENT_REQUEST_TYPE;
import static com.infectdistrack.model.Constants.PHONE_NUMBER_PK_TAG;
import static com.infectdistrack.model.Constants.RETRIEVE_PATIENT_DATA_SCRIPT_NAME;
import static com.infectdistrack.model.Constants.SCRIPT_PATH;
import static com.infectdistrack.model.Constants.formCount;

public class RetrievePatientDataUsingVolley {

    private PhoneNumberCheckoutActivity phoneNumberCheckoutActivity;
    private RequestQueue requestQueue;

    private static final String TAG = "RetrievePatientData",
            VOLLEY_REQUEST_TAG = "VolleyRequest",
            NO = "NO",
            YES = "YES",
            DOES_PATIENT_EXIST = "does_patient_exist",
            LAST_ASSOCIATED_PHONE_NUMBER = "last_associated_phone_number",
            PATIENT_MAIN_DATA_HEADER = "patient_as_json",
            PATIENT_DATA_HEADER_OBJECT = "patient_data",
            PATIENT_PHONE_NUMBER = "phone_number_pk",
            PATIENT_NAME = "name",
            PATIENT_GENDER = "gender",
            PATIENT_BIRTH_DATE = "date_of_birth",
            PATIENT_WILAYA = "wilaya",
            PATIENT_MOUGHATAA = "moughataa";

    private boolean isAlreadyRegistrated = false;
    private String exceptionInfo = "";
    private Patient patient;
    private String selectedItem, parentID;

    public RetrievePatientDataUsingVolley(PhoneNumberCheckoutActivity phoneNumberCheckoutActivity) {
        this.phoneNumberCheckoutActivity = phoneNumberCheckoutActivity;
        requestQueue = Volley.newRequestQueue(phoneNumberCheckoutActivity);
    }

    public void setRequestType(String selectedItem) {
        this.selectedItem = selectedItem;
    }

    public void setParentID(String parentID) {
        this.parentID = parentID;
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

                phoneNumberCheckoutActivity.getPatientPhoneNumberFromVolley(exceptionInfo, isAlreadyRegistrated, patient);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put(PHONE_NUMBER_PK_TAG, phoneNumberCheckoutActivity.getPhoneNumber());
                params.put(PATIENT_REQUEST_TYPE, selectedItem);
                params.put(PARENT_ID_TAG, parentID);

                return params;
            }
        };
        request.setTag(VOLLEY_REQUEST_TAG);
        requestQueue.add(request);
    }

    private void getDataFromJSONDocument(String response) {
        try {
            JSONObject result = new JSONObject(response);

            if (result.isNull(PATIENT_MAIN_DATA_HEADER))
                exceptionInfo = response;
            else {
                JSONObject patientAsJSON = result.getJSONObject(PATIENT_MAIN_DATA_HEADER);

                if (patientAsJSON.has(DOES_PATIENT_EXIST)) {
                    String doesPatientExist = patientAsJSON.getString(DOES_PATIENT_EXIST);

                    if (doesPatientExist.equals(NO))
                        isAlreadyRegistrated = false;
                    else if (doesPatientExist.equals(YES)) {
                        if (!patientAsJSON.isNull(PATIENT_DATA_HEADER_OBJECT)) {

                            formCount = Integer.parseInt(patientAsJSON.getString(PARENT_ID_TAG));
                            Log.e(TAG, "formCount : " + formCount);

                            JSONObject patientDataObject = patientAsJSON.getJSONObject(PATIENT_DATA_HEADER_OBJECT);
                            // Le LAST_ASSOCIATED_PHONE_NUMBER serait "null" si le user choisit l'option Associé et fournit un ID qui n'existe pas dans la BD
                            if (patientDataObject.has(LAST_ASSOCIATED_PHONE_NUMBER) && patientDataObject.getString(LAST_ASSOCIATED_PHONE_NUMBER).equals("null"))
                                isAlreadyRegistrated = false;
                            else {
                                isAlreadyRegistrated = true;
                                patient = new Patient(
                                        // Si le user choisit l'option Associé, on récupère le dernier item associé via LAST_ASSOCIATED_PHONE_NUMBER
                                        // Si le user choisit l'option Unique, on récuprère l'id du patient via PATIENT_PHONE_NUMBER
                                        patientDataObject.getString(patientDataObject.has(LAST_ASSOCIATED_PHONE_NUMBER) ? LAST_ASSOCIATED_PHONE_NUMBER : PATIENT_PHONE_NUMBER),
                                        patientDataObject.getString(PATIENT_NAME),
                                        patientDataObject.getString(PATIENT_GENDER),
                                        patientDataObject.getString(PATIENT_BIRTH_DATE),
                                        patientDataObject.getString(PATIENT_WILAYA),
                                        patientDataObject.getString(PATIENT_MOUGHATAA));
                            }
                        } else
                            exceptionInfo = "Unrecognized value for patient_data tag !" + "\n" + response;
                    } else
                        exceptionInfo = "Unrecognized value for does_patient_exist tag !";
                } else
                    exceptionInfo = response;
            }
        } catch (JSONException e) {
            Log.e(TAG, "JSONException : " + Log.getStackTraceString(e));
            Log.e(TAG, "response from PHP script : " + response);
        } finally {
            phoneNumberCheckoutActivity.getPatientPhoneNumberFromVolley(exceptionInfo, isAlreadyRegistrated, patient);
        }
    }
}
