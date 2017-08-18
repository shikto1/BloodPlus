package com.example.shishir.blood.Activity;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shishir.blood.Adapter.AllDonorAdapter;
import com.example.shishir.blood.Adapter.DonorAdapter;
import com.example.shishir.blood.Database.DonorTableManager;
import com.example.shishir.blood.Database.LocalDatabase;
import com.example.shishir.blood.Donor;
import com.example.shishir.blood.ExtraClass.Constants;
import com.example.shishir.blood.ExtraClass.MySingleton;
import com.example.shishir.blood.Fragment.UpdateDonationDateFragment;
import com.example.shishir.blood.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AllDonorActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    ListView donorListView;
    ProgressDialog pDialog;
    ArrayList<Donor> donorArrayList;
    private int arrayLength;
    //   TextView totalDonorTV;
    LocalDatabase localDatabase;
    private AllDonorAdapter allDonorAdapter;
    BroadcastReceiver popUp_Menu_Click_Receiver;
    boolean popUp_Menu_Receiver_Register = false;
    ActionBar actionBar;
    private SearchView searchView;
    //  Toolbar toolbar;
    // Spinner bloodSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_donor);
        donorListView = (ListView) findViewById(R.id.donorListView);
        searchView = (SearchView) findViewById(R.id.donorSearchView);
        donorListView.setTextFilterEnabled(true);
        localDatabase = new LocalDatabase(this);
        pDialog = new ProgressDialog(this);
        donorArrayList = new ArrayList<Donor>();
        actionBar = getSupportActionBar();
        setUpReceiver();
        useVolley();

        //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // getSupportActionBar().setHomeButtonEnabled(true);

//        toolbar = (Toolbar) findViewById(R.id.toolbarAtAllDonor);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        bloodSpinner = ((Spinner) findViewById(R.id.bloodgroupSpinnerAtToolbar));
//        bloodSpinner.setAdapter(ArrayAdapter.createFromResource(this, R.array.bloodGroup,
//                android.R.layout.simple_list_item_1));


//        totalDonorTV = (TextView) findViewById(R.id.totalDonor);

        //registerForContextMenu(donorListView);
    }

    private void setUpReceiver() {
        popUp_Menu_Click_Receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle bundle = intent.getBundleExtra("event");
                final int itemID = bundle.getInt("itemID");
                final int pos = bundle.getInt("position");
                switch (itemID) {
                    case R.id.updateDonationDate: {
                        Bundle b = new Bundle();
                        b.putString("name", donorArrayList.get(pos).getDonorName());
                        b.putString("blood", donorArrayList.get(pos).getBloodGroup());
                        b.putString("contact", donorArrayList.get(pos).getContactNumber());
                        UpdateDonationDateFragment fragment = new UpdateDonationDateFragment();
                        fragment.setArguments(b);
                        fragment.show(getSupportFragmentManager(), "SS");
                        break;
                    }
                    case R.id.removeFromBlood: {
                        new AlertDialog.Builder(AllDonorActivity.this).setTitle("Remove from BLOOD+ ?")
                                .setMessage("Once you remove, the user will be permanently deleted from database and it can not be undone.")
                                .setCancelable(false)
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                removeFromBloodPlus(pos);
                            }
                        }).show();

                        break;
                    }
                    case R.id.detailsAtALlDonor: {
                        ToastMessage("Here I will show details");
                        break;
                    }
                }
            }
        };

    }

    private void useVolley() {

        pDialog.setMessage("Fetching data...");
        pDialog.setCancelable(false);
        pDialog.show();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                Constants.URL_GET_ALL_DONOR, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray donorArray = response.getJSONArray("Donor");
                            arrayLength = donorArray.length();
                            actionBar.setTitle("Total (" + arrayLength + ")");
                            for (int i = 0; i < arrayLength; i++) {
                                JSONObject singleDonor = donorArray.getJSONObject(i);
                                donorArrayList.add(new Donor(singleDonor.getString("Name"), singleDonor.getString("Blood"), singleDonor.getString("Location"),
                                        singleDonor.getString("Contact"), "", singleDonor.getString("LastDonate")));
                            }
                            allDonorAdapter = new AllDonorAdapter(AllDonorActivity.this, donorArrayList);
                            donorListView.setAdapter(allDonorAdapter);
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


    private void ToastMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }



    public void removeFromBloodPlus(final int position) {
        pDialog.setMessage("Removing from Blood+...");
        pDialog.setCancelable(false);
        pDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, Constants.URL_REMOVE_FROM_BLOOD_PLUS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(AllDonorActivity.this, response, Toast.LENGTH_SHORT).show();
                donorArrayList.remove(position);
                allDonorAdapter.notifyDataSetChanged();
                actionBar.setTitle("Total (" + donorArrayList.size() + ") ");
                pDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("name", donorArrayList.get(position).getDonorName());
                map.put("blood", donorArrayList.get(position).getBloodGroup());
                map.put("contact", donorArrayList.get(position).getContactNumber());
                return map;
            }
        };
        MySingleton.getInstance(this).addToRequestQueue(request);

    }


    @Override
    protected void onStart() {
        setUpSearchView();
        if (!popUp_Menu_Receiver_Register) {
            registerReceiver(popUp_Menu_Click_Receiver, new IntentFilter(Constants.POPUP_MENU_CLICKED_ACTION));
            popUp_Menu_Receiver_Register = true;
        }
        super.onStart();
    }


    @Override
    protected void onStop() {
        if (popUp_Menu_Receiver_Register) {
            unregisterReceiver(popUp_Menu_Click_Receiver);
            popUp_Menu_Receiver_Register = false;
        }
        super.onStop();
    }

    private void setUpSearchView() {
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("Search Here");
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) {
            donorListView.clearTextFilter();
        } else {
            donorListView.setFilterText(newText.toLowerCase());
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_for_refresh, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if (id == android.R.id.home) {
            this.finish();
            return true;
        }
        if(id==R.id.refreshDonorList){
            refreshDonorInfo();
        }
        return super.onOptionsItemSelected(item);
    }
    private void refreshDonorInfo() {
        donorArrayList.clear();
        final ProgressDialog pd=new ProgressDialog(this);
        pd.setMessage("Refreshing...");
        pd.setCancelable(false);
        pd.show();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                Constants.URL_GET_ALL_DONOR, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray donorArray = response.getJSONArray("Donor");
                            arrayLength = donorArray.length();
                            actionBar.setTitle("Total (" + arrayLength + ")");
                            for (int i = 0; i < arrayLength; i++) {
                                JSONObject singleDonor = donorArray.getJSONObject(i);
                                donorArrayList.add(new Donor(singleDonor.getString("Name"), singleDonor.getString("Blood"), singleDonor.getString("Location"),
                                        singleDonor.getString("Contact"), "", singleDonor.getString("LastDonate")));
                            }
                            allDonorAdapter = new AllDonorAdapter(AllDonorActivity.this, donorArrayList);
                            donorListView.setAdapter(allDonorAdapter);
                            pd.hide();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        pDialog.hide();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                pd.hide();
            }
        });

// Adding request to request queue
        MySingleton.getInstance(this).addToRequestQueue(jsonObjReq);
    }


}
