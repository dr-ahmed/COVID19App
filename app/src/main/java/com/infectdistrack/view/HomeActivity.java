package com.infectdistrack.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.infectdistrack.R;
import com.infectdistrack.model.SharedPrefsManager;
import com.infectdistrack.model.User;
import com.infectdistrack.model.ViewPagerAdapter;

import java.util.ArrayList;

import static com.infectdistrack.model.Constants.ADMIN_LABEL;
import static com.infectdistrack.model.Constants.SUPER_ADMIN;
import static com.infectdistrack.model.Constants.USER;
import static com.infectdistrack.model.Constants.USER_LABEL;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "HomeActivity";

    private User currentUser;
    private TextView welcomeLabelTxt;
    private Button addUserBtn, makeActionBtn, logoutBtn;

    private ViewPager mMyViewPager;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initViews();
        setListeners();

        /*
        mTabLayout = findViewById(R.id.tab_layout);
        mMyViewPager = findViewById(R.id.view_pager);

        init();
         */
    }

    private void init() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new LoginFrag());
        fragments.add(new BlankFragment());
        fragments.add(new LikeFragment());
        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 1, fragments);
        mMyViewPager.setAdapter(pagerAdapter);
        mTabLayout.setupWithViewPager(mMyViewPager, true);
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
        logoutBtn = findViewById(R.id.logout_btn);
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

    private void launchActivityWithBundle(Class<?> cls, String userTag, String bundleTag) {
        Intent intent = new Intent(HomeActivity.this, cls);
        Bundle bundle = new Bundle();
        bundle.putParcelable(userTag, currentUser);
        intent.putExtra(bundleTag, bundle);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.new_user_btn:
                launchActivityWithBundle(NewUserActivity.class, "parentUser", "parentUserBundle");
                break;
            case R.id.make_action_btn:
                break;
            case R.id.logout_btn:
                doYouWantToExit();
                break;
            default: {
            }
        }
    }

    private void doYouWantToExit() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        DialogFragmentForExitConfirmation dialog = new DialogFragmentForExitConfirmation(this);

        Bundle args = new Bundle();
        args.putString("title", "Demande de confirmation");
        args.putString("message", "Êtes-vous sûr de vouloir vous déconnecter ?");
        dialog.setArguments(args);

        ft.add(dialog, "dialogFragmentForExitConfirmation");
        ft.commit();
    }

    public void logOut() {
        // clear sharedpref data
        SharedPrefsManager sharedPrefsManager = new SharedPrefsManager(HomeActivity.this);
        sharedPrefsManager.clearUserData();

        launchActivityWithBundle(LoginActivity.class, "userloggedOut", "userloggedOutBundle");
        finish();
    }
}
