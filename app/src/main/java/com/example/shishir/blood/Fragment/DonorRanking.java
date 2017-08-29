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
import com.example.shishir.blood.ExtraClass.MySingleton;
import com.example.shishir.blood.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DonorRanking extends Fragment {


    ListView donorRankingListView;
    ProgressDialog progressDialog=new ProgressDialog(getActivity());
    ArrayList<Donor> donorRankingList=new ArrayList<Donor>();
    DonorRankingAdapter donorRankingAdapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_donor_ranking, container, false);
        findViewById(view);
        return view;
    }

    private void findViewById(View view) {
        donorRankingListView= (ListView) view.findViewById(R.id.donorRankingListView);
        donorRankingAdapter=new DonorRankingAdapter();

        getDonorRankingList();
    }

    private void getDonorRankingList() {
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Constants.URL_GET_ADMIN, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray donorArray = response.getJSONArray("Admin");
                    int arrayLength = donorArray.length();
                    for (int i = 0; i < arrayLength; i++) {
                        JSONObject singleDonor = donorArray.getJSONObject(i);
                        String bloodG = singleDonor.getString("Blood");
                        String donorName = singleDonor.getString("Name");
                        String contact = singleDonor.getString("Contact");
                        String locationStr = singleDonor.getString("Location");
                        String lastDonate = singleDonor.getString("LastDonate");
                        String subLocality = singleDonor.getString("SubLocality");
                        donorRankingList.add(new Donor(donorName, bloodG, subLocality + ", " + locationStr, contact, "birthDate", lastDonate));
                    }
                 //   donorRankingAdapter = new (getActivity(), donorRankingList);
                    donorRankingListView.setAdapter(donorRankingAdapter);


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
