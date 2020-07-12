package com.infectdistrack.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import com.infectdistrack.model.Patient;

import static com.infectdistrack.model.Constants.UNIQUE_ITEM;

public class DialogFragmentAboutPhoneNumberConfirmation extends DialogFragment {

    private PhoneNumberCheckoutActivity phoneNumberCheckoutActivity;
    private Patient patient;

    public DialogFragmentAboutPhoneNumberConfirmation(PhoneNumberCheckoutActivity phoneNumberCheckoutActivity, Patient patient) {
        this.phoneNumberCheckoutActivity = phoneNumberCheckoutActivity;
        this.patient = patient;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialogBuiler = new AlertDialog.Builder(getActivity());
        dialogBuiler.setTitle("Demande de confirmation");
        dialogBuiler.setMessage("Cet identifiant existe déjà, voulez-vous lui associer un nouveau formulaire ?");
        dialogBuiler.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                phoneNumberCheckoutActivity.openPhoneNumberDetailsFragmentAndInflatePatientObject(patient, UNIQUE_ITEM);
            }
        });
        dialogBuiler.setNegativeButton("Non", null);

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
    }
}
