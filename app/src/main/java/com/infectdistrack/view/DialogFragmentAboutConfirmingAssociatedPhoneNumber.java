package com.infectdistrack.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import com.infectdistrack.model.Patient;

import static com.infectdistrack.model.Constants.ASSOCIATED_ITEM;

public class DialogFragmentAboutConfirmingAssociatedPhoneNumber extends DialogFragment {

    private PhoneNumberCheckoutActivity phoneNumberCheckoutActivity;
    private Patient patient;
    private int associatedNumber;

    public DialogFragmentAboutConfirmingAssociatedPhoneNumber(PhoneNumberCheckoutActivity phoneNumberCheckoutActivity, Patient patient, int associatedNumber) {
        this.phoneNumberCheckoutActivity = phoneNumberCheckoutActivity;
        this.patient = patient;
        this.associatedNumber = associatedNumber;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialogBuiler = new AlertDialog.Builder(getActivity());
        dialogBuiler.setTitle("Demande de confirmation");

        String variantMessage = "";

        if (associatedNumber == 0)
            variantMessage = " aucun identifiant n'y est déjà associé.";
        else if (associatedNumber == 1)
            variantMessage = " un seul identifiant y est déjà associé.";
        else
            variantMessage = associatedNumber + " identifiants y sont déjà associés.";

        dialogBuiler.setMessage("Ce numéro est déjà sauvegardé dans la base de données, " + variantMessage
                + "\nVoulez-vous lui associer un nouvel identifiant numéro " + (associatedNumber + 1) + " ?");
        dialogBuiler.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                phoneNumberCheckoutActivity.openPhoneNumberDetailsFragmentAndInflatePatientObject(patient, ASSOCIATED_ITEM);
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
