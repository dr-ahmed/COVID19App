package com.infectdistrack.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.infectdistrack.R;
import com.infectdistrack.model.Covid19Form;
import com.infectdistrack.model.User;
import com.infectdistrack.model.ViewPagerAdapter;

import java.util.ArrayList;

public class Covid19FormActivity extends AppCompatActivity {

    private static final String TAG = "COVID19FormActivity";

    private CustomViewPager customViewPager;
    private TabLayout tabLayout;

    private Covid19FormPart1 covid19FormPart1;
    private Covid19FormPart2 covid19FormPart2;
    private Covid19FormPart3 covid19FormPart3;
    private Covid19FormPart4 covid19FormPart4;

    private User parentUser;
    private Covid19Form covid19CurrentForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid19_form);

        init();
    }

    private void init() {
        try {
            Bundle bundle = getIntent().getBundleExtra("parentUserBundle");
            parentUser = bundle.getParcelable("parentUser");

            covid19CurrentForm = new Covid19Form();
            covid19CurrentForm.setParentUserId(parentUser.getId());
        } catch (Exception e) {
            Toast.makeText(this, "Désolé, l'identifiant de l'utilisateur parent n'est pas valide !", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Prent user Id is not valid !!!!!");
            return;
        }

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

    public boolean isPart1FormDone() {
        return !covid19FormPart1.isFieldEmpty();
    }

    public boolean isPart2FormDone() {
        return covid19FormPart2.IsCheckboxChecked();
    }

    public boolean isPart3FormDone() {
        return covid19FormPart3.IsCheckboxChecked();
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
