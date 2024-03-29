package com.infectdistrack.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.FragmentTransaction;

import com.infectdistrack.model.CheckUserSessionDataValidityAsyncTask;
import com.infectdistrack.model.LoginAsyncTask;
import com.infectdistrack.model.SharedPrefsManager;
import com.infectdistrack.model.User;
import com.infectdistrack.model.Utilities;
import com.infectdistrack.view.DialogFragmentForAskingUserWhenSessionDataIsNoLongerValid;
import com.infectdistrack.view.DialogFragmentToResetPasswordDuringFirstLogin;
import com.infectdistrack.view.DialogFragmentWithOnlyOneButton;
import com.infectdistrack.view.LoginActivity;

import java.security.NoSuchAlgorithmException;

import static com.infectdistrack.model.Constants.DEVICE_NOT_CONNECTED_TO_INTERNET;
import static com.infectdistrack.model.Constants.SOCKET_TIMEOUT_EXCEPTION;
import static com.infectdistrack.model.Utilities.SHA256;
import static com.infectdistrack.model.Utilities.replaceApostrophe;
import static com.infectdistrack.presenter.UIBasicController.hideProgressDialog;
import static com.infectdistrack.presenter.UIBasicController.isFieldEmpty;
import static com.infectdistrack.presenter.UIBasicController.showMessage;
import static com.infectdistrack.presenter.UIBasicController.showProgressDialog;

public class LoginController {

    private static final String TAG = "LoginController";
    private LoginActivity loginActivity;
    private User userObjectFromSharedPrefs;

    public LoginController(LoginActivity loginActivity) {
        this.loginActivity = loginActivity;
        SharedPrefsManager sharedPrefsManager = new SharedPrefsManager(loginActivity);
        userObjectFromSharedPrefs = sharedPrefsManager.isUserLoggedIn();
    }

    public LoginActivity getLoginActivity() {
        return loginActivity;
    }

    public void onClickConnectButtonController() {
        if (isFieldEmpty(loginActivity.getEmailEdt()) || isFieldEmpty(loginActivity.getPasswordEdt()))
            return;

        showProgressDialog(loginActivity, "Vérification des informations en cours ...");

        verifyUser();
    }

    public void checkUserSessionDataValidity() {

        // Si le phone n'est pas connecté au net ou s'il y est connecté mais la connexion est faible de sorte qu'on a pas pu faire un ping correctement
        if (Utilities.checkInternetConnectionAvailability().equals(DEVICE_NOT_CONNECTED_TO_INTERNET)) {
            showMessage(loginActivity, "Pas de connexion internet", "Merci de vérifier votre connexion internet!");
            return;
        }

        CheckUserSessionDataValidityAsyncTask checkUserSessionDataValidityAsyncTask = new CheckUserSessionDataValidityAsyncTask(this);
        showProgressDialog(loginActivity, "Vérification des données de session ...");
        // L'appel de replaceApostrophe permet de gérer le ' (exemple : centre d'appels)
        checkUserSessionDataValidityAsyncTask.execute(replaceApostrophe(userObjectFromSharedPrefs.getFullName()),
                userObjectFromSharedPrefs.getEmail(),
                userObjectFromSharedPrefs.getPassword(),
                userObjectFromSharedPrefs.getCategory(),
                String.valueOf(userObjectFromSharedPrefs.getAssociateAdmin()),
                replaceApostrophe(userObjectFromSharedPrefs.getWilaya()),
                replaceApostrophe(userObjectFromSharedPrefs.getMoughataa()),
                replaceApostrophe(userObjectFromSharedPrefs.getUserType()),
                replaceApostrophe(userObjectFromSharedPrefs.getEstablishmentType()),
                replaceApostrophe(userObjectFromSharedPrefs.getEstablishmentCategory()));
    }

    public void checkUserSessionData(Intent intent) {
        // Si les données du user sont deja sauvegardées dans les shared prefs, vérifier si elles sont encore conformes avec les infos in DB
        if (userObjectFromSharedPrefs != null) {
            // afficher le mail du user dans l'edittext
            loginActivity.getEmailEdt().setText(userObjectFromSharedPrefs.getEmail());
            checkUserSessionDataValidity();
        } else { // Si les données du user ne sont pas saved in shared prefs
            if (getUserEmailFromSharedPrefs(intent) != null) { // Si le user vient de se déconnecter
                loginActivity.getEmailEdt().setText(getUserEmailFromSharedPrefs(intent));
                loginActivity.getPasswordEdt().requestFocus();
            } else // Si l'activity de Login est lancée pour la première fois (sans etre precédée pour une action de logging)
                Log.e(TAG, "checkUserPrefs: empty session data !");
        }
    }

    public void whenUserSessionDataIsSavedInSharedPrefs(String exceptionInfo, boolean sessionDataIsStillValid) {
        hideProgressDialog();
        if (exceptionInfo.isEmpty()) {
            if (!sessionDataIsStillValid) { // vérifier si les données de session ne sont plus identiques avec les infos in database
                showMessageWhenSessionDataIsNoLongerValid();
            } else // Si les données de session sont identiques avec celles qui sont dans la BD
                loginActivity.openHomeActivity(userObjectFromSharedPrefs);
        } else {
            // Si le phone n'est pas connecté au net ou s'il y est connecté mais la connexion est faible de sorte qu'on a pas pu faire un ping correctement
            if (exceptionInfo.equals(SOCKET_TIMEOUT_EXCEPTION))
                showDialogFragmentWithLogOutOrTryAgainOptions();
            else
                showMessage(loginActivity, "Problème survenu", "Désolé, une erreur s'est produite (Code d'erreur : 001)");
            //showMessageUsingDialogFragment(newUserActivity, "Exception", "Une erreur s'est produite !\n" + "DETAILS :\n" + exceptionInfo);
        }
    }

