package com.infectdistrack.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.infectdistrack.R;
import com.infectdistrack.model.Constants;
import com.infectdistrack.model.User;
import com.infectdistrack.presenter.NewUserController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.infectdistrack.model.Constants.ADMIN_LABEL;
import static com.infectdistrack.model.Constants.DEFAULT_WILAYA;
import static com.infectdistrack.model.Constants.SUPER_ADMIN;
import static com.infectdistrack.model.Constants.USER_LABEL;
import static com.infectdistrack.model.Constants.setWilayasAndMoughataas;
import static com.infectdistrack.model.Constants.wilayasAndMoughataas;

public class NewUserActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private User parentUser;
    private static final String TAG = "NewUserActivity";
    private TextView newUserLabelTxt;
    private EditText newUserFullNameEdt, newUserEmailEdt, newUserPasswordEdt, newUserPasswordConfirmationEdt;
    private LinearLayout moughataaLayout;
    private Spinner newUserWilayaSpinner, newUserMoughataaSpinner;
    private ArrayAdapter<String> wilayaAdapter, moughataaAdapter;
    private RadioGroup newUserEstablishmentRadioGroup;
    private String establishmentType = "";
    private Button addNewUserBtn;

    public User getParentUser() {
        return parentUser;
    }

    public EditText getNewUserFullNameEdt() {
        return newUserFullNameEdt;
    }

    public EditText getNewUserEmailEdt() {
        return newUserEmailEdt;
    }

    public EditText getNewUserPasswordEdt() {
        return newUserPasswordEdt;
    }

    public EditText getNewUserPasswordConfirmationEdt() {
        return newUserPasswordConfirmationEdt;
    }

    public String getNewUserWilaya() {
        return newUserWilayaSpinner.getSelectedItem().toString();
    }

    public String getNewUserMoughataa() {
        return newUserMoughataaSpinner.getSelectedItem().toString();
    }

    public String getEstablishmentType() {
        return establishmentType;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        getUserDataFromHomeActivity();
        setWilayasAndMoughataas();
        initViews(wilayasAndMoughataas.keySet());
    }

    private void initViews(Set<String> wilayaSet) {
        newUserLabelTxt = findViewById(R.id.new_user_label_txt);
        newUserLabelTxt.setText("Informations du nouvel ".concat(parentUser.getCategory().equals(SUPER_ADMIN) ? ADMIN_LABEL : USER_LABEL));

        newUserFullNameEdt = findViewById(R.id.new_user_full_name_edt);
        newUserEmailEdt = findViewById(R.id.new_user_email_edt);
        newUserPasswordEdt = findViewById(R.id.new_user_password_edt);
        newUserPasswordConfirmationEdt = findViewById(R.id.new_user_password_confirmation_edt);

        newUserWilayaSpinner = findViewById(R.id.new_user_wilaya_spinner);
        wilayaAdapter = new ArrayAdapter<>(this, R.layout.custom_spinner_item, wilayaSet.toArray(new String[wilayaSet.size()]));
        newUserWilayaSpinner.setAdapter(wilayaAdapter);
        newUserWilayaSpinner.setOnItemSelectedListener(this);

        moughataaLayout = findViewById(R.id.moughataa_layout);
        newUserMoughataaSpinner = findViewById(R.id.new_user_moughataa_spinner);

        newUserEstablishmentRadioGroup = findViewById(R.id.new_user_establishment_radio_group);
        newUserEstablishmentRadioGroup.setOnCheckedChangeListener(this);

        addNewUserBtn = findViewById(R.id.add_new_user_btn);
        addNewUserBtn.setOnClickListener(this);
    }

    private void getUserDataFromHomeActivity() {
        Bundle bundle = getIntent().getBundleExtra("parentUserBundle");
        parentUser = bundle.getParcelable("parentUser");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.add_new_user_btn) {
            NewUserController newUserController = new NewUserController(this);
            newUserController.onClickAddNewUserButtonController();
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (group.getId() == R.id.new_user_establishment_radio_group) {
            switch (checkedId) {
                case R.id.call_center_item:
                    establishmentType = "Centre d'appels";
                    break;
                case R.id.labo_item:
                    establishmentType = "Laboratoire";
                    break;
                case R.id.other_establishment_item:
                    establishmentType = "Autre";
            }
        }
    }

    public void resetUIComponents() {
        newUserFullNameEdt.setText("");
        newUserEmailEdt.setText("");
        newUserPasswordEdt.setText("");
        newUserPasswordConfirmationEdt.setText("");

        newUserWilayaSpinner.setSelection(0);

        RadioButton callCenterRadioButton = findViewById(R.id.call_center_item);
        callCenterRadioButton.setChecked(false);
        RadioButton laboRadioButton = findViewById(R.id.labo_item);
        laboRadioButton.setChecked(false);
        RadioButton otherRadioButton = findViewById(R.id.other_establishment_item);
        otherRadioButton.setChecked(false);
        establishmentType = "";

        newUserFullNameEdt.requestFocus();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        moughataaLayout.setVisibility(newUserWilayaSpinner.getSelectedItem().toString().equals(DEFAULT_WILAYA) ? View.GONE : View.VISIBLE);
        ArrayList<String> moughataas = wilayasAndMoughataas.get(newUserWilayaSpinner.getSelectedItem());
        moughataaAdapter = new ArrayAdapter<String>(NewUserActivity.this, R.layout.custom_spinner_item, moughataas.toArray(new String[moughataas.size()]));
        newUserMoughataaSpinner.setAdapter(moughataaAdapter);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
