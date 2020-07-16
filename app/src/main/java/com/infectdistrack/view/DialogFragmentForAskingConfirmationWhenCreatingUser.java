package com.infectdistrack.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import com.infectdistrack.presenter.NewUserController;

public class DialogFragmentForAskingConfirmationWhenCreatingUser extends DialogFragment {

    private NewUserController newUserController;

    public DialogFragmentForAskingConfirmationWhenCreatingUser(NewUserController newUserController) {
        this.newUserController = newUserController;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setMessage(getArguments().getString("message"));
        dialogBuilder.setTitle(getArguments().getString("title"));
        dialogBuilder.setNegativeButton("Non", null);
        dialogBuilder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                newUserController.insertUser();
            }
        });
        return dialogBuilder.create();
    }

    @Override
    public void onStart() {
        super.onStart();

        // Le préfixe 0x permet de convertir un code Hex en int
        // Les codes Hex sont fournis en mode RGBA (red, green, blue and alpha channel)
        int startColor = 0xFFB88378; // Couleur début
        int endColor = 0xFFF4D0CA; // Couleur fin
        GradientDrawable gradient = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                new int[]{startColor, endColor});
        // Courber les bords du Dialog
        gradient.setCornerRadius(30f);
        getDialog().getWindow().setBackgroundDrawable(gradient);

        ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
        ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_NEGATIVE).setAllCaps(false);

        ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
        ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_POSITIVE).setAllCaps(false);
    }
}
