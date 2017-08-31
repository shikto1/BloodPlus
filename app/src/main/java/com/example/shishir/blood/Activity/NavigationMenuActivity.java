package com.example.shishir.blood.Activity;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.shishir.blood.Fragment.AboutBloodPlus;
import com.example.shishir.blood.Fragment.ActivityOfBloodPlus;
import com.example.shishir.blood.Fragment.Admin;
import com.example.shishir.blood.Fragment.DonorRankingFragment;
import com.example.shishir.blood.Fragment.PushNotificationFragment;
import com.example.shishir.blood.R;

public class NavigationMenuActivity extends AppCompatActivity {

    FragmentManager fragmentManager;
    private int fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_menu);
        //  getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
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
            case 4: {
                fragmentManager.beginTransaction().add(R.id.parentLayoutForNavigationMenu, new ActivityOfBloodPlus()).commit();
                getSupportActionBar().setTitle("BLOOD+ Activities");
                break;
            }
            case 5: {
                fragmentManager.beginTransaction().add(R.id.parentLayoutForNavigationMenu, new DonorRankingFragment()).commit();
                getSupportActionBar().setTitle("BLOOD+ Donor Ranking");
                break;
            }
            case 6: {
                fragmentManager.beginTransaction().add(R.id.parentLayoutForNavigationMenu, new PushNotificationFragment()).commit();
                getSupportActionBar().setTitle("Send Message");
                break;
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        } else
            return super.onOptionsItemSelected(item);
    }
}
