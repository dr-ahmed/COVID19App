package com.infectdistrack.model;

import android.content.Context;
import android.content.SharedPreferences;

import static com.infectdistrack.model.Util.*;

public class SharedPrefsManager {

    private Context context;
    private String prefsTag = "userInfo";
    private final String DEFAULT_VALUE = "DEFAULT_VALUE";

    public SharedPrefsManager(Context context) {
        this.context = context;
    }

    public void saveUserInfo(User user) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(prefsTag, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_NAME_TAG, user.getName());
        editor.putString(USER_EMAIL_TAG, user.getEmail());
        editor.putString(USER_PASSWORD_TAG, user.getPassword());
        editor.putString(USER_CATEGORY_TAG, user.getCategory());
        editor.putString(USER_ASSOCIATE_ADMIN_TAG, user.getAssociateAdmin());
        editor.putString(USER_LOCATION_TAG, user.getLocation());
        editor.putString(USER_ESTABLISHMENT_TAG, user.getEstablishment());
        editor.apply();
    }

    private String getDataByTag(SharedPreferences sharedPreferences, String dataTag) {
        return sharedPreferences.getString(dataTag, DEFAULT_VALUE);
    }

    public User isUserLogedIn() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(prefsTag, Context.MODE_PRIVATE);
        boolean isNameEmpty = getDataByTag(sharedPreferences, USER_NAME_TAG).equals(DEFAULT_VALUE),
                isEmailEmpty = getDataByTag(sharedPreferences, USER_EMAIL_TAG).equals(DEFAULT_VALUE),
                isPasswordEmpty = getDataByTag(sharedPreferences, USER_PASSWORD_TAG).equals(DEFAULT_VALUE),
                isCategoryEmpty = getDataByTag(sharedPreferences, USER_CATEGORY_TAG).equals(DEFAULT_VALUE),
                isAssociateAdminEmpty = getDataByTag(sharedPreferences, USER_ASSOCIATE_ADMIN_TAG).equals(DEFAULT_VALUE),
                isLocationEmpty = getDataByTag(sharedPreferences, USER_LOCATION_TAG).equals(DEFAULT_VALUE),
                isEstablishmentEmpty = getDataByTag(sharedPreferences, USER_ESTABLISHMENT_TAG).equals(DEFAULT_VALUE);

        User user = null;
        if (!isNameEmpty && !isEmailEmpty && !isPasswordEmpty && !isCategoryEmpty && !isAssociateAdminEmpty && !isLocationEmpty && !isEstablishmentEmpty)
            user = new User(getDataByTag(sharedPreferences, USER_NAME_TAG),
                    getDataByTag(sharedPreferences, USER_EMAIL_TAG),
                    getDataByTag(sharedPreferences, USER_PASSWORD_TAG),
                    getDataByTag(sharedPreferences, USER_CATEGORY_TAG),
                    getDataByTag(sharedPreferences, USER_ASSOCIATE_ADMIN_TAG),
                    getDataByTag(sharedPreferences, USER_LOCATION_TAG),
                    getDataByTag(sharedPreferences, USER_ESTABLISHMENT_TAG));
        return user;
    }

    public void clearUserData() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(prefsTag, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}