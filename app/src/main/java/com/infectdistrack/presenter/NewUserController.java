package com.infectdistrack.presenter;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.FragmentTransaction;

import com.infectdistrack.model.CheckDuplicateUserAsyncTask;
import com.infectdistrack.model.NewUserAsyncTask;
import com.infectdistrack.model.User;
import com.infectdistrack.model.Utilities;
import com.infectdistrack.view.DialogFragmentForAskingConfirmationWhenCreatingUser;
import com.infectdistrack.view.DialogFragmentForAskingUserAboutSendingAccountInformation;
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
        if (newUserActivity.getNewUserWilaya().equals(DEFAULT_WILAYA)) {
            showMessage(newUserActivity, "Wilaya obligatoire", "Veuillez sélectionner une wilaya!");
            return;
        }
        if (newUserActivity.getEstablishmentType().isEmpty()) {
            showMessage(newUserActivity, "Etablissement obligatoire", "Veuillez sélectionner un établissement!");
            return;
        }

        checkingDuplicateUser();
    }

    private void askForConfirmation() {
        FragmentTransaction ft = newUserActivity.getSupportFragmentManager().beginTransaction();
        DialogFragmentForAskingConfirmationWhenCreatingUser dialog = new DialogFragmentForAskingConfirmationWhenCreatingUser(this);

        Bundle args = new Bundle();
        args.putString("title", "Demande de confirmation");
        args.putString("message", "Êtes-vous sûr de vouloir créer un nouvel " + getCategoryOfUserToCreate() + " dont les informations sont :"
                + "\nNom : " + newUserActivity.getNewUserFullNameEdt().getText().toString()
                + "\nEmail : " + newUserActivity.getNewUserEmailEdt().getText().toString()
                + "\nMot de passe : " + newUserActivity.getNewUserPasswordEdt().getText().toString()
                + "\nWilaya : " + newUserActivity.getNewUserWilaya()
                + "\nEtablissement : " + newUserActivity.getEstablishmentType());
        dialog.setArguments(args);

        ft.add(dialog, "dialogFragmentForAskingConfirmationWhenCreatingUser");
        ft.commit();
    }

    private void checkingDuplicateUser() {
        showProgressDialog(newUserActivity, "Vérification des informations du compte en cours ...");

        CheckDuplicateUserAsyncTask checkDuplicateUserAsyncTask = new CheckDuplicateUserAsyncTask(this);
        checkDuplicateUserAsyncTask.execute(newUserActivity.getNewUserEmailEdt().getText().toString());
    }

    public void onCheckDuplicateUserResponse(String exceptionInfo, boolean userIsUnique) {
        hideProgressDialog();
        if (exceptionInfo.isEmpty()) {
            if (userIsUnique) {
                askForConfirmation();
            } else
                showMessage(newUserActivity, "Email déjà existant", "Désolé, cet email est deja utilsié, veuillez essayer un autre !");
        } else {
            if (!Utilities.isInternetAvailable())
                showMessage(newUserActivity, "Pas de connexion internet", "Merci de vérifier votre connexion internet!");
            else
                showMessage(newUserActivity, "Problème survenu", "Désolé, une erreur s'est produite (Code d'erreur : 004)");
            //showMessageUsingDialogFragment(newUserActivity, "Exception", "Une erreur s'est produite !\n" + "DETAILS :\n" + exceptionInfo);
        }
    }

    public void insertUser() {
        NewUserAsyncTask loginAsyncTask = new NewUserAsyncTask(this);
        try {
            String fullName = replaceApostrophe(newUserActivity.getNewUserFullNameEdt().getText().toString()),
                    email = newUserActivity.getNewUserEmailEdt().getText().toString(),
                    password = SHA256(newUserActivity.getNewUserPasswordEdt().getText().toString()),
                    category = isSuperAdmin ? ADMIN : USER,
                    associateAdmin = String.valueOf(newUserActivity.getParentUser().getId()),
                    wilaya = newUserActivity.getNewUserWilaya(),
                    establishment = replaceApostrophe(newUserActivity.getEstablishmentType());
            showProgressDialog(newUserActivity, "Création du compte en cours ...");
            loginAsyncTask.execute(fullName, email, password, category, associateAdmin, wilaya, establishment);
        } catch (NoSuchAlgorithmException e) {
            showMessage(newUserActivity, "Problème survenu", "Désolé, une erreur s'est produite (Code d'erreur : 005)");
        }
    }

    public void onNewUserAdded(User user, String exceptionInfo, boolean isUserAdded) {
        hideProgressDialog();
        if (isUserAdded) {
            askAdminAboutSendingAccountInformation();
            newUserActivity.resetUIComponents();
        } else {
            if (!Utilities.isInternetAvailable())
                showMessage(newUserActivity, "Pas de connexion internet", "Merci de vérifier votre connexion internet!");
            else
                showMessage(newUserActivity, "Problème survenu", "Désolé, une erreur s'est produite (Code d'erreur : 006)");
            //showMessageUsingDialogFragment(newUserActivity, "Exception", "Une erreur s'est produite !\n" + "DETAILS :\n" + exceptionInfo);
        }
    }

    private void askAdminAboutSendingAccountInformation() {
        FragmentTransaction ft = newUserActivity.getSupportFragmentManager().beginTransaction();
        DialogFragmentForAskingUserAboutSendingAccountInformation dialog = new DialogFragmentForAskingUserAboutSendingAccountInformation(this);

        Bundle args = new Bundle();
        args.putString("title", "Opération réussie");
        args.putString("message", "Le compte de l'" + getCategoryAndFullNameOfUserToCreate()
                + " a été créé avec succès. Voulez-vous lui envoyer les informations du compte ?");
        dialog.setArguments(args);

        ft.add(dialog, "dialogFragmentForAskingUserAboutSendingAccountInformation");
        ft.commit();
    }

    private String getCategoryOfUserToCreate() {
        return (isSuperAdmin ? ADMIN_LABEL : USER_LABEL);
    }

    private String getCategoryAndFullNameOfUserToCreate() {
        return (isSuperAdmin ? ADMIN_LABEL : USER_LABEL) + newUserActivity.getNewUserFullNameEdt().getText().toString();
    }
}