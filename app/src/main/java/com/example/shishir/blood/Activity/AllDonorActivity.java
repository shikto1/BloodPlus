package com.example.shishir.blood.Activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.shishir.blood.Adapter.DonorAdapter;
import com.example.shishir.blood.Database.DonorTableManager;
import com.example.shishir.blood.Donor;
import com.example.shishir.blood.ExtraClass.Constants;
import com.example.shishir.blood.ExtraClass.MySingleton;
import com.example.shishir.blood.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AllDonorActivity extends AppCompatActivity {

    ListView donorListView;
    ProgressDialog pDialog;
    ArrayList<Donor> donorArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_donor);


        ((Spinner) findViewById(R.id.bloodgroupSpinnerAtToolbar)).setAdapter(ArrayAdapter.createFromResource(this, R.array.bloodGroup,
                android.R.layout.simple_list_item_1));

        donorListView = (ListView) findViewById(R.id.donorListView);
        pDialog = new ProgressDialog(this);
        donorArrayList = new ArrayList<>();
        useVolley();
    }

    private void useVolley() {

        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        pDialog.show();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                Constants.URL_GET_ALL_DONOR, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray donorArray = response.getJSONArray("Donor");
                            int arrayLength = donorArray.length();
                            for (int i = 0; i < arrayLength; i++) {
                                JSONObject singleDonor = donorArray.getJSONObject(i);
                                String donorName = singleDonor.getString("Name");
                                String contact = singleDonor.getString("Contact");
                                donorArrayList.add(new Donor(donorName, contact));
                            }
                            donorListView.setAdapter(new DonorAdapter(AllDonorActivity.this, donorArrayList));
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
        MySingleton.getInstance(this).addToRequestQueue(jsonObjReq);
    }
}
