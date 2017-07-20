package com.example.shishir.blood;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.shishir.blood.Fragment.SearchFrgment;

public class SearchDonorActivity extends AppCompatActivity {

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_donor);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.parentLayoutForSearchDonor, new SearchFrgment()).commit();
    }

    @Override
    public void onBackPressed() {
        int fr = fragmentManager.getBackStackEntryCount();
        if (fr > 0)
            fragmentManager.popBackStack();
        else super.onBackPressed();
    }
}
