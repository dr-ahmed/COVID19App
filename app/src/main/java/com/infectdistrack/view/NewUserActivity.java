package com.infectdistrack.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
import com.infectdistrack.model.User;
import com.infectdistrack.presenter.NewUserController;

import java.util.ArrayList;
import java.util.Set;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.infectdistrack.model.Constants.ADMIN_LABEL;
import static com.infectdistrack.model.Constants.ADMIN_TYPE;
import static com.infectdistrack.model.Constants.DEFAULT_WILAYA;
import static com.infectdistrack.model.Constants.OTHER_ESTABLISHMENT;
import static com.infectdistrack.model.Constants.SUPER_ADMIN;
import static com.infectdistrack.model.Constants.USER_LABEL;
import static com.infectdistrack.model.Constants.USER_TYPE;
import static com.infectdistrack.model.Constants.setWilayasAndMoughataas;
import static com.infectdistrack.model.Constants.wilayasAndMoughataas;

public class NewUserActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private User parentUser;
    private static final String TAG = "NewUserActivity";
    private TextView newUserLabelTxt;
    private EditText newUserFullNameEdt, newUserEmailEdt, newUserPasswordEdt, newUserPasswordConfirmationEdt;
    private LinearLayout moughataaLayout;
    private Spinner newUserWilayaSpinner, newUserMoughataaSpinner, adminTypeSpinner, userTypeSpinner;
    private ArrayAdapter<String> wilayaAdapter, moughataaAdapter, adminAdapter, userAdapter;
    private EditText userTypeEdt;
    private RadioGroup newUserEstablishmentRadioGroup, publicEstablishmentDetails, privateEstablishmentDetails;
    private EditText otherEstablishmentEdt;
    private TextView establishmentTxt;
    private String establishmentCategory = "", establishmentType = "";
    private Button addNewUserBtn;

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

        adminTypeSpinner = findViewById(R.id.admin_type_spinner);
        adminAdapter = new ArrayAdapter<>(this, R.layout.custom_spinner_item, ADMIN_TYPE);
        adminTypeSpinner.setAdapter(adminAdapter);

        userTypeSpinner = findViewById(R.id.user_type_spinner);
        userAdapter = new ArrayAdapter<>(this, R.layout.custom_spinner_item, USER_TYPE);
        userTypeSpinner.setAdapter(userAdapter);
        userTypeSpinner.setOnItemSelectedListener(this);

        // Si le user est super admin, on fait disparaitre le spinner de user_type, sinon, on fait disparaitre le spinner de admin_type
        if (parentUser.getCategory().equals(SUPER_ADMIN))
            userTypeSpinner.setVisibility(GONE);
        else
            adminTypeSpinner.setVisibility(GONE);

        userTypeEdt = findViewById(R.id.user_type_editext);
        userTypeEdt.setVisibility(GONE);

        newUserEstablishmentRadioGroup = findViewById(R.id.new_user_establishment_radio_group);
        newUserEstablishmentRadioGroup.setOnCheckedChangeListener(this);

        establishmentTxt = findViewById(R.id.establishment_type_textview);
        establishmentTxt.setVisibility(GONE);

        publicEstablishmentDetails = findViewById(R.id.public_establishment_details);
        publicEstablishmentDetails.setOnCheckedChangeListener(this);
        publicEstablishmentDetails.setVisibility(GONE);

        privateEstablishmentDetails = findViewById(R.id.private_establishment_details);
        privateEstablishmentDetails.setOnCheckedChangeListener(this);
        privateEstablishmentDetails.setVisibility(GONE);

        otherEstablishmentEdt = findViewById(R.id.other_establishment_editext);
        otherEstablishmentEdt.setVisibility(GONE);

        addNewUserBtn = findViewById(R.id.add_new_user_btn);
        addNewUserBtn.setOnClickListener(this);
    }

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

    public String getNewUserTypeFromSpinner() {
        return userTypeSpinner.getSelectedItem().toString();
    }

    public String getNewUserTypeFromEdittext() {
        return userTypeEdt.getText().toString();
    }

    public String getEstablishmentCategory() {
        return establishmentCategory;
    }

    public String getEstablishmentType() {
        return establishmentType;
    }

    public String getOtherEstablishmentValue() {
        return otherEstablishmentEdt.getText().toString();
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
        switch (group.getId()) {
            case R.id.new_user_establishment_radio_group:
                if (establishmentTxt.getVisibility() == GONE)
                    establishmentTxt.setVisibility(VISIBLE);
                switch (checkedId) {
                    case R.id.public_establishment_item:
                        publicEstablishmentDetails.setVisibility(VISIBLE);
                        privateEstablishmentDetails.setVisibility(GONE);
                        otherEstablishmentEdt.setVisibility(GONE);
                        establishmentCategory = "Public";
                        break;
                    case R.id.private_establishment_item:
                        privateEstablishmentDetails.setVisibility(VISIBLE);
                        publicEstablishmentDetails.setVisibility(GONE);
                        otherEstablishmentEdt.setVisibility(GONE);
                        establishmentCategory = "Privé";
                        break;
                    case R.id.other_establishment_item:
                        otherEstablishmentEdt.setVisibility(VISIBLE);
                        privateEstablishmentDetails.setVisibility(GONE);
                        publicEstablishmentDetails.setVisibility(GONE);
                        establishmentCategory = OTHER_ESTABLISHMENT;
                }
                break;
            case R.id.public_establishment_details:
                // La réinitialisation de la variable establishmentType permet de ne pas compter la sélection deja faite dans privateEstablishmentDetails
                // lorsque l'un des radioButton de publicEstablishmentDetails est sélectionné et vice-versa
                establishmentType = "";
                switch (checkedId) {
                    case R.id.call_center_item:
                        establishmentType = "Centre d'appels 1155";
                        break;
                    case R.id.hospital_item:
                        establishmentType = "Hôpital";
                        break;
                    case R.id.health_center_item:
                        establishmentType = "Centre de santé";
                        break;
                    case R.id.public_labo_item:
                        establishmentType = "Laboratoire public";
                        break;
                    case R.id.research_unit_item:
                        establishmentType = "Unité de recherche";
                }
                break;
            case R.id.private_establishment_details:
                // La réinitialisation de la variable establishmentType permet de ne pas compter la sélection deja faite dans privateEstablishmentDetails
                // lorsque l'un des radioButton de publicEstablishmentDetails est sélectionné et vice-versa
                establishmentType = "";
                switch (checkedId) {
                    case R.id.clinic_item:
                        establishmentType = "Clinique";
                        break;
                    case R.id.cabinet_item:
                        establishmentType = "Cabinet";
                        break;
                    case R.id.private_labo_item:
                        establishmentType = "Laboratoire privé";
                        break;
                    case R.id.study_center_item:
                        establishmentType = "Centre d’études";
                }
        }
    }

    public void resetUIComponents() {
        newUserFullNameEdt.setText("");
        newUserEmailEdt.setText("");
        newUserPasswordEdt.setText("");
        newUserPasswordConfirmationEdt.setText("");

        newUserWilayaSpinner.setSelection(0);

        RadioButton callCenterRadioButton = findViewById(R.id.public_establishment_item);
        callCenterRadioButton.setChecked(false);
        RadioButton laboRadioButton = findViewById(R.id.private_establishment_item);
        laboRadioButton.setChecked(false);
        RadioButton otherRadioButton = findViewById(R.id.other_establishment_item);
        otherRadioButton.setChecked(false);
        establishmentType = "";

        newUserFullNameEdt.requestFocus();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.new_user_wilaya_spinner) {
            moughataaLayout.setVisibility(newUserWilayaSpinner.getSelectedItem().toString().equals(DEFAULT_WILAYA) ? GONE : VISIBLE);
            ArrayList<String> moughataas = wilayasAndMoughataas.get(newUserWilayaSpinner.getSelectedItem());
            moughataaAdapter = new ArrayAdapter<>(NewUserActivity.this, R.layout.custom_spinner_item, moughataas.toArray(new String[moughataas.size()]));
            newUserMoughataaSpinner.setAdapter(moughataaAdapter);
        } else if (parent.getId() == R.id.user_type_spinner) {
            if (userTypeSpinner.getSelectedItem().toString().equals(USER_TYPE[6])) {
                userTypeEdt.setVisibility(VISIBLE);
                userTypeEdt.requestFocus();
            } else
                userTypeEdt.setVisibility(GONE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
