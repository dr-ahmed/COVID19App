package com.infectdistrack.presenter;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.FragmentTransaction;

import com.infectdistrack.model.CheckDuplicateUserAsyncTask;
import com.infectdistrack.model.NewUserAsyncTask;
import com.infectdistrack.model.SendingMailAsyncTask;
import com.infectdistrack.model.Utilities;
import com.infectdistrack.view.DialogFragmentAboutDoYoWantToTrayAgainSendingEmail;
import com.infectdistrack.view.DialogFragmentForAskingConfirmationWhenCreatingUser;
import com.infectdistrack.view.DialogFragmentForAskingUserAboutSendingAccountInformation;
import com.infectdistrack.view.NewUserActivity;

import java.security.NoSuchAlgorithmException;

import static com.infectdistrack.model.Constants.*;
import static com.infectdistrack.model.Utilities.*;
import static com.infectdistrack.presenter.UIBasicController.*;

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
        if (newUserActivity.getNewUserTypeFromSpinner().equals(USER_TYPE_LIST[6]) && newUserActivity.getNewUserTypeFromEdittext().isEmpty()) {
            showMessage(newUserActivity, "Type obligatoire", "Veuillez saisir le type de l'utilisateur!");
            return;
        }
        if (newUserActivity.getEstablishmentType().isEmpty()) {
            showMessage(newUserActivity, "Etablissement obligatoire", "Veuillez sélectionner le type de établissement!");
            return;
        }
        /*
          Si le user sélectionne la catégorie "Public" ou "Privé" et il ne sélectionne pas le type de l'établissement
        */
        if (newUserActivity.getEstablishmentType().equals(PUBLIC_ESTABLISHMENT) && newUserActivity.getPublicEstablishmentCategory().isEmpty()) {
            showMessage(newUserActivity, "Etablissement obligatoire", "Veuillez sélectionner la catégorie de l'établissement public!");
            return;
        }
        if (newUserActivity.getEstablishmentType().equals(PRIVATE_ESTABLISHMENT) && newUserActivity.getPrivateEstablishmentCategory().isEmpty()) {
            showMessage(newUserActivity, "Etablissement obligatoire", "Veuillez sélectionner la catégorie de l'établissement privé!");
            return;
        }
        // Si le user sélectionne la catégorie "Autre" et il ne précise rien dans l'edittex
        if (newUserActivity.getEstablishmentType().equals(OTHER_ESTABLISHMENT) && newUserActivity.getOtherEstablishmentCategory().isEmpty()) {
            showMessage(newUserActivity, "Etablissement obligatoire", "Veuillez saisir la catégorie de l'établissement!");
            return;
        }

        checkDuplicateUser();
    }

    private void askForConfirmation() {
        FragmentTransaction ft = newUserActivity.getSupportFragmentManager().beginTransaction();
        DialogFragmentForAskingConfirmationWhenCreatingUser dialog = new DialogFragmentForAskingConfirmationWhenCreatingUser(this);

        Bundle args = new Bundle();
        args.putString("title", "Demande de confirmation");
        args.putString("message", "Êtes-vous sûr de vouloir créer un nouvel " + getCategoryOfUserToCreate() + " dont les informations sont :\n"
                + newUserActivity.getNewUser().toString());
        dialog.setArguments(args);

        ft.add(dialog, "dialogFragmentForAskingConfirmationWhenCreatingUser");
        ft.commit();
    }

    private void checkDuplicateUser() {
        if (Utilities.checkInternetConnectionAvailability().equals(DEVICE_NOT_CONNECTED_TO_INTERNET)) {
            showMessage(newUserActivity, "Pas de connexion internet", "Merci de vérifier votre connexion internet!");
            return;
        }

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
            if (exceptionInfo.equals(SOCKET_TIMEOUT_EXCEPTION))
                showMessage(newUserActivity, "Pas de connexion internet", "Merci de vérifier votre connexion internet!");
            else
                showMessage(newUserActivity, "Problème survenu", "Désolé, une erreur s'est produite (Code d'erreur : 004)");
            //showMessageUsingDialogFragment(newUserActivity, "Exception", "Une erreur s'est produite !\n" + "DETAILS :\n" + exceptionInfo);
        }
    }

    public void insertUser() {
        if (Utilities.checkInternetConnectionAvailability().equals(DEVICE_NOT_CONNECTED_TO_INTERNET)) {
            showMessage(newUserActivity, "Pas de connexion internet", "Merci de vérifier votre connexion internet!");
            return;
        }

        NewUserAsyncTask newUserAsyncTask = new NewUserAsyncTask(this);
        try {
            String fullName = replaceApostrophe(newUserActivity.getNewUser().getFullName()),
                    email = newUserActivity.getNewUser().getEmail(),
                    password = SHA256(newUserActivity.getNewUser().getPassword()),
                    category = isSuperAdmin ? ADMIN : USER,
                    associateAdmin = String.valueOf(newUserActivity.getParentUser().getId()),
                    wilaya = replaceApostrophe(newUserActivity.getNewUser().getWilaya()),
                    moughataa = replaceApostrophe(newUserActivity.getNewUser().getMoughataa()),
                    userType = replaceApostrophe(newUserActivity.getNewUser().getUserType()),
                    establishmentType = replaceApostrophe(newUserActivity.getNewUser().getEstablishmentType()),
                    establishmentCategory = replaceApostrophe(newUserActivity.getNewUser().getEstablishmentCategory());
            showProgressDialog(newUserActivity, "Création du compte en cours ...");
            newUserAsyncTask.execute(fullName, email, password, category, associateAdmin, wilaya, moughataa,
                    userType, establishmentType, establishmentCategory, getTodayDate());
        } catch (NoSuchAlgorithmException e) {
            showMessage(newUserActivity, "Problème survenu", "Désolé, une erreur s'est produite (Code d'erreur : 005)");
        }
    }

    public void onNewUserAdded(String exceptionInfo, boolean isUserAdded) {
        hideProgressDialog();
        if (isUserAdded) {
            askAdminAboutSendingAccountInformation();
        } else {
            if (exceptionInfo.equals(SOCKET_TIMEOUT_EXCEPTION))
                showMessage(newUserActivity, "Pas de connexion internet", "Merci de vérifier votre connexion internet!");
            else
                showMessage(newUserActivity, "Problème survenu", "Désolé, une erreur s'est produite (Code d'erreur : 006)");
            //showMessageUsingDialogFragment(newUserActivity, "Exception", "Une erreur s'est produite !\n" + "DETAILS :\n" + exceptionInfo);
        }
    }

    private void askAdminAboutSendingAccountInformation() {
        MAIL_SUBJECT = "Détails de votre nouveau compte sur l'application d'AMDRS";
        MAIL_ADDRESS = "Bonjour " + newUserActivity.getNewUserFullNameEdt().getText().toString() + ",\n"
                + "L'administrateur " + newUserActivity.getParentUser().getFullName() + " vous a créé un nouveau compte dont les informations sont comme suit :\n"
                + newUserActivity.getNewUser().toString()
                + "\n\nLe mot de passe temporaire de votre compte est : " + newUserActivity.getNewUser().getPassword() + ". " +
                "L'application vous demandera de changer ce mot de passe lorsque vous accédez à votre compte pour la première fois." +
                "\n\nRendez-vous sur l'application d'AMDRS." +
                "\nÀ bientôt.";

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
        if (Utilities.checkInternetConnectionAvailability().equals(DEVICE_NOT_CONNECTED_TO_INTERNET)) {
            showMessage(newUserActivity, "Pas de connexion internet", "Merci de vérifier votre connexion internet!");
            return;
        }

        SendingMailAsyncTask sendingMailAsyncTask = new SendingMailAsyncTask(this);
        showProgressDialog(newUserActivity, "Envoi du courrier électronique en cours ...");
        sendingMailAsyncTask.execute(newUserActivity.getNewUserEmailEdt().getText().toString(), MAIL_SUBJECT, MAIL_ADDRESS);
    }

    public void onMailSent(String exceptionInfo) {
        hideProgressDialog();
        if (exceptionInfo.isEmpty()) {
            newUserActivity.resetUIComponents();
            showMessage(newUserActivity, "Opération accomplie", "Le courrier a été envoyé avec succès.");
        } else {
            String title, message;
            if (exceptionInfo.equals(SOCKET_TIMEOUT_EXCEPTION)) {
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