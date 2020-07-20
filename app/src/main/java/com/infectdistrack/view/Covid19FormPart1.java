package com.infectdistrack.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.infectdistrack.R;
import com.infectdistrack.model.Covid19Form;

public class Covid19FormPart1 extends Fragment implements CompoundButton.OnCheckedChangeListener {

    private static final String TAG = "COVID19FormPart1";

    private Covid19FormActivity covid19FormActivity;
    private View rootView;
    private CheckBox noSympBox, feverBox, frissonsBox, touxBox, malDeGorgeBox, rhumeBox, fatigueBox,
            douleursMusculairesBox, douleursArticulaireBox, mauxDeTeteBox, diarrheeBox, nauseesBox, vomissementsBox,
            perteDodoratBox, perteDeGoutBox, perteDappetiBox, saignementsDeNezBox, hemoptysieBox, conjonctiviteBox,
            eruptionCutaneeBox, essoufflementBox, convulsionsBox, alterationCeConscienceBox, autresSignesDeGraviteBox, otherSympBox;
    private EditText autresSignesDeGraviteEdt, otherSympEdt;
    private final String PLEASE_SPECIFY_SYMPTOMS = "Veuillez préciser les autres symptômes !";
    private Covid19Form covid19FormObject;

    public Covid19FormPart1(Covid19Form covid19FormObject) {
        this.covid19FormObject = covid19FormObject;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_covid19_form_part1, container, false);
        covid19FormActivity = (Covid19FormActivity) getActivity();

        initViews(rootView);

