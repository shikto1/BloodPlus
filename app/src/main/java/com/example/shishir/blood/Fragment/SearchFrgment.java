package com.example.shishir.blood.Fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shishir.blood.Database.DonorTableManager;
import com.example.shishir.blood.Database.LocalDatabase;
import com.example.shishir.blood.Donor;
import com.example.shishir.blood.R;

import java.util.ArrayList;

import fr.ganfra.materialspinner.MaterialSpinner;


public class SearchFrgment extends Fragment {

    Spinner bloodGroup;
    TextView bloodSpinnerAsTextView;
    AutoCompleteTextView location;
    Button searchBtn;
    FragmentManager fragmentManager;
    FragmentTransaction transaction;
    String selectedBlood, selectedLocation;
    LocalDatabase localDatabase;
    boolean fTime;
    InputMethodManager inputMethodManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        fTime = true;
        findViewById(view);
        return view;

    }

    private void findViewById(View view) {
        bloodGroup = (Spinner) view.findViewById(R.id.bloodGroupSpinnerAtSearchFragment);
        location = (AutoCompleteTextView) view.findViewById(R.id.autoCompleTextViewForLocation);
        location.setThreshold(2);

        searchBtn = (Button) view.findViewById(R.id.searchButtonAtSearchFragment);
        bloodSpinnerAsTextView = (TextView) view.findViewById(R.id.asBloodSpinner);
        bloodGroup.setAdapter(ArrayAdapter.createFromResource(getActivity(), R.array.bloodGroup, android.R.layout.simple_list_item_1));
        location.setAdapter(ArrayAdapter.createFromResource(getActivity(), R.array.locationArray, android.R.layout.simple_list_item_1));
        fragmentManager = getActivity().getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        localDatabase = new LocalDatabase(getActivity());
        inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        bloodGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (fTime) {
                    fTime = false;
                } else
                    selectedBlood = parent.getItemAtPosition(position).toString();
                bloodSpinnerAsTextView.setText(selectedBlood);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ////This code if for hiding keyboard after selecting location from autoCompleteTextView....................................................

        location.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                inputMethodManager.hideSoftInputFromWindow(location.getWindowToken(), 0);
            }
        });


        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String blood = bloodSpinnerAsTextView.getText().toString();
                String loc = location.getText().toString();
                if (blood.length() > 0) {
                    if (loc.length() > 0) {
                        localDatabase.setSelectedBloodGrop(blood);
                        localDatabase.setSelectedLocation(loc);
                        new MyHelperTask().execute(blood, loc);
                    } else
                        Toast.makeText(getActivity(), "Enter Location !", Toast.LENGTH_SHORT).show();

                } else
                    Toast.makeText(getActivity(), "Choose Blood Group !", Toast.LENGTH_SHORT).show();

            }
        });
        bloodSpinnerAsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bloodGroup.performClick();
            }
        });

    }

// this is for searching data.........................................................................................

    class MyHelperTask extends AsyncTask<String, Void, ArrayList<Donor>> {

        // ProgressDialog dialog = new ProgressDialog(getActivity());
        ArrayList<Donor> donorList = new ArrayList<>();
        DonorTableManager donorTableManager = new DonorTableManager(getActivity());
        SearchListFragment searchListFragment;
        Bundle b = new Bundle();

        @Override
        protected void onPreExecute() {
            // dialog.setMessage("Loading...");
            //  dialog.setCancelable(false);
            //  dialog.show();
            super.onPreExecute();
        }

        @Override
        protected ArrayList<Donor> doInBackground(String... params) {
            donorList = donorTableManager.getAllDonorSearched(params[0], params[1]);
            return donorList;
        }

        @Override
        protected void onPostExecute(ArrayList<Donor> list) {
            //  dialog.dismiss();
            if (list.isEmpty()) {
                new AlertDialog.Builder(getActivity()).setMessage("Sorry, No Donor Found !")
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            } else {
                searchListFragment = new SearchListFragment();
                b.putSerializable("list", list);
                searchListFragment.setArguments(b);
                transaction.replace(R.id.parentLayoutForSearchDonor, searchListFragment);
                transaction.addToBackStack("h");
                transaction.commit();
            }
        }
    }

}
