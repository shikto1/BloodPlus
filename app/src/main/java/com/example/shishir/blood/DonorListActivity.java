package com.example.shishir.blood;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.shishir.blood.Database.DonorTableManager;

public class DonorListActivity extends AppCompatActivity {

    ListView donorList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_list);


        ((Spinner) findViewById(R.id.bloodgroupSpinnerAtToolbar)).setAdapter(ArrayAdapter.createFromResource(this, R.array.bloodGroup,
                android.R.layout.simple_list_item_1));

        donorList = (ListView) findViewById(R.id.donorListView);
        donorList.setAdapter(new DonorAdapter(this, new DonorTableManager(this).getAllDonor()));
    }
}
