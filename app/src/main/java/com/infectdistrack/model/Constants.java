package com.infectdistrack.model;

public class Constants {
    private static final String TAG = "Util";

    public static String YES = "YES",
            POST_METHOD = "POST",
            SCRIPT_PATH = "http://www.amdrs.org/infectdisdb/", // http://192.168.1.100/
            LOGIN_SCRIPT_NAME = "login.php",
            ADD_NEW_USER_SCRIPT_NAME = "add_new_user.php",
            CHECKING_DUPLICATE_USER_SCRIPT_NAME = "check_duplicate_user.php",
            CHECKING_USER_SESSION_DATA_VALIDITY_SCRIPT_NAME = "check_user_session_data_validity.php",
            ADD_NEW_FORM_SCRIPT_NAME = "add_new_form.php",
            ENCODING = "UTF-8",
            CHAR_SET_NAME = "iso-8859-1",
            JSON_HEADER_TAG = "User",
            DOES_USER_EXIST_TAG = "does_user_exist",
            SUCCEESSFUL = "succeessful",
            ALLOWED_CHARACTERS = "789tuvwxyzIJKLMNOPQRabcde01234fghijkABCDEqrs56STUVFGHlmnopWXYZ",
            EMAIL = "amdrs2020@gmail.com",
            PASSWORD = "&_&COVID19";


    static String USER_ID_TAG = "id",
            USER_FULL_NAME_TAG = "full_name",
            USER_EMAIL_TAG = "email",
            USER_PASSWORD_TAG = "password",
            USER_CATEGORY_TAG = "category",
            USER_ASSOCIATE_ADMIN_TAG = "associate_admin",
            USER_WILAYA_TAG = "wilaya",
            USER_ESTABLISHMENT_TAG = "establishment",
            USER_CREATION_DATE_TAG = "creation_date";

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

    static String PARENT_USER_ID = "parentUserID",
            PATIENT_NAME = "patientName",
            PATIENT_PHONE_NUMBER = "patientTel",
            PATIENT_GENDER = "patientgenre",
            PATIENT_AGE = "patientAge",
            PATIENT_WILAYA = "patientWilaya",
            PATIENT_SUSPECTED_CASES = "patientSuspecCase",
            PATIENT_SYMPTOMS = "patientSymp",
            PATIENT_TERRAIN = "patientTerrain",
            PATIENT_COVID19_CONFIRMED = "patientCovidConfirm",
            PATIENT_EVOLUTION = "patientEvolution",
            FORM_ADDITION_DATE = "additionDate";
}
