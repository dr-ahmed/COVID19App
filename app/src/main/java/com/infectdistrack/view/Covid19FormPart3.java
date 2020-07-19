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
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.infectdistrack.R;
import com.infectdistrack.model.Covid19Form;
import com.infectdistrack.model.Utilities;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.infectdistrack.model.Constants.COMPATIBLE;
import static com.infectdistrack.model.Constants.CONTROLE;
import static com.infectdistrack.model.Constants.DEPISTAGE;
import static com.infectdistrack.model.Constants.IGG;
import static com.infectdistrack.model.Constants.IGM;
import static com.infectdistrack.model.Constants.INCOMPATIBLE;
import static com.infectdistrack.model.Constants.NEGATIF;
import static com.infectdistrack.model.Constants.NON;
import static com.infectdistrack.model.Constants.NOT_KNOW;
import static com.infectdistrack.model.Constants.OUI;
import static com.infectdistrack.model.Constants.POSITIF;

public class Covid19FormPart3 extends Fragment implements RadioGroup.OnCheckedChangeListener, DatePicker.OnDateChangedListener, CompoundButton.OnCheckedChangeListener {

    private static final String TAG = "COVID19FormPart3";

    private View rootView;

    private RadioGroup testCovid19RadioGroup, typeDeTestCovid19RadioGroup, resultatTestTDRRadioGroup, detailsTestTDRRadioGroup, resultatTestPCRRadioGroup,
            resultatTestScannerRadioGroup;
    private String reponseFromTestCovid19 = "", reponseFromTypeDeTestCovid19 = "", reponseFromResultatTDR = "", reponseFromDetailsTDR = "",
            reponseFromPCRTest = "", reponseFromScannerTest = "";
    private LinearLayout depistageControleLayout, typeEtDateDeTestLayout, resultatTestTDRLayout, detailsTestTDRLayout,
            resultatTestPCRLayout, resultatTestScannerLayout;
    private DatePicker datePicker;
    private boolean isDateUnchanged = true;
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

        typeEtDateDeTestLayout = rootView.findViewById(R.id.layout_for_type_et_date_de_test);

        datePicker = rootView.findViewById(R.id.date_test_datepicker);
        int[] calendar = Utilities.getCurrentDayMonthAndYear();
        datePicker.init(calendar[0], calendar[1], calendar[2], this);

        resultatTestTDRRadioGroup = rootView.findViewById(R.id.radio_group_for_resultat_test_tdr);
        resultatTestTDRRadioGroup.setOnCheckedChangeListener(this);

        detailsTestTDRRadioGroup = rootView.findViewById(R.id.radio_group_for_details_test_tdr);
        detailsTestTDRRadioGroup.setOnCheckedChangeListener(this);

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

        resultatTestPCRRadioGroup = rootView.findViewById(R.id.radio_group_for_resultat_test_pcr);
        resultatTestPCRRadioGroup.setOnCheckedChangeListener(this);

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
                if (checkedId == R.id.depistage_item_from_type_de_test_covid19) {
                    reponseFromTypeDeTestCovid19 = DEPISTAGE;
                    scannerCheckBox.setVisibility(VISIBLE);
                } else if (checkedId == R.id.controle_item_from_type_de_test_covid19) {
                    scannerCheckBox.setVisibility(GONE);
                    reponseFromTypeDeTestCovid19 = CONTROLE;
                }
                // si le user choisit dépistage ou controle est que le typeEtDateDeTestLayout était hidden, affiche-le
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
            case R.id.radio_group_for_resultat_test_pcr: {
                if (checkedId == R.id.positif_item_from_resultat_test_pcr)
                    reponseFromPCRTest = POSITIF;
                else if (checkedId == R.id.negatif_item_from_resultat_test_pcr)
                    reponseFromPCRTest = NEGATIF;
            }
            break;
            case R.id.radio_group_for_resultat_test_scanner: {
                if (checkedId == R.id.compatible_item_from_resultat_test_scanner)
                    reponseFromScannerTest = COMPATIBLE;
                else if (checkedId == R.id.incompatible_item_from_resultat_test_scanner)
                    reponseFromScannerTest = INCOMPATIBLE;
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
                if (!isChecked) {
                    detailsTestTDRLayout.setVisibility(GONE);
                    // si le user décoche le TDR, supprimer les checks des RadioGroups y relatifs
                    resultatTestTDRRadioGroup.clearCheck();
                    detailsTestTDRRadioGroup.clearCheck();
                }
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

    public boolean isFieldEmpty() {
        // si le user ne précise pas si le patient a fait ou non un test covid
        if (reponseFromTestCovid19.isEmpty())
            return true;
        else { // là, le user a choisi OUI ou NON comme réponse à la première question
            if (depistageControleLayout.getVisibility() == VISIBLE) {
                if (reponseFromTypeDeTestCovid19.isEmpty()) // si le user ne choisit ni Dépistage, ni Controle
                    return true;
                else {
                    // Si le user choisit Dépistage ou Controle
                    if (reponseFromTypeDeTestCovid19.equals(DEPISTAGE) || reponseFromTypeDeTestCovid19.equals(CONTROLE)) {
                        // si le user ne précise pas la date du test
                        if (isDateUnchanged)
                            return true;

                        // si le user choisit dépistage et ne sélectionne aucun type de test
                        if (reponseFromTypeDeTestCovid19.equals(DEPISTAGE) && !tdrCheckBox.isChecked()
                                && !pcrCheckBox.isChecked() && !scannerCheckBox.isChecked())
                            return true;

                        // si le user choisit controle et ne sélectionne aucun type de test
                        if (reponseFromTypeDeTestCovid19.equals(CONTROLE) && !tdrCheckBox.isChecked() && !pcrCheckBox.isChecked())
                            return true;

                        // si le user coche le test TDR
                        if (tdrCheckBox.isChecked()) {
                            if (reponseFromResultatTDR.isEmpty()) // si le user ne précise pas est-ce que le TDR est posifit ou négatif
                                return true;
                                // si le user précise que le TDR est positif mais ne précise pas son type (igg, igm ou ne sait pas)
                            else if (reponseFromResultatTDR.equals(POSITIF) && reponseFromDetailsTDR.isEmpty())
                                return true;
                        }

                        // si le user coche le test PCR mais ne précise s'il est posifit ou négatif
                        if (pcrCheckBox.isChecked() && reponseFromPCRTest.isEmpty())
                            return true;

                        // Si le user choisit Dépistage, coche le test Scanner et ne précise pas s'il est comptabile ou non
                        if (reponseFromTypeDeTestCovid19.equals(DEPISTAGE) && tdrCheckBox.isChecked() && reponseFromScannerTest.isEmpty())
                            return true;

                    } else { // Si la réponse du user n'est ni DEPISTAGE, ni CONTROLE !
                        Toast.makeText(getActivity(), "Erreur DC001", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        if (view.getId() == R.id.date_test_datepicker)
            isDateUnchanged = false;
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
