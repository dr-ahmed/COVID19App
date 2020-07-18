package com.infectdistrack.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.infectdistrack.R;
import com.infectdistrack.model.Covid19Form;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.infectdistrack.model.Constants.CONTROLE;
import static com.infectdistrack.model.Constants.DEPISTAGE;
import static com.infectdistrack.model.Constants.IGG;
import static com.infectdistrack.model.Constants.IGM;
import static com.infectdistrack.model.Constants.NEGATIF;
import static com.infectdistrack.model.Constants.NON;
import static com.infectdistrack.model.Constants.NOT_KNOW;
import static com.infectdistrack.model.Constants.OUI;
import static com.infectdistrack.model.Constants.POSITIF;

public class Covid19FormPart3 extends Fragment implements RadioGroup.OnCheckedChangeListener, CompoundButton.OnCheckedChangeListener {

    private static final String TAG = "COVID19FormPart3";

    private View rootView;

    private RadioGroup testCovid19RadioGroup, typeDeTestCovid19RadioGroup, resultatTestScannerRadioGroup, resultatTestTDRRadioGroup;
    private String reponseFromTestCovid19 = "", reponseFromTypeDeTestCovid19 = "", reponseFromResultatTDR = "", reponseFromDetailsTDR = "";
    private LinearLayout depistageControleLayout, typeEtDateDeTestLayout, resultatTestTDRLayout, detailsTestTDRLayout,
            resultatTestPCRLayout, resultatTestScannerLayout;
    private DatePicker datePicker;
    private CheckBox tdrCheckBox, pcrCheckBox, scannerCheckBox;

    private Covid19Form covid19FormObject;

    public Covid19FormPart3(Covid19Form covid19FormObject) {
        this.covid19FormObject = covid19FormObject;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_covid19_form_part3, container, false);

        initViews(rootView);

        return rootView;
    }

    private void initViews(View rootView) {
        testCovid19RadioGroup = rootView.findViewById(R.id.radio_group_for_test_covid19);
        testCovid19RadioGroup.setOnCheckedChangeListener(this);
        depistageControleLayout = rootView.findViewById(R.id.depistage_controle_layout);
        typeDeTestCovid19RadioGroup = rootView.findViewById(R.id.radio_group_for_type_de_test_covid19);
        typeDeTestCovid19RadioGroup.setOnCheckedChangeListener(this);
        datePicker = rootView.findViewById(R.id.date_test_datepicker);
        typeEtDateDeTestLayout = rootView.findViewById(R.id.layout_for_type_et_date_de_test);

        resultatTestTDRRadioGroup = rootView.findViewById(R.id.radio_group_for_resultat_test_tdr);
        resultatTestTDRRadioGroup.setOnCheckedChangeListener(this);


        tdrCheckBox = rootView.findViewById(R.id.tdr_item_from_type_du_test_covid19);
        tdrCheckBox.setOnCheckedChangeListener(this);
        pcrCheckBox = rootView.findViewById(R.id.pcr_item_from_type_du_test_covid19);
        pcrCheckBox.setOnCheckedChangeListener(this);
        scannerCheckBox = rootView.findViewById(R.id.scanner_item_from_type_du_test_covid19);
        scannerCheckBox.setOnCheckedChangeListener(this);


        resultatTestTDRLayout = rootView.findViewById(R.id.layout_for_resultat_test_tdr);
        detailsTestTDRLayout = rootView.findViewById(R.id.layout_for_details_test_tdr);
        resultatTestPCRLayout = rootView.findViewById(R.id.layout_for_resultat_test_pcr);
        resultatTestScannerLayout = rootView.findViewById(R.id.layout_for_resultat_test_scanner);

        resultatTestScannerRadioGroup = rootView.findViewById(R.id.radio_group_for_resultat_test_scanner);
        resultatTestScannerRadioGroup.setOnCheckedChangeListener(this);
    }

    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (group.getId()) {
            case R.id.radio_group_for_test_covid19: {
                if (checkedId == R.id.yes_item_from_test_covid19) {
                    depistageControleLayout.setVisibility(VISIBLE);
                    reponseFromTestCovid19 = OUI;
                } else if (checkedId == R.id.no_item_from_test_covid19) {
                    reponseFromTestCovid19 = NON;
                    depistageControleLayout.setVisibility(GONE);
                }
            }
            break;
            case R.id.radio_group_for_type_de_test_covid19: {
                if (checkedId == R.id.depistage_item_from_type_de_test_covid19)
                    reponseFromTypeDeTestCovid19 = DEPISTAGE;
                else if (checkedId == R.id.controle_item_from_type_de_test_covid19)
                    reponseFromTypeDeTestCovid19 = CONTROLE;

                if (typeEtDateDeTestLayout.getVisibility() == GONE)
                    typeEtDateDeTestLayout.setVisibility(VISIBLE);
            }
            break;
            case R.id.radio_group_for_resultat_test_tdr: {
                if (checkedId == R.id.positif_item_from_resultat_test_tdr) {
                    reponseFromResultatTDR = POSITIF;
                    detailsTestTDRLayout.setVisibility(VISIBLE);
                } else if (checkedId == R.id.negatif_item_from_resultat_test_tdr) {
                    reponseFromResultatTDR = NEGATIF;
                    detailsTestTDRLayout.setVisibility(GONE);
                }
            }
            break;
            case R.id.radio_group_for_details_test_tdr: {
                if (checkedId == R.id.igm_item_from_details_test_tdr)
                    reponseFromDetailsTDR = IGM;
                else if (checkedId == R.id.igg_item_from_details_test_tdr)
                    reponseFromDetailsTDR = IGG;
                else if (checkedId == R.id.not_know_item_from_details_test_tdr)
                    reponseFromDetailsTDR = NOT_KNOW;
            }
            break;
            default: {

            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.tdr_item_from_type_du_test_covid19:
                resultatTestTDRLayout.setVisibility(isChecked ? VISIBLE : GONE);
                if (!isChecked)
                    detailsTestTDRLayout.setVisibility(GONE);
                break;
            case R.id.pcr_item_from_type_du_test_covid19:
                resultatTestPCRLayout.setVisibility(isChecked ? VISIBLE : GONE);
                break;
            case R.id.scanner_item_from_type_du_test_covid19:
                resultatTestScannerLayout.setVisibility(isChecked ? VISIBLE : GONE);
                break;
            default: {

            }
        }
    }

    private boolean isAtLeastCheckboxChecked() {
        /*
        return diabeteBox.isChecked() || htaBox.isChecked() || grossesseBox.isChecked()
                || immunoBox.isChecked() || pathiesBox.isChecked() || fumurBox.isChecked()
                || autresTerrainBox.isChecked() || aucunTerrainBox.isChecked();
        */
        return true;
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
        /*
        terrainTextView.setText("CHOIX OBLIGATOIRE");
        terrainTextView.setTextColor(Color.RED);
        */
    }

    private void resetTextview() {
        /*
        terrainTextView.setText("Terrain");
        terrainTextView.setTextColor(Color.DKGRAY);
        */
    }

    private String getPatientTerrain() {
        StringBuilder terrain = new StringBuilder();

        /*
        if (diabeteBox.isChecked())
            terrain.append("Diabète").append(";");
        if (grossesseBox.isChecked())
            terrain.append("Grossesse").append(";");
         */

        return terrain.toString();
    }

    public void setValues() {
        covid19FormObject.setTerrain(getPatientTerrain());
        //Log.e(TAG, covid19FormObject.toString());
    }
}
