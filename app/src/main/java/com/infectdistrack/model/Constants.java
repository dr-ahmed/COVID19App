package com.infectdistrack.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;

public class Constants {
    private static final String TAG = "Constants";

    public static final String YES = "YES", NO = "NO",
            OUI = "OUI", NON = "NON", NOT_KNOW = "SAIT_PAS",
            HOPITAL = "HOPITAL", PHARMACIE = "PHARMACIE", CENTRE_SANTE = "CENTRE_SANTE", NUMERO_VERT = "NUMERO_VERT", CLINIQUE_PRIVEE = "CLINIQUE_PRIVE",
            CONFIANCE = "CONFIANCE", TRANSPORT = "TRANSPORT", ACCESSIBILITE = "ACCESSIBILITE", SOCIAL = "SOCIAL", PHOBIE = "PHOBIE",
            BAS = "BAS", MOYEN = "MOYEN", ELEVE = "ELEVE",
            DEPISTAGE = "DEPISTAGE", CONTROLE = "CONTROLE",
            POSITIF = "POSITIF", NEGATIF = "NEGATIF",
            COMPATIBLE = "COMPATIBLE", INCOMPATIBLE = "INCOMPATIBLE",
            IGG = "IGG", IGM = "IGM",
            DECEDE = "DECEDE", VIVANT = "VIVANT",
            DOMICILE = "DOMICILE", STRUCTURE_SANITAIRE = "STRUCTURE_SANITAIRE",
            HOSPITALISE = "HOSPITALISE", CONFINE_A_DOMICILE = "CONFINE_A_DOMICILE", GUERI = "GUERI", AUTRE = "AUTRE",
            SOCKET_TIMEOUT_EXCEPTION = "java.net.SocketTimeoutException",
            DEVICE_NOT_CONNECTED_TO_INTERNET = "DEVICE_NOT_CONNECTED_TO_INTERNET";

    public static final String POST_METHOD = "POST",
            SCRIPT_PATH = "https://www.amdrs.org/infectdisdb/",
            LOGIN_SCRIPT_NAME = "login.php",
            ADD_NEW_USER_SCRIPT_NAME = "add_new_user.php",
            CHECKING_DUPLICATE_USER_SCRIPT_NAME = "check_duplicate_user.php",
            CHECKING_USER_SESSION_DATA_VALIDITY_SCRIPT_NAME = "check_user_session_data_validity.php",
            ADD_NEW_FORM_SCRIPT_NAME = "add_new_form.php",
            RESET_PASSWORD_DURING_FIRST_LOGIN_SCRIPT_NAME = "reset_password_during_first_login.php",
            RETRIEVE_PATIENT_DATA_SCRIPT_NAME = "retrieve_patient_data.php",
            ENCODING = "UTF-8",
            NO_INTERNET_CONNECTION = "NO_INTERNET_CONNECTION",
            CHAR_SET_NAME = "iso-8859-1",
            JSON_HEADER_TAG = "User",
            DOES_USER_EXIST_TAG = "does_user_exist",
            SUCCEESSFUL = "succeessful",
            ALLOWED_CHARACTERS = "789tuvwxyzIJKLMNOPQRabcde01234fghijkABCDEqrs56STUVFGHlmnopWXYZ",
            EMAIL = "amdrs2020@gmail.com",
            PASSWORD = "&_&COVID19";

    public static final int PASSWORD_SIZE = 8;

    public static final String USER_ID_TAG = "id",
            USER_FULL_NAME_TAG = "full_name",
            USER_FIRST_LOGIN_TAG = "first_login",
            USER_PHONE_NUMBER_TAG = "phone_number",
            USER_EMAIL_TAG = "email",
            USER_PASSWORD_TAG = "password",
            USER_CATEGORY_TAG = "category",
            USER_ASSOCIATE_ADMIN_TAG = "associate_admin",
            USER_WILAYA_TAG = "wilaya",
            USER_MOUGHATAA_TAG = "moughataa",
            USER_TYPE_TAG = "user_type",
            USER_ESTABLISHMENT_TYPE_TAG = "establishment_type",
            USER_ESTABLISHMENT_CATEGORY_TAG = "establishment_category",
            USER_CREATION_DATE_TAG = "creation_date",
            PHONE_NUMBER_PK_TAG = "phone_number_pk",
            PATIENT_REQUEST_TYPE = "patient_request_type",
            PARENT_ID_TAG = "parent_id",
            PATIENT_OBJECT_TAG = "patient_object_tag",
            DOES_PATIENT_EXIST = "does_patient_exist";

    public static final String EMPTY_STRING = "",
            DEFAULT_WILAYA = "Choisir la wilaya",
            DEFAULT_AGE = "Choisir l'âge",
            DEFAULT_TYPE = "Choisir le type",
            PUBLIC_ESTABLISHMENT = "Public",
            PRIVATE_ESTABLISHMENT = "Privé",
            OTHER_ESTABLISHMENT = "Autre";

