package com.example.shishir.blood.Fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.shishir.blood.Activity.AllDonorActivity;
import com.example.shishir.blood.Adapter.AddNewAdminAdapter;
import com.example.shishir.blood.Adapter.AllDonorAdapter;
import com.example.shishir.blood.Donor;
import com.example.shishir.blood.ExtraClass.Constants;
import com.example.shishir.blood.ExtraClass.MySingleton;
import com.example.shishir.blood.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddAdminFragment extends Fragment {

    private ListView donorListView;
    ProgressDialog pDialog;
    AddNewAdminAdapter addNewAdminAdapter;
    ArrayList<Donor> donorArrayList;
    int arrayLength;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_admin, container, false);
        findViewById(view);
        return view;
    }

    private void findViewById(View view) {
        donorListView = (ListView) view.findViewById(R.id.addAdminListView);
        donorArrayList = new ArrayList<Donor>();
        getAllNonAdmin();
    }

    private void getAllNonAdmin() {
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Fetching data...");
        pDialog.setCancelable(false);
        pDialog.show();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                Constants.URL_GET_ALL_NON_ADMIN, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray donorArray = response.getJSONArray("Donor");
                            arrayLength = donorArray.length();
                            for (int i = 0; i < arrayLength; i++) {
                                JSONObject singleDonor = donorArray.getJSONObject(i);
                                donorArrayList.add(new Donor(singleDonor.getString("Name"), singleDonor.getString("Blood"), singleDonor.getString("Location"),
                                        singleDonor.getString("Contact"), "", singleDonor.getString("LastDonate")));
                            }
                            addNewAdminAdapter = new AddNewAdminAdapter(getActivity(), donorArrayList);
                            donorListView.setAdapter(addNewAdminAdapter);
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


//    @Override
//    public void onClick(View v) {
//
//        String selected = "";
//        SparseBooleanArray checkArray = adminListView.getCheckedItemPositions();
//        int size = checkArray.size();
//        for (int i = 0; i < size; i++) {
//            int position = checkArray.keyAt(i);
//            selected += donorName[position] + "\n";
//        }
//        Toast.makeText(getActivity(), selected, Toast.LENGTH_SHORT).show();
//    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.add_admin_second_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        getActivity().getSupportFragmentManager().popBackStack();
        return super.onOptionsItemSelected(item);
    }
}
