package com.infectdistrack.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.infectdistrack.R;
import com.infectdistrack.model.RetrievePatientDataUsingVolley;

import static com.infectdistrack.model.Constants.NO_INTERNET_CONNECTION;
import static com.infectdistrack.presenter.UIBasicController.hideProgressDialog;
import static com.infectdistrack.presenter.UIBasicController.showProgressDialog;

public class PhoneNumberCheckoutFragment extends Fragment implements View.OnClickListener {

    private View rootView;
    private EditText patientPhoneNumberEdt;
    private RadioGroup patientIdTypeRadioGroup;
    private Button checkoutBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_phone_number_checkout, container, false);
        initViews();

        return rootView;
    }

    private void initViews() {
        patientPhoneNumberEdt = rootView.findViewById(R.id.patient_phone_number_checkout);
        patientIdTypeRadioGroup = rootView.findViewById(R.id.patient_id_radio_group);
        checkoutBtn = rootView.findViewById(R.id.verify_patient_btn);
        checkoutBtn.setOnClickListener(this);
    }

    public String getPhoneNumber() {
        return patientPhoneNumberEdt.getText().toString();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.verify_patient_btn) {
            showProgressDialog(getActivity(), "Vérification de l'identifiant en cours ...");

            RetrievePatientDataUsingVolley retrievePatientDataUsingVolley = new RetrievePatientDataUsingVolley(this);
            retrievePatientDataUsingVolley.getDataFromServer();

        }
    }

    public void getPatientPhoneNumberFromVolley(String exceptionOccured, boolean isAlreadyRegistrated, String patientPhoneNumber) {
        hideProgressDialog();

        if (exceptionOccured.isEmpty()) {
            if (isAlreadyRegistrated)
                Toast.makeText(getActivity(), "Already registred !", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(getActivity(), "NOT already registred !", Toast.LENGTH_LONG).show();
        } else {
            if (exceptionOccured.equals(NO_INTERNET_CONNECTION))
                Toast.makeText(getActivity(), "Veuillez vérifier votre connexion internet !", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(getActivity(), "Fatal error ...", Toast.LENGTH_LONG).show();
        }
    }
}
