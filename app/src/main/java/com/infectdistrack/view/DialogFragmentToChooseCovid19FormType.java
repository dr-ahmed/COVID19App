package com.infectdistrack.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

public class DialogFragmentToChooseCovid19FormType extends DialogFragment {

    private static final String TAG = "ChooseCovid19FormType";

    private HomeActivity homeActivity;
    private String[] dialogList = {"Enquête épidémiologique", "Veille communautaire"};
    private final int checkedItem = -1; // default item checked
    private int currentItem = -1;

    public DialogFragmentToChooseCovid19FormType(HomeActivity homeActivity) {
        this.homeActivity = homeActivity;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialogBuiler = new AlertDialog.Builder(getActivity());
        dialogBuiler.setTitle("Formulaires COVID-19");
        dialogBuiler.setSingleChoiceItems(dialogList, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                currentItem = i;
            }
        });

        dialogBuiler.setPositiveButton("Accéder", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                if (currentItem == 0)
                    homeActivity.launchPhoneNumberCheckoutActivity();
                else if (currentItem == 1)
                    homeActivity.launchCommunityWatchFragment();
            }
        });

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
