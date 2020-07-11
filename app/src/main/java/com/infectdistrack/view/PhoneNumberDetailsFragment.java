package com.infectdistrack.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.infectdistrack.R;

public class PhoneNumberDetailsFragment extends Fragment {

    private View rootView;
    private EditText phoneNumberEdt, nameEdt;
    private RadioGroup radioGroup;
    private TextView birthDateTxt;
    private DatePicker birthDateDtPicker;
    private Spinner wilayaSpinner, moughataaSpinner;
    private Button cancelBtn, continueBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_phone_number_details, container, false);
        initViews();

        return rootView;
    }

    private void initViews() {
        phoneNumberEdt = rootView.findViewById(R.id.patient_phone_number);
        nameEdt = rootView.findViewById(R.id.patient_name);
        radioGroup = rootView.findViewById(R.id.patient_gender_radio_group);
        birthDateTxt = rootView.findViewById(R.id.patient_birth_date_edt_textview);
        birthDateDtPicker = rootView.findViewById(R.id.patient_birth_date_edt_datepicker);
        wilayaSpinner = rootView.findViewById(R.id.patient_wilaya_txt);
        moughataaSpinner = rootView.findViewById(R.id.patient_wilaya_spinner);
        cancelBtn = rootView.findViewById(R.id.patient_cancel_btn);
        continueBtn = rootView.findViewById(R.id.patient_continue_btn);
    }
}
