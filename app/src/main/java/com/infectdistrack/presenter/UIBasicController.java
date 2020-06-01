package com.infectdistrack.presenter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.infectdistrack.R;
import com.infectdistrack.view.DialogFragmentWithOnlyOneButton;

public class UIBasicController {

    private static ProgressDialog progressDialog;

    static void showProgressDialog(AppCompatActivity appCompatActivity, String message) {
        progressDialog = new ProgressDialog(appCompatActivity, R.style.CustomProgressDialogStyle);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public static void hideProgressDialog() {
        progressDialog.dismiss();
    }

    static boolean isFieldEmpty(EditText editText) {
        if (editText.getText().toString().trim().length() == 0) {
            editText.requestFocus();
            editText.setError("Champ obligatoire !");
            return true;
        }
        return false;
    }

    public static void showMessage(AppCompatActivity activity, String title, String message) {
        FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
        DialogFragmentWithOnlyOneButton dialog = new DialogFragmentWithOnlyOneButton();

        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("message", message);
        dialog.setArguments(args);

        ft.add(dialog, "fragmentDialogTag");
        ft.commit();
    }
}
