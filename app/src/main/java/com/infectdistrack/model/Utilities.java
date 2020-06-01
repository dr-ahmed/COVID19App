package com.infectdistrack.model;

import android.util.Base64;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

import static com.infectdistrack.model.Constants.ALLOWED_CHARACTERS;

public class Utilities {

    private static final String TAG = "Utilities";

    public static boolean isInternetAvailable() {
        String netAddress;
        try {
            netAddress = new InternetCheckingTask().execute("www.google.com").get(100, TimeUnit.MILLISECONDS);
            return !netAddress.equals("");
        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
            return false;
        }
    }

    public static String replaceApostrophe(String string) {
        return string.contains("'") ? string.replaceAll("'", "''") : string;
    }

    public static String removeApostrophe(String string) {
        return string.contains("''") ? string.replaceAll("''", "'") : string;
    }

    static String generteNewPassword(final int size) {
        final SecureRandom random = new SecureRandom();
        final StringBuilder sb = new StringBuilder(size);
        for (int i = 0; i < size; ++i)
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        return sb.toString();
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

    static String bytesToHexString(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }
}
