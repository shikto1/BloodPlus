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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.shishir.blood.Activity.AllDonorActivity;
import com.example.shishir.blood.Adapter.DonorAdapter;
import com.example.shishir.blood.Database.DonorTableManager;
import com.example.shishir.blood.Database.LocalDatabase;
import com.example.shishir.blood.Donor;
import com.example.shishir.blood.ExtraClass.Constants;
import com.example.shishir.blood.ExtraClass.MyDialog;
import com.example.shishir.blood.ExtraClass.MySingleton;
import com.example.shishir.blood.Network;
import com.example.shishir.blood.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    String bloodStr, locationStr;
    ProgressDialog pDialog;
    ArrayList<Donor> donorArrayList;


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
        donorArrayList = new ArrayList<Donor>();
        inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        bloodGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (fTime) {
                    fTime = false;
                } else {
                    selectedBlood = parent.getItemAtPosition(position).toString();
                    bloodSpinnerAsTextView.setText(selectedBlood);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ////This code if for hiding keyboard after selecting location from autoCompleteTextView....................................................

        location.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                locationStr = parent.getItemAtPosition(position).toString();
                inputMethodManager.hideSoftInputFromWindow(location.getWindowToken(), 0);
            }
        });


        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Network.isNetAvailable(getActivity())) {
                    bloodStr = bloodSpinnerAsTextView.getText().toString();
                    if (bloodStr.length() == 0) {
                        ToastMessage("Select Your Blood Group");
                    } else if (locationStr == null) {
                        ToastMessage("Enter Your Location");
                    } else {
                        searchDonor();
                    }
                } else {
                    Network.showInternetAlertDialog(getActivity());
                }


            }
        });
        bloodSpinnerAsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bloodGroup.performClick();
            }
        });
    }

    private void searchDonor() {
        {

            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Loading...");
            pDialog.setCancelable(false);
            pDialog.show();


            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                    Constants.URL_GET_SEARCHED_DONOR, null,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                int res = response.getInt("success");
                                if (res == 1) {
                                    try {
                                        JSONArray donorArray = response.getJSONArray("Donor");
                                        int arrayLength = donorArray.length();
                                        for (int i = 0; i < arrayLength; i++) {
                                            JSONObject singleDonor = donorArray.getJSONObject(i);
                                            String donorName = singleDonor.getString("Name");
                                            String contact = singleDonor.getString("Contact");
                                            donorArrayList.add(new Donor(donorName, contact));
                                        }

                                        SearchListFragment searchListFragment = new SearchListFragment();
                                        Bundle b = new Bundle();
                                        b.putSerializable("list", donorArrayList);
                                        searchListFragment.setArguments(b);
                                        transaction.replace(R.id.parentLayoutForSearchDonor, searchListFragment);
                                        transaction.addToBackStack("h");
                                        transaction.commit();
                                        pDialog.hide();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    pDialog.hide();
                                } else {
                                    MyDialog.alert(getActivity(), "Sorry !", "No Donor Found");

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {

                    pDialog.hide();
                }
            });

            MySingleton.getInstance(getActivity()).addToRequestQueue(jsonObjReq);
        }
    }


    private void ToastMessage(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

}
