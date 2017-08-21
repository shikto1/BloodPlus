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
import com.android.volley.toolbox.StringRequest;
import com.example.shishir.blood.Activity.AllDonorActivity;
import com.example.shishir.blood.Adapter.ActivityOfBloodPlusAdapter;
import com.example.shishir.blood.Adapter.AllDonorAdapter;
import com.example.shishir.blood.BloodPlusActivity;
import com.example.shishir.blood.Donor;
import com.example.shishir.blood.ExtraClass.Constants;
import com.example.shishir.blood.ExtraClass.MySingleton;
import com.example.shishir.blood.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class ActivityOfBloodPlus extends Fragment {

    ListView listView;
    ProgressDialog pDialog;
    ArrayList<BloodPlusActivity> activitiesList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_activity_of_blood_plus, container, false);
        findViewById(view);
        return view;
    }

    private void findViewById(View view) {
        listView = (ListView) view.findViewById(R.id.activityListView);
        pDialog=new ProgressDialog(getActivity());
        activitiesList=new ArrayList<BloodPlusActivity>();

        setUpListView();
    }

    private void setUpListView() {

        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        pDialog.show();
        final Date[] date1 = new Date[1];
        final Date date2;

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                Constants.URL_GET_INFO_FROM_ACTIVITY_TABLE, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray donorArray = response.getJSONArray("Donor");
                            int len=donorArray.length();
                            for (int i = 0; i < len; i++) {
                                JSONObject singleDonor = donorArray.getJSONObject(i);
                                activitiesList.add(new BloodPlusActivity(singleDonor.getString("Name"),singleDonor.getString("Blood"),
                                        singleDonor.getString("Hospital"),"",singleDonor.getString("dDate")));

                                Collections.sort(activitiesList, new Comparator<BloodPlusActivity>() {
                                    @Override
                                    public int compare(BloodPlusActivity obj1, BloodPlusActivity obj2) {
                                        int i=0;
                                        try {
                                            Date d1 =dateFormat.parse(obj1.getDonationDate());
                                            Date d2=dateFormat.parse(obj2.getDonationDate());
                                            i=d2.compareTo(d1);
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                        return i;
                                    }
                                });
                            }


                            listView.setAdapter(new ActivityOfBloodPlusAdapter(getActivity(),activitiesList));
                            pDialog.hide();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        pDialog.hide();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                pDialog.hide();
            }
        });

// Adding request to request queue
        MySingleton.getInstance(getActivity()).addToRequestQueue(jsonObjReq);
    }



}
