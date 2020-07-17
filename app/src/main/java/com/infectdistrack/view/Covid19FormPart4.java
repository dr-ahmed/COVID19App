package com.infectdistrack.view;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.infectdistrack.R;
import com.infectdistrack.model.Covid19Form;
import com.infectdistrack.model.User;
import com.infectdistrack.presenter.Covid19NewFormController;

public class Covid19FormPart4 extends Fragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private static final String TAG = "COVID19FormPart4";

    private View rootView;
    private TextView confirmedCasesTxt, evolutionTxt;
    private RadioGroup confirmedCasesRadioGroup, evolutionRadioGroup;
    private String confirmedCasesValue = "", evolutionValue = "";
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

        initViews(rootView);

        return rootView;
    }

    private void initViews(View rootView) {
        /*
        confirmedCasesTxt = rootView.findViewById(R.id.status_actuel_txt);
        evolutionTxt = rootView.findViewById(R.id.form_evolution_txt);

        confirmedCasesRadioGroup = rootView.findViewById(R.id.radio_group_status_actuel);
        confirmedCasesRadioGroup.setOnCheckedChangeListener(this);

        evolutionRadioGroup = rootView.findViewById(R.id.patient_evolution_radio_group);
        evolutionRadioGroup.setOnCheckedChangeListener(this);
         */

        submitBtn = rootView.findViewById(R.id.patient_submit_button);
        submitBtn.setVisibility(View.GONE);
        submitBtn.setOnClickListener(this);

        checkSpinners();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        /*
        switch (group.getId()) {
            case R.id.radio_group_status_actuel: {
                if (checkedId == R.id.co_cov_tdr_item)
                    confirmedCasesValue = "TDR / ELISA positif";
                else if (checkedId == R.id.co_cov_pcr_item)
                    confirmedCasesValue = "PCR positif";
                else if (checkedId == R.id.co_cov_ct_scan_item)
                    confirmedCasesValue = "CT-Scan compatible avec COVID-19";
                else if (checkedId == R.id.co_cov_na_item)
                    confirmedCasesValue = "NA";
                checkSpinners();
            }
            break;
            case R.id.patient_evolution_radio_group: {
                switch (checkedId) {
                    case R.id.evol_decede_item:
                        evolutionValue = "Décédé";
                        break;
                    case R.id.evol_confin_item:
                        evolutionValue = "En confinement";
                        break;
                    case R.id.evol_gueri_item:
                        evolutionValue = "Guéri";
                        break;
                    case R.id.evol_autre_item:
                        evolutionValue = "Autre";
                        break;
                }
                checkSpinners();
            }
        }
        */
    }

    private void checkSpinners() {
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

        submitBtn.setVisibility(View.VISIBLE);
    }

    private void setRedTextview(TextView textView) {
        textView.setText("CHOIX OBLIGATOIRE");
        textView.setTextColor(Color.RED);
    }

    private void resetTextview(TextView textView, String s) {
        textView.setText(s);
        textView.setTextColor(Color.DKGRAY);
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
        covid19FormObject.setConfirmedCovid19Case(confirmedCasesValue);
        covid19FormObject.setEvolution(evolutionValue);

        //Log.e(TAG, covid19FormObject.toString());
    }

    public boolean areAllRequiredFieldsCompleted() {
        return false;
    }
}
