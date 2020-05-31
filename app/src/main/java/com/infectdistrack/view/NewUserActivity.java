package com.infectdistrack.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.infectdistrack.R;
import com.infectdistrack.model.User;
import com.infectdistrack.presenter.NewUserController;

import static com.infectdistrack.model.Constants.WILAYAS;

public class NewUserActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private User parentUser;
    private static final String TAG = "NewUserActivity";
    private TextView newUserLabelTxt;
    private EditText newUserFullNameEdt, newUserEmailEdt, newUserPasswordEdt, newUserPasswordConfirmationEdt;
    private Spinner newUserWilayaSpinner;
    private ArrayAdapter<String> adapter;
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

    public Spinner getNewUserWilayaSpinner() {
        return newUserWilayaSpinner;
    }

    public String getEstablishmentType() {
        return establishmentType;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        initViews();
        getUserDataFromHomeActivity();
    }

    private void initViews() {
        newUserLabelTxt = findViewById(R.id.new_user_label_txt);
        newUserLabelTxt.setText("Informations du nouvel utilisateur");

        newUserFullNameEdt = findViewById(R.id.new_user_full_name_edt);
        newUserEmailEdt = findViewById(R.id.new_user_email_edt);
        newUserPasswordEdt = findViewById(R.id.new_user_password_edt);
        newUserPasswordConfirmationEdt = findViewById(R.id.new_user_password_confirmation_edt);

        newUserWilayaSpinner = findViewById(R.id.new_user_wilaya_spinner);
        adapter = new ArrayAdapter<>(this, R.layout.custom_spinner_item, WILAYAS);
        newUserWilayaSpinner.setAdapter(adapter);

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
                case R.id.other_item:
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
        RadioButton otherRadioButton = findViewById(R.id.other_item);
        otherRadioButton.setChecked(false);
    }
}
