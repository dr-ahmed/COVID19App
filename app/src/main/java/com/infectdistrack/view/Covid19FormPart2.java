package com.infectdistrack.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.infectdistrack.R;
import com.infectdistrack.model.Covid19Form;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.infectdistrack.model.Constants.BAS;
import static com.infectdistrack.model.Constants.ELEVE;
import static com.infectdistrack.model.Constants.MOYEN;
import static com.infectdistrack.model.Constants.NON;
import static com.infectdistrack.model.Constants.NOT_KNOW;
import static com.infectdistrack.model.Constants.OUI;
import static com.infectdistrack.model.Constants.YES;
import static com.infectdistrack.model.Utilities.isPhoneNumberValid;

public class Covid19FormPart2 extends Fragment implements DatePicker.OnDateChangedListener, RadioGroup.OnCheckedChangeListener {

    private static final String TAG = "COVID19FormPart2";

    private Covid19FormActivity covid19FormActivity;

    private View rootView;
    private boolean isDateChanged = false;
    private LinearLayout symptomsLayout, structureMedecinLayout, raisonDePourquoiDuPatientLayout, detailsSabsenterDuTravailLayout,
            detailsDuDernierContactLayout, conditionPreDisposanteLayout;
    private RadioGroup consulterMedecinRadioGroup, sabsenterDuTravailRadioGroup, dernierContactRadioGroup, niveauSocioEconomiqueRadioGroup, conditionPreDisposanteRadioGroup;
    private String reponseFromyesConsulter_medecin = "", reponseFromsabsenterDuTravail = "",
            reposerFromDernierContact = "", reponseFromNiveauSocioEconomique = "", reposerFromConditionPreDisposante = "";
    private EditText phoneNumberDernierContact, autreConditionPreDisposanteEdt;
    private DatePicker dernierContactDatePicker;
    CheckBox asthmeChechbox, autreQuAsthmeChechbox, hypertensionChechbox, nephropathiesChechbox, hepatiqueChechbox, neuromusculaireChechbox, diabeteChechbox,
            cancerChechbox, grossesseChechbox, obesiteChechbox, tabacChechbox, immunosuppressionChechbox, immunosuppressionTraitementChechbox, autreConditionChechbox;

    private Covid19Form covid19FormObject;

    public Covid19FormPart2(Covid19Form covid19FormObject) {
        this.covid19FormObject = covid19FormObject;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_covid19_form_part2, container, false);
        covid19FormActivity = (Covid19FormActivity) getActivity();

        initViews();
        initChechboxs();

