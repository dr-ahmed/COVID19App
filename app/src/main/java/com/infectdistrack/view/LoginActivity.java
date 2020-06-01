package com.infectdistrack.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.infectdistrack.R;
import com.infectdistrack.model.CheckUserSessionDataValidityAsyncTask;
import com.infectdistrack.model.SharedPrefsManager;
import com.infectdistrack.model.User;
import com.infectdistrack.presenter.LoginController;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";
    private EditText emailEdt, passwordEdt;
    LoginController loginController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        loginController = new LoginController(this);
        loginController.checkUserSessionData(getIntent());
    }

    private void initViews() {
        emailEdt = findViewById(R.id.login_email_edt);
        passwordEdt = findViewById(R.id.login_password_edt);
        Button connectBtn = findViewById(R.id.login_connect_btn);
        connectBtn.setOnClickListener(this);
    }

    public EditText getEmailEdt() {
        return emailEdt;
    }

    public EditText getPasswordEdt() {
        return passwordEdt;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.login_connect_btn) {
            loginController.onClickConnectButtonController();
        }
    }

    public void openHomeActivity(User user) {
        Intent intent = new Intent(this, HomeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("currentUser", user);
        intent.putExtra("currentUserBundle", bundle);
        startActivity(intent);
        finish();
    }
}
