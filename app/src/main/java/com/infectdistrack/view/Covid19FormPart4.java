package com.infectdistrack.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.infectdistrack.R;
import com.infectdistrack.model.Covid19Form;
import com.infectdistrack.model.User;
import com.infectdistrack.model.Utilities;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.infectdistrack.model.Constants.AUTRE;
import static com.infectdistrack.model.Constants.CONFINE_A_DOMICILE;
import static com.infectdistrack.model.Constants.DECEDE;
import static com.infectdistrack.model.Constants.DOMICILE;
import static com.infectdistrack.model.Constants.EMPTY_STRING;
import static com.infectdistrack.model.Constants.GUERI;
import static com.infectdistrack.model.Constants.HOSPITALISE;
import static com.infectdistrack.model.Constants.STRUCTURES_SANITAIRES;
import static com.infectdistrack.model.Constants.STRUCTURE_SANITAIRE;
import static com.infectdistrack.model.Constants.VIVANT;

public class Covid19FormPart4 extends Fragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener, DatePicker.OnDateChangedListener {

    private static final String TAG = "COVID19FormPart4";

    private View rootView;
    private LinearLayout decesLayout, vivantLayout, detailsDecesLayout, detailsHospitalisatinLayout;
    private RadioGroup statutActuelRadioGroup, lieuDuDecesRadioGroup, detailsVivantRadioGroup;
    private String statutActuelValue = "", responseFromLieuDuDeces = "", detailsVivant = "";
    private DatePicker dateDeces, dateAdmissionPourHospitalisation;
    private boolean isDateDeceUnchanged = true, isDateAdmissionUnchanged = true;
    private NumberPicker dureeDhospitalisationNumberPicker;
    private Spinner structureSanitairePourDeces, structureSanitairePourHospitalisation;
    private ArrayAdapter<String> structureSanitairePourDecesAdapter, structureSanitairePourHospitalisationAdapter;
    private EditText autreStatutPourVivantEdt;
    private Button submitBtn;
    private Covid19Form covid19FormObject;
    private User parentUser;

    public Covid19FormPart4(Covid19Form covid19FormObject) {
        this.covid19FormObject = covid19FormObject;
    }

    public Covid19Form getFinalCovid19FormResult() {
        return covid19FormObject;
    }

    public void setParentUser(User parentUser) {
        this.parentUser = parentUser;
    }

    public User getParentUser() {
        return parentUser;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_covid19_form_part4, container, false);

        initViews();