    public static LinkedHashMap<String, ArrayList<String>> wilayasAndMoughataas = new LinkedHashMap<>();

    public final static String UNIQUE_ITEM = "UNIQUE_ITEM",
            ASSOCIATED_ITEM = "ASSOCIATED_ITEM",
            OPTION_TAG = "OPTION_TAG",
            PHONE_NUMBER_TAG = "PHONE_NUMBER_TAG",
            BUNDLE_EXTRA_TAG = "BUNDLE_EXTRA_TAG";

    public static void setWilayasAndMoughataas() {
        wilayasAndMoughataas.put(DEFAULT_WILAYA, new ArrayList<>(Collections.singletonList(EMPTY_STRING)));
        wilayasAndMoughataas.put("Adrar", new ArrayList<>(Arrays.asList(EMPTY_STRING, "Aoujeft", "Atar", "Chinguetti", "Ouadane")));
        wilayasAndMoughataas.put("Assaba", new ArrayList<>(Arrays.asList(EMPTY_STRING, "Barkewol (Aftout)", "Boumdeid", "Guerou", "Kankossa", "Kiffa")));
        wilayasAndMoughataas.put("Brakna", new ArrayList<>(Arrays.asList(EMPTY_STRING, "Aleg", "Bababé", "Boghé", "M'Bagne", "Magta-Lahjar")));
        wilayasAndMoughataas.put("Dakhlet Nouadhibou", new ArrayList<>(Arrays.asList(EMPTY_STRING, "Nouadhibou")));
        wilayasAndMoughataas.put("Gorgol", new ArrayList<>(Arrays.asList(EMPTY_STRING, "Kaédi", "M'Bout", "Maghama", "Monguel")));
        wilayasAndMoughataas.put("Guidimakha", new ArrayList<>(Arrays.asList(EMPTY_STRING, "Ould Yengé", "Sélibabi")));
        wilayasAndMoughataas.put("Hodh Ech Chargui", new ArrayList<>(Arrays.asList(EMPTY_STRING, "Amourj", "Bassikounou", "Djiguenni", "Néma", "Oualata", "Timbedra")));
        wilayasAndMoughataas.put("Hodh El Gharbi", new ArrayList<>(Arrays.asList(EMPTY_STRING, "Aioun El Atrouss", "Kobenni", "Tamchekett", "Tintane")));
        wilayasAndMoughataas.put("Inchiri", new ArrayList<>(Arrays.asList(EMPTY_STRING, "Akjoujt")));
        wilayasAndMoughataas.put("Nouakchott-Nord", new ArrayList<>(Arrays.asList(EMPTY_STRING, "Dar Naim", "Teyareth", "Toujounine")));
        wilayasAndMoughataas.put("Nouakchott-Ouest", new ArrayList<>(Arrays.asList(EMPTY_STRING, "Ksar", "Sebkha", "Tevragh Zeina")));
        wilayasAndMoughataas.put("Nouakchott-Sud", new ArrayList<>(Arrays.asList(EMPTY_STRING, "Araffat", "ElMina", "Riyadh")));
        wilayasAndMoughataas.put("Tagant", new ArrayList<>(Arrays.asList(EMPTY_STRING, "Moudjeria", "Tichitt", "Tidjikdja")));
        wilayasAndMoughataas.put("Tiris Zemmour", new ArrayList<>(Arrays.asList(EMPTY_STRING, "Bir Moghrein", "F'Derick", "Zouerate")));
        wilayasAndMoughataas.put("Trarza", new ArrayList<>(Arrays.asList(EMPTY_STRING, "Boutilimit", "Keurmacen", "Mederdra", "Ouad Naga", "R'Kiz", "Rosso")));
    }

    public static final String STRUCTURES_SANITAIRES[] = {EMPTY_STRING, "CNC", "CNH", "INHV", "HCZ", "H Amitié", "H M-E", "CNO", "H Militaire", "H Khalifa",
            "H Néma", "H Aioun", "H Kiffa", "H Kaédi", "H Sélibaby", "H Aleg", "H Rosso", "H Atar", "H Zoueratt", "H Nouadhibou", "H Tidjikdja", "H Akjoujt"};

    public static final String SUPER_ADMIN = "SUPER_ADMIN",
            ADMIN = "ADMIN",
            USER = "USER",
            ADMIN_LABEL = "administrateur ",
            USER_LABEL = "utilisateur ";

    public static final String[] ADMIN_TYPE_LIST = new String[]{DEFAULT_TYPE, "Chef d'équipe", "Médecin chef", "Chef service", "Directeur", "Spécialiste", "Chercheur", "Autre"},
            USER_TYPE_LIST = new String[]{DEFAULT_TYPE, "Agent d'équipe", "Superviseur", "Point focal", "Médecin", "Thésard", "Autre"};

    static final int READ_TIMEOUT = 10000, CONNECTION_TIMEOUT = 10000;

    public static User CURRENT_USER;
    public static int formCount = 0;
}
