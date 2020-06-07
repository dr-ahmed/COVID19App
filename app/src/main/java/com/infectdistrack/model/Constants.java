package com.infectdistrack.model;

public class Constants {
    private static final String TAG = "Util";

    static String POST_METHOD = "POST",
            SCRIPT_PATH = "http://192.168.1.100/infectdisdb/",
            LOGIN_SCRIPT_NAME = "login.php",
            ADD_NEW_USER_SCRIPT_NAME = "add_new_user.php",
            CHECKING_DUPLICATE_USER_SCRIPT_NAME = "check_duplicate_user.php",
            CHECKING_USER_SESSION_DATA_VALIDITY_SCRIPT_NAME = "check_user_session_data_validity.php",
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
            ALLOWED_CHARACTERS = "789tuvwxyzIJKLMNOPQRabcde01234fghijkABCDEqrs56STUVFGHlmnopWXYZ",
            EMAIL = "amdrs2020@gmail.com",
            PASSWORD = "&_&COVID19";

    public static final String DEFAULT_WILAYA = "Choisir la Wilaya",
            DEFAULT_AGE = "Choisir l'âge";

    public static final String SUPER_ADMIN = "SUPER_ADMIN",
            ADMIN = "ADMIN",
            USER = "USER",
            ADMIN_LABEL = "administrateur ",
            USER_LABEL = "utilisateur ";

    public static final String[] WILAYAS = new String[]{DEFAULT_WILAYA, "Nouakchott-Sud", "Nouakchott-Ouest",
            "Nouakchott-Nord", "Hodh Ech-Chargui", "Hodh El Gharbi", "Assaba", "Brakna", "Gorgol", "Guidimakha",
            "Trarza", "Tagant", "Inchiri", "Adrar", "Tiris Zemour", "Nouadhibou"},
            AGE_LIST = new String[]{DEFAULT_AGE, "0-5", "6-15", "16-30", "31-40", "41-50", "51-60", "61-80", "81-90", "100 ou plus"};

    static final int READ_TIMEOUT = 15000, CONNECTION_TIMEOUT = 15000;
}
