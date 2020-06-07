package com.infectdistrack.view;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.infectdistrack.R;

public class COVID19FormPart2 extends Fragment {

    private static final String TAG = "COVID19FormPart2";

    private View rootView;
    private TextView textView;
    private CheckBox feverBox, astheniaBox, cephaleeBox, touxBox, dyspneeBox, malDeGorgeBox, rhiniteBox,
            congestionNasalBox, dysguesieBox, diarrheeBox, nauseesBox, otherSympBox, noSympBox;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_covid19_form_part2, container, false);

        initViews(rootView);

        return rootView;
    }

    private void initViews(View rootView) {
        textView = rootView.findViewById(R.id.patient_symptoms_txt);

        feverBox = rootView.findViewById(R.id.fever_chechbox);
        astheniaBox = rootView.findViewById(R.id.asthenia_chechbox);
        cephaleeBox = rootView.findViewById(R.id.cephalee_chechbox);
        touxBox = rootView.findViewById(R.id.toux_chechbox);
        dyspneeBox = rootView.findViewById(R.id.dyspnee_chechbox);
        malDeGorgeBox = rootView.findViewById(R.id.mal_de_gorge_chechbox);
        rhiniteBox = rootView.findViewById(R.id.rhinite_chechbox);
        congestionNasalBox = rootView.findViewById(R.id.congestion_nasal_chechbox);
        dysguesieBox = rootView.findViewById(R.id.dysguesie_chechbox);
        diarrheeBox = rootView.findViewById(R.id.diarrhee_chechbox);
        nauseesBox = rootView.findViewById(R.id.nausees_chechbox);
        otherSympBox = rootView.findViewById(R.id.other_symp_chechbox);
        noSympBox = rootView.findViewById(R.id.no_symp_chechbox);
    }

    private boolean isAtLeastCheckboxChecked() {
        return feverBox.isChecked() || astheniaBox.isChecked() || cephaleeBox.isChecked() || touxBox.isChecked()
                || dyspneeBox.isChecked() || malDeGorgeBox.isChecked() || rhiniteBox.isChecked()
                || congestionNasalBox.isChecked() || dysguesieBox.isChecked() || diarrheeBox.isChecked()
                || nauseesBox.isChecked() || otherSympBox.isChecked() || noSympBox.isChecked();
    }

    public boolean IsCheckboxChecked() {
        if (isAtLeastCheckboxChecked()) {
            resetTextview();
            return true;
        } else {
            setRedTextview();
            return false;
        }
    }

    private void setRedTextview() {
        textView.setText("CHOIX OBLIGATOIRE");
        textView.setTextColor(Color.RED);
    }

    private void resetTextview() {
        textView.setText("Symptômes");
        textView.setTextColor(Color.DKGRAY);
    }
}
