package com.infectdistrack.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import com.infectdistrack.model.SharedPrefsManager;
import com.infectdistrack.presenter.Covid19NewFormController;

import static com.infectdistrack.presenter.UIBasicController.hideProgressDialog;

public class DialogFragmentForGoingBackAfterAddingForm extends DialogFragment {

    private Covid19NewFormController covid19NewFormController;

    public DialogFragmentForGoingBackAfterAddingForm(Covid19NewFormController covid19NewFormController) {
        this.covid19NewFormController = covid19NewFormController;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialogBuiler = new AlertDialog.Builder(getActivity());
        dialogBuiler.setMessage(getArguments().getString("message"));
        dialogBuiler.setTitle(getArguments().getString("title"));
        dialogBuiler.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(covid19NewFormController.getCovid19FormPart4().getActivity(), HomeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("currentUser", covid19NewFormController.getCovid19FormPart4().getParentUser());
                intent.putExtra("currentUserBundle", bundle);
                startActivity(intent);
                covid19NewFormController.getCovid19FormPart4().getActivity().finish();
            }
        });
        dialogBuiler.setNegativeButton("Non", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(covid19NewFormController.getCovid19FormPart4().getActivity(), Covid19FormActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("parentUser", covid19NewFormController.getCovid19FormPart4().getParentUser());
                intent.putExtra("parentUserBundle", bundle);
                startActivity(intent);
                covid19NewFormController.getCovid19FormPart4().getActivity().finish();
            }
        });
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
    }
}
