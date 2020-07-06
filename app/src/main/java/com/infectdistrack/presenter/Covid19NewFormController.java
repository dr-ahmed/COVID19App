package com.infectdistrack.presenter;

import android.os.Bundle;

import androidx.fragment.app.FragmentTransaction;

import com.infectdistrack.model.Covid19Form;
import com.infectdistrack.model.Covid19NewFormAsyncTask;
import com.infectdistrack.model.Utilities;
import com.infectdistrack.view.Covid19FormPart4;
import com.infectdistrack.view.DialogFragmentForGoingBackAfterAddingForm;

import static com.infectdistrack.model.Constants.NO_CONNECTION_OR_TIMEOUT_EXCEPTION_TAG;
import static com.infectdistrack.model.Utilities.getTodayDate;
import static com.infectdistrack.presenter.UIBasicController.hideProgressDialog;
import static com.infectdistrack.presenter.UIBasicController.showMessage;
import static com.infectdistrack.presenter.UIBasicController.showProgressDialog;

public class Covid19NewFormController {

    private static final String TAG = "CovidFormController";

    private Covid19FormPart4 covid19FormPart4;
    private Covid19Form covid19Form;

    public Covid19NewFormController(Covid19FormPart4 covid19FormPart4) {
        this.covid19FormPart4 = covid19FormPart4;
        covid19Form = covid19FormPart4.getFinalCovid19FormResult();
    }

    public Covid19FormPart4 getCovid19FormPart4() {
        return covid19FormPart4;
    }

    public void insertForm() {
        Covid19NewFormAsyncTask covid19NewFormAsyncTask = new Covid19NewFormAsyncTask(this);
        showProgressDialog(covid19FormPart4.getActivity(), "Insertion du formulaire en cours ...");

        covid19NewFormAsyncTask.execute(String.valueOf(covid19Form.getParentUserId()), covid19Form.getName(), covid19Form.getPhoneNumber(),
                covid19Form.getGendre(), String.valueOf(covid19Form.getAge()), covid19Form.getWilaya(), covid19Form.getSuspectedCases(),
                covid19Form.getSymptoms(), covid19Form.getTerrain(), covid19Form.getConfirmedCovid19Case(), covid19Form.getEvolution(), getTodayDate());
    }

    public void onFormAdded(String exceptionInfo, boolean isFormAdded) {
        hideProgressDialog();
        if (isFormAdded) {
            doYouWantToGoBack();
        } else {
            if (Utilities.isInternetAvailable().equals(NO_CONNECTION_OR_TIMEOUT_EXCEPTION_TAG))
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