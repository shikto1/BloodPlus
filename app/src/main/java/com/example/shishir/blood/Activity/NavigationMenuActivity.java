package com.example.shishir.blood.Activity;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.shishir.blood.Fragment.AboutBloodPlus;
import com.example.shishir.blood.Fragment.Admin;
import com.example.shishir.blood.R;

public class NavigationMenuActivity extends AppCompatActivity {

    FragmentManager fragmentManager;
    private int fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_menu);

        fragmentManager = getSupportFragmentManager();
        fragment = getIntent().getIntExtra("frg", 0);
        openFragment(fragment);
    }

    private void openFragment(int fragment) {
        switch (fragment) {
            case 3: {
                fragmentManager.beginTransaction().add(R.id.parentLayoutForNavigationMenu, new Admin()).commit();
                getSupportActionBar().setTitle("Admin");
                break;
            }
            case 7: {
                fragmentManager.beginTransaction().add(R.id.parentLayoutForNavigationMenu, new AboutBloodPlus()).commit();
                getSupportActionBar().setTitle("About BLOOD+");
                break;
            }
        }
    }
}
