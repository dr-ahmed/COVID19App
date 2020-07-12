package com.infectdistrack.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.infectdistrack.R;
import com.infectdistrack.model.Patient;
import com.infectdistrack.model.RetrievePatientDataUsingVolley;

import static com.infectdistrack.model.Constants.ASSOCIATED_ITEM;
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

public class PhoneNumberCheckoutFragment extends Fragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private static final String TAG = "PhoneNumberCheckoutFrag";

    private View rootView;
    private EditText patientPhoneNumberEdt;
    private RadioGroup patientIdTypeRadioGroup;
    private Button checkoutBtn;
    private String selectedItem = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_phone_number_checkout, container, false);
        initViews();

        return rootView;
    }

    private void initViews() {
        patientPhoneNumberEdt = rootView.findViewById(R.id.patient_phone_number_checkout);
        patientIdTypeRadioGroup = rootView.findViewById(R.id.patient_id_radio_group);
        patientIdTypeRadioGroup.setOnCheckedChangeListener(this);
        checkoutBtn = rootView.findViewById(R.id.verify_patient_btn);
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
                showMessage(getActivity(), "Choix obligatoire", "Veuillez préciser si cet identifiant est unique ou associé !");
                return;
            }

            showProgressDialog(getActivity(), "Vérification de l'identifiant en cours ...");

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
                Toast.makeText(getActivity(), "Veuillez vérifier votre connexion internet !", Toast.LENGTH_LONG).show();
            else // show exceptionInfo for more details !
                showMessage(getActivity(), "Problème survenu", "Désolé, une erreur s'est produite (Code d'erreur : 008)");
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
                    showMessage(getActivity(), "Identifiant saturé", "Savez-vous que 9 identifiants sont déjà associés à celui-ci ?" +
                            " Vous ne pouvez donc plus lui associer des identifiants. Veuillez saisir un identifiant différent.");
                else {
                    associatedNumber++;
                    String patientPhoneNumberWithoutAssociationNumber = patient.getPhoneNumber().substring(0, patient.getPhoneNumber().length() - 1);
                    associatedPhoneNumber = patientPhoneNumberWithoutAssociationNumber.concat(String.valueOf(associatedNumber));
                    patient.setPhoneNumber(associatedPhoneNumber);

                    doYouConfirmTheAssociatedNumber(patient, associatedNumber - 1);
                }
            } else {
                Toast.makeText(getActivity(), "Invalid ID length !", Toast.LENGTH_SHORT).show();
            }
        } else
            doYouToContinueWithSuchUniquePhoneNumber(patient);
    }

    private void whenPatientIsNotYetRegistrated(Patient patient) {
        if (selectedItem.equals(ASSOCIATED_ITEM)) {
            // informer le user que l'option associer ne peut etre utilsié avec un id n'existant pas deja
            showMessage(getActivity(), "Identifiant introuvable", "Cet identifiant n'existe pas encore. Vous ne pouvez pas donc utiliser l'option \"Associé\"." +
                    " Veuillez sélectionner l'option \"Unique\" si vous voulez ajouter cet identifiant ou saisir un identifiant déjà existant en cas d'association.");
        } else {
            // diriger le user vers FragmentDetails vierge
            PhoneNumberDetailsFragment phoneNumberDetailsFragment = new PhoneNumberDetailsFragment(patient);
            Bundle bundle = new Bundle();
            bundle.putString(PHONE_NUMBER_TAG, patientPhoneNumberEdt.getText().toString());
            bundle.putString(OPTION_TAG, UNIQUE_ITEM);
            phoneNumberDetailsFragment.setArguments(bundle);
            FragmentTransaction fragTransaction = getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_layout, phoneNumberDetailsFragment, "phoneNumberDetailsFragment");
            fragTransaction.commit();
        }
    }

    public void doYouConfirmTheAssociatedNumber(Patient patient, int associatedNumber) {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        DialogFragmentAboutConfirmingAssociatedPhoneNumber dialog =
                new DialogFragmentAboutConfirmingAssociatedPhoneNumber(this, patient, associatedNumber);

        ft.add(dialog, "dialogFragmentAboutConfirmingAssociatedPhoneNumber");
        ft.commit();
    }

    public void doYouToContinueWithSuchUniquePhoneNumber(Patient patient) {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        DialogFragmentAboutPhoneNumberConfirmation dialog =
                new DialogFragmentAboutPhoneNumberConfirmation(this, patient);

        ft.add(dialog, "dialogFragmentAboutConfirmingAssociatedPhoneNumber");
        ft.commit();
    }


    public void openPhoneNumberDetailsFragmentAndInflatePatientObject(Patient patient, String option) {
        // diriger le user vers FragmentDetails vierge
        PhoneNumberDetailsFragment phoneNumberDetailsFragment = new PhoneNumberDetailsFragment(patient);
        Bundle bundle = new Bundle();
        bundle.putParcelable(PATIENT_OBJECT_TAG, patient);
        bundle.putString(OPTION_TAG, option);
        phoneNumberDetailsFragment.setArguments(bundle);
        FragmentTransaction fragTransaction = getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_layout, phoneNumberDetailsFragment, "phoneNumberDetailsFragment");
        fragTransaction.commit();
    }
}
