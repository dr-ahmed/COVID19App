package com.infectdistrack.view;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.infectdistrack.R;
import com.infectdistrack.model.Covid19Form;

import java.util.ArrayList;
import java.util.Set;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.infectdistrack.model.Constants.DEFAULT_WILAYA;
import static com.infectdistrack.model.Constants.EMPTY_STRING;
import static com.infectdistrack.model.Constants.YES;
import static com.infectdistrack.model.Constants.setWilayasAndMoughataas;
import static com.infectdistrack.model.Constants.wilayasAndMoughataas;
import static com.infectdistrack.model.Utilities.isPhoneNumberValid;

public class Covid19FormPart2 extends Fragment implements DatePicker.OnDateChangedListener, AdapterView.OnItemSelectedListener, RadioGroup.OnCheckedChangeListener {

    private static final String TAG = "COVID19FormPart1";

    private View rootView;
    private EditText patientNameEdt, patientPhoneNumberEdt;
    private RadioGroup patientGenderRadioGroup, patientSuspectedCasesRadioGroup;
    private String patientGender = "", patientSuspectedCasesDescription = "";
    private Spinner patientWilayaSpinner, patientMoughataaSpinner;
    private LinearLayout moughataaLayout;
    private ArrayAdapter<String> wilayaSpinnerAdapter, patientMoughataaAdapter;
    private DatePicker patientAgeDatePicker;
    private boolean isDateChanged = false;

    private Covid19Form covid19FormObject;

    public Covid19FormPart2(Covid19Form covid19FormObject) {
        this.covid19FormObject = covid19FormObject;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_covid19_form_part2, container, false);

        setWilayasAndMoughataas();
        initViews(rootView, wilayasAndMoughataas.keySet());

        return rootView;
    }

    private void initViews(View rootView, Set<String> wilayaSet) {
        patientNameEdt = rootView.findViewById(R.id.patient_name);
        patientPhoneNumberEdt = rootView.findViewById(R.id.patient_phone_number);

        /*
        patientGenderRadioGroup = rootView.findViewById(R.id.patient_gender_radio_groupXXX);
        patientGenderRadioGroup.setOnCheckedChangeListener(this);

        patientSuspectedCasesRadioGroup = rootView.findViewById(R.id.patient_suspected_cases_radio_group);
        patientSuspectedCasesRadioGroup.setOnCheckedChangeListener(this);

        patientAgeDatePicker = rootView.findViewById(R.id.patient_birth_date_edt_datepickerXXX);
        patientAgeDatePicker.init(1960, 10, 28, this);

        patientWilayaSpinner = rootView.findViewById(R.id.patient_wilaya_spinnerXXXX);
        wilayaSpinnerAdapter = new ArrayAdapter<>(getActivity(), R.layout.custom_spinner_item, wilayaSet.toArray(new String[wilayaSet.size()]));
        patientWilayaSpinner.setAdapter(wilayaSpinnerAdapter);
        patientWilayaSpinner.setOnItemSelectedListener(this);

        moughataaLayout = rootView.findViewById(R.id.patient_moughataa_layout);
        patientMoughataaSpinner = rootView.findViewById(R.id.patient_moughataa_spinnerXXX);

         */
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        /*
        switch (group.getId()) {
            case R.id.patient_gender_radio_groupXXX: {
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
            }
        }

         */
    }

    public boolean isFieldEmpty() {
        /*
        TextView genderTextView = rootView.findViewById(R.id.patient_gender_txt),
                ageTextView = rootView.findViewById(R.id.patient_birth_date_txt_label),
                wilayaTextView = rootView.findViewById(R.id.patient_wilaya_txtXXX),
                moughataaTextView = rootView.findViewById(R.id.patient_moughataa_txt),
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

        String phoneNumberFeedback = isPhoneNumberValid(patientPhoneNumberEdt.getText().toString());
        if (!phoneNumberFeedback.equals(YES)) {
            patientPhoneNumberEdt.requestFocus();
            patientPhoneNumberEdt.setError(phoneNumberFeedback);
            return true;
        }

        if (patientGender.isEmpty()) {
            setRedTextview(genderTextView, "GENRE OBLIGATOIRE");
            return true;
        } else
            resetTextview(genderTextView, "Genre");

        if (!isDateChanged) {
            setRedTextview(ageTextView, "DATE DE NAISSANCE OBLIGATOIRE");
            return true;
        } else
            resetTextview(ageTextView, "Date de naissance");

        if (patientWilayaSpinner.getSelectedItem().toString().equals(DEFAULT_WILAYA)) {
            setRedTextview(wilayaTextView, "WILAYA OBLIGATOIRE");
            return true;
        } else
            resetTextview(wilayaTextView, "Wilaya");

        if (patientMoughataaSpinner.getSelectedItem().toString().equals(EMPTY_STRING)) {
            setRedTextview(moughataaTextView, "MOUGHATAA OBLIGATOIRE");
            return true;
        } else
            resetTextview(moughataaTextView, "Moughataa");

        if (patientSuspectedCasesDescription.isEmpty()) {
            setRedTextview(suspectTextView, "CAS SUSPECTS OBLIGATOIRES");
            return true;
        } else
            resetTextview(suspectTextView, "Cas suspects");

        */
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
        /*
        covid19FormObject.setName(patientNameEdt.getText().toString());
        covid19FormObject.setPhoneNumber(patientPhoneNumberEdt.getText().toString());
        covid19FormObject.setGendre(patientGender);

        String dateOfBirth = patientAgeDatePicker.getDayOfMonth() + "/" + (patientAgeDatePicker.getMonth() + 1) + "/" + patientAgeDatePicker.getYear();
        covid19FormObject.setAge(dateOfBirth);
        covid19FormObject.setWilaya(patientWilayaSpinner.getSelectedItem().toString());
        covid19FormObject.setSuspectedCases(patientSuspectedCasesDescription);
         */

        //Log.e(TAG, covid19FormObject.toString());
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        /*
        if (parent.getId() == R.id.patient_wilaya_spinnerXXXX) {
            moughataaLayout.setVisibility(patientWilayaSpinner.getSelectedItem().toString().equals(DEFAULT_WILAYA) ? GONE : VISIBLE);
            ArrayList<String> moughataas = wilayasAndMoughataas.get(patientWilayaSpinner.getSelectedItem());
            patientMoughataaAdapter = new ArrayAdapter<>(getActivity(), R.layout.custom_spinner_item, moughataas.toArray(new String[moughataas.size()]));
            patientMoughataaSpinner.setAdapter(patientMoughataaAdapter);
        }
         */
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        /*
        if (view.getId() == R.id.patient_birth_date_edt_datepickerXXX)
            isDateChanged = true;
         */
    }
}
