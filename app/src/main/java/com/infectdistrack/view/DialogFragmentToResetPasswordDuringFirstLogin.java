package com.infectdistrack.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.infectdistrack.R;
import com.infectdistrack.model.User;

import java.security.NoSuchAlgorithmException;

import static com.infectdistrack.model.Utilities.SHA256;

public class DialogFragmentToResetPasswordDuringFirstLogin extends DialogFragment {


    private static final String TAG = "DialogFragmentToResetPasswordDuringFirstLogin";

    private LoginActivity loginActivity;
    private User user;

    public DialogFragmentToResetPasswordDuringFirstLogin(LoginActivity loginActivity, User user) {
        this.loginActivity = loginActivity;
        this.user = user;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialogBuiler = new AlertDialog.Builder(getActivity());
        LinearLayout rootView = loginActivity.findViewById(R.id.root_view);

        View viewInflated = LayoutInflater.from(loginActivity).inflate(R.layout.reset_password_view, rootView, false);
        final EditText newPasswordEdt = viewInflated.findViewById(R.id.new_password);
        newPasswordEdt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        final EditText newPasswordConfirmationEdt = viewInflated.findViewById(R.id.new_password_confirmation);

        dialogBuiler.setTitle("Réinitialisation du mot de passe")
                .setMessage("Veuillez saisir votre nouveau mot de passe et le confirmer")
                .setView(viewInflated)
                .setPositiveButton("Continuer", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //Do nothing here because we override this button later to change the close behaviour.
                        //However, we still need this because on older versions of Android unless we
                        //pass a handler the button doesn't get instantiated
                    }
                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(loginActivity, "Mot de passe non réinitialisé !", Toast.LENGTH_SHORT).show();
                    }
                });

        final AlertDialog cutomAlertDialog = dialogBuiler.create();
        cutomAlertDialog.setOnShowListener((new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button b = cutomAlertDialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (newPasswordEdt.getText().toString().trim().isEmpty()) {
                            newPasswordEdt.requestFocus();
                            newPasswordEdt.setError("Veuillez saisir votre nouveau mot de passe !");
                            return;
                        }
                        try {
                            String newPasswordHashed = SHA256(newPasswordEdt.getText().toString());
                            if (newPasswordHashed.equals(user.getPassword())) {
                                newPasswordEdt.requestFocus();
                                newPasswordEdt.setError("Le nouveau mot de passe doit etre différent de l'initial !");
                                return;
                            }
                        } catch (NoSuchAlgorithmException e) {
                            Toast.makeText(loginActivity, "Impossible de hasher le nouveau mot de passe !", Toast.LENGTH_LONG).show();
                        }
                        if (newPasswordConfirmationEdt.getText().toString().trim().isEmpty()) {
                            newPasswordConfirmationEdt.requestFocus();
                            newPasswordConfirmationEdt.setError("Veuillez confirmer votre nouveau mot de passe !");
                            return;
                        }
                        if (!newPasswordEdt.getText().toString().equals(newPasswordConfirmationEdt.getText().toString())) {
                            newPasswordConfirmationEdt.requestFocus();
                            newPasswordConfirmationEdt.setError("Le mot de passe et sa confirmation ne sont pas identiques!");
                            return;
                        }

                        int x = 1;
                        if (x == 1) {
                            Toast.makeText(loginActivity, "C'est bon !", Toast.LENGTH_SHORT).show();
                            cutomAlertDialog.dismiss();
                        } else {
                            Toast.makeText(loginActivity, "Ce n'est bon ..", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }));
        // mettre le focus sur le champs de saisie
        newPasswordEdt.requestFocus();

        return cutomAlertDialog;
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
