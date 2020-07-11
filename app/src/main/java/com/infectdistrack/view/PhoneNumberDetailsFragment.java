package com.infectdistrack.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.infectdistrack.R;
import com.infectdistrack.model.Patient;

import java.util.ArrayList;
import java.util.Set;

import static com.infectdistrack.model.Constants.PATIENT_OBJECT_TAG;
import static com.infectdistrack.model.Constants.setWilayasAndMoughataas;
import static com.infectdistrack.model.Constants.wilayasAndMoughataas;

public class PhoneNumberDetailsFragment extends Fragment {

    private View rootView;
    private EditText phoneNumberEdt, nameEdt;
    private RadioGroup radioGroup;
    private RadioButton genderM, genderF;
    private TextView birthDateTxt;
    private DatePicker birthDateDtPicker;
    private Spinner wilayaSpinner, moughataaSpinner;
    private ArrayAdapter<String> wilayaAdapter, moughataaAdapter;
    private Set<String> wilayaSet;
    private Button cancelBtn, continueBtn;

    private Patient patient;

    public PhoneNumberDetailsFragment(Patient patient) {
        this.patient = patient;
    }

    private void setMaxLengthForPhoneNumberEditText(int maxLengthofEditText) {
        phoneNumberEdt.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLengthofEditText)});
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_phone_number_details, container, false);

        setWilayasAndMoughataas();
        initViews();

        if (getArguments() != null && getArguments().getParcelable(PATIENT_OBJECT_TAG) != null) {
            patient = getArguments().getParcelable(PATIENT_OBJECT_TAG);
            phoneNumberEdt.setText(patient.getPhoneNumber());
            setMaxLengthForPhoneNumberEditText(19);//patient.getPhoneNumber().length()
            phoneNumberEdt.setEnabled(false);
            nameEdt.setText(patient.getName());
            nameEdt.setEnabled(false);

            if (patient.getGender().equals("M"))
                genderM.setChecked(true);
            else if (patient.getGender().equals("F"))
                genderF.setChecked(true);
            genderM.setEnabled(false);
            genderF.setEnabled(false);

            birthDateDtPicker.setVisibility(View.GONE);
            birthDateTxt.setText(patient.getDateOfBirth());
            wilayaSpinner.setSelection(new ArrayList<>(wilayaSet).indexOf(patient.getWilaya()));
            wilayaSpinner.setEnabled(false);

            ArrayList<String> moughataas = wilayasAndMoughataas.get(patient.getWilaya());
            moughataaAdapter = new ArrayAdapter<>(getActivity(), R.layout.custom_spinner_item, moughataas.toArray(new String[moughataas.size()]));
            moughataaSpinner.setAdapter(moughataaAdapter);
            moughataaSpinner.setSelection(moughataas.indexOf(patient.getMoughataa()));
            moughataaSpinner.setEnabled(false);
        } else
            birthDateTxt.setVisibility(View.GONE);

        return rootView;
    }

    private void initViews() {
        phoneNumberEdt = rootView.findViewById(R.id.patient_phone_number);
        nameEdt = rootView.findViewById(R.id.patient_name);
        radioGroup = rootView.findViewById(R.id.patient_gender_radio_group);
        genderM = rootView.findViewById(R.id.patient_male_item);
        genderF = rootView.findViewById(R.id.patient_female_item);
        birthDateTxt = rootView.findViewById(R.id.patient_birth_date_edt_textview);
        birthDateDtPicker = rootView.findViewById(R.id.patient_birth_date_edt_datepicker);
        wilayaSpinner = rootView.findViewById(R.id.patient_wilaya_spinner);

        wilayaSet = wilayasAndMoughataas.keySet();
        wilayaAdapter = new ArrayAdapter<>(getActivity(), R.layout.custom_spinner_item, wilayaSet.toArray(new String[wilayaSet.size()]));
        wilayaSpinner.setAdapter(wilayaAdapter);

        moughataaSpinner = rootView.findViewById(R.id.patient_moughataa_spinner);
        cancelBtn = rootView.findViewById(R.id.patient_cancel_btn);
        continueBtn = rootView.findViewById(R.id.patient_continue_btn);
    }
}