        return rootView;
    }

    public void setSymptomsLayoutVisibility() {
        symptomsLayout.setVisibility(covid19FormActivity.getSymptomsLayoutVisibility() ? VISIBLE : GONE);
    }

    private void initViews() {
        symptomsLayout = rootView.findViewById(R.id.layout_for_symptoms_details);
        structureMedecinLayout = rootView.findViewById(R.id.layout_for_structure_medecin);
        raisonDePourquoiDuPatientLayout = rootView.findViewById(R.id.layout_for_raison_de_pourquoi_du_patient);
        detailsSabsenterDuTravailLayout = rootView.findViewById(R.id.layout_for_les_details_sabsenter_du_travail);
        detailsDuDernierContactLayout = rootView.findViewById(R.id.layout_for_details_du_dernier_contact);
        conditionPreDisposanteLayout = rootView.findViewById(R.id.layout_for_quelle_condition_pre_disposante);
        consulterMedecinRadioGroup = rootView.findViewById(R.id.radio_group_for_consulter_medecin);
        consulterMedecinRadioGroup.setOnCheckedChangeListener(this);
        sabsenterDuTravailRadioGroup = rootView.findViewById(R.id.radio_group_for_sabsenter_du_travail);
        sabsenterDuTravailRadioGroup.setOnCheckedChangeListener(this);
        dernierContactRadioGroup = rootView.findViewById(R.id.radio_group_for_dernier_contact);
        dernierContactRadioGroup.setOnCheckedChangeListener(this);
        phoneNumberDernierContact = rootView.findViewById(R.id.phone_number_for_personne_suspecte);
        dernierContactDatePicker = rootView.findViewById(R.id.dernier_contact_personne_suspecte_datepicker);
        niveauSocioEconomiqueRadioGroup = rootView.findViewById(R.id.radio_group_for_niveau_socio_economique);
        niveauSocioEconomiqueRadioGroup.setOnCheckedChangeListener(this);
        conditionPreDisposanteRadioGroup = rootView.findViewById(R.id.radio_group_for_condition_pre_disposante);
        conditionPreDisposanteRadioGroup.setOnCheckedChangeListener(this);
    }

    private void initChechboxs() {
        asthmeChechbox = rootView.findViewById(R.id.asthme_chechbox_from_quelle_condition_pre_disposante);
        autreQuAsthmeChechbox = rootView.findViewById(R.id.autre_que_asthme_chechbox_from_quelle_condition_pre_disposante);
        hypertensionChechbox = rootView.findViewById(R.id.hypertension_chechbox_from_quelle_condition_pre_disposante);
        nephropathiesChechbox = rootView.findViewById(R.id.nephropathies_chechbox_from_quelle_condition_pre_disposante);
        hepatiqueChechbox = rootView.findViewById(R.id.hepatique_chronique_chechbox_from_quelle_condition_pre_disposante);
        neuromusculaireChechbox = rootView.findViewById(R.id.neuromusculaire_chechbox_from_quelle_condition_pre_disposante);
        diabeteChechbox = rootView.findViewById(R.id.diabete_chechbox_from_quelle_condition_pre_disposante);
        cancerChechbox = rootView.findViewById(R.id.cancer_chechbox_from_quelle_condition_pre_disposante);
        grossesseChechbox = rootView.findViewById(R.id.grossesse_chechbox_from_quelle_condition_pre_disposante);
        obesiteChechbox = rootView.findViewById(R.id.obesite_chechbox_from_quelle_condition_pre_disposante);
        tabacChechbox = rootView.findViewById(R.id.tabac_chechbox_from_quelle_condition_pre_disposante);
        immunosuppressionChechbox = rootView.findViewById(R.id.immunosuppression_hiv_chechbox_from_quelle_condition_pre_disposante);
        immunosuppressionTraitementChechbox = rootView.findViewById(R.id.immunosuppression_traitement_chechbox_from_quelle_condition_pre_disposante);
        autreConditionChechbox = rootView.findViewById(R.id.autre_condition_chechbox_from_quelle_condition_pre_disposante);
        autreConditionPreDisposanteEdt = rootView.findViewById(R.id.autre_condition_from_condition_pre_disposante_edt);
    }

    private boolean isAtLeastCheckboxChecked() {
        return asthmeChechbox.isChecked() || autreQuAsthmeChechbox.isChecked() || hypertensionChechbox.isChecked() || nephropathiesChechbox.isChecked()
                || hepatiqueChechbox.isChecked() || neuromusculaireChechbox.isChecked() || diabeteChechbox.isChecked() || cancerChechbox.isChecked()
                || grossesseChechbox.isChecked() || obesiteChechbox.isChecked() || tabacChechbox.isChecked() || immunosuppressionChechbox.isChecked()
                || immunosuppressionTraitementChechbox.isChecked() || autreConditionChechbox.isChecked();
    }

    private boolean autreConditionPreDisposanteEdittextIsEmpty() {
        if (autreConditionChechbox.isChecked()) {
            if (autreConditionPreDisposanteEdt.getText().toString().trim().isEmpty()) {
                autreConditionPreDisposanteEdt.requestFocus();
                autreConditionPreDisposanteEdt.setText("Veuillez préciser les autres conditions médicales !");
                return true;
            } else
                return false;
        }
        return false;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (group.getId()) {
            case R.id.radio_group_for_consulter_medecin: {
                if (checkedId == R.id.yes_item_from_consulter_medecin) {
                    structureMedecinLayout.setVisibility(VISIBLE);
                    raisonDePourquoiDuPatientLayout.setVisibility(GONE);
                    reponseFromyesConsulter_medecin = OUI;
                } else if (checkedId == R.id.no_item_from_consulter_medecin) {
                    reponseFromyesConsulter_medecin = NON;
                    raisonDePourquoiDuPatientLayout.setVisibility(VISIBLE);
                    structureMedecinLayout.setVisibility(GONE);
                }
            }
            break;
            case R.id.radio_group_for_sabsenter_du_travail: {
                if (checkedId == R.id.yes_item_from_sabsenter_du_travail) {
                    reponseFromsabsenterDuTravail = OUI;
                    detailsSabsenterDuTravailLayout.setVisibility(VISIBLE);
                } else if (checkedId == R.id.no_item_from_sabsenter_du_travail) {
                    reponseFromsabsenterDuTravail = NON;
                    detailsSabsenterDuTravailLayout.setVisibility(GONE);
                }
            }
            break;
            case R.id.radio_group_for_dernier_contact: {
                if (checkedId == R.id.yes_item_from_dernier_contact) {
                    reposerFromDernierContact = OUI;
                    detailsDuDernierContactLayout.setVisibility(VISIBLE);
                } else if (checkedId == R.id.no_item_from_dernier_contact) {
                    reposerFromDernierContact = NON;
                    detailsDuDernierContactLayout.setVisibility(GONE);
                } else if (checkedId == R.id.not_know_item_from_dernier_contact) {
                    reposerFromDernierContact = NOT_KNOW;
                    detailsDuDernierContactLayout.setVisibility(GONE);
                }
            }
            break;
            case R.id.radio_group_for_niveau_socio_economique: {
                if (checkedId == R.id.bas_item_from_niveau_socio_economique)
                    reponseFromNiveauSocioEconomique = BAS;
                else if (checkedId == R.id.moyen_item_from_niveau_socio_economique)
                    reponseFromNiveauSocioEconomique = MOYEN;
                else if (checkedId == R.id.eleve_item_from_niveau_socio_economique)
                    reponseFromNiveauSocioEconomique = ELEVE;
            }
            break;
            case R.id.radio_group_for_condition_pre_disposante: {
                if (checkedId == R.id.yes_item_from_condition_pre_disposante) {
                    reposerFromConditionPreDisposante = OUI;
                    conditionPreDisposanteLayout.setVisibility(VISIBLE);
                } else if (checkedId == R.id.no_item_from_condition_pre_disposante) {
                    reposerFromConditionPreDisposante = NON;
                    conditionPreDisposanteLayout.setVisibility(GONE);
                }
            }
            break;
            default: {
            }
        }
    }

    public boolean isFieldEmpty() {
        if (detailsDuDernierContactLayout.getVisibility() == VISIBLE) {
            String phoneNumberFeedback = isPhoneNumberValid(phoneNumberDernierContact.getText().toString());
            if (!phoneNumberFeedback.equals(YES)) {
                phoneNumberDernierContact.requestFocus();
                phoneNumberDernierContact.setError(phoneNumberFeedback);
                return true;
            }

            if (!isDateChanged)
                return true;

            if (conditionPreDisposanteLayout.getVisibility() == VISIBLE) {
                if (!isAtLeastCheckboxChecked())
                    return true;
                else if (autreConditionPreDisposanteEdittextIsEmpty())
                    return true;
            }

        }

        return false;
    }

    public void setValues() {
        /*
        covid19FormObject.setName(patientNameEdt.getText().toString());
        covid19FormObject.setPhoneNumber(patientPhoneNumberEdt.getText().toString());
        covid19FormObject.setGendre(patientGender);

        String dateOfBirth = patientAgeDatePicker.getDayOfMonth() + "/" + (patientAgeDatePicker.getMonth() + 1) + "/" + patientAgeDatePicker.getYear();
        covid19FormObject.setAge(dateOfBirth);
        covid19FormObject.setWilaya(patientWilayaSpinner.getSelectedItem().toString());
        covid19FormObject.setSuspectedCases(patientSuspectedCasesDescription);
         */

        //Log.e(TAG, covid19FormObject.toString());
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        if (view.getId() == R.id.dernier_contact_personne_suspecte_datepicker)
            isDateChanged = true;
    }
}
