package com.infectdistrack.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.infectdistrack.R;
import com.infectdistrack.model.Patient;

import java.util.ArrayList;
import java.util.Set;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.infectdistrack.model.Constants.ASSOCIATED_ITEM;
import static com.infectdistrack.model.Constants.BUNDLE_EXTRA_TAG;
import static com.infectdistrack.model.Constants.DEFAULT_WILAYA;
import static com.infectdistrack.model.Constants.OPTION_TAG;
import static com.infectdistrack.model.Constants.PATIENT_OBJECT_TAG;
import static com.infectdistrack.model.Constants.PHONE_NUMBER_TAG;
import static com.infectdistrack.model.Constants.UNIQUE_ITEM;
import static com.infectdistrack.model.Constants.USER_TYPE_LIST;
import static com.infectdistrack.model.Constants.setWilayasAndMoughataas;
import static com.infectdistrack.model.Constants.wilayasAndMoughataas;
import static com.infectdistrack.presenter.UIBasicController.showMessage;

public class PhoneNumberDetailsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private static final String TAG = "PhoneNumberDetailsActi";

    private EditText phoneNumberEdt, nameEdt;
    private RadioGroup radioGroup;
    private RadioButton genderM, genderF;
    private TextView birthDateTxt;
    private DatePicker birthDateDtPicker;
    private Spinner wilayaSpinner, moughataaSpinner;
    private ArrayAdapter<String> wilayaAdapter, moughataaAdapter;
    private LinearLayout moughataaLayout;
    private Set<String> wilayaSet;
    private ArrayList<String> moughataas;
    private Button cancelBtn, continueBtn;

    private Patient patient;

    private void setMaxLengthForPhoneNumberEditText(int maxLengthofEditText) {
        phoneNumberEdt.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLengthofEditText)});
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_phone_number_details);

        setWilayasAndMoughataas();
        initViews();

        // Si le user saisit un ID qui n'existe pas dans la BD et choisit l'option Unique
        if (getIntent().getStringExtra(OPTION_TAG) != null && getIntent().getStringExtra(OPTION_TAG).equals(UNIQUE_ITEM)
                && getIntent().getStringExtra(PHONE_NUMBER_TAG) != null) {
            phoneNumberEdt.setText(getIntent().getStringExtra(PHONE_NUMBER_TAG));
            setMaxLengthForPhoneNumberEditText(8);
            birthDateTxt.setVisibility(View.GONE);

            initWilayaSpinner();

            return;
        }

        // Si le user saisit un ID qui existe dans la BD, on récupère les infos du patient en question
        if (getIntent().getBundleExtra(BUNDLE_EXTRA_TAG) != null) {
            Bundle bundle = getIntent().getBundleExtra(BUNDLE_EXTRA_TAG);
            patient = bundle.getParcelable(PATIENT_OBJECT_TAG);

            // Si le bundle comprend OPTION_TAG
            if (bundle.getString(OPTION_TAG) != null) {
                // Si le user choisit l'option Unique, on désactive tous les Views et on affiches les données du patient sur les Views
                if (bundle.getString(OPTION_TAG).equals(UNIQUE_ITEM)) {
                    phoneNumberEdt.setText(patient.getPhoneNumber());
                    setMaxLengthForPhoneNumberEditText(9);
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

                    initAdapterAndSpinner(wilayaAdapter, wilayaSpinner, patient.getWilaya());
                    initAdapterAndSpinner(moughataaAdapter, moughataaSpinner, patient.getMoughataa());
                } else // Si le user choisit l'option Associé, on affiche le phone number dans l'edittext et on le désactive. Les autres Views restent enabled
                    if (bundle.getString(OPTION_TAG).equals(ASSOCIATED_ITEM)) {
                        phoneNumberEdt.setText(patient.getPhoneNumber());
                        setMaxLengthForPhoneNumberEditText(9);
                        phoneNumberEdt.setEnabled(false);
                        birthDateTxt.setVisibility(View.GONE);

                        initWilayaSpinner();
                    } else
                        showMessage(this, "Problème survenu", "Désolé, une erreur s'est produite (Code d'erreur : 010)");
            } else
                showMessage(this, "Problème survenu", "Désolé, une erreur s'est produite (Code d'erreur : 011)");
        } else
            showMessage(this, "Problème survenu", "Désolé, une erreur s'est produite (Code d'erreur : 012)");

    }

    private void initViews() {
        phoneNumberEdt = findViewById(R.id.patient_phone_number);
        nameEdt = findViewById(R.id.patient_name);
        radioGroup = findViewById(R.id.patient_gender_radio_group);
        genderM = findViewById(R.id.patient_male_item);
        genderF = findViewById(R.id.patient_female_item);

        birthDateTxt = findViewById(R.id.patient_birth_date_edt_textview);
        birthDateDtPicker = findViewById(R.id.patient_birth_date_edt_datepicker);

        wilayaSpinner = findViewById(R.id.patient_wilaya_spinner);
        moughataaLayout = findViewById(R.id.patient_moughataa_layout);
        moughataaSpinner = findViewById(R.id.patient_moughataa_spinner);

        cancelBtn = findViewById(R.id.patient_cancel_btn);
        cancelBtn.setOnClickListener(this);
        continueBtn = findViewById(R.id.patient_continue_btn);
        continueBtn.setOnClickListener(this);
    }

    // Cette méthode permet de créer un adapter du spinner avec un seul élément qui est la wilaya ou la moughataa du patient
    // et  affiche la wilaya ou la moughataa sur le spinner via SetSelection
    private void initAdapterAndSpinner(ArrayAdapter<String> adapter, Spinner spinner, String item) {
        adapter = new ArrayAdapter<>(this, R.layout.custom_spinner_item, new String[]{item});
        spinner.setAdapter(adapter);
        spinner.setSelection(0);
        spinner.setEnabled(false);
    }

    // Cette méthode permet de remplir la liste des Wilaya et d'associer un ItemSelectedListener
    // pour ensuite appeler onItemSelected et assoier au spinner de Moughataa la liste des moughataas accosiés à la wilaya choisie
    private void initWilayaSpinner() {
        wilayaSpinner.setOnItemSelectedListener(this);
        wilayaSet = wilayasAndMoughataas.keySet();
        wilayaAdapter = new ArrayAdapter<>(this, R.layout.custom_spinner_item, wilayaSet.toArray(new String[wilayaSet.size()]));
        wilayaSpinner.setAdapter(wilayaAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.patient_wilaya_spinner) {
            moughataaLayout.setVisibility(wilayaSpinner.getSelectedItem().toString().equals(DEFAULT_WILAYA) ? GONE : VISIBLE);
            moughataas = wilayasAndMoughataas.get(wilayaSpinner.getSelectedItem());
            moughataaAdapter = new ArrayAdapter<>(this, R.layout.custom_spinner_item, moughataas.toArray(new String[moughataas.size()]));
            moughataaSpinner.setAdapter(moughataaAdapter);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.patient_cancel_btn) {
            finish();
        } else if (v.getId() == R.id.patient_continue_btn) {
            // Lancer le formulaire de covid19
        }
    }
}
