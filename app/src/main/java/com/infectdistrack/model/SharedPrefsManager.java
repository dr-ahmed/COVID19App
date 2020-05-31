package com.infectdistrack.model;

import android.content.Context;
import android.content.SharedPreferences;

import static com.infectdistrack.model.Constants.*;

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
        editor.putInt(USER_ID_TAG, user.getId());
        editor.putString(USER_FULL_NAME_TAG, user.getFullName());
        editor.putString(USER_EMAIL_TAG, user.getEmail());
        editor.putString(USER_PASSWORD_TAG, user.getPassword());
        editor.putString(USER_CATEGORY_TAG, user.getCategory());
        editor.putInt(USER_ASSOCIATE_ADMIN_TAG, user.getAssociateAdmin());
        editor.putString(USER_WILAYA_TAG, user.getWilaya());
        editor.putString(USER_ESTABLISHMENT_TAG, user.getEstablishment());
        editor.apply();
    }

    private String getDataByTag(SharedPreferences sharedPreferences, String dataTag) {
        return sharedPreferences.getString(dataTag, DEFAULT_VALUE);
    }

    public User isUserLoggedIn() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(prefsTag, Context.MODE_PRIVATE);
        boolean isIdEmpty = sharedPreferences.getInt(USER_ID_TAG, -1) == -1,
                isNameEmpty = getDataByTag(sharedPreferences, USER_FULL_NAME_TAG).equals(DEFAULT_VALUE),
                isEmailEmpty = getDataByTag(sharedPreferences, USER_EMAIL_TAG).equals(DEFAULT_VALUE),
                isPasswordEmpty = getDataByTag(sharedPreferences, USER_PASSWORD_TAG).equals(DEFAULT_VALUE),
                isCategoryEmpty = getDataByTag(sharedPreferences, USER_CATEGORY_TAG).equals(DEFAULT_VALUE),
                isAssociateAdminEmpty = sharedPreferences.getInt(USER_ASSOCIATE_ADMIN_TAG, -1) == -1,
                isLocationEmpty = getDataByTag(sharedPreferences, USER_WILAYA_TAG).equals(DEFAULT_VALUE),
                isEstablishmentEmpty = getDataByTag(sharedPreferences, USER_ESTABLISHMENT_TAG).equals(DEFAULT_VALUE);

        User user = null;
        if (!isIdEmpty && !isNameEmpty && !isEmailEmpty && !isPasswordEmpty && !isCategoryEmpty && !isAssociateAdminEmpty && !isLocationEmpty && !isEstablishmentEmpty)
            user = new User(sharedPreferences.getInt(USER_ID_TAG, -1),
                    getDataByTag(sharedPreferences, USER_FULL_NAME_TAG),
                    getDataByTag(sharedPreferences, USER_EMAIL_TAG),
                    getDataByTag(sharedPreferences, USER_PASSWORD_TAG),
                    getDataByTag(sharedPreferences, USER_CATEGORY_TAG),
                    sharedPreferences.getInt(USER_ASSOCIATE_ADMIN_TAG, -1),
                    getDataByTag(sharedPreferences, USER_WILAYA_TAG),
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