package com.infectdistrack.presenter;

import android.widget.EditText;

import com.infectdistrack.model.LoginAsyncTask;
import com.infectdistrack.model.SharedPrefsManager;
import com.infectdistrack.model.User;
import com.infectdistrack.model.Util;
import com.infectdistrack.view.LoginActivity;

public class LoginController {

    private LoginActivity view;

    public LoginController(LoginActivity view) {
        this.view = view;
    }

    private boolean isFieldEmpty(EditText editText) {
        if (editText.getText().toString().trim().length() == 0) {
            editText.requestFocus();
            editText.setError("Champ obligatoire !");
            return true;
        }
        return false;
    }

    public void onClickConnectButtonController() {
        if (isFieldEmpty(view.getLoginEdt()) || isFieldEmpty(view.getPasswordEdt()))
            return;

        view.showProgressDialog();

        verifyUser();
    }

    private void verifyUser() {
        LoginAsyncTask loginAsyncTask = new LoginAsyncTask(this);
        String login = view.getLoginEdt().getText().toString(), password = view.getPasswordEdt().getText().toString();
        loginAsyncTask.execute(login, password);
    }

    public void onLoginResponse(User user, String exceptionInfo, boolean userIsConfirmed) {
        if (exceptionInfo.isEmpty()) {
            if (userIsConfirmed) {
                SharedPrefsManager sharedPrefsManager = new SharedPrefsManager(view);
                sharedPrefsManager.saveUserInfo(user);
                view.openHomeActivity(user);
            } else
                view.showMessage("Informations incorrectes", "Login ou mot de passe incorrect !");
        } else {
            if (!Util.isInternetAvailable())
                view.showMessage("Pas de connexion internet", "Merci de vérifier votre connexion internet!");
            else
                view.showMessage("Exception", "Désolé, une erreur s'est produite !\n" + "DETAILS :\n" + exceptionInfo);
        }
    }
}
