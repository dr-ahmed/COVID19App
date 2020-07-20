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
    private String responseFromTestCovid19 = "", responseFromTypeDeTestCovid19 = "", responseFromResultatTDR = "", responseFromDetailsTDR = "",
            responseFromPCRTest = "", responseFromScannerTest = "";
    private LinearLayout depistageControleLayout, typeEtDateDeTestLayout, resultatTestTDRLayout, detailsTestTDRLayout,
            resultatTestPCRLayout, resultatTestScannerLayout;
    private DatePicker datePicker;
    private String dateTest = "";
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
                    responseFromTestCovid19 = OUI;
                } else if (checkedId == R.id.no_item_from_test_covid19) {
                    responseFromTestCovid19 = NON;
                    depistageControleLayout.setVisibility(GONE);
                }
            }
            break;
            case R.id.radio_group_for_type_de_test_covid19: {
                if (checkedId == R.id.depistage_item_from_type_de_test_covid19) {
                    responseFromTypeDeTestCovid19 = DEPISTAGE;
                    scannerCheckBox.setVisibility(VISIBLE);
                } else if (checkedId == R.id.controle_item_from_type_de_test_covid19) {
                    // set the scanner checkbox as unchecked
                    scannerCheckBox.setChecked(false);
                    // Faites disparaitre le checkbox de scanner
                    scannerCheckBox.setVisibility(GONE);
                    // clear check from the TestScannerRadioGroup
                    resultatTestScannerRadioGroup.clearCheck();
                    // set the indicator as default
                    responseFromScannerTest = "";
                    // Faites disparaitre le resultatTestScannerLayout du résultat de scanner
                    resultatTestScannerLayout.setVisibility(GONE);
                    responseFromTypeDeTestCovid19 = CONTROLE;
                }
                // si le user choisit dépistage ou controle est que le typeEtDateDeTestLayout était hidden, affiche-le
                if (typeEtDateDeTestLayout.getVisibility() == GONE)
                    typeEtDateDeTestLayout.setVisibility(VISIBLE);
            }
            break;
            case R.id.radio_group_for_resultat_test_tdr: {
                if (checkedId == R.id.positif_item_from_resultat_test_tdr) {
                    responseFromResultatTDR = POSITIF;
                    detailsTestTDRLayout.setVisibility(VISIBLE);
                } else if (checkedId == R.id.negatif_item_from_resultat_test_tdr) {
                    responseFromResultatTDR = NEGATIF;
                    detailsTestTDRLayout.setVisibility(GONE);
                }
            }
            break;
            case R.id.radio_group_for_details_test_tdr: {
                if (checkedId == R.id.igm_item_from_details_test_tdr)
                    responseFromDetailsTDR = IGM;
                else if (checkedId == R.id.igg_item_from_details_test_tdr)
                    responseFromDetailsTDR = IGG;
                else if (checkedId == R.id.not_know_item_from_details_test_tdr)
                    responseFromDetailsTDR = NOT_KNOW;
            }
            break;
            case R.id.radio_group_for_resultat_test_pcr: {
                if (checkedId == R.id.positif_item_from_resultat_test_pcr)
                    responseFromPCRTest = POSITIF;
                else if (checkedId == R.id.negatif_item_from_resultat_test_pcr)
                    responseFromPCRTest = NEGATIF;
            }
            break;
            case R.id.radio_group_for_resultat_test_scanner: {
                if (checkedId == R.id.compatible_item_from_resultat_test_scanner)
                    responseFromScannerTest = COMPATIBLE;
                else if (checkedId == R.id.incompatible_item_from_resultat_test_scanner)
                    responseFromScannerTest = INCOMPATIBLE;
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
                    // si le user décoche le TDR, supprimer les checks des RadioGroups y relatifs et remettre à vide leurs reponses
                    resultatTestTDRRadioGroup.clearCheck();
                    responseFromResultatTDR = "";
                    detailsTestTDRRadioGroup.clearCheck();
                    responseFromDetailsTDR = "";
                    // Pour que le detailsTestTDRLayout disparaisse, on doit le faire GONE après le clearCheck de detailsTestTDRRadioGroup
                    // Pourquoi ? Je pense que le clearCheck fait réparaitre le layout or something like that. Je ne suis pas sur ! :)
                    detailsTestTDRLayout.setVisibility(GONE);
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

    public boolean areAllRequiredFieldsCompleted() {
        // si le user ne précise pas si le patient a fait ou non un test covid
        if (responseFromTestCovid19.isEmpty())
            return false;
        else { // là, le user a choisi OUI ou NON comme réponse à la première question
            if (depistageControleLayout.getVisibility() == VISIBLE) {
                if (responseFromTypeDeTestCovid19.isEmpty()) // si le user ne choisit ni Dépistage, ni Controle
                    return false;
                else {
                    // Si le user choisit Dépistage ou Controle
                    if (responseFromTypeDeTestCovid19.equals(DEPISTAGE) || responseFromTypeDeTestCovid19.equals(CONTROLE)) {
                        // si le user ne précise pas la date du test
                        if (isDateUnchanged)
                            return false;

                        // si le user choisit dépistage et ne sélectionne aucun type de test
                        if (responseFromTypeDeTestCovid19.equals(DEPISTAGE) && !tdrCheckBox.isChecked()
                                && !pcrCheckBox.isChecked() && !scannerCheckBox.isChecked())
                            return false;

                        // si le user choisit controle et ne sélectionne aucun type de test
                        if (responseFromTypeDeTestCovid19.equals(CONTROLE) && !tdrCheckBox.isChecked() && !pcrCheckBox.isChecked())
                            return false;

                        // si le user coche le test TDR
                        if (tdrCheckBox.isChecked()) {
                            if (responseFromResultatTDR.isEmpty()) // si le user ne précise pas est-ce que le TDR est posifit ou négatif
                                return false;
                                // si le user précise que le TDR est positif mais ne précise pas son type (igg, igm ou ne sait pas)
                            else if (responseFromResultatTDR.equals(POSITIF) && responseFromDetailsTDR.isEmpty())
                                return false;
                        }

                        // si le user coche le test PCR mais ne précise s'il est posifit ou négatif
                        if (pcrCheckBox.isChecked() && responseFromPCRTest.isEmpty())
                            return false;

                        // Si le user choisit Dépistage, coche le test Scanner et ne précise pas s'il est comptabile ou non
                        if (responseFromTypeDeTestCovid19.equals(DEPISTAGE) && scannerCheckBox.isChecked() && responseFromScannerTest.isEmpty())
                            return false;

                    } else { // Si la réponse du user n'est ni DEPISTAGE, ni CONTROLE !
                        Toast.makeText(getActivity(), "Erreur DC001", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void resetValues() {
        covid19FormObject.setTestCovid("");
        covid19FormObject.setTypeTest("");
        covid19FormObject.setDateTest("");
        covid19FormObject.setTdr("");
        covid19FormObject.setPcr("");
        covid19FormObject.setScanner("");
        covid19FormObject.setTdrResponse("");
        covid19FormObject.setTdrDetails("");
        covid19FormObject.setPcrReponse("");
        covid19FormObject.setScannerResponse("");
    }

    public void setValues() {
        resetValues();

        covid19FormObject.setTestCovid(responseFromTestCovid19);
        covid19FormObject.setTypeTest(responseFromTypeDeTestCovid19);
        covid19FormObject.setDateTest(dateTest);

        if (tdrCheckBox.isChecked()) {
            covid19FormObject.setTdr("TDR");
            covid19FormObject.setTdrResponse(responseFromResultatTDR);
            if (covid19FormObject.getTdrResponse().equals(POSITIF))
                covid19FormObject.setTdrDetails(responseFromDetailsTDR);
        }

        if (pcrCheckBox.isChecked()) {
            covid19FormObject.setPcr("PCR");
            covid19FormObject.setPcrReponse(responseFromPCRTest);
        }

        if (scannerCheckBox.isChecked()) {
            covid19FormObject.setScanner("SCANNER");
            covid19FormObject.setScannerResponse(responseFromScannerTest);
        }

        // Log.e(TAG, covid19FormObject.toString());
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        if (view.getId() == R.id.date_test_datepicker) {
            dateTest = dayOfMonth + "/" + monthOfYear + "/" + year;
            isDateUnchanged = false;
        }
    }
}
