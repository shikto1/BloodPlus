package com.example.shishir.blood.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.example.shishir.blood.Database.LocalDatabase;
import com.example.shishir.blood.R;

import fr.ganfra.materialspinner.MaterialSpinner;


public class SearchFrgment extends Fragment {

    MaterialSpinner bloodGroup, location;
    Button searchBtn;
    FragmentManager fragmentManager;
    FragmentTransaction transaction;
    String selectedBlood, selectedLocation;
    LocalDatabase localDatabase;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        findViewById(view);
        return view;

    }

    private void findViewById(View view) {
        bloodGroup = (MaterialSpinner) view.findViewById(R.id.bloodGroupSpinnerAtSearchFragment);
        location = (MaterialSpinner) view.findViewById(R.id.locationSpinnerAtSearchFragment);

        searchBtn = (Button) view.findViewById(R.id.searchButtonAtSearchFragment);
        fragmentManager = getActivity().getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        localDatabase=new LocalDatabase(getActivity());

        bloodGroup.setAdapter(ArrayAdapter.createFromResource(getActivity(), R.array.bloodGroup, android.R.layout.simple_list_item_1));
        location.setAdapter(ArrayAdapter.createFromResource(getActivity(), R.array.locationArray, android.R.layout.simple_list_item_1));

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                localDatabase.setSelectedBloodGrop(selectedBlood);
                localDatabase.setSelectedLocation(selectedLocation);
                transaction.replace(R.id.parentLayoutForSearchDonor, new SearchListFragment()).addToBackStack("sr").commit();
                Toast.makeText(getActivity(),selectedBlood+" , "+selectedLocation,Toast.LENGTH_LONG).show();

            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        bloodGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedBlood = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedLocation = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        super.onActivityCreated(savedInstanceState);
    }
}