    private void showMessageWhenSessionDataIsNoLongerValid() {
        FragmentTransaction ft = loginActivity.getSupportFragmentManager().beginTransaction();
        DialogFragmentWithOnlyOneButton dialog = new DialogFragmentWithOnlyOneButton();

        Bundle args = new Bundle();
        args.putString("title", "Données de session invalides");
        args.putString("message", "Les données de session ne sont plus valides, veuillez vous reconnecter.");
        dialog.setArguments(args);

        ft.add(dialog, "dialogFragmentWithOnlyOneButton");
        ft.commit();
        logOutWhenSessionDataIsNoLongerValid();
    }

    private void showDialogFragmentWithLogOutOrTryAgainOptions() {
        FragmentTransaction ft = loginActivity.getSupportFragmentManager().beginTransaction();
        DialogFragmentForAskingUserWhenSessionDataIsNoLongerValid dialog = new DialogFragmentForAskingUserWhenSessionDataIsNoLongerValid(this);

        Bundle args = new Bundle();
        args.putString("title", "Pas de connexion internet");
        args.putString("message", "Nous avons detecté une session d'utilisateur qui était ouverte. " +
                "Nous avons besoin de connexion Internet pour pouvoir vérifier la validité des données de cette session.");
        dialog.setArguments(args);

        ft.add(dialog, "dialogFragmentForAskingConfirmationWhenCreatingUser");
        ft.commit();
    }

    public void logOutWhenSessionDataIsNoLongerValid() {
        SharedPrefsManager sharedPrefsManager = new SharedPrefsManager(loginActivity);
        sharedPrefsManager.clearUserData();
    }

    private String getUserEmailFromSharedPrefs(Intent intent) {
        if (intent.hasExtra("userloggedOutBundle")) {
            try {
                Bundle bundle = intent.getBundleExtra("userloggedOutBundle");
                User user = bundle.getParcelable("userloggedOut");
                return user.getEmail();
            } catch (NullPointerException e) {
                return null;
            }
        }
        return null;
    }

    private void verifyUser() {
        if (Utilities.checkInternetConnectionAvailability().equals(DEVICE_NOT_CONNECTED_TO_INTERNET)) {
            showMessage(loginActivity, "Pas de connexion internet", "Merci de vérifier votre connexion internet!");
            /**
             * Faire disparaitre le ProgressDialog s'il n'y a pas de connexion internet
             * Sinon, on va le faire disparaitre dans la méthode onLoginResponse lors de la recpetion de la réponse de {@link LoginAsyncTask}
             */
            hideProgressDialog();
            return;
        }

        LoginAsyncTask loginAsyncTask = new LoginAsyncTask(this);
        String email = loginActivity.getEmailEdt().getText().toString(), password = loginActivity.getPasswordEdt().getText().toString();
        try {
            loginAsyncTask.execute(email, SHA256(password));
        } catch (NoSuchAlgorithmException e) {
            showMessage(loginActivity, "Problème survenu", "Désolé, une erreur s'est produite (Code d'erreur : 002)");
        }
    }

    public void onLoginResponse(User user, String exceptionInfo, boolean userIsConfirmed, boolean isFirstLogin) {
        hideProgressDialog();
        if (exceptionInfo.isEmpty()) {
            if (userIsConfirmed) {
                if (isFirstLogin)
                    resetPassword(user);
                else
                    saveUserDataInSharedPrefsAndGoToHomeActivity(loginActivity, user);
            } else
                showMessage(loginActivity, "Informations incorrectes", "Email ou mot de passe incorrect !");
        } else {
            if (exceptionInfo.equals(SOCKET_TIMEOUT_EXCEPTION))
                showMessage(loginActivity, "Pas de connexion internet", "Merci de vérifier votre connexion internet!");
            else
                showMessage(loginActivity, "Problème survenu", "Désolé, une erreur s'est produite (Code d'erreur : 003)");
            //showMessageUsingDialogFragment(newUserActivity, "Exception", "Une erreur s'est produite !\n" + "DETAILS :\n" + exceptionInfo);
        }
    }

    private void resetPassword(final User user) {
        FragmentTransaction ft = loginActivity.getSupportFragmentManager().beginTransaction();
        DialogFragmentToResetPasswordDuringFirstLogin dialog = new DialogFragmentToResetPasswordDuringFirstLogin(this, user);
        ft.add(dialog, "dialogFragmentToResetPasswordDuringFirstLogin");
        ft.commit();
    }

    public void saveUserDataInSharedPrefsAndGoToHomeActivity(LoginActivity loginActivity, User user) {
        SharedPrefsManager sharedPrefsManager = new SharedPrefsManager(loginActivity);
        sharedPrefsManager.saveUserInfo(user);
        loginActivity.openHomeActivity(user);
    }
}
