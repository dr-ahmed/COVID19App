package com.infectdistrack.presenter;

import android.os.Bundle;

import androidx.fragment.app.FragmentTransaction;

import com.infectdistrack.model.Covid19Form;
import com.infectdistrack.model.Covid19NewFormAsyncTask;
import com.infectdistrack.model.Patient;
import com.infectdistrack.model.Utilities;
import com.infectdistrack.view.Covid19FormActivity;
import com.infectdistrack.view.Covid19FormPart4;
import com.infectdistrack.view.DialogFragmentForGoingBackAfterAddingForm;

import static com.infectdistrack.model.Constants.DEVICE_NOT_CONNECTED_TO_INTERNET;
import static com.infectdistrack.model.Constants.SOCKET_TIMEOUT_EXCEPTION;
import static com.infectdistrack.model.Utilities.getTodayDate;
import static com.infectdistrack.model.Utilities.replaceApostrophe;
import static com.infectdistrack.presenter.UIBasicController.hideProgressDialog;
import static com.infectdistrack.presenter.UIBasicController.showMessage;
import static com.infectdistrack.presenter.UIBasicController.showProgressDialog;

public class Covid19NewFormController {

    private static final String TAG = "CovidFormController";

    private Covid19FormActivity covid19FormActivity;
    private Covid19FormPart4 covid19FormPart4;
    private Patient patient;
    private Integer parentUser;
    private Covid19Form covid19Form;

    public Covid19NewFormController(Covid19FormPart4 covid19FormPart4) {
        this.covid19FormPart4 = covid19FormPart4;
        covid19FormActivity = (Covid19FormActivity) covid19FormPart4.getActivity();
        patient = covid19FormActivity.getPatient();
        parentUser = covid19FormActivity.getParentUser().getId();
        covid19Form = covid19FormPart4.getFinalCovid19FormResult();
    }

    public Covid19FormPart4 getCovid19FormPart4() {
        return covid19FormPart4;
    }

    public void insertForm() {
        if (Utilities.checkInternetConnectionAvailability().equals(DEVICE_NOT_CONNECTED_TO_INTERNET)) {
            showMessage(covid19FormPart4.getActivity(), "Pas de connexion internet", "Merci de vérifier votre connexion internet!");
            return;
        }

        Covid19NewFormAsyncTask covid19NewFormAsyncTask = new Covid19NewFormAsyncTask(this);
        showProgressDialog(covid19FormPart4.getActivity(), "Insertion du formulaire en cours ...");

        if (!covid19FormActivity.getDoesPatientExist())
            covid19NewFormAsyncTask.execute(patient.getPhoneNumber(), patient.getName(), patient.getGender(),
                    patient.getDateOfBirth(), replaceApostrophe(patient.getWilaya()), replaceApostrophe(patient.getMoughataa()),
                    covid19Form.getFormID(), String.valueOf(covid19Form.getParentUserID()), replaceApostrophe(covid19Form.getSymptoms()),
                    covid19Form.getConsulterMedecin(), covid19Form.getStrucureMedecin(), covid19Form.getRaisonAbsence(),
                    covid19Form.getSabsenterDuTravail(), covid19Form.getCombienDeJours(), covid19Form.getContactAvecPersonneSuspecte(),
                    covid19Form.getTelPersonneSuspecte(), covid19Form.getDateDernierContactPersonneSuspecte(), covid19Form.getNiveauSocioEconomique(),
                    covid19Form.getConditionPreDisposante(), replaceApostrophe(covid19Form.getListeDesConditionsPreDisposantes()), covid19Form.getTestCovid(),
                    covid19Form.getTypeTest(), covid19Form.getDateTest(), covid19Form.getTdr(), covid19Form.getPcr(), covid19Form.getScanner(),
                    covid19Form.getTdrResponse(), covid19Form.getTdrDetails(), covid19Form.getPcrReponse(), covid19Form.getScannerResponse(),
                    covid19Form.getStatutActuel(), replaceApostrophe(covid19Form.getDetailVivant()), covid19Form.getDateAdmission(), replaceApostrophe(covid19Form.getStructureSanitaireHospitalisation()),
                    covid19Form.getDateDeces(), covid19Form.getLieuDeces(), covid19Form.getDureeHospitalisation(), replaceApostrophe(covid19Form.getStructureSanitaireDeces())
                    , getTodayDate());
        else
            covid19NewFormAsyncTask.execute(patient.getPhoneNumber(), "", "", "", "", "",
                    covid19Form.getFormID(), String.valueOf(covid19Form.getParentUserID()), replaceApostrophe(covid19Form.getSymptoms()),
                    covid19Form.getConsulterMedecin(), covid19Form.getStrucureMedecin(), covid19Form.getRaisonAbsence(),
                    covid19Form.getSabsenterDuTravail(), covid19Form.getCombienDeJours(), covid19Form.getContactAvecPersonneSuspecte(),
                    covid19Form.getTelPersonneSuspecte(), covid19Form.getDateDernierContactPersonneSuspecte(), covid19Form.getNiveauSocioEconomique(),
                    covid19Form.getConditionPreDisposante(), replaceApostrophe(covid19Form.getListeDesConditionsPreDisposantes()), covid19Form.getTestCovid(),
                    covid19Form.getTypeTest(), covid19Form.getDateTest(), covid19Form.getTdr(), covid19Form.getPcr(), covid19Form.getScanner(),
                    covid19Form.getTdrResponse(), covid19Form.getTdrDetails(), covid19Form.getPcrReponse(), covid19Form.getScannerResponse(),
                    covid19Form.getStatutActuel(), replaceApostrophe(covid19Form.getDetailVivant()), covid19Form.getDateAdmission(), replaceApostrophe(covid19Form.getStructureSanitaireHospitalisation()),
                    covid19Form.getDateDeces(), covid19Form.getLieuDeces(), covid19Form.getDureeHospitalisation(), replaceApostrophe(covid19Form.getStructureSanitaireDeces())
                    , getTodayDate());
    }

    public void onFormAdded(String exceptionInfo, boolean isFormAdded) {
        hideProgressDialog();
        if (isFormAdded) {
            doYouWantToGoBack();
        } else {
            if (exceptionInfo.equals(SOCKET_TIMEOUT_EXCEPTION))
                showMessage(covid19FormPart4.getActivity(), "Pas de connexion internet", "Merci de vérifier votre connexion internet!");
            else
                showMessage(covid19FormPart4.getActivity(), "Problème survenu", "Désolé, une erreur s'est produite (Code d'erreur : 007)");
            //showMessageUsingDialogFragment(covid19FormPart4.getActivity(), "Exception", "Une erreur s'est produite !\n" + "DETAILS :\n" + exceptionInfo);
        }
    }

    private void doYouWantToGoBack() {
        FragmentTransaction ft = covid19FormPart4.getActivity().getSupportFragmentManager().beginTransaction();
        DialogFragmentForGoingBackAfterAddingForm dialog = new DialogFragmentForGoingBackAfterAddingForm(this);

        Bundle args = new Bundle();
        args.putString("title", "Opération réussie");
        args.putString("message", "Formulaire inséré avec succès. Voulez-vous retourner à l'interface d'accueil ?");
        dialog.setArguments(args);

        ft.add(dialog, "dialogFragmentForGoingBackAfterAddingForm");
        ft.commit();
    }
}