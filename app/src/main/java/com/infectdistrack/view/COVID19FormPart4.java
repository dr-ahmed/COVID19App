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
import android.widget.Toast;

import com.infectdistrack.R;

public class COVID19FormPart4 extends Fragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private View rootView;
    private TextView confirmedCasesTxt, evolutionTxt;
    private RadioGroup confirmedCasesRadioGroup, evolutionRadioGroup;
    private String confirmedCasesValue = "", evolutionValue = "";
    private Button submitBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_covid19_form_part4, container, false);

        initViews(rootView);

        return rootView;
    }

    private void initViews(View rootView) {
        confirmedCasesTxt = rootView.findViewById(R.id.confirmed_cases_covid19_txt);
        evolutionTxt = rootView.findViewById(R.id.form_evolution_txt);

        confirmedCasesRadioGroup = rootView.findViewById(R.id.patient_confirmed_cases_covid19_radio_group);
        confirmedCasesRadioGroup.setOnCheckedChangeListener(this);

        evolutionRadioGroup = rootView.findViewById(R.id.patient_evolution_radio_group);
        evolutionRadioGroup.setOnCheckedChangeListener(this);

        submitBtn = rootView.findViewById(R.id.patien_submit_button);
        submitBtn.setVisibility(View.GONE);
        submitBtn.setOnClickListener(this);

        checkSpinners();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (group.getId()) {
            case R.id.patient_confirmed_cases_covid19_radio_group: {
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
        if (v.getId() == R.id.patien_submit_button) {
            Toast.makeText(getActivity(), "C'est Ok !!", Toast.LENGTH_SHORT).show();
        }
    }
}
