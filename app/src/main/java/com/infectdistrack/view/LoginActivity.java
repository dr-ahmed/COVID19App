package com.infectdistrack.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.infectdistrack.R;
import com.infectdistrack.model.SharedPrefsManager;
import com.infectdistrack.model.User;
import com.infectdistrack.presenter.LoginController;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";
    private EditText loginEdt, passwordEdt;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        checkUserSessionData();
    }

    private void initViews() {
        loginEdt = findViewById(R.id.email_edt);
        passwordEdt = findViewById(R.id.password_edt);
        Button connectBtn = findViewById(R.id.connect_btn);
        connectBtn.setOnClickListener(this);
    }

    private void checkUserSessionData() {
        SharedPrefsManager sharedPrefsManager = new SharedPrefsManager(this);
        User user = sharedPrefsManager.isUserLogedIn();
        if (user != null)
            openHomeActivity(user);
        else
            Log.e(TAG, "checkUserPrefs: empty session data !");
    }

    public EditText getLoginEdt() {
        return loginEdt;
    }

    public EditText getPasswordEdt() {
        return passwordEdt;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.connect_btn) {
            LoginController loginController = new LoginController(this);
            loginController.onClickConnectButtonController();
        }
    }

    public void showProgressDialog() {
        progressDialog = new ProgressDialog(this, R.style.CustomProgressDialogStyle);
        progressDialog.setMessage("Vérification des informations en cours ...");
        progressDialog.show();
    }

    public void showMessage(String title, String message) {
        progressDialog.dismiss();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        DialogFragmentWithOnlyOneButton dialog = new DialogFragmentWithOnlyOneButton();

        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("message", message);
        dialog.setArguments(args);

        ft.add(dialog, "oooo");
        ft.commit();
    }

    public void openHomeActivity(User user) {
        Intent intent = new Intent(this, HomeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("user", user);
        intent.putExtra("userBundle", bundle);
        startActivity(intent);
        finish();
    }
}
