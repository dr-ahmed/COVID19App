package com.infectdistrack.presenter;

import android.util.Log;

import com.infectdistrack.model.NewUserAsyncTask;
import com.infectdistrack.model.User;
import com.infectdistrack.model.Utilities;
import com.infectdistrack.view.NewUserActivity;

import java.security.NoSuchAlgorithmException;

import static com.infectdistrack.model.Constants.ADMIN;
import static com.infectdistrack.model.Constants.ADMIN_LABEL;
import static com.infectdistrack.model.Constants.DEFAULT_WILAYA;
import static com.infectdistrack.model.Constants.SUPER_ADMIN;
import static com.infectdistrack.model.Constants.USER;
import static com.infectdistrack.model.Constants.USER_LABEL;
import static com.infectdistrack.model.Utilities.SHA256;
import static com.infectdistrack.model.Utilities.isEmailValid;
import static com.infectdistrack.model.Utilities.replaceApostrophe;
import static com.infectdistrack.presenter.UIBasicController.hideProgressDialog;
import static com.infectdistrack.presenter.UIBasicController.isFieldEmpty;
import static com.infectdistrack.presenter.UIBasicController.showMessage;
import static com.infectdistrack.presenter.UIBasicController.showProgressDialog;

public class NewUserController {

    private static final String TAG = "NewUserController";
    private NewUserActivity newUserActivity;
    private boolean isSuperAdmin;

    public NewUserController(NewUserActivity newUserActivity) {
        this.newUserActivity = newUserActivity;
        isSuperAdmin = newUserActivity.getParentUser().getCategory().equals(SUPER_ADMIN);
    }

    public void onClickAddNewUserButtonController() {
        if (isFieldEmpty(newUserActivity.getNewUserFullNameEdt())
                || isFieldEmpty(newUserActivity.getNewUserEmailEdt())
                || isFieldEmpty(newUserActivity.getNewUserPasswordEdt())
                || isFieldEmpty(newUserActivity.getNewUserPasswordConfirmationEdt()))
            return;
        if (!isEmailValid(newUserActivity.getNewUserEmailEdt().getText().toString())) {
            newUserActivity.getNewUserEmailEdt().requestFocus();
            newUserActivity.getNewUserEmailEdt().setError("Syntaxe invalide !");
            return;
        }
        if (!newUserActivity.getNewUserPasswordEdt().getText().toString().equals(newUserActivity.getNewUserPasswordConfirmationEdt().getText().toString())) {
            showMessage(newUserActivity, "Mots de passe incohérents", "Le mot de passe et sa confirmation ne sont pas identiques!");
            return;
        }
        if (newUserActivity.getNewUserWilayaSpinner().getSelectedItem().toString().equals(DEFAULT_WILAYA)) {
            showMessage(newUserActivity, "Wilaya obligatoire", "Veuillez sélectionner une wilaya!");
            return;
        }
        if (newUserActivity.getEstablishmentType().isEmpty()) {
            showMessage(newUserActivity, "Etablissement obligatoire", "Veuillez sélectionner un établissement!");
            return;
        }
        showProgressDialog(newUserActivity, "Création du compte en cours ...");

        insertUser();
    }

    private void insertUser() {
        NewUserAsyncTask loginAsyncTask = new NewUserAsyncTask(this);
        try {
            String fullName = replaceApostrophe(newUserActivity.getNewUserFullNameEdt().getText().toString()),
                    email = newUserActivity.getNewUserEmailEdt().getText().toString(),
                    password = SHA256(newUserActivity.getNewUserPasswordEdt().getText().toString()),
                    category = isSuperAdmin ? ADMIN : USER,
                    associateAdmin = String.valueOf(newUserActivity.getParentUser().getId()),
                    wilaya = newUserActivity.getNewUserWilayaSpinner().getSelectedItem().toString(),
                    establishment = replaceApostrophe(newUserActivity.getEstablishmentType());
            loginAsyncTask.execute(fullName, email, password, category, associateAdmin, wilaya, establishment);
        } catch (NoSuchAlgorithmException e) {
            showMessage(newUserActivity, "Problème survenu", "Désolé, une erreur s'est produite (Code d'erreur : 003)");
        }
    }

    public void onNewUserAdded(User user, String exceptionInfo, boolean isUserAdded) {
        hideProgressDialog();
        if (isUserAdded) {
            showMessage(newUserActivity, "Opération réussie", "Le compte de l'"
                    + (isSuperAdmin ? ADMIN_LABEL : USER_LABEL) + newUserActivity.getNewUserFullNameEdt().getText().toString()
                    + " a été créé avec succès. Voulez-vous lui envoyer les informations du compte ?");
            newUserActivity.resetUIComponents();
            Log.e(TAG, "onNewUserAdded: afficher un AltertDialog de trois choix : Par Email, Partager, Non.");
        } else {
            if (!Utilities.isInternetAvailable())
                showMessage(newUserActivity, "Pas de connexion internet", "Merci de vérifier votre connexion internet!");
            else
                showMessage(newUserActivity, "Problème survenu", "Désolé, une erreur s'est produite (Code d'erreur : 004)");
            //showMessageUsingDialogFragment(newUserActivity, "Exception", "Une erreur s'est produite !\n" + "DETAILS :\n" + exceptionInfo);
        }
    }
}