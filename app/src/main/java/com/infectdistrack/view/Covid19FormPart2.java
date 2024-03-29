package com.infectdistrack.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.infectdistrack.R;
import com.infectdistrack.model.Covid19Form;
import com.infectdistrack.model.Utilities;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.infectdistrack.model.Constants.ACCESSIBILITE;
import static com.infectdistrack.model.Constants.BAS;
import static com.infectdistrack.model.Constants.CENTRE_SANTE;
import static com.infectdistrack.model.Constants.CLINIQUE_PRIVEE;
import static com.infectdistrack.model.Constants.CONFIANCE;
import static com.infectdistrack.model.Constants.ELEVE;
import static com.infectdistrack.model.Constants.HOPITAL;
import static com.infectdistrack.model.Constants.MOYEN;
import static com.infectdistrack.model.Constants.NON;
import static com.infectdistrack.model.Constants.NOT_KNOW;
import static com.infectdistrack.model.Constants.NUMERO_VERT;
import static com.infectdistrack.model.Constants.OUI;
import static com.infectdistrack.model.Constants.PHARMACIE;
import static com.infectdistrack.model.Constants.PHOBIE;
import static com.infectdistrack.model.Constants.SOCIAL;
import static com.infectdistrack.model.Constants.TRANSPORT;
import static com.infectdistrack.model.Constants.YES;
import static com.infectdistrack.model.Utilities.isPhoneNumberValid;

public class Covid19FormPart2 extends Fragment implements DatePicker.OnDateChangedListener, RadioGroup.OnCheckedChangeListener, CompoundButton.OnCheckedChangeListener {

    private static final String TAG = "COVID19FormPart2";

    private Covid19FormActivity covid19FormActivity;

    private View rootView;
    private boolean isDateUnchanged = true;
    private LinearLayout symptomsLayout, structureMedecinLayout, raisonDePourquoiDuPatientLayout, detailsSabsenterDuTravailLayout,
            detailsDuDernierContactLayout, conditionPreDisposanteLayout;
    private RadioGroup consulterMedecinRadioGroup, structureMedecinRadioGroup, raisonDePourquoiDuPatientRadioGroup, sabsenterDuTravailRadioGroup, dernierContactRadioGroup,
            niveauSocioEconomiqueRadioGroup, conditionPreDisposanteRadioGroup;
    private String responseFromConsulterMedecin = "", responseFromStructureMededin = "", responseFromRaisonAbsence = "", responseFromSabsenterDuTravail = "",
            resposerFromContactSuspect = "", responseFromNiveauSocioEconomique = "", resposerFromConditionPreDisposante = "";
    private NumberPicker nombreDeJoursPicker;
    private EditText phoneNumberDernierContact, autreConditionPreDisposanteEdt;
    private DatePicker dernierContactDatePicker;
    private String dateDuDernierContact = "";
    private CheckBox asthmeChechbox, autreQuAsthmeChechbox, hypertensionChechbox, nephropathiesChechbox, hepatiqueChechbox, neuromusculaireChechbox, diabeteChechbox,
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
        structureMedecinRadioGroup = rootView.findViewById(R.id.radio_group_for_structure_medecin);
        structureMedecinRadioGroup.setOnCheckedChangeListener(this);
        raisonDePourquoiDuPatientRadioGroup = rootView.findViewById(R.id.radio_group_for_raison_de_pourquoi_du_patient);
        raisonDePourquoiDuPatientRadioGroup.setOnCheckedChangeListener(this);
        sabsenterDuTravailRadioGroup = rootView.findViewById(R.id.radio_group_for_sabsenter_du_travail);
        sabsenterDuTravailRadioGroup.setOnCheckedChangeListener(this);
        dernierContactRadioGroup = rootView.findViewById(R.id.radio_group_for_contact_suspect);
        dernierContactRadioGroup.setOnCheckedChangeListener(this);
        phoneNumberDernierContact = rootView.findViewById(R.id.phone_number_for_personne_suspecte);

        nombreDeJoursPicker = rootView.findViewById(R.id.nombre_de_jours_picker);
        nombreDeJoursPicker.setMinValue(0);
        nombreDeJoursPicker.setMaxValue(100);

