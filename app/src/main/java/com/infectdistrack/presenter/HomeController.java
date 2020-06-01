package com.infectdistrack.presenter;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.FragmentTransaction;

import com.infectdistrack.model.SharedPrefsManager;
import com.infectdistrack.model.User;
import com.infectdistrack.view.DialogFragmentForLogOutFromHomeActivity;
import com.infectdistrack.view.HomeActivity;
import com.infectdistrack.view.LoginActivity;

public class HomeController {

    private static final String TAG = "HomeController";
    private Class<?> cls;
    private HomeActivity homeActivity;

    public HomeController(HomeActivity homeActivity) {
        this.homeActivity = homeActivity;
    }

    public HomeActivity getHomeActivity() {
        return homeActivity;
    }

    public void launchActivityWithBundle(Class<?> cls, String userTag, String bundleTag, User user) {
        Intent intent = new Intent(homeActivity.getBaseContext(), cls);
        Bundle bundle = new Bundle();
        bundle.putParcelable(userTag, user);
        intent.putExtra(bundleTag, bundle);
        homeActivity.startActivity(intent);
    }

    public void doYouWantToExit() {
        FragmentTransaction ft = homeActivity.getSupportFragmentManager().beginTransaction();
        DialogFragmentForLogOutFromHomeActivity dialog = new DialogFragmentForLogOutFromHomeActivity(this);

        Bundle args = new Bundle();
        args.putString("title", "Demande de confirmation");
        args.putString("message", "Êtes-vous sûr de vouloir vous déconnecter ?");
        dialog.setArguments(args);

        ft.add(dialog, "dialogFragmentForExitConfirmation");
        ft.commit();
    }

    public void logOut() {
        // clear sharedpref data
        SharedPrefsManager sharedPrefsManager = new SharedPrefsManager(homeActivity.getBaseContext());
        sharedPrefsManager.clearUserData();

        launchActivityWithBundle(LoginActivity.class, "userloggedOut", "userloggedOutBundle", homeActivity.getCurrentUser());
        homeActivity.finish();
    }
}