        return rootView;
    }

    private void initViews(View rootView) {
        noSympBox = rootView.findViewById(R.id.no_symp_chechbox);
        noSympBox.setOnCheckedChangeListener(this);
        feverBox = rootView.findViewById(R.id.fever_chechbox);
        frissonsBox = rootView.findViewById(R.id.frisson_chechbox);
        touxBox = rootView.findViewById(R.id.toux_chechbox);
        touxBox = rootView.findViewById(R.id.toux_chechbox);
        malDeGorgeBox = rootView.findViewById(R.id.mal_de_gorge_chechbox);
        rhumeBox = rootView.findViewById(R.id.rhume_chechbox);
        fatigueBox = rootView.findViewById(R.id.fatigue_chechbox);
        douleursMusculairesBox = rootView.findViewById(R.id.douleurs_musculaires_chechbox);
        douleursArticulaireBox = rootView.findViewById(R.id.douleurs_articulaires_chechbox);
        mauxDeTeteBox = rootView.findViewById(R.id.maux_de_tete_chechbox);
        diarrheeBox = rootView.findViewById(R.id.diarrhee_chechbox);
        nauseesBox = rootView.findViewById(R.id.nausees_chechbox);
        vomissementsBox = rootView.findViewById(R.id.vomissements_chechbox);
        perteDodoratBox = rootView.findViewById(R.id.perte_odorat_chechbox);
        perteDeGoutBox = rootView.findViewById(R.id.perte_gout_chechbox);
        perteDappetiBox = rootView.findViewById(R.id.perte_appetit_chechbox);
        saignementsDeNezBox = rootView.findViewById(R.id.saignements_nez_chechbox);
        hemoptysieBox = rootView.findViewById(R.id.hemoptysie_chechbox);
        conjonctiviteBox = rootView.findViewById(R.id.conjonctivite_chechbox);
        eruptionCutaneeBox = rootView.findViewById(R.id.eruption_cutanee_chechbox);
        essoufflementBox = rootView.findViewById(R.id.essoufflement_chechbox);
        convulsionsBox = rootView.findViewById(R.id.convulsions_chechbox);
        alterationCeConscienceBox = rootView.findViewById(R.id.alteration_de_conscience_chechbox);
        autresSignesDeGraviteBox = rootView.findViewById(R.id.autres_signes_de_gravite_chechbox);
        autresSignesDeGraviteBox.setOnCheckedChangeListener(this);
        autresSignesDeGraviteEdt = rootView.findViewById(R.id.autres_signes_de_gravite_edittext);
        otherSympBox = rootView.findViewById(R.id.other_symp_chechbox);
        otherSympBox.setOnCheckedChangeListener(this);
        otherSympEdt = rootView.findViewById(R.id.other_symp_edittext);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.no_symp_chechbox:
                setEnabledAllOtherCheckBox(!isChecked);
                covid19FormActivity.setSymptomsLayoutVisibility(!isChecked);
                break;
            case R.id.autres_signes_de_gravite_chechbox:
                autresSignesDeGraviteEdt.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                break;
            case R.id.other_symp_chechbox:
                otherSympEdt.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                break;
            default: {

            }
        }
    }

    private void setEnabledAllOtherCheckBox(boolean enabledFlag) {
        feverBox.setEnabled(enabledFlag);
        frissonsBox.setEnabled(enabledFlag);
        touxBox.setEnabled(enabledFlag);
        malDeGorgeBox.setEnabled(enabledFlag);
        rhumeBox.setEnabled(enabledFlag);
        fatigueBox.setEnabled(enabledFlag);
        douleursMusculairesBox.setEnabled(enabledFlag);
        douleursArticulaireBox.setEnabled(enabledFlag);
        mauxDeTeteBox.setEnabled(enabledFlag);
        diarrheeBox.setEnabled(enabledFlag);
        nauseesBox.setEnabled(enabledFlag);
        vomissementsBox.setEnabled(enabledFlag);
        perteDodoratBox.setEnabled(enabledFlag);
        perteDeGoutBox.setEnabled(enabledFlag);
        perteDappetiBox.setEnabled(enabledFlag);
        saignementsDeNezBox.setEnabled(enabledFlag);
        hemoptysieBox.setEnabled(enabledFlag);
        conjonctiviteBox.setEnabled(enabledFlag);
        eruptionCutaneeBox.setEnabled(enabledFlag);
        essoufflementBox.setEnabled(enabledFlag);
        convulsionsBox.setEnabled(enabledFlag);
        alterationCeConscienceBox.setEnabled(enabledFlag);
        autresSignesDeGraviteBox.setEnabled(enabledFlag);
        otherSympBox.setEnabled(enabledFlag);
    }

    private boolean isAtLeastCheckboxChecked() {
        return noSympBox.isChecked() || feverBox.isChecked() || frissonsBox.isChecked() || touxBox.isChecked() || malDeGorgeBox.isChecked() || rhumeBox.isChecked()
                || fatigueBox.isChecked() || douleursMusculairesBox.isChecked() || douleursArticulaireBox.isChecked() || mauxDeTeteBox.isChecked() || diarrheeBox.isChecked()
                || nauseesBox.isChecked() || vomissementsBox.isChecked() || perteDodoratBox.isChecked() || perteDeGoutBox.isChecked() || perteDappetiBox.isChecked()
                || saignementsDeNezBox.isChecked() || hemoptysieBox.isChecked() || conjonctiviteBox.isChecked() || eruptionCutaneeBox.isChecked() || essoufflementBox.isChecked()
                || convulsionsBox.isChecked() || alterationCeConscienceBox.isChecked() || autresSignesDeGraviteBox.isChecked() || otherSympBox.isChecked();
    }

    public boolean areAllRequiredFieldsCompleted() {
        if (isAtLeastCheckboxChecked()) {
            return noEdittextIsEmpty();
        } else
            return false;
    }

    private boolean noEdittextIsEmpty() {
        if (autresSignesDeGraviteBox.isChecked() && autresSignesDeGraviteEdt.getText().toString().trim().isEmpty()) {
            autresSignesDeGraviteEdt.requestFocus();
            autresSignesDeGraviteEdt.setError(PLEASE_SPECIFY_SYMPTOMS);
            return false;
        }

        if (otherSympBox.isChecked() && otherSympEdt.getText().toString().trim().isEmpty()) {
            otherSympEdt.requestFocus();
            otherSympEdt.setError(PLEASE_SPECIFY_SYMPTOMS);
            return false;
        }

        return true;
    }

    private String getPatientSymptoms() {
        StringBuilder symptoms = new StringBuilder();

        if (noSympBox.isChecked()) {
            symptoms.append("Aucun symptome");
        } else {
            if (feverBox.isChecked())
                symptoms.append("Fièvre").append(";");
            if (frissonsBox.isChecked())
                symptoms.append(frissonsBox.getText()).append(";");
            if (touxBox.isChecked())
                symptoms.append(touxBox.getText()).append(";");
            if (malDeGorgeBox.isChecked())
                symptoms.append(malDeGorgeBox.getText()).append(";");
            if (rhumeBox.isChecked())
                symptoms.append(rhumeBox.getText()).append(";");
            if (fatigueBox.isChecked())
                symptoms.append("Fatigue").append(";");
            if (douleursMusculairesBox.isChecked())
                symptoms.append("Myalgie").append(";");
            if (douleursArticulaireBox.isChecked())
                symptoms.append("Arthralgies").append(";");
            if (mauxDeTeteBox.isChecked())
                symptoms.append("Céphalées").append(";");
            if (diarrheeBox.isChecked())
                symptoms.append(diarrheeBox.getText()).append(";");
            if (nauseesBox.isChecked())
                symptoms.append(nauseesBox.getText()).append(";");
            if (vomissementsBox.isChecked())
                symptoms.append(vomissementsBox.getText()).append(";");
            if (perteDodoratBox.isChecked())
                symptoms.append("Anosmie").append(";");
            if (perteDeGoutBox.isChecked())
                symptoms.append("Agueusie").append(";");
            if (perteDappetiBox.isChecked())
                symptoms.append("Anorexie").append(";");
            if (saignementsDeNezBox.isChecked())
                symptoms.append("Epistaxis").append(";");
            if (hemoptysieBox.isChecked())
                symptoms.append(hemoptysieBox.getText()).append(";");
            if (conjonctiviteBox.isChecked())
                symptoms.append(conjonctiviteBox.getText()).append(";");
            if (eruptionCutaneeBox.isChecked())
                symptoms.append(eruptionCutaneeBox.getText()).append(";");
            if (essoufflementBox.isChecked())
                symptoms.append("Dyspnée").append(";");
            if (convulsionsBox.isChecked())
                symptoms.append(convulsionsBox.getText()).append(";");
            if (alterationCeConscienceBox.isChecked())
                symptoms.append(alterationCeConscienceBox.getText()).append(";");
            if (autresSignesDeGraviteBox.isChecked())
                symptoms.append(autresSignesDeGraviteEdt.getText()).append(";");
            if (otherSympBox.isChecked())
                symptoms.append(otherSympEdt.getText());
        }

        return symptoms.toString();
    }

    public void setValues() {
        covid19FormObject.setSymptoms(getPatientSymptoms());
        //Log.e(TAG, covid19FormObject.toString());
    }
}