        dernierContactDatePicker = rootView.findViewById(R.id.dernier_contact_personne_suspecte_datepicker);
        int[] calendar = Utilities.getCurrentDayMonthAndYear();
        dernierContactDatePicker.init(calendar[0], calendar[1], calendar[2], this);
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
        autreConditionChechbox.setOnCheckedChangeListener(this);
        autreConditionPreDisposanteEdt = rootView.findViewById(R.id.autre_condition_from_condition_pre_disposante_edt);
    }

    private boolean isAtLeastCheckboxChecked() {
        return asthmeChechbox.isChecked() || autreQuAsthmeChechbox.isChecked() || hypertensionChechbox.isChecked() || nephropathiesChechbox.isChecked()
                || hepatiqueChechbox.isChecked() || neuromusculaireChechbox.isChecked() || diabeteChechbox.isChecked() || cancerChechbox.isChecked()
                || grossesseChechbox.isChecked() || obesiteChechbox.isChecked() || tabacChechbox.isChecked() || immunosuppressionChechbox.isChecked()
                || immunosuppressionTraitementChechbox.isChecked() || autreConditionChechbox.isChecked();
    }

    private String getListeDesConditionsPreDisposantes() {
        StringBuilder listeDesConditionsPreDisposantes = new StringBuilder();

        if (asthmeChechbox.isChecked())
            listeDesConditionsPreDisposantes.append(asthmeChechbox.getText()).append(";");
        if (autreQuAsthmeChechbox.isChecked())
            listeDesConditionsPreDisposantes.append("Autre qu'asthme").append(";");
        if (hypertensionChechbox.isChecked())
            listeDesConditionsPreDisposantes.append("Cardiorespiratoire").append(";");
        if (nephropathiesChechbox.isChecked())
            listeDesConditionsPreDisposantes.append(nephropathiesChechbox.getText()).append(";");
        if (hepatiqueChechbox.isChecked())
            listeDesConditionsPreDisposantes.append("Hépatique").append(";");
        if (neuromusculaireChechbox.isChecked())
            listeDesConditionsPreDisposantes.append("Neurologique").append(";");
        if (diabeteChechbox.isChecked())
            listeDesConditionsPreDisposantes.append("Diabète").append(";");
        if (cancerChechbox.isChecked())
            listeDesConditionsPreDisposantes.append(cancerChechbox.getText()).append(";");
        if (grossesseChechbox.isChecked())
            listeDesConditionsPreDisposantes.append("Grossesse").append(";");
        if (obesiteChechbox.isChecked())
            listeDesConditionsPreDisposantes.append(obesiteChechbox.getText()).append(";");
        if (tabacChechbox.isChecked())
            listeDesConditionsPreDisposantes.append(tabacChechbox.getText()).append(";");
        if (immunosuppressionChechbox.isChecked())
            listeDesConditionsPreDisposantes.append("Immuno1").append(";");
        if (immunosuppressionTraitementChechbox.isChecked())
            listeDesConditionsPreDisposantes.append("Immuno2").append(";");
        if (autreConditionChechbox.isChecked())
            listeDesConditionsPreDisposantes.append(autreConditionPreDisposanteEdt.getText().toString()).append(";");

        return listeDesConditionsPreDisposantes.toString();
    }


    private boolean autreConditionPreDisposanteEdittextIsEmpty() {
        if (autreConditionChechbox.isChecked()) {
            if (autreConditionPreDisposanteEdt.getText().toString().trim().isEmpty()) {
                autreConditionPreDisposanteEdt.requestFocus();
                autreConditionPreDisposanteEdt.setError("Veuillez préciser les autres conditions médicales !");
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
                    responseFromConsulterMedecin = OUI;
                } else if (checkedId == R.id.no_item_from_consulter_medecin) {
                    responseFromConsulterMedecin = NON;
                    raisonDePourquoiDuPatientLayout.setVisibility(VISIBLE);
                    structureMedecinLayout.setVisibility(GONE);
                }
            }
            break;
            case R.id.radio_group_for_structure_medecin: {
                switch (checkedId) {
                    case R.id.hopital_item_from_structure_medecin:
                        responseFromStructureMededin = HOPITAL;
                        break;
                    case R.id.pharmacie_item_from_structure_medecin:
                        responseFromStructureMededin = PHARMACIE;
                        break;
                    case R.id.centre_de_sante_item_from_structure_medecin:
                        responseFromStructureMededin = CENTRE_SANTE;
                        break;
                    case R.id.numero_vert_item_from_structure_medecin:
                        responseFromStructureMededin = NUMERO_VERT;
                        break;
                    case R.id.clinique_prive_item_from_structure_medecin:
                        responseFromStructureMededin = CLINIQUE_PRIVEE;
                        break;
                    default: {
                    }
                }
            }
            break;
            case R.id.radio_group_for_raison_de_pourquoi_du_patient: {
                switch (checkedId) {
                    case R.id.confiance_item_from_raison_de_pourquoi_du_patient:
                        responseFromRaisonAbsence = CONFIANCE;
                        break;
                    case R.id.transport_item_from_raison_de_pourquoi_du_patient:
                        responseFromRaisonAbsence = TRANSPORT;
                        break;
                    case R.id.accessibilite_item_from_raison_de_pourquoi_du_patient:
                        responseFromRaisonAbsence = ACCESSIBILITE;
                        break;
                    case R.id.social_item_from_raison_de_pourquoi_du_patient:
                        responseFromRaisonAbsence = SOCIAL;
                        break;
                    case R.id.phobie_item_from_raison_de_pourquoi_du_patient:
                        responseFromRaisonAbsence = PHOBIE;
                        break;
                    default: {
                    }
                }
            }
            break;
            case R.id.radio_group_for_sabsenter_du_travail: {
                if (checkedId == R.id.yes_item_from_sabsenter_du_travail) {
                    responseFromSabsenterDuTravail = OUI;
                    detailsSabsenterDuTravailLayout.setVisibility(VISIBLE);
                } else if (checkedId == R.id.no_item_from_sabsenter_du_travail) {
                    responseFromSabsenterDuTravail = NON;
                    detailsSabsenterDuTravailLayout.setVisibility(GONE);
                }
            }
            break;
            case R.id.radio_group_for_contact_suspect: {
                if (checkedId == R.id.yes_item_from_contact_suspect) {
                    resposerFromContactSuspect = OUI;
                    detailsDuDernierContactLayout.setVisibility(VISIBLE);
                } else if (checkedId == R.id.no_item_from_contact_suspect) {
                    resposerFromContactSuspect = NON;
                    detailsDuDernierContactLayout.setVisibility(GONE);
                } else if (checkedId == R.id.not_know_item_from_contact_suspect) {
                    resposerFromContactSuspect = NOT_KNOW;
                    detailsDuDernierContactLayout.setVisibility(GONE);
                }
            }
            break;
            case R.id.radio_group_for_niveau_socio_economique: {
                if (checkedId == R.id.bas_item_from_niveau_socio_economique)
                    responseFromNiveauSocioEconomique = BAS;
                else if (checkedId == R.id.moyen_item_from_niveau_socio_economique)
                    responseFromNiveauSocioEconomique = MOYEN;
                else if (checkedId == R.id.eleve_item_from_niveau_socio_economique)
                    responseFromNiveauSocioEconomique = ELEVE;
            }
            break;
            case R.id.radio_group_for_condition_pre_disposante: {
                if (checkedId == R.id.yes_item_from_condition_pre_disposante) {
                    resposerFromConditionPreDisposante = OUI;
                    conditionPreDisposanteLayout.setVisibility(VISIBLE);
                } else if (checkedId == R.id.no_item_from_condition_pre_disposante) {
                    resposerFromConditionPreDisposante = NON;
                    conditionPreDisposanteLayout.setVisibility(GONE);
                }
            }
            break;
            default: {
            }
        }
    }

    public boolean areAllRequiredFieldsCompleted() {
        // Si le patient a des symptomes
        if (symptomsLayout.getVisibility() == VISIBLE) {
            // Si le user ne coche pas les deux radiobuttons relatifs aux symptomes
            if (responseFromConsulterMedecin.isEmpty() || responseFromSabsenterDuTravail.isEmpty())
                return false;
            else { // Si le user coche les deux RadioGroups de symptomes

                // si le patient a visité un médecin
                if (responseFromConsulterMedecin.equals(OUI)) {
                    if (responseFromStructureMededin.isEmpty()) // si le patient ne précise pas la structure du médecin
                        return false;
                } else if (responseFromConsulterMedecin.equals(NON)) { // Si le user répond par Non
                    if (responseFromRaisonAbsence.isEmpty()) // Si le user ne préciser pas le pouqruoiq du patient
                        return false;
                } else { // Si la réponse du user n'est ni OUI, ni NON !
                    Toast.makeText(getActivity(), "Erreur P001", Toast.LENGTH_SHORT).show();
                    return false;
                }

                // si le patient s'est absenté du boulout
                if (responseFromSabsenterDuTravail.equals(OUI)) {
                    if (nombreDeJoursPicker.getValue() == 0) // Si le user ne précise pas le nombre de jours
                        return false;
                } else if (!responseFromSabsenterDuTravail.equals(NON)) { // Si la réponse du user n'est ni OUI, ni NON !
                    Toast.makeText(getActivity(), "Erreur P002", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        }

        // si la patient ne répond pas à la question de la personne suspecte
        if (resposerFromContactSuspect.isEmpty())
            return false;

        // si le patient avait contacté une personne suspecte
        if (detailsDuDernierContactLayout.getVisibility() == VISIBLE) {
            String phoneNumberFeedback = isPhoneNumberValid(phoneNumberDernierContact.getText().toString());
            if (!phoneNumberFeedback.equals(YES)) { // si le numéro de téléphone de la personne suspecte ne correspond pas au regex exigé
                phoneNumberDernierContact.requestFocus();
                phoneNumberDernierContact.setError(phoneNumberFeedback);
                return false;
            }

            // si le user ne précise pas une date
            if (isDateUnchanged)
                return false;
        }

        // si la user ne choisi pas un niveau socio-économique ou s'il ne répond pas à la question de condition pré-disposante
        if (responseFromNiveauSocioEconomique.isEmpty() || resposerFromConditionPreDisposante.isEmpty())
            return false;

        // si le patient a une condition pré-disposante
        if (conditionPreDisposanteLayout.getVisibility() == VISIBLE) {
            if (!isAtLeastCheckboxChecked())
                return false;
            else if (autreConditionPreDisposanteEdittextIsEmpty())
                return false;
        }

        return true;
    }

    private void resetValues() {
        covid19FormObject.setConsulterMedecin("");
        covid19FormObject.setStrucureMedecin("");
        covid19FormObject.setRaisonAbsence("");
        covid19FormObject.setSabsenterDuTravail("");
        covid19FormObject.setCombienDeJours("");
        covid19FormObject.setContactAvecPersonneSuspecte("");
        covid19FormObject.setTelPersonneSuspecte("");
        covid19FormObject.setDateDernierContactPersonneSuspecte("");
        covid19FormObject.setNiveauSocioEconomique("");
        covid19FormObject.setConditionPreDisposante("");
        covid19FormObject.setListeDesConditionsPreDisposantes("");
    }

    public void setValues() {

        resetValues();

        covid19FormObject.setConsulterMedecin(responseFromConsulterMedecin);

        if (responseFromConsulterMedecin.equals(OUI))
            covid19FormObject.setStrucureMedecin(responseFromStructureMededin);
        else
            covid19FormObject.setRaisonAbsence(responseFromRaisonAbsence);

        covid19FormObject.setSabsenterDuTravail(responseFromSabsenterDuTravail);

        if (covid19FormObject.getSabsenterDuTravail().equals(OUI))
            covid19FormObject.setCombienDeJours(String.valueOf(nombreDeJoursPicker.getValue()));

        covid19FormObject.setContactAvecPersonneSuspecte(resposerFromContactSuspect);

        if (covid19FormObject.getContactAvecPersonneSuspecte().equals(OUI)) {
            covid19FormObject.setTelPersonneSuspecte(phoneNumberDernierContact.getText().toString());
            covid19FormObject.setDateDernierContactPersonneSuspecte(dateDuDernierContact);
        }

        covid19FormObject.setNiveauSocioEconomique(responseFromNiveauSocioEconomique);

        covid19FormObject.setConditionPreDisposante(resposerFromConditionPreDisposante);
        if (covid19FormObject.getConditionPreDisposante().equals(OUI))
            covid19FormObject.setListeDesConditionsPreDisposantes(getListeDesConditionsPreDisposantes());

        //Log.e(TAG, covid19FormObject.toString());
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        if (view.getId() == R.id.dernier_contact_personne_suspecte_datepicker) {
            isDateUnchanged = false;
            dateDuDernierContact = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getId() == R.id.autre_condition_chechbox_from_quelle_condition_pre_disposante)
            autreConditionPreDisposanteEdt.setVisibility(isChecked ? VISIBLE : GONE);
    }
}
