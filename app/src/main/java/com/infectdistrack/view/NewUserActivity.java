package com.infectdistrack.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import static com.infectdistrack.model.Constants.ADMIN_TYPE_LIST;
import static com.infectdistrack.model.Constants.DEFAULT_WILAYA;
import static com.infectdistrack.model.Constants.OTHER_ESTABLISHMENT;
import static com.infectdistrack.model.Constants.PASSWORD_SIZE;
import static com.infectdistrack.model.Constants.PRIVATE_ESTABLISHMENT;
import static com.infectdistrack.model.Constants.PUBLIC_ESTABLISHMENT;
import static com.infectdistrack.model.Constants.SUPER_ADMIN;
import static com.infectdistrack.model.Constants.USER_LABEL;
import static com.infectdistrack.model.Constants.USER_TYPE_LIST;
import static com.infectdistrack.model.Constants.setWilayasAndMoughataas;
import static com.infectdistrack.model.Constants.wilayasAndMoughataas;
import static com.infectdistrack.model.Utilities.generteNewPassword;

public class NewUserActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private User parentUser, newUser;
    private static final String TAG = "NewUserActivity";
    private TextView newUserLabelTxt;
    private EditText newUserFullNameEdt, newUserEmailEdt;
    private LinearLayout moughataaLayout;
    private Spinner newUserWilayaSpinner, newUserMoughataaSpinner, userTypeSpinner;
    private ArrayAdapter<String> wilayaAdapter, moughataaAdapter, userAdapter;
    private RadioGroup newUserEstablishmentRadioGroup, publicEstablishmentRadioGroup, privateEstablishmentRadioGroup;
    private EditText userTypeEdt, otherEstablishmentCategoryEdt;
    private TextView establishmentTxt;
    private String establishmentType = "";
    private String publicEstablishmentCategory = "", privateEstablishmentCategory = "";

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

        newUserWilayaSpinner = findViewById(R.id.new_user_wilaya_spinner);
        wilayaAdapter = new ArrayAdapter<>(this, R.layout.custom_spinner_item, wilayaSet.toArray(new String[wilayaSet.size()]));
        newUserWilayaSpinner.setAdapter(wilayaAdapter);
        newUserWilayaSpinner.setOnItemSelectedListener(this);

        moughataaLayout = findViewById(R.id.new_user_moughataa_layout);
        newUserMoughataaSpinner = findViewById(R.id.new_user_moughataa_spinner);

        userTypeSpinner = findViewById(R.id.user_type_spinner);
        userAdapter = new ArrayAdapter<>(this, R.layout.custom_spinner_item, parentUser.getCategory().equals(SUPER_ADMIN) ? ADMIN_TYPE_LIST : USER_TYPE_LIST);
        userTypeSpinner.setAdapter(userAdapter);
        userTypeSpinner.setOnItemSelectedListener(this);

        userTypeEdt = findViewById(R.id.user_type_editext);
        userTypeEdt.setVisibility(GONE);

        newUserEstablishmentRadioGroup = findViewById(R.id.new_user_establishment_radio_group);
        newUserEstablishmentRadioGroup.setOnCheckedChangeListener(this);

        establishmentTxt = findViewById(R.id.establishment_type_textview);
        establishmentTxt.setVisibility(GONE);

        publicEstablishmentRadioGroup = findViewById(R.id.public_establishment_details);
        publicEstablishmentRadioGroup.setOnCheckedChangeListener(this);
        publicEstablishmentRadioGroup.setVisibility(GONE);

        privateEstablishmentRadioGroup = findViewById(R.id.private_establishment_details);
        privateEstablishmentRadioGroup.setOnCheckedChangeListener(this);
        privateEstablishmentRadioGroup.setVisibility(GONE);

        otherEstablishmentCategoryEdt = findViewById(R.id.other_establishment_editext);
        otherEstablishmentCategoryEdt.setVisibility(GONE);

        Button addNewUserBtn = findViewById(R.id.add_new_user_btn);
        addNewUserBtn.setOnClickListener(this);
    }

    public User getParentUser() {
        return parentUser;
    }

    public User getNewUser() {
        return newUser;
    }

    public EditText getNewUserFullNameEdt() {
        return newUserFullNameEdt;
    }

    public EditText getNewUserEmailEdt() {
        return newUserEmailEdt;
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

    public String getEstablishmentType() {
        return establishmentType;
    }

    public String getPublicEstablishmentCategory() {
        return publicEstablishmentCategory;
    }

    public String getPrivateEstablishmentCategory() {
        return privateEstablishmentCategory;
    }

    public String getOtherEstablishmentCategory() {
        return otherEstablishmentCategoryEdt.getText().toString();
    }

    private void getUserDataFromHomeActivity() {
        Bundle bundle = getIntent().getBundleExtra("parentUserBundle");
        parentUser = bundle.getParcelable("parentUser");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.add_new_user_btn) {
            String userType = userTypeSpinner.getSelectedItem().toString().equals(USER_TYPE_LIST[6])
                    ? userTypeEdt.getText().toString()
                    : userTypeSpinner.getSelectedItem().toString();

            String establishmentCategory;
            if (!publicEstablishmentCategory.isEmpty())
                establishmentCategory = publicEstablishmentCategory;
            else if (!privateEstablishmentCategory.isEmpty())
                establishmentCategory = privateEstablishmentCategory;
            else
                establishmentCategory = otherEstablishmentCategoryEdt.getText().toString();

            newUser = new User(newUserFullNameEdt.getText().toString(), newUserEmailEdt.getText().toString(), generteNewPassword(PASSWORD_SIZE),
                    getNewUserWilaya(), getNewUserMoughataa(), userType, establishmentType, establishmentCategory);
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
                        // Ceci permet de réinitialiser le type de l'établissement et le radioGroup si le user bascule de Privé vers Public
                        privateEstablishmentRadioGroup.clearCheck();
                        privateEstablishmentCategory = "";

                        publicEstablishmentRadioGroup.setVisibility(VISIBLE);
                        privateEstablishmentRadioGroup.setVisibility(GONE);
                        otherEstablishmentCategoryEdt.setVisibility(GONE);
                        establishmentType = PUBLIC_ESTABLISHMENT;

                        break;
                    case R.id.private_establishment_item:
                        // Ceci permet de réinitialiser le type de l'établissement et le radioGroup si le user bascule de Public vers Privé
                        publicEstablishmentRadioGroup.clearCheck();
                        publicEstablishmentCategory = "";

                        privateEstablishmentRadioGroup.setVisibility(VISIBLE);
                        publicEstablishmentRadioGroup.setVisibility(GONE);
                        otherEstablishmentCategoryEdt.setVisibility(GONE);
                        establishmentType = PRIVATE_ESTABLISHMENT;
                        break;
                    case R.id.other_establishment_item:
                        // Ceci permet de réinitialiser le type de l'établissement et le radioGroup si le user bascule de Public ou Privé vers Autre
                        publicEstablishmentRadioGroup.clearCheck();
                        publicEstablishmentCategory = "";
                        privateEstablishmentRadioGroup.clearCheck();
                        privateEstablishmentCategory = "";

                        otherEstablishmentCategoryEdt.setVisibility(VISIBLE);
                        privateEstablishmentRadioGroup.setVisibility(GONE);
                        publicEstablishmentRadioGroup.setVisibility(GONE);
                        establishmentType = OTHER_ESTABLISHMENT;
                }
                break;
            case R.id.public_establishment_details:
                switch (checkedId) {
                    case R.id.call_center_item:
                        publicEstablishmentCategory = "Centre d'appels 1155";
                        break;
                    case R.id.hospital_item:
                        publicEstablishmentCategory = "Hôpital";
                        break;
                    case R.id.health_center_item:
                        publicEstablishmentCategory = "Centre de santé";
                        break;
                    case R.id.public_labo_item:
                        publicEstablishmentCategory = "Laboratoire";
                        break;
                    case R.id.research_unit_item:
                        publicEstablishmentCategory = "Unité de recherche";
                }
                break;
            case R.id.private_establishment_details:
                switch (checkedId) {
                    case R.id.clinic_item:
                        privateEstablishmentCategory = "Clinique";
                        break;
                    case R.id.cabinet_item:
                        privateEstablishmentCategory = "Cabinet";
                        break;
                    case R.id.private_labo_item:
                        privateEstablishmentCategory = "Laboratoire";
                        break;
                    case R.id.study_center_item:
                        privateEstablishmentCategory = "Centre d’études";
                }
        }
    }

    public void resetUIComponents() {
        newUserFullNameEdt.setText("");
        newUserEmailEdt.setText("");

        newUserWilayaSpinner.setSelection(0);
        userTypeSpinner.setSelection(0);
        userTypeEdt.setText("");
        userTypeEdt.setVisibility(GONE);

        newUserEstablishmentRadioGroup.clearCheck();
        establishmentType = "";

        establishmentTxt.setVisibility(GONE);

        publicEstablishmentRadioGroup.clearCheck();
        publicEstablishmentRadioGroup.setVisibility(GONE);
        publicEstablishmentCategory = "";

        privateEstablishmentRadioGroup.clearCheck();
        privateEstablishmentRadioGroup.setVisibility(GONE);
        privateEstablishmentCategory = "";

        otherEstablishmentCategoryEdt.setText("");
        otherEstablishmentCategoryEdt.setVisibility(GONE);

        newUserFullNameEdt.requestFocus();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.new_user_wilaya_spinner) {
            moughataaLayout.setVisibility(newUserWilayaSpinner.getSelectedItem().toString().equals(DEFAULT_WILAYA) ? GONE : VISIBLE);
            ArrayList<String> moughataas = wilayasAndMoughataas.get(newUserWilayaSpinner.getSelectedItem());
            moughataaAdapter = new ArrayAdapter<>(this, R.layout.custom_spinner_item, moughataas.toArray(new String[moughataas.size()]));
            newUserMoughataaSpinner.setAdapter(moughataaAdapter);
        } else if (parent.getId() == R.id.user_type_spinner) {
            if (userTypeSpinner.getSelectedItem().toString().equals(USER_TYPE_LIST[6])) {
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
