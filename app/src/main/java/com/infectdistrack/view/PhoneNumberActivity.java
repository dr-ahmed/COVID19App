package com.infectdistrack.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.infectdistrack.R;

public class PhoneNumberActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number);

        displayPhoneNumberCheckoutFragment();
    }

    private void displayPhoneNumberCheckoutFragment() {
        PhoneNumberCheckoutFragment phoneNumberCheckoutFragment = new PhoneNumberCheckoutFragment();
        FragmentTransaction fragTransaction = getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_layout, phoneNumberCheckoutFragment, "phoneNumberCheckoutFragment");
        fragTransaction.commit();
    }
}
