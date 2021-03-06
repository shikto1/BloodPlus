package com.example.shishir.blood.Activity;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.shishir.blood.Fragment.CreateAccountUsingGmail;
import com.example.shishir.blood.Fragment.LoginWithFacebookOrMailFragment;
import com.example.shishir.blood.R;

public class RegisterLoginActivity extends AppCompatActivity {

    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_login);

        fragmentManager = getSupportFragmentManager();
        String rec = getIntent().getStringExtra("rr");

        if (rec.equals("login")) {
            fragmentManager.beginTransaction()
                    .add(R.id.parentLayoutRegisterLogin, new LoginWithFacebookOrMailFragment())
                    .commit();
        } else {
            fragmentManager.beginTransaction()
                    .add(R.id.parentLayoutRegisterLogin, new CreateAccountUsingGmail())
                    .commit();
        }
    }
}
