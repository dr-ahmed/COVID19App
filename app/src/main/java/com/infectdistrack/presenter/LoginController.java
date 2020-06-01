package com.infectdistrack.presenter;

import com.infectdistrack.model.LoginAsyncTask;
import com.infectdistrack.model.SharedPrefsManager;
import com.infectdistrack.model.User;
import com.infectdistrack.model.Utilities;
import com.infectdistrack.view.LoginActivity;

import java.security.NoSuchAlgorithmException;

import static com.infectdistrack.model.Utilities.SHA256;
import static com.infectdistrack.presenter.UIBasicController.hideProgressDialog;
import static com.infectdistrack.presenter.UIBasicController.isFieldEmpty;
import static com.infectdistrack.presenter.UIBasicController.showMessage;
import static com.infectdistrack.presenter.UIBasicController.showProgressDialog;

public class LoginController {

    private LoginActivity loginActivity;

    public LoginController(LoginActivity loginActivity) {
        this.loginActivity = loginActivity;
    }

    public void onClickConnectButtonController() {
        if (isFieldEmpty(loginActivity.getEmailEdt()) || isFieldEmpty(loginActivity.getPasswordEdt()))
            return;

        showProgressDialog(loginActivity, "Vérification des informations en cours ...");

        verifyUser();
    }

    private void verifyUser() {
        LoginAsyncTask loginAsyncTask = new LoginAsyncTask(this);
        String email = loginActivity.getEmailEdt().getText().toString(), password = loginActivity.getPasswordEdt().getText().toString();
        try {
            loginAsyncTask.execute(email, SHA256(password));
        } catch (NoSuchAlgorithmException e) {
            showMessage(loginActivity, "Problème survenu", "Désolé, une erreur s'est produite (Code d'erreur : 001)");
        }
    }

    public void onLoginResponse(User user, String exceptionInfo, boolean userIsConfirmed) {
        hideProgressDialog();
        if (exceptionInfo.isEmpty()) {
            if (userIsConfirmed) {
                SharedPrefsManager sharedPrefsManager = new SharedPrefsManager(loginActivity);
                sharedPrefsManager.saveUserInfo(user);
                loginActivity.openHomeActivity(user);
            } else
                showMessage(loginActivity, "Informations incorrectes", "Email ou mot de passe incorrect !");
        } else {
            if (!Utilities.isInternetAvailable())
                showMessage(loginActivity, "Pas de connexion internet", "Merci de vérifier votre connexion internet!");
            else
                showMessage(loginActivity, "Problème survenu", "Désolé, une erreur s'est produite (Code d'erreur : 002)");
            //showMessageUsingDialogFragment(newUserActivity, "Exception", "Une erreur s'est produite !\n" + "DETAILS :\n" + exceptionInfo);
        }
    }
}
