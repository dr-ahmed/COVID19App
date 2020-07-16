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
import com.infectdistrack.model.ResetPasswordDuringFirstLoginAsyncTask;
import com.infectdistrack.model.User;
import com.infectdistrack.model.Utilities;
import com.infectdistrack.presenter.LoginController;

import java.security.NoSuchAlgorithmException;

import static com.infectdistrack.model.Constants.NO_CONNECTION_OR_TIMEOUT_EXCEPTION_TAG;
import static com.infectdistrack.model.Constants.SOCKET_TIMEOUT_EXCEPTION;
import static com.infectdistrack.model.Utilities.SHA256;
import static com.infectdistrack.presenter.UIBasicController.hideProgressDialog;
import static com.infectdistrack.presenter.UIBasicController.showProgressDialog;

public class DialogFragmentToResetPasswordDuringFirstLogin extends DialogFragment {


    private static final String TAG = "DialogFragmentToResetPasswordDuringFirstLogin";

    private LoginController loginController;
    private User user;
    private AlertDialog cutomAlertDialog = null;

    public DialogFragmentToResetPasswordDuringFirstLogin(LoginController loginController, User user) {
        this.loginController = loginController;
        this.user = user;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialogBuiler = new AlertDialog.Builder(getActivity());
        LinearLayout rootView = loginController.getLoginActivity().findViewById(R.id.root_view);

        View viewInflated = LayoutInflater.from(loginController.getLoginActivity()).inflate(R.layout.reset_password_view, rootView, false);
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
                        Toast.makeText(loginController.getLoginActivity(), "Opération annulée !", Toast.LENGTH_SHORT).show();
                    }
                });

        cutomAlertDialog = dialogBuiler.create();
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
                        String newPasswordHashed;
                        try {
                            newPasswordHashed = SHA256(newPasswordEdt.getText().toString());
                            if (newPasswordHashed.equals(user.getPassword())) {
                                newPasswordEdt.requestFocus();
                                newPasswordEdt.setError("Le nouveau mot de passe doit etre différent de l'initial !");
                                return;
                            }
                        } catch (NoSuchAlgorithmException e) {
                            Toast.makeText(loginController.getLoginActivity(), "Impossible de hasher le nouveau mot de passe !", Toast.LENGTH_LONG).show();
                            return;
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

                        ResetPasswordDuringFirstLoginAsyncTask firstLoginAsyncTask = new ResetPasswordDuringFirstLoginAsyncTask(DialogFragmentToResetPasswordDuringFirstLogin.this);
                        showProgressDialog(loginController.getLoginActivity(), "Nous mettons à jour votre mot de passe ...");
                        firstLoginAsyncTask.execute(String.valueOf(user.getId()), newPasswordHashed);
                    }
                });
            }
        }));
        // mettre le focus sur le champs de saisie
        newPasswordEdt.requestFocus();

        return cutomAlertDialog;
    }

    public void onResetPassword(String exceptionInfo, boolean isCommited) {
        hideProgressDialog();

        if (exceptionInfo.isEmpty()) {
            if (isCommited) {
                cutomAlertDialog.dismiss();
                Toast.makeText(loginController.getLoginActivity(), "Mot de passe réinitialisé avec succès", Toast.LENGTH_SHORT).show();
                loginController.saveUserDataInSharedPrefsAndGoToHomeActivity(loginController.getLoginActivity(), user);
            } else
                Toast.makeText(loginController.getLoginActivity(), "Désolé, une erreur s'est produite (Code d'erreur RP01)", Toast.LENGTH_LONG).show();
        } else {
            if (exceptionInfo.equals(SOCKET_TIMEOUT_EXCEPTION))
                Toast.makeText(loginController.getLoginActivity(), "Merci de vérifier votre connexion internet!", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(loginController.getLoginActivity(), "Désolé, une erreur s'est produite (Code d'erreur RP02)", Toast.LENGTH_SHORT).show();
            //showMessageUsingDialogFragment(loginController.getLoginActivity(), "Exception", "Une erreur s'est produite !\n" + "DETAILS :\n" + exceptionInfo);
        }
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
