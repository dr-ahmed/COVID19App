package com.infectdistrack.model;

import android.util.Base64;
import android.util.Log;

import java.net.SocketTimeoutException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static com.infectdistrack.model.Constants.ALLOWED_CHARACTERS;
import static com.infectdistrack.model.Constants.NO;
import static com.infectdistrack.model.Constants.DEVICE_NOT_CONNECTED_TO_INTERNET;
import static com.infectdistrack.model.Constants.YES;

public class Utilities {

    private static final String TAG = "Utilities";

    public static String isPhoneNumberValid(String phoneNumber) {
        // full regex expression : ^[234]\d{7}$
        if (phoneNumber.matches("^[234].*$")) {
            if (phoneNumber.matches("\\d+"))
                if (phoneNumber.matches("^\\d{8}$"))
                    return YES;
                else
                    return "Le numéro de téléphone doit être constitué de 8 chiffres";
            else
                return "Le numéro de téléphone doit être constitué uniquement de chiffres";
        } else
            return "Le numéro de téléphone doit commencer par 2, 3 ou 4";
    }

    public static String checkInternetConnectionAvailability() {
        String netAddress;
        try {
            netAddress = new InternetCheckingAsyncTask().execute("google.com").get(400, TimeUnit.MILLISECONDS);
            // La vérification ci-dessous provient de la condition (return address != null ? address.getHostAddress() : null) dans la classe InternetCheckingAsyncTask
            return netAddress == null ? DEVICE_NOT_CONNECTED_TO_INTERNET : YES;
            // Si netAddress = null, càd soit le phone n'est pas conencté à Internet, soit l'adresse pinguée n'est pas correcte
        } catch (Exception e) { // Ce catch permet de catcher InterruptedException, ExecutionException et TimeoutException
            Log.e(TAG, Log.getStackTraceString(e));
            // L'exception TimeoutException se déclanche si le phone est théoriquement connecté au net mais on arrive pas
            // à recevoir le résultat de ping au bout de 100 millisecondes. Voir les params de get : get(100, TimeUnit.MILLISECONDS)
            if (e instanceof TimeoutException)
                return DEVICE_NOT_CONNECTED_TO_INTERNET;
            return NO;
        }
    }

    public static String replaceApostrophe(String string) {
        return string.contains("'") ? string.replaceAll("'", "''") : string;
    }

    public static String removeApostrophe(String string) {
        return string.contains("''") ? string.replaceAll("''", "'") : string;
    }

    private static String FromSetToString(Set<Character> password) {
        StringBuilder sb = new StringBuilder();
        for (Character ch : password)
            sb.append(ch);
        return sb.toString();
    }

    public static String generteNewPassword(int size) {
        SecureRandom random = new SecureRandom();
        Set<Character> password = new LinkedHashSet<>();
        while (password.size() < size)
            password.add(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        return FromSetToString(password);
    }

    public static String SHA256(String text) throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(text.getBytes());
        byte[] digest = md.digest();

        return Base64.encodeToString(digest, Base64.NO_WRAP);
    }

    public static boolean isEmailValid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static String getTodayDate() {
        return new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE).format(Calendar.getInstance().getTime());
    }
}
