package com.infectdistrack.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.infectdistrack.R;
import com.infectdistrack.model.ViewPagerAdapter;

import java.util.ArrayList;

public class COVID19FormActivity extends AppCompatActivity {

    private CustomViewPager customViewPager;
    private TabLayout tabLayout;

    private COVID19FormPart1 covid19FormPart1;
    private COVID19FormPart2 covid19FormPart2;
    private COVID19FormPart3 covid19FormPart3;
    private COVID19FormPart4 covid19FormPart4;

    private ViewPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid19_form);

        init();
    }

    private void init() {
        tabLayout = findViewById(R.id.tab_layout);
        customViewPager = findViewById(R.id.view_pager);
        customViewPager.setParentActivity(this);

        covid19FormPart1 = new COVID19FormPart1();
        covid19FormPart2 = new COVID19FormPart2();
        covid19FormPart3 = new COVID19FormPart3();
        covid19FormPart4 = new COVID19FormPart4();

        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(covid19FormPart1);
        fragments.add(covid19FormPart2);
        fragments.add(covid19FormPart3);
        fragments.add(covid19FormPart4);

        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 1, fragments);
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

    public COVID19FormPart1 getCovid19FormPart1() {
        return covid19FormPart1;
    }

    public COVID19FormPart2 getCovid19FormPart2() {
        return covid19FormPart2;
    }

    public COVID19FormPart3 getCovid19FormPart3() {
        return covid19FormPart3;
    }
}
