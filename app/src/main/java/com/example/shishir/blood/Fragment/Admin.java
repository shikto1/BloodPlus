package com.example.shishir.blood.Fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.shishir.blood.Adapter.AdminAdapter;
import com.example.shishir.blood.Donor;
import com.example.shishir.blood.ExtraClass.Constants;
import com.example.shishir.blood.ExtraClass.MySingleton;
import com.example.shishir.blood.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Admin extends Fragment {
    private ListView adminListView;
    ProgressDialog progressDialog;
    ArrayList<Donor> adminArrayList;
    ActionBar actionBar;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin, container, false);
        doWork(view);
        return view;
    }

    private void doWork(View view) {
        adminListView = (ListView) view.findViewById(R.id.adminListView);
        progressDialog = new ProgressDialog(getActivity());
        actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        adminArrayList = new ArrayList<Donor>();
        getAdminList();
    }

    private void getAdminList() {
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
                        adminArrayList.add(new Donor(donorName, bloodG, locationStr, contact, "birthDate", lastDonate));
                    }
                    actionBar.setTitle("Admin (" + arrayLength + ") ");
                    adminListView.setAdapter(new AdminAdapter(getActivity(), adminArrayList));


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


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.over_flow_menu_for_add_admin, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.parentLayoutForNavigationMenu, new AddAdminFragment())
                .addToBackStack("aa")
                .commit();
        return super.onOptionsItemSelected(item);
    }
}
