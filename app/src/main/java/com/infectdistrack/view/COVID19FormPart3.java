package com.infectdistrack.view;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.infectdistrack.R;

public class COVID19FormPart3 extends Fragment {

    private View rootView;
    private TextView terrainTextView;
    private CheckBox diabeteBox, htaBox, grossesseBox, immunoBox, pathiesBox, fumurBox, autresTerrainBox, aucunTerrainBox;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_covid19_form_part3, container, false);

        initViews(rootView);

        return rootView;
    }

    private void initViews(View rootView) {
        terrainTextView = rootView.findViewById(R.id.terrain_textview);

        diabeteBox = rootView.findViewById(R.id.diabete_box);
        htaBox = rootView.findViewById(R.id.hta_box);
        grossesseBox = rootView.findViewById(R.id.grossesse_box);
        immunoBox = rootView.findViewById(R.id.immuno_box);
        pathiesBox = rootView.findViewById(R.id.pathies_box);
        fumurBox = rootView.findViewById(R.id.fumeur_box);
        autresTerrainBox = rootView.findViewById(R.id.autres_terrain_box);
        aucunTerrainBox = rootView.findViewById(R.id.aucun_terrain_box);
    }

    private boolean isAtLeastCheckboxChecked() {
        return diabeteBox.isChecked() || htaBox.isChecked() || grossesseBox.isChecked()
                || immunoBox.isChecked() || pathiesBox.isChecked() || fumurBox.isChecked()
                || autresTerrainBox.isChecked() || aucunTerrainBox.isChecked();
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
        terrainTextView.setText("CHOIX OBLIGATOIRE");
        terrainTextView.setTextColor(Color.RED);
    }

    private void resetTextview() {
        terrainTextView.setText("Terrain");
        terrainTextView.setTextColor(Color.DKGRAY);
    }
}
