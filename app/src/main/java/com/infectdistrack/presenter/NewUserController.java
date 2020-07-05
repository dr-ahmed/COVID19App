package com.infectdistrack.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.FragmentTransaction;

import com.infectdistrack.model.CheckDuplicateUserAsyncTask;
import com.infectdistrack.model.NewUserAsyncTask;
import com.infectdistrack.model.SendingMailAsyncTask;
import com.infectdistrack.view.DialogFragmentAboutDoYoWantToTrayAgainSendingEmail;
import com.infectdistrack.view.DialogFragmentForAskingConfirmationWhenCreatingUser;
import com.infectdistrack.view.DialogFragmentForAskingUserAboutSendingAccountInformation;
import com.infectdistrack.view.NewUserActivity;

import java.security.NoSuchAlgorithmException;

import static com.infectdistrack.model.Constants.ADMIN;
import static com.infectdistrack.model.Constants.ADMIN_LABEL;
import static com.infectdistrack.model.Constants.DEFAULT_TYPE;
import static com.infectdistrack.model.Constants.DEFAULT_WILAYA;
import static com.infectdistrack.model.Constants.EMPTY_STRING;
import static com.infectdistrack.model.Constants.OTHER_ESTABLISHMENT;
import static com.infectdistrack.model.Constants.SUPER_ADMIN;
import static com.infectdistrack.model.Constants.USER;
import static com.infectdistrack.model.Constants.USER_LABEL;
import static com.infectdistrack.model.Constants.USER_TYPE;
import static com.infectdistrack.model.Utilities.SHA256;
import static com.infectdistrack.model.Utilities.getTodayDate;
import static com.infectdistrack.model.Utilities.isEmailValid;
import static com.infectdistrack.model.Utilities.isInternetAvailable;
import static com.infectdistrack.model.Utilities.replaceApostrophe;
import static com.infectdistrack.presenter.UIBasicController.hideProgressDialog;
import static com.infectdistrack.presenter.UIBasicController.isFieldEmpty;
import static com.infectdistrack.presenter.UIBasicController.showMessage;
import static com.infectdistrack.presenter.UIBasicController.showProgressDialog;

public class NewUserController {

    private static final String TAG = "NewUserController";
    private NewUserActivity newUserActivity;
    private boolean isSuperAdmin;

    private String MAIL_SUBJECT, MAIL_ADDRESS;

    public NewUserController(NewUserActivity newUserActivity) {
        this.newUserActivity = newUserActivity;
        isSuperAdmin = newUserActivity.getParentUser().getCategory().equals(SUPER_ADMIN);
    }

    public NewUserActivity getNewUserActivity() {
        return newUserActivity;
    }

    public void onClickAddNewUserButtonController() {
        if (isFieldEmpty(newUserActivity.getNewUserFullNameEdt())
                || isFieldEmpty(newUserActivity.getNewUserEmailEdt()))
            return;
        if (!isEmailValid(newUserActivity.getNewUserEmailEdt().getText().toString())) {
            newUserActivity.getNewUserEmailEdt().requestFocus();
            newUserActivity.getNewUserEmailEdt().setError("Syntaxe invalide !");
            return;
        }
        if (isFieldEmpty(newUserActivity.getNewUserPasswordEdt())
                || isFieldEmpty(newUserActivity.getNewUserPasswordConfirmationEdt()))
            return;
        if (!newUserActivity.getNewUserPasswordEdt().getText().toString().equals(newUserActivity.getNewUserPasswordConfirmationEdt().getText().toString())) {
            showMessage(newUserActivity, "Mots de passe incohérents", "Le mot de passe et sa confirmation ne sont pas identiques!");
            return;
        }
        if (newUserActivity.getNewUserWilaya().equals(DEFAULT_WILAYA)) {
            showMessage(newUserActivity, "Wilaya obligatoire", "Veuillez sélectionner une wilaya!");
            return;
        }
        if (newUserActivity.getNewUserMoughataa().equals(EMPTY_STRING)) {
            showMessage(newUserActivity, "Moughataa obligatoire", "Veuillez sélectionner une moughataa!");
            return;
        }
        if (newUserActivity.getNewUserTypeFromSpinner().equals(DEFAULT_TYPE)) {
            showMessage(newUserActivity, "Type obligatoire", "Veuillez sélectionner le type de l'utilisateur!");
            return;
        }
        if (newUserActivity.getNewUserTypeFromSpinner().equals(USER_TYPE[6]) && newUserActivity.getNewUserTypeFromEdittext().isEmpty()) {
            showMessage(newUserActivity, "Type obligatoire", "Veuillez saisir le type de l'utilisateur!");
            return;
        }
        if (newUserActivity.getEstablishmentCategory().isEmpty()) {
            showMessage(newUserActivity, "Etablissement obligatoire", "Veuillez sélectionner la catégorie de établissement!");
            return;
        }
        if (newUserActivity.getEstablishmentType().isEmpty() && !newUserActivity.getEstablishmentCategory().equals(OTHER_ESTABLISHMENT)) {
            showMessage(newUserActivity, "Etablissement obligatoire", "Veuillez sélectionner le type de l'établissement!");
            return;
        }
        if (newUserActivity.getEstablishmentCategory().equals(OTHER_ESTABLISHMENT) && newUserActivity.getOtherEstablishmentValue().isEmpty()) {
            showMessage(newUserActivity, "Etablissement obligatoire", "Veuillez saisir le type de l'établissement!");
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
                + getUserInformation());
        dialog.setArguments(args);

        ft.add(dialog, "dialogFragmentForAskingConfirmationWhenCreatingUser");
        ft.commit();
    }

