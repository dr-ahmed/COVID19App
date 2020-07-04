package com.infectdistrack.model;

import java.util.ArrayList;
import java.util.HashMap;

public class Constants {
    private static final String TAG = "Util";

    static String YES = "YES",
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

    static HashMap<String, ArrayList<String>> wilayasAndMoughataas = new HashMap<>();

    public static void setWilayasAndMoughataas() {
        ArrayList<String> moughataas = new ArrayList<>();
        moughataas.add("Aoujeft");
        moughataas.add("Atar");
        moughataas.add("Chinguetti");
        moughataas.add("Ouadane");
        wilayasAndMoughataas.put("Adrar", moughataas);

        moughataas.clear();
        moughataas.add("Barkewol (Aftout)");
        moughataas.add("Boumdeid");
        moughataas.add("Guerou");
        moughataas.add("Kankossa");
        moughataas.add("Kiffa");
        wilayasAndMoughataas.put("Assaba", moughataas);

        moughataas.clear();
        moughataas.add("Aleg");
        moughataas.add("Bababé");
        moughataas.add("Boghé");
        moughataas.add("M'Bagne");
        moughataas.add("Magta-Lahjar");
        wilayasAndMoughataas.put("Brakna", moughataas);

        moughataas.clear();
        moughataas.add("Nouadhibou");
        wilayasAndMoughataas.put("Dakhlet Nouadhibou", moughataas);

        moughataas.clear();
        moughataas.add("Kaédi");
        moughataas.add("M'Bout");
        moughataas.add("Maghama");
        moughataas.add("Monguel");
        wilayasAndMoughataas.put("Gorgol", moughataas);

        moughataas.clear();
        moughataas.add("Ould Yengé");
        moughataas.add("Sélibabi");
        wilayasAndMoughataas.put("Guidimakha", moughataas);

        moughataas.clear();
        moughataas.add("Amourj");
        moughataas.add("Bassikounou");
        moughataas.add("Djiguenni");
        moughataas.add("Néma");
        moughataas.add("Oualata");
        moughataas.add("Timbedra");
        wilayasAndMoughataas.put("Hodh Ech Chargui", moughataas);

        moughataas.clear();
        moughataas.add("Aïoun El Atrouss");
        moughataas.add("Kobenni");
        moughataas.add("Tamchekett");
        moughataas.add("Tintane");
        wilayasAndMoughataas.put("Hodh El Gharbi", moughataas);

        moughataas.clear();
        moughataas.add("Akjoujt");
        wilayasAndMoughataas.put("Inchiri", moughataas);

        moughataas.clear();
        moughataas.add("Dar Naim");
        moughataas.add("Teyareth");
        moughataas.add("Toujounine");
        wilayasAndMoughataas.put("Nouakchott-Nord", moughataas);

        moughataas.clear();
        moughataas.add("Ksar");
        moughataas.add("Sebkha");
        moughataas.add("Tevragh Zeïna");
        wilayasAndMoughataas.put("Nouakchott-Ouest", moughataas);

        moughataas.clear();
        moughataas.add("Araffat");
        moughataas.add("El Mina");
        moughataas.add("Riyadh");
        wilayasAndMoughataas.put("Nouakchott-Sud", moughataas);

        moughataas.clear();
        moughataas.add("Moudjeria");
        moughataas.add("Tichitt");
        moughataas.add("Tidjikdja");
        wilayasAndMoughataas.put("Tagant", moughataas);

        moughataas.clear();
        moughataas.add("Bir Moghreïn");
        moughataas.add("F'Derick");
        moughataas.add("Zouerate");
        wilayasAndMoughataas.put("Tiris Zemmour", moughataas);

        moughataas.clear();
        moughataas.add("Boutilimit");
        moughataas.add("Keurmacen");
        moughataas.add("Mederdra");
        moughataas.add("Ouad Naga");
        moughataas.add("R'Kiz");
        moughataas.add("Rosso");
        wilayasAndMoughataas.put("Trarza", moughataas);
    }

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
