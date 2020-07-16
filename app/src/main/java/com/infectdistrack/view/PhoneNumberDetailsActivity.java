package com.infectdistrack.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.infectdistrack.R;
import com.infectdistrack.model.Patient;

import java.util.ArrayList;
import java.util.Set;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.infectdistrack.model.Constants.ASSOCIATED_ITEM;
import static com.infectdistrack.model.Constants.BUNDLE_EXTRA_TAG;
import static com.infectdistrack.model.Constants.DEFAULT_WILAYA;
import static com.infectdistrack.model.Constants.EMPTY_STRING;
import static com.infectdistrack.model.Constants.OPTION_TAG;
import static com.infectdistrack.model.Constants.PATIENT_OBJECT_TAG;
import static com.infectdistrack.model.Constants.PHONE_NUMBER_TAG;
import static com.infectdistrack.model.Constants.UNIQUE_ITEM;
import static com.infectdistrack.model.Constants.setWilayasAndMoughataas;
import static com.infectdistrack.model.Constants.wilayasAndMoughataas;
import static com.infectdistrack.presenter.UIBasicController.showMessage;

public class PhoneNumberDetailsActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener,
        AdapterView.OnItemSelectedListener, View.OnClickListener, DatePicker.OnDateChangedListener {

    private static final String TAG = "PhoneNumberDetailsActi";

    private EditText phoneNumberEdt, nameEdt;
    private RadioGroup radioGroup;
    private String patientGender = "";
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

    private boolean isUserUnique = false;
    private boolean isDateChanged = false;

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
            String phoneNumber = getIntent().getStringExtra(PHONE_NUMBER_TAG);
            phoneNumberEdt.setText(phoneNumber);
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

                    isUserUnique = true;
                } else // Si le user choisit l'option Associé, on affiche le phone number dans l'edittext et on le désactive. Les autres Views restent enabled
                    if (bundle.getString(OPTION_TAG).equals(ASSOCIATED_ITEM)) {
                        phoneNumberEdt.setText(patient.getPhoneNumber());
                        setMaxLengthForPhoneNumberEditText(9);
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
        radioGroup.setOnCheckedChangeListener(this);
        genderM = findViewById(R.id.patient_male_item);
        genderF = findViewById(R.id.patient_female_item);

        birthDateTxt = findViewById(R.id.patient_birth_date_edt_textview);
        birthDateDtPicker = findViewById(R.id.patient_birth_date_edt_datepicker);
        birthDateDtPicker.init(1960, 10, 28, this);

        wilayaSpinner = findViewById(R.id.patient_wilaya_spinner);
        moughataaLayout = findViewById(R.id.patient_moughataa_layout);
        moughataaSpinner = findViewById(R.id.patient_moughataa_spinner);

        cancelBtn = findViewById(R.id.patient_cancel_btn);
        cancelBtn.setOnClickListener(this);
        continueBtn = findViewById(R.id.patient_continue_btn);
        continueBtn.setOnClickListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.patient_male_item)
            patientGender = "M";
        else if (checkedId == R.id.patient_female_item)
            patientGender = "F";
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
        if (v.getId() == R.id.patient_cancel_btn)
            finish();
        else if (v.getId() == R.id.patient_continue_btn) {
            /*
                On peut basculer vers le formulaire de COVID-19 dans 3 cas :
                1. si le user veut associer un nouveau identifiant à un identifiant deja existant
                2. si le user veut remplir un nouveau formulaire pour un nouveau patient
                3. si le user veut remplir un nouveau formulaire pour un patient deja existant

                Pour s'assurer que les infos du patient sont deja fournies avant de basculer vers le formulaire de COVID-19, on vérifie :
                - S'il s'agit de 3., on bascule.
                - S'il s'agit de 1. ou 2., on vérifie si tous les champs sont remplis:
                    - Si oui, on bascule
                    - Sinon, on alerte le user
             */

            if (patient == null)
                patient = new Patient();

            if (isUserUnique || areAllViewsFilled())
                launchCovid19Activity();
        }
    }

    private void launchCovid19Activity() {
        //Log.e(TAG, "patient data : " + patient.toString());

        Intent intent = new Intent(this, Covid19FormActivity.class);
        intent.putExtra(PATIENT_OBJECT_TAG, patient);
        startActivity(intent);
    }

    private boolean areAllViewsFilled() {

        if (phoneNumberEdt.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "ID invalide !", Toast.LENGTH_SHORT).show();
            return false;
        } else
            patient.setPhoneNumber(phoneNumberEdt.getText().toString());

        if (nameEdt.getText().toString().trim().isEmpty()) {
            nameEdt.requestFocus();
            nameEdt.setError("Le nom du patient est obligatoire!");
            return false;
        } else
            patient.setName(nameEdt.getText().toString());

        if (patientGender.isEmpty()) {
            showMessage(this, "Genre obligatoire", "Veuillez préciser le genre du patient");
            return false;
        } else
            patient.setGender(patientGender);

        if (birthDateTxt.getVisibility() == VISIBLE)
            patient.setDateOfBirth(patientGender.isEmpty() ? "NONE!!!" : birthDateTxt.getText().toString());
        else if (isDateChanged)
            patient.setDateOfBirth(birthDateDtPicker.getDayOfMonth() + "/" + (birthDateDtPicker.getMonth() + 1) + "/" + birthDateDtPicker.getYear());
        else {
            showMessage(this, "Date obligatoire", "Veuillez préciser la date du patient");
            return false;
        }

        if (wilayaSpinner.getSelectedItem().equals(DEFAULT_WILAYA)) {
            showMessage(this, "Wilaya obligatoire", "Veuillez préciser la wilaya du patient");
            return false;
        } else
            patient.setWilaya(wilayaSpinner.getSelectedItem().toString());

        if (moughataaSpinner.getSelectedItem().equals(EMPTY_STRING)) {
            showMessage(this, "Moughataa obligatoire", "Veuillez préciser la moughataa du patient");
            return false;
        } else
            patient.setMoughataa(moughataaSpinner.getSelectedItem().toString());

        return true;
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        if (view.getId() == R.id.patient_birth_date_edt_datepicker)
            isDateChanged = true;
    }
}
