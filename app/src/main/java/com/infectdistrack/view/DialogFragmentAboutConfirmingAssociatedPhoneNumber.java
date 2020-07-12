package com.infectdistrack.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.infectdistrack.model.Patient;
import com.infectdistrack.presenter.NewUserController;

import static com.infectdistrack.model.Constants.ASSOCIATED_ITEM;
import static com.infectdistrack.presenter.UIBasicController.hideProgressDialog;

public class DialogFragmentAboutConfirmingAssociatedPhoneNumber extends DialogFragment {

    private PhoneNumberCheckoutFragment phoneNumberCheckoutFragment;
    private Patient patient;
    private int associatedNumber;

    public DialogFragmentAboutConfirmingAssociatedPhoneNumber(PhoneNumberCheckoutFragment phoneNumberCheckoutFragment, Patient patient, int associatedNumber) {
        this.phoneNumberCheckoutFragment = phoneNumberCheckoutFragment;
        this.patient = patient;
        this.associatedNumber = associatedNumber;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialogBuiler = new AlertDialog.Builder(getActivity());
        dialogBuiler.setTitle("Demande de confirmation");
        dialogBuiler.setMessage("Ce numéro est déjà sauvegardé dans la base de données, "
                + (associatedNumber == 0 ? " aucun identifiant n'y est déjà associé." : associatedNumber + " identifiants y sont déjà associés.")
                + "\nVoulez-vous lui associer un nouvel identifiant numéro " + (associatedNumber + 1) + " ?");
        dialogBuiler.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                phoneNumberCheckoutFragment.openPhoneNumberDetailsFragmentAndInflatePatientObject(patient, ASSOCIATED_ITEM);
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
