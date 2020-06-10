package com.infectdistrack.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.infectdistrack.R;
import com.infectdistrack.model.User;
import com.infectdistrack.presenter.HomeController;

import static com.infectdistrack.model.Constants.ADMIN_LABEL;
import static com.infectdistrack.model.Constants.SUPER_ADMIN;
import static com.infectdistrack.model.Constants.USER;
import static com.infectdistrack.model.Constants.USER_LABEL;
import static com.infectdistrack.presenter.UIBasicController.showMessage;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private static final String TAG = "HomeActivity";

    private User currentUser;
    private TextView welcomeLabelTxt;
    private Button addUserBtn, makeActionBtn, logoutBtn;
    private HomeController homeController;
    private RadioGroup formTypeRadioGroup, actionTypeRadioGroup;
    private String formTypeValue = "", actionTypeValue = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        init();
    }

    private void init() {
        initViews();
        setListeners();
        homeController = new HomeController(this);
    }

    private void initViews() {
        welcomeLabelTxt = findViewById(R.id.welcome_label_txt);
        getUserDataFromLoginActivity();
        addUserBtn = findViewById(R.id.new_user_btn);
        if (currentUser.getCategory().equals(USER))
            addUserBtn.setVisibility(View.GONE);
        else
            addUserBtn.setText("Ajouter un ".concat(currentUser.getCategory().equals(SUPER_ADMIN) ? ADMIN_LABEL : USER_LABEL));

        makeActionBtn = findViewById(R.id.make_action_btn);

        formTypeRadioGroup = findViewById(R.id.form_type_radio_group);
        formTypeRadioGroup.setOnCheckedChangeListener(this);
        actionTypeRadioGroup = findViewById(R.id.action_type_radio_group);
        actionTypeRadioGroup.setOnCheckedChangeListener(this);

        logoutBtn = findViewById(R.id.logout_btn);
    }

    public User getCurrentUser() {
        return currentUser;
    }

    private void setListeners() {
        addUserBtn.setOnClickListener(this);
        makeActionBtn.setOnClickListener(this);
        logoutBtn.setOnClickListener(this);
    }

    private void getUserDataFromLoginActivity() {
        Bundle bundle = getIntent().getBundleExtra("currentUserBundle");
        currentUser = bundle.getParcelable("currentUser");
        welcomeLabelTxt.setText("Bienvenue " + currentUser.getFullName());
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (group.getId() == R.id.form_type_radio_group) {
            if (checkedId == R.id.covid19_item)
                formTypeValue = "COVID19";
            else if (checkedId == R.id.deces_item)
                formTypeValue = "DECES";
        } else if (group.getId() == R.id.action_type_radio_group) {
            if (checkedId == R.id.remplir_item)
                actionTypeValue = "REMPLIR";
            else if (checkedId == R.id.visualiser_item)
                actionTypeValue = "VISUALISER";
        }
    }

    private void onStartNewCovid19Form() {
        if (formTypeValue.isEmpty()) {
            showMessage(this, "Choix obligatoire", "Veuillez sélectionner le type de formulaire");
            return;
        }

        if (actionTypeValue.isEmpty()) {
            showMessage(this, "Choix obligatoire", "Veuillez sélectionner le type d'action");
            return;
        }

        if (formTypeValue.equals("COVID19") && actionTypeValue.equals("REMPLIR")) {
            Intent intent = new Intent(this, Covid19FormActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("parentUser", currentUser);
            intent.putExtra("parentUserBundle", bundle);
            startActivity(intent);
        } else
            showMessage(this, "À suivre", "Cette fonctionnalité serait bientôt disponible");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.new_user_btn:
                homeController.launchActivityWithBundle(NewUserActivity.class, "parentUser", "parentUserBundle", currentUser);
                break;
            case R.id.make_action_btn:
                onStartNewCovid19Form();
                break;
            case R.id.logout_btn:
                homeController.doYouWantToExit();
                break;
            default: {
            }
        }
    }
}
