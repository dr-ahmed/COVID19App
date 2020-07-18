package com.infectdistrack.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.infectdistrack.R;
import com.infectdistrack.model.Covid19Form;
import com.infectdistrack.model.User;
import com.infectdistrack.presenter.Covid19NewFormController;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.infectdistrack.model.Constants.AUTRE;
import static com.infectdistrack.model.Constants.CONFINE_A_DOMICILE;
import static com.infectdistrack.model.Constants.DECEDE;
import static com.infectdistrack.model.Constants.DOMICILE;
import static com.infectdistrack.model.Constants.GUERI;
import static com.infectdistrack.model.Constants.HOSPITALISE;
import static com.infectdistrack.model.Constants.STRUCTURES_SANITAIRES;
import static com.infectdistrack.model.Constants.STRUCTURE_SANITAIRE;
import static com.infectdistrack.model.Constants.VIVANT;

public class Covid19FormPart4 extends Fragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private static final String TAG = "COVID19FormPart4";

    private View rootView;
    private LinearLayout decesLayout, vivantLayout, detailsDecesLayout, detailsHospitalisatinLayout;
    private RadioGroup statutActuelRadioGroup, lieuDuDecesRadioGroup, detailsVivantRadioGroup;
    private String statutActuelValue = "", detailsDeces = "", detailsVivant = "";
    private NumberPicker dureeDhospitalisationPicker;
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

        detailsDecesLayout = rootView.findViewById(R.id.details_deces_layout);
        dureeDhospitalisationPicker = rootView.findViewById(R.id.duree_d_hospitalisation_picker);
        dureeDhospitalisationPicker.setMinValue(0);
        dureeDhospitalisationPicker.setMaxValue(100);

        structureSanitairePourDeces = rootView.findViewById(R.id.structure_sanitaire_pour_deces_spinner);
        structureSanitairePourDecesAdapter = new ArrayAdapter<>(getActivity(), R.layout.custom_spinner_item, STRUCTURES_SANITAIRES);
        structureSanitairePourDeces.setAdapter(structureSanitairePourDecesAdapter);

        detailsVivantRadioGroup = rootView.findViewById(R.id.radio_group_details_vivant);
        detailsVivantRadioGroup.setOnCheckedChangeListener(this);

        structureSanitairePourHospitalisation = rootView.findViewById(R.id.structure_sanitaire_pour_hospitalisation_spinner);
        structureSanitairePourHospitalisationAdapter = new ArrayAdapter<>(getActivity(), R.layout.custom_spinner_item, STRUCTURES_SANITAIRES);
        structureSanitairePourHospitalisation.setAdapter(structureSanitairePourHospitalisationAdapter);

        detailsHospitalisatinLayout = rootView.findViewById(R.id.details_hospitalisation_layout);

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
                    detailsDeces = STRUCTURE_SANITAIRE;
                } else if (checkedId == R.id.domicile_item_from_lieu_du_deces) {
                    detailsDecesLayout.setVisibility(GONE);
                    detailsDeces = DOMICILE;
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

    private void checkSpinners() {
        /*
        if (confirmedCasesValue.isEmpty()) {
            setRedTextview(confirmedCasesTxt);
            return;
        } else
            resetTextview(confirmedCasesTxt, "Cas confirmé de COVID-19");

        if (evolutionValue.isEmpty()) {
            setRedTextview(evolutionTxt);
            return;
        } else
            resetTextview(evolutionTxt, "Évolution");
         */

        submitBtn.setVisibility(VISIBLE);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.patient_submit_button) {
            setValues();
            Covid19NewFormController covid19NewFormController = new Covid19NewFormController(this);
            covid19NewFormController.insertForm();
        }
    }

    public void setValues() {
        // covid19FormObject.setConfirmedCovid19Case(statutActuelValue);
        // covid19FormObject.setEvolution(evolutionValue);

        //Log.e(TAG, covid19FormObject.toString());
    }

    public boolean areAllRequiredFieldsCompleted() {
        return false;
    }
}
