package com.infectdistrack.view;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.infectdistrack.R;

import static com.infectdistrack.model.Constants.AGE_LIST;
import static com.infectdistrack.model.Constants.DEFAULT_AGE;
import static com.infectdistrack.model.Constants.DEFAULT_WILAYA;
import static com.infectdistrack.model.Constants.WILAYAS;

public class COVID19FormPart1 extends Fragment implements RadioGroup.OnCheckedChangeListener {

    private static final String TAG = "COVID19FormPart1";
    private View rootView;
    private EditText patientNameEdt, patientPhoneNumberEdt;
    private RadioGroup patientGenderRadioGroup, patientSuspectedCasesRadioGroup;
    private String patientGender = "", patientSuspectedCasesDescription = "";
    private Spinner patientAgeSpinner, patientWilayaSpinner;
    private ArrayAdapter<String> ageSpinnerAdapter, wilayaSpinnerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_covid19_form_part1, container, false);

        initViews(rootView);

        return rootView;
    }

    private void initViews(View rootView) {
        patientNameEdt = rootView.findViewById(R.id.patient_name);
        patientPhoneNumberEdt = rootView.findViewById(R.id.patient_phone_number);

        patientGenderRadioGroup = rootView.findViewById(R.id.patient_gender_radio_group);
        patientGenderRadioGroup.setOnCheckedChangeListener(this);

        patientSuspectedCasesRadioGroup = rootView.findViewById(R.id.patient_suspected_cases_radio_group);
        patientSuspectedCasesRadioGroup.setOnCheckedChangeListener(this);

        patientAgeSpinner = rootView.findViewById(R.id.patient_age_spinner);
        ageSpinnerAdapter = new ArrayAdapter<>(getActivity(), R.layout.custom_spinner_item, AGE_LIST);
        patientAgeSpinner.setAdapter(ageSpinnerAdapter);

        patientWilayaSpinner = rootView.findViewById(R.id.patient_wilaya_spinner);
        wilayaSpinnerAdapter = new ArrayAdapter<>(getActivity(), R.layout.custom_spinner_item, WILAYAS);
        patientWilayaSpinner.setAdapter(wilayaSpinnerAdapter);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (group.getId()) {
            case R.id.patient_gender_radio_group: {
                if (checkedId == R.id.male_item)
                    patientGender = "M";
                else if (checkedId == R.id.female_item)
                    patientGender = "F";
            }
            break;
            case R.id.patient_suspected_cases_radio_group: {
                switch (checkedId) {
                    case R.id.direct_contact:
                        patientSuspectedCasesDescription = "Contact direct";
                        break;
                    case R.id.direct_contact_with_symptoms:
                        patientSuspectedCasesDescription = "Contact direct avec des symptômes";
                        break;
                    case R.id.patient_having_covid19_symptoms:
                        patientSuspectedCasesDescription = "Malade présentant des symptômes de COVID-19";
                }
                //setValues();
            }
        }
    }

    public boolean isFieldEmpty() {
        TextView genderTextView = rootView.findViewById(R.id.patient_gender_txt),
                ageTextView = rootView.findViewById(R.id.patient_age_txt),
                wilayaTextView = rootView.findViewById(R.id.patient_wilaya_txt),
                suspectTextView = rootView.findViewById(R.id.patient_suspected_cases_txt);

        if (patientNameEdt.getText().toString().trim().isEmpty()) {
            patientNameEdt.requestFocus();
            patientNameEdt.setError("Veuillez saisir le nom!");
            return true;
        }

        if (patientPhoneNumberEdt.getText().toString().trim().isEmpty()) {
            patientPhoneNumberEdt.requestFocus();
            patientPhoneNumberEdt.setError("Le numéro de téléphone est obligatoire!");
            return true;
        }


        if (patientGender.isEmpty()) {
            setRedTextview(genderTextView, "GENRE OBLIGATOIRE");
            return true;
        } else
            resetTextview(genderTextView, "Genre");

        if (patientAgeSpinner.getSelectedItem().toString().equals(DEFAULT_AGE)) {
            setRedTextview(ageTextView, "AGE OBLIGATOIRE");
            return true;
        } else
            resetTextview(ageTextView, "Âge");

        if (patientWilayaSpinner.getSelectedItem().toString().equals(DEFAULT_WILAYA)) {
            setRedTextview(wilayaTextView, "WILAYA OBLIGATOIRE");
            return true;
        } else
            resetTextview(wilayaTextView, "Wilaya");

        if (patientSuspectedCasesDescription.isEmpty()) {
            setRedTextview(suspectTextView, "CAS SUSPECTS OBLIGATOIRES");
            return true;
        } else
            resetTextview(suspectTextView, "Cas suspects");

        return false;
    }

    private void setRedTextview(TextView textView, String s) {
        textView.setText(s);
        textView.setTextColor(Color.RED);
    }

    private void resetTextview(TextView textView, String s) {
        textView.setText(s);
        textView.setTextColor(Color.DKGRAY);
    }

    public void setValues() {
        Log.e(TAG, "Nom : " + patientNameEdt.getText().toString() + "\n"
                + "Tel : " + patientPhoneNumberEdt.getText().toString() + "\n"
                + "Genre : " + patientGender + "\n"
                + "Age : " + patientAgeSpinner.getSelectedItem().toString() + "\n"
                + "Wilaya : " + patientWilayaSpinner.getSelectedItem().toString() + "\n"
                + "Cas suspects : " + patientSuspectedCasesDescription);
    }
}
