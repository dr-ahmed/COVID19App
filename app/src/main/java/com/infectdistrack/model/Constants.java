package com.infectdistrack.model;

public class Constants {
    private static final String TAG = "Util";

    static String POST_METHOD = "POST",
            SCRIPT_PATH = "http://192.168.1.100/infectdisdb/",
            LOGIN_SCRIPT_NAME = "login.php",
            ADD_NEW_USER_SCRIPT_NAME = "add_new_user.php",
            ENCODING = "UTF-8",
            USER_ID_TAG = "id",
            USER_FULL_NAME_TAG = "full_name",
            USER_EMAIL_TAG = "email",
            USER_PASSWORD_TAG = "password",
            USER_CATEGORY_TAG = "category",
            USER_ASSOCIATE_ADMIN_TAG = "associate_admin",
            USER_WILAYA_TAG = "wilaya",
            USER_ESTABLISHMENT_TAG = "establishment",
            CHAR_SET_NAME = "iso-8859-1",
            JSON_HEADER_TAG = "User",
            SUCCEESSFUL = "succeessful",
            ALLOWED_NUMBERS = "7826345901",
            ALLOWED_CHARACTERS = "789tuvwxyzIJKLMNOPQRabcde01234fghijkABCDEqrs56STUVFGHlmnopWXYZ";

    public static final String DEFAULT_WILAYA = "Choisir la Wilaya";

    public static final String SUPER_ADMIN = "SUPER_ADMIN",
            ADMIN = "ADMIN",
            USER = "USER";

    public static final String[] WILAYAS = new String[]{DEFAULT_WILAYA, "Nouakchott-Sud", "Nouakchott-Ouest",
            "Nouakchott-Nord", "Hodh Ech-Chargui", "Hodh El Gharbi", "Assaba", "Brakna", "Gorgol", "Guidimakha",
            "Trarza", "Tagant", "Inchiri", "Adrar", "Tiris Zemour", "Nouadhibou"};

    static final int READ_TIMEOUT = 15000, CONNECTION_TIMEOUT = 15000;
}