        return rootView;
    }

    private void initViews() {
        decesLayout = rootView.findViewById(R.id.deces_layout);
        decesLayout.setVisibility(GONE);
        vivantLayout = rootView.findViewById(R.id.vivant_layout);
        vivantLayout.setVisibility(GONE);

        statutActuelRadioGroup = rootView.findViewById(R.id.radio_group_statut_actuel);
        statutActuelRadioGroup.setOnCheckedChangeListener(this);

        lieuDuDecesRadioGroup = rootView.findViewById(R.id.radio_group_lieu_du_deces);
        lieuDuDecesRadioGroup.setOnCheckedChangeListener(this);

        dateDeces = rootView.findViewById(R.id.datepicker_pour_deces);
        int[] dateDeces = Utilities.getCurrentDayMonthAndYear();
        this.dateDeces.init(dateDeces[0], dateDeces[1], dateDeces[2], this);

        detailsDecesLayout = rootView.findViewById(R.id.details_deces_layout);
        dureeDhospitalisationNumberPicker = rootView.findViewById(R.id.duree_d_hospitalisation_picker);
        dureeDhospitalisationNumberPicker.setMinValue(0);
        dureeDhospitalisationNumberPicker.setMaxValue(100);

        structureSanitairePourDeces = rootView.findViewById(R.id.structure_sanitaire_pour_deces_spinner);
        structureSanitairePourDecesAdapter = new ArrayAdapter<>(getActivity(), R.layout.custom_spinner_item, STRUCTURES_SANITAIRES);
        structureSanitairePourDeces.setAdapter(structureSanitairePourDecesAdapter);

        detailsVivantRadioGroup = rootView.findViewById(R.id.radio_group_details_vivant);
        detailsVivantRadioGroup.setOnCheckedChangeListener(this);

        structureSanitairePourHospitalisation = rootView.findViewById(R.id.structure_sanitaire_pour_hospitalisation_spinner);
        structureSanitairePourHospitalisationAdapter = new ArrayAdapter<>(getActivity(), R.layout.custom_spinner_item, STRUCTURES_SANITAIRES);
        structureSanitairePourHospitalisation.setAdapter(structureSanitairePourHospitalisationAdapter);

        detailsHospitalisatinLayout = rootView.findViewById(R.id.details_hospitalisation_layout);

        dateAdmissionPourHospitalisation = rootView.findViewById(R.id.datepicker_pour_hospitalisation);
        int[] dateAdmission = Utilities.getCurrentDayMonthAndYear();
        dateAdmissionPourHospitalisation.init(dateAdmission[0], dateAdmission[1], dateAdmission[2], this);

        autreStatutPourVivantEdt = rootView.findViewById(R.id.autre_a_preciser_pour_vivant_radiobutton);

        submitBtn = rootView.findViewById(R.id.patient_submit_button);
        submitBtn.setVisibility(GONE);
        submitBtn.setOnClickListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (group.getId()) {
            case R.id.radio_group_statut_actuel: {
                if (checkedId == R.id.decede_item) {
                    decesLayout.setVisibility(VISIBLE);
                    vivantLayout.setVisibility(GONE);
                    statutActuelValue = DECEDE;
                } else if (checkedId == R.id.vivant_item) {
                    vivantLayout.setVisibility(VISIBLE);
                    decesLayout.setVisibility(GONE);
                    statutActuelValue = VIVANT;
                }
            }
            break;
            case R.id.radio_group_lieu_du_deces: {
                if (checkedId == R.id.structure_sanitaire_item_from_lieu_du_deces) {
                    detailsDecesLayout.setVisibility(VISIBLE);
                    responseFromLieuDuDeces = STRUCTURE_SANITAIRE;
                } else if (checkedId == R.id.domicile_item_from_lieu_du_deces) {
                    detailsDecesLayout.setVisibility(GONE);
                    responseFromLieuDuDeces = DOMICILE;
                }
            }
            break;
            case R.id.radio_group_details_vivant: {
                if (checkedId == R.id.hospitalisation_from_radiobutton_from_statut_actuel) {
                    autreStatutPourVivantEdt.setVisibility(GONE);
                    detailsHospitalisatinLayout.setVisibility(VISIBLE);
                    detailsVivant = HOSPITALISE;
                } else {
                    detailsHospitalisatinLayout.setVisibility(GONE);
                    if (checkedId == R.id.confine_a_domicile_radiobutton_from_statut_actuel) {
                        autreStatutPourVivantEdt.setVisibility(GONE);
                        detailsVivant = CONFINE_A_DOMICILE;
                    } else if (checkedId == R.id.gueri_radiobutton_from_statut_actuel) {
                        autreStatutPourVivantEdt.setVisibility(GONE);
                        detailsVivant = GUERI;
                    } else if (checkedId == R.id.autre_radiobutton_from_statut_actuel) {
                        autreStatutPourVivantEdt.setVisibility(VISIBLE);
                        detailsVivant = AUTRE;
                    }
                }
            }
            break;
            default: {
            }
        }

    }

    public boolean areAllRequiredFieldsCompleted() {
        // si le user ne précise pas le statut actuel du patient
        switch (statutActuelValue) {
            case EMPTY_STRING:
                return false;
            case DECEDE:
                // si le user ne précise pas la date du test
                if (isDateDeceUnchanged)
                    return false;
                // si le user ne choisit pas le lieu du deces (à domicile ou dans une structure sanitaire)
                if (responseFromLieuDuDeces.isEmpty())
                    return false;
                else { // if he does ! :)
                    // Si le patient est décedé dans une strucutre sanitaire
                    if (responseFromLieuDuDeces.equals(STRUCTURE_SANITAIRE)) {
                        // si le user ne précise pas la durée d'hospitalisation
                        if (dureeDhospitalisationNumberPicker.getValue() == 0)
                            return false;

                        // si le user ne fournit pas le nom de la structure sanitaire
                        if (structureSanitairePourDeces.getSelectedItem().toString().equals(EMPTY_STRING))
                            return false;
                    }
                }
                break;
            case VIVANT:
                switch (detailsVivant) {
                    case CONFINE_A_DOMICILE:
                        GUERI:
                        submitBtn.setVisibility(VISIBLE);
                        return true;
                    case AUTRE:
                        // si le user choisit "Autre"
                        if (autreStatutPourVivantEdt.getText().toString().trim().isEmpty()) {
                            autreStatutPourVivantEdt.requestFocus();
                            autreStatutPourVivantEdt.setError("De quel \"autre\" s'agit-il ??");
                            return false;
                        }
                        break;
                    case HOSPITALISE:
                        // si le user ne précise pas la date d'admission ou s'il ne choisit pas la structure sanitaire
                        if (isDateAdmissionUnchanged || structureSanitairePourDeces.getSelectedItem().toString().equals(EMPTY_STRING))
                            return false;
                        break;
                    default: {
                        Toast.makeText(getActivity(), "Erreur EV002", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }
                break;
            default: {
                Toast.makeText(getActivity(), "Erreur EV001", Toast.LENGTH_SHORT).show();
                return false;
            }

        }

        submitBtn.setVisibility(VISIBLE);
        return true;
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        if (view.getId() == R.id.datepicker_pour_deces)
            isDateDeceUnchanged = false;
        else if (view.getId() == R.id.datepicker_pour_hospitalisation)
            isDateAdmissionUnchanged = false;
        else
            Toast.makeText(getActivity(), "Erreur EV003", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.patient_submit_button) {
            Toast.makeText(getActivity(), "C'est OK !!", Toast.LENGTH_SHORT).show();
            /*
            setValues();
            Covid19NewFormController covid19NewFormController = new Covid19NewFormController(this);
            covid19NewFormController.insertForm();
            */
        }
    }

    public void setValues() {
        // covid19FormObject.setConfirmedCovid19Case(statutActuelValue);
        // covid19FormObject.setEvolution(evolutionValue);

        //Log.e(TAG, covid19FormObject.toString());
    }
}
