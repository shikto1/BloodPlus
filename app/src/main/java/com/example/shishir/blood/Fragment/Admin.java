package com.example.shishir.blood.Fragment;


import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.shishir.blood.Adapter.AdminAdapter;
import com.example.shishir.blood.Donor;
import com.example.shishir.blood.ExtraClass.Constants;
import com.example.shishir.blood.ExtraClass.MySingleton;
import com.example.shishir.blood.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Admin extends Fragment {
    private ListView adminListView;
    ProgressDialog progressDialog;
    ArrayList<Donor> adminArrayList;
    ActionBar actionBar;
    BroadcastReceiver popUp_Menu_Click_Receiver;
    boolean popUp_Menu_Receiver_Register = false;
    AdminAdapter adminAdapter;


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
                    adminAdapter = new AdminAdapter(getActivity(), adminArrayList);
                    adminListView.setAdapter(adminAdapter);


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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        popUp_Menu_Click_Receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle bundle = intent.getBundleExtra("event");
                final int itemID = bundle.getInt("itemID");
                final int pos = bundle.getInt("position");
                switch (itemID) {
                    case R.id.removeAsAdmin: {
                        new AlertDialog.Builder(getActivity()).setTitle("Remove as Admin ?")
                                .setMessage("Once you remove, the user will not be able to edit any info. ")
                                .setCancelable(false)
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                removeAsAdmin(pos);

                            }
                        }).show();

                        break;
                    }
                    case R.id.removeFromBloodPlus: {
                        new AlertDialog.Builder(getActivity()).setTitle("Remove from BLOOD+ ?")
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
                }
            }
        };
        super.onActivityCreated(savedInstanceState);
    }

    public void removeAsAdmin(final int position) {
        progressDialog.setMessage("Removing as Admin...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, Constants.URL_REMOVE_AS_ADMIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                adminArrayList.remove(position);
                adminAdapter.notifyDataSetChanged();
                actionBar.setTitle("Admin (" + adminArrayList.size() + ") ");
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("name", adminArrayList.get(position).getDonorName());
                map.put("blood", adminArrayList.get(position).getBloodGroup());
                map.put("contact", adminArrayList.get(position).getContactNumber());
                return map;
            }
        };
        MySingleton.getInstance(getActivity()).addToRequestQueue(request);

    }

    public void removeFromBloodPlus(final int position) {
        progressDialog.setMessage("Removing from Blood+...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, Constants.URL_REMOVE_FROM_BLOOD_PLUS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                adminArrayList.remove(position);
                adminAdapter.notifyDataSetChanged();
                actionBar.setTitle("Admin (" + adminArrayList.size() + ") ");
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("name", adminArrayList.get(position).getDonorName());
                map.put("blood", adminArrayList.get(position).getBloodGroup());
                map.put("contact", adminArrayList.get(position).getContactNumber());
                return map;
            }
        };
        MySingleton.getInstance(getActivity()).addToRequestQueue(request);

    }

    @Override
    public void onStart() {
        if (!popUp_Menu_Receiver_Register) {
            getActivity().registerReceiver(popUp_Menu_Click_Receiver, new IntentFilter(Constants.POPUP_MENU_CLICKED_ACTION));
            popUp_Menu_Receiver_Register = true;
        }
        super.onStart();
    }

    @Override
    public void onStop() {
        if (popUp_Menu_Receiver_Register) {
            getActivity().unregisterReceiver(popUp_Menu_Click_Receiver);
            popUp_Menu_Receiver_Register = false;
        }
        super.onStop();
    }
}
