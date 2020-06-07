package com.infectdistrack.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import com.infectdistrack.presenter.NewUserController;

public class DialogFragmentForAskingUserAboutSendingAccountInformation extends DialogFragment {

    private NewUserController newUserController;

    public DialogFragmentForAskingUserAboutSendingAccountInformation(NewUserController newUserController) {
        this.newUserController = newUserController;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialogBuiler = new AlertDialog.Builder(getActivity());
        dialogBuiler.setMessage(getArguments().getString("message"));
        dialogBuiler.setTitle(getArguments().getString("title"));
        dialogBuiler.setPositiveButton("Par email", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                newUserController.sendUserAccountInformationWithEmail();
            }
        });
        dialogBuiler.setNegativeButton("Partager", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                newUserController.shareUserAccountInformation();
            }
        });
        dialogBuiler.setNeutralButton("Non", null);
        setCancelable(false);

        return dialogBuiler.create();
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

        ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(Color.BLACK);
        ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_NEUTRAL).setAllCaps(false);
    }
}