    private String getUserInformation() {
        return "\nNom : " + newUserActivity.getNewUserFullNameEdt().getText().toString()
                + "\nEmail : " + newUserActivity.getNewUserEmailEdt().getText().toString()
                + "\nMot de passe : " + newUserActivity.getNewUserPasswordEdt().getText().toString()
                + "\nWilaya : " + newUserActivity.getNewUserWilaya()
                + "\nEtablissement : " + newUserActivity.getEstablishmentType();
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
            if (!isInternetAvailable())
                showMessage(newUserActivity, "Pas de connexion internet", "Merci de vérifier votre connexion internet!");
            else
                showMessage(newUserActivity, "Problème survenu", "Désolé, une erreur s'est produite (Code d'erreur : 004)");
            //showMessageUsingDialogFragment(newUserActivity, "Exception", "Une erreur s'est produite !\n" + "DETAILS :\n" + exceptionInfo);
        }
    }

    public void insertUser() {
        NewUserAsyncTask newUserAsyncTask = new NewUserAsyncTask(this);
        try {
            String fullName = replaceApostrophe(newUserActivity.getNewUserFullNameEdt().getText().toString()),
                    email = newUserActivity.getNewUserEmailEdt().getText().toString(),
                    password = SHA256(newUserActivity.getNewUserPasswordEdt().getText().toString()),
                    category = isSuperAdmin ? ADMIN : USER,
                    associateAdmin = String.valueOf(newUserActivity.getParentUser().getId()),
                    wilaya = newUserActivity.getNewUserWilaya(),
                    moughataa = newUserActivity.getNewUserMoughataa(),
                    establishment = replaceApostrophe(newUserActivity.getEstablishmentType());
            showProgressDialog(newUserActivity, "Création du compte en cours ...");
            newUserAsyncTask.execute(fullName, email, password, category, associateAdmin, wilaya, moughataa, establishment, getTodayDate());
        } catch (NoSuchAlgorithmException e) {
            showMessage(newUserActivity, "Problème survenu", "Désolé, une erreur s'est produite (Code d'erreur : 005)");
        }
    }

    public void onNewUserAdded(String exceptionInfo, boolean isUserAdded) {
        hideProgressDialog();
        if (isUserAdded) {
            askAdminAboutSendingAccountInformation();
        } else {
            if (!isInternetAvailable())
                showMessage(newUserActivity, "Pas de connexion internet", "Merci de vérifier votre connexion internet!");
            else
                showMessage(newUserActivity, "Problème survenu", "Désolé, une erreur s'est produite (Code d'erreur : 006)");
            //showMessageUsingDialogFragment(newUserActivity, "Exception", "Une erreur s'est produite !\n" + "DETAILS :\n" + exceptionInfo);
        }
    }

    private void askAdminAboutSendingAccountInformation() {
        MAIL_SUBJECT = "Détails de votre compte sur l'application COVID-19";
        MAIL_ADDRESS = "Bonjour " + newUserActivity.getNewUserFullNameEdt().getText().toString() + ",\n"
                + "L'administrateur " + newUserActivity.getParentUser().getFullName() + " vous a créé un nouveau compte dont les informations sont :"
                + getUserInformation()
                + "\n\nRendez-vous sur l'application COVID-19" +
                "\nA bientôt.";

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

    private void doYouWantToTrySendingMailAgain(String title, String message) {
        FragmentTransaction ft = newUserActivity.getSupportFragmentManager().beginTransaction();
        DialogFragmentAboutDoYoWantToTrayAgainSendingEmail dialog = new DialogFragmentAboutDoYoWantToTrayAgainSendingEmail(this);

        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("message", message);
        dialog.setArguments(args);

        ft.add(dialog, "dialogFragmentAboutDoYoWantToTrayAgainSendingEmail");
        ft.commit();
    }

    public void shareUserAccountInformation() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, MAIL_SUBJECT);
        shareIntent.putExtra(Intent.EXTRA_TEXT, MAIL_ADDRESS);
        newUserActivity.startActivity(Intent.createChooser(shareIntent, "Partager avec"));
        newUserActivity.resetUIComponents();
    }

    public void sendUserAccountInformationWithEmail() {
        SendingMailAsyncTask sendingMailAsyncTask = new SendingMailAsyncTask(this);
        showProgressDialog(newUserActivity, "Envoi du courrier électronique en cours ...");
        sendingMailAsyncTask.execute(newUserActivity.getNewUserEmailEdt().getText().toString(), MAIL_SUBJECT, MAIL_ADDRESS);
    }

    public void onMailSent(boolean noExceptionOccurred) {
        hideProgressDialog();
        if (noExceptionOccurred) {
            newUserActivity.resetUIComponents();
            showMessage(newUserActivity, "Opération accomplie", "Le courrier a été envoyé avec succès.");
        } else {
            String title, message;
            if (!isInternetAvailable()) {
                title = "Pas de connexion internet";
                message = "Merci de vérifier votre connexion internet!";
            } else {
                title = "Une erreur s'est produite";
                message = "Désolé, l'envoi du courrier électronique a échoué. Veuillez réssayer plus tard.";
            }
            doYouWantToTrySendingMailAgain(title, message);
        }
    }

    private String getCategoryOfUserToCreate() {
        return (isSuperAdmin ? ADMIN_LABEL : USER_LABEL);
    }

    private String getCategoryAndFullNameOfUserToCreate() {
        return (isSuperAdmin ? ADMIN_LABEL : USER_LABEL) + newUserActivity.getNewUserFullNameEdt().getText().toString();
    }
}