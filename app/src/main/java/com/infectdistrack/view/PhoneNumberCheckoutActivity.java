package com.infectdistrack.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.infectdistrack.R;
import com.infectdistrack.model.Patient;
import com.infectdistrack.model.RetrievePatientDataUsingVolley;
import com.infectdistrack.model.Utilities;

import static com.infectdistrack.model.Constants.ASSOCIATED_ITEM;
import static com.infectdistrack.model.Constants.BUNDLE_EXTRA_TAG;
import static com.infectdistrack.model.Constants.DEVICE_NOT_CONNECTED_TO_INTERNET;
import static com.infectdistrack.model.Constants.NO_INTERNET_CONNECTION;
import static com.infectdistrack.model.Constants.OPTION_TAG;
import static com.infectdistrack.model.Constants.PATIENT_OBJECT_TAG;
import static com.infectdistrack.model.Constants.PHONE_NUMBER_TAG;
import static com.infectdistrack.model.Constants.UNIQUE_ITEM;
import static com.infectdistrack.model.Constants.YES;
import static com.infectdistrack.model.Utilities.isPhoneNumberValid;
import static com.infectdistrack.presenter.UIBasicController.hideProgressDialog;
import static com.infectdistrack.presenter.UIBasicController.showMessage;
import static com.infectdistrack.presenter.UIBasicController.showProgressDialog;

public class PhoneNumberCheckoutActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private static final String TAG = "PhoneNumberCheckoutActi";

    private EditText patientPhoneNumberEdt;
    private RadioGroup patientIdTypeRadioGroup;
    private Button checkoutBtn;
    private String selectedItem = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_phone_number_checkout);

        initViews();
        patientPhoneNumberEdt.requestFocus();
    }

    private void initViews() {
        patientPhoneNumberEdt = findViewById(R.id.patient_phone_number_checkout);
        patientIdTypeRadioGroup = findViewById(R.id.patient_id_radio_group);
        patientIdTypeRadioGroup.setOnCheckedChangeListener(this);
        checkoutBtn = findViewById(R.id.verify_patient_btn);
        checkoutBtn.setOnClickListener(this);
    }

    public String getPhoneNumber() {
        return patientPhoneNumberEdt.getText().toString();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (group.getId() == R.id.patient_id_radio_group) {
            switch (checkedId) {
                case R.id.associated_item:
                    selectedItem = ASSOCIATED_ITEM;
                    break;
                case R.id.unique_item:
                    selectedItem = UNIQUE_ITEM;
                    break;
                default: {

                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.verify_patient_btn) {
            String phoneNumberFeedback = isPhoneNumberValid(patientPhoneNumberEdt.getText().toString());
            if (!phoneNumberFeedback.equals(YES)) {
                patientPhoneNumberEdt.requestFocus();
                patientPhoneNumberEdt.setError(phoneNumberFeedback);
                return;
            }

            if (selectedItem.isEmpty()) {
                showMessage(this, "Choix obligatoire", "Veuillez préciser si cet identifiant est unique ou associé !");
                return;
            }

            if (Utilities.checkInternetConnectionAvailability().equals(DEVICE_NOT_CONNECTED_TO_INTERNET)) {
                showMessage(this, "Pas de connexion internet", "Merci de vérifier votre connexion internet!");
                return;
            }

            showProgressDialog(this, "Vérification de l'identifiant en cours ...");

            RetrievePatientDataUsingVolley retrievePatientDataUsingVolley = new RetrievePatientDataUsingVolley(this);
            retrievePatientDataUsingVolley.setRequestType(selectedItem);
            retrievePatientDataUsingVolley.getDataFromServer();

        }
    }

    public void getPatientPhoneNumberFromVolley(String exceptionInfo, boolean isAlreadyRegistrated, Patient patient) {
        hideProgressDialog();

        if (exceptionInfo.isEmpty()) {
            if (isAlreadyRegistrated)
                whenPatientIsAlreadyRegistrated(patient);
            else
                whenPatientIsNotYetRegistrated(patient);
        } else {
            if (exceptionInfo.equals(NO_INTERNET_CONNECTION))
                Toast.makeText(this, "Veuillez vérifier votre connexion internet !", Toast.LENGTH_LONG).show();
            else // show exceptionInfo for more details !
                showMessage(this, "Problème survenu", "Désolé, une erreur s'est produite (Code d'erreur : 008)");
        }
    }

    private void whenPatientIsAlreadyRegistrated(Patient patient) {
        if (selectedItem.equals(ASSOCIATED_ITEM)) { // demander au user de confirmer le numéro d'association
            int associatedNumber;
            String associatedPhoneNumber;
            if (patient.getPhoneNumber().length() == 8) {
                associatedNumber = 1;
                associatedPhoneNumber = patient.getPhoneNumber().concat(String.valueOf(associatedNumber));

                patient.setPhoneNumber(associatedPhoneNumber);
                doYouConfirmTheAssociatedNumber(patient, 0);
            } else if (patient.getPhoneNumber().length() == 9) {
                associatedNumber = Integer.parseInt(patient.getPhoneNumber().substring(patient.getPhoneNumber().length() - 1));
                if (associatedNumber == 9)
                    showMessage(this, "Identifiant saturé", "Savez-vous que 9 identifiants sont déjà associés à celui-ci ?" +
                            " Vous ne pouvez donc plus lui associer des identifiants. Veuillez saisir un identifiant différent.");
                else {
                    associatedNumber++;
                    String patientPhoneNumberWithoutAssociationNumber = patient.getPhoneNumber().substring(0, patient.getPhoneNumber().length() - 1);
                    associatedPhoneNumber = patientPhoneNumberWithoutAssociationNumber.concat(String.valueOf(associatedNumber));
                    patient.setPhoneNumber(associatedPhoneNumber);

                    doYouConfirmTheAssociatedNumber(patient, associatedNumber - 1);
                }
            } else {
                Toast.makeText(this, "Invalid ID length !", Toast.LENGTH_SHORT).show();
            }
        } else
            doYouToContinueWithSuchUniquePhoneNumber(patient);
    }

    private void whenPatientIsNotYetRegistrated(Patient patient) {
        if (selectedItem.equals(ASSOCIATED_ITEM)) {
            // informer le user que l'option associer ne peut etre utilsié avec un id n'existant pas deja
            showMessage(this, "Identifiant introuvable", "Cet identifiant n'existe pas encore. Vous ne pouvez pas donc utiliser l'option \"Associé\"." +
                    " Veuillez sélectionner l'option \"Unique\" si vous voulez ajouter cet identifiant ou saisir un identifiant déjà existant en cas d'association.");
        } else {
            // diriger le user vers FragmentDetails vierge
            Intent intent = new Intent(this, PhoneNumberDetailsActivity.class);
            intent.putExtra(PHONE_NUMBER_TAG, patientPhoneNumberEdt.getText().toString());
            intent.putExtra(OPTION_TAG, UNIQUE_ITEM);
            startActivity(intent);
        }
    }

    public void doYouConfirmTheAssociatedNumber(Patient patient, int associatedNumber) {
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
        DialogFragmentAboutConfirmingAssociatedPhoneNumber dialog =
                new DialogFragmentAboutConfirmingAssociatedPhoneNumber(this, patient, associatedNumber);

        ft.add(dialog, "dialogFragmentAboutConfirmingAssociatedPhoneNumber");
        ft.commit();
    }

    public void doYouToContinueWithSuchUniquePhoneNumber(Patient patient) {
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
        DialogFragmentAboutPhoneNumberConfirmation dialog =
                new DialogFragmentAboutPhoneNumberConfirmation(this, patient);

        ft.add(dialog, "dialogFragmentAboutConfirmingAssociatedPhoneNumber");
        ft.commit();
    }


    public void openPhoneNumberDetailsFragmentAndInflatePatientObject(Patient patient, String option) {
        // diriger le user vers FragmentDetails vierge
        Intent intent = new Intent(this, PhoneNumberDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(PATIENT_OBJECT_TAG, patient);
        bundle.putString(OPTION_TAG, option);
        intent.putExtra(BUNDLE_EXTRA_TAG, bundle);
        startActivity(intent);
    }
}
