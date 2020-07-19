package com.infectdistrack.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.infectdistrack.R;
import com.infectdistrack.model.Covid19Form;
import com.infectdistrack.model.Patient;
import com.infectdistrack.model.User;
import com.infectdistrack.model.ViewPagerAdapter;

import java.util.ArrayList;

import static android.view.View.GONE;
import static com.infectdistrack.model.Constants.CURRENT_USER;
import static com.infectdistrack.model.Constants.PATIENT_OBJECT_TAG;

public class Covid19FormActivity extends AppCompatActivity {

    private static final String TAG = "COVID19FormActivity";

    private TextView fragmentCurrentLabel, errorLabelTextView;
    private CustomViewPager customViewPager;
    private TabLayout tabLayout;

    private Covid19FormPart1 covid19FormPart1;
    // we set as true the default visibility of symptom layout car le checkbox noSympBox est par défaut non coché
    // donc, on part du principe que si noSympBox est coché, on cache le symptom layout, sinon, on l'affiche
    private boolean symptomsLayoutVisibility = true;

    private Covid19FormPart2 covid19FormPart2;
    private Covid19FormPart3 covid19FormPart3;
    private Covid19FormPart4 covid19FormPart4;

    private Patient patient;
    private User parentUser;
    private Covid19Form covid19CurrentForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid19_form);

        init();
    }

    private void init() {
        if (CURRENT_USER == null) {
            Toast.makeText(this, "Désolé, l'identifiant de l'utilisateur parent n'est pas valide !", Toast.LENGTH_SHORT).show();
            return;
        } else if (!getIntent().hasExtra(PATIENT_OBJECT_TAG)) {
            Toast.makeText(this, "Désolé, l'identifiant du patient n'est pas valide !", Toast.LENGTH_SHORT).show();
            return;
        } else {
            parentUser = CURRENT_USER;
            patient = getIntent().getParcelableExtra(PATIENT_OBJECT_TAG);

            covid19CurrentForm = new Covid19Form();
            covid19CurrentForm.setParentUserId(parentUser.getId());
        }

        fragmentCurrentLabel = findViewById(R.id.fragment_current_label_txt);
        errorLabelTextView = findViewById(R.id.error_label_txt);
        tabLayout = findViewById(R.id.tab_layout);
        customViewPager = findViewById(R.id.view_pager);
        customViewPager.setParentActivity(this);

        covid19FormPart1 = new Covid19FormPart1(covid19CurrentForm);
        covid19FormPart2 = new Covid19FormPart2(covid19CurrentForm);
        covid19FormPart3 = new Covid19FormPart3(covid19CurrentForm);
        covid19FormPart4 = new Covid19FormPart4(covid19CurrentForm);
        covid19FormPart4.setParentUser(parentUser);

        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(covid19FormPart1);
        fragments.add(covid19FormPart2);
        fragments.add(covid19FormPart3);
        fragments.add(covid19FormPart4);

        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 1, fragments);
        customViewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(customViewPager, true);
    }

    public boolean getSymptomsLayoutVisibility() {
        return symptomsLayoutVisibility;
    }

    public void setSymptomsLayoutVisibility(boolean symptomsLayoutVisibility) {
        this.symptomsLayoutVisibility = symptomsLayoutVisibility;
    }

    public void setFragmentCurrentLabel(String fragmentTitle) {
        fragmentCurrentLabel.setText(fragmentTitle);
    }

    public void setErrorMessage(String message) {
        errorLabelTextView.setVisibility(View.VISIBLE);
        errorLabelTextView.setText(message);
        errorLabelTextView.setTextColor(Color.RED);
    }

    public void resetErrorLabel() {
        errorLabelTextView.setText("");
        errorLabelTextView.setTextColor(Color.DKGRAY);
        errorLabelTextView.setVisibility(GONE);
    }

    public boolean isPart1FormDone() {
        return covid19FormPart1.areAllRequiredFieldsCompleted();
    }

    public boolean isPart2FormDone() {
        return covid19FormPart2.areAllRequiredFieldsCompleted();
    }

    public boolean isPart3FormDone() {
        return covid19FormPart3.areAllRequiredFieldsCompleted();
    }

    public boolean isPart4FormDone() {
        return covid19FormPart4.areAllRequiredFieldsCompleted();
    }

    public Covid19FormPart1 getCovid19FormPart1() {
        return covid19FormPart1;
    }

    public Covid19FormPart2 getCovid19FormPart2() {
        return covid19FormPart2;
    }

    public Covid19FormPart3 getCovid19FormPart3() {
        return covid19FormPart3;
    }
}
