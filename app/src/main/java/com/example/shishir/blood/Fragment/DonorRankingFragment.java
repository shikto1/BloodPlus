package com.example.shishir.blood.Fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.shishir.blood.Adapter.AdminAdapter;
import com.example.shishir.blood.Adapter.DonorRankingAdapter;
import com.example.shishir.blood.Donor;
import com.example.shishir.blood.ExtraClass.Constants;
import com.example.shishir.blood.ExtraClass.DonorRanking;
import com.example.shishir.blood.ExtraClass.MySingleton;
import com.example.shishir.blood.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class DonorRankingFragment extends Fragment {


    ListView donorRankingListView;
    ProgressDialog progressDialog;
    ArrayList<DonorRanking> donorRankingList = new ArrayList<DonorRanking>();
    DonorRankingAdapter donorRankingAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_donor_ranking, container, false);
        findViewById(view);
        return view;
    }

    private void findViewById(View view) {
        donorRankingListView = (ListView) view.findViewById(R.id.donorRankingListView);
        LayoutInflater myInflater = getActivity().getLayoutInflater();
        ViewGroup myHeader = (ViewGroup) myInflater.inflate(R.layout.single_layout_for_donor_ranking, donorRankingListView, false);
        donorRankingListView.addHeaderView(myHeader, null, false);

        setUpListView();
    }

    private void setUpListView() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Constants.URL_GET_DONOR_RANKING_LIST, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray donorArray = response.getJSONArray("Donor");
                    int arrayLength = donorArray.length();
                    for (int i = 0; i < arrayLength; i++) {
                        JSONObject singleDonor = donorArray.getJSONObject(i);
                        donorRankingList.add(new DonorRanking(singleDonor.getString("Name"), singleDonor.getString("Blood"),
                                singleDonor.getString("nDonation")));
                    }
//                    Collections.sort(donorRankingList, new Comparator<DonorRanking>() {
//                        @Override
//                        public int compare(DonorRanking donor1, DonorRanking donor2) {
//                            return donor2.getNumberOfDonation().compareTo(donor1.getNumberOfDonation());
//                        }
//                    });

                    donorRankingAdapter = new DonorRankingAdapter(getActivity(), donorRankingList);
                    donorRankingListView.setAdapter(donorRankingAdapter);
                    progressDialog.dismiss();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            }
        });
        MySingleton.getInstance(getActivity()).addToRequestQueue(request);

    }

}
