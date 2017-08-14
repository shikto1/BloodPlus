package com.example.shishir.blood.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.shishir.blood.Adapter.DonorAdapter;
import com.example.shishir.blood.Database.DonorTableManager;
import com.example.shishir.blood.Database.LocalDatabase;
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
    private int arrayLength;
    TextView totalDonorTV;
    LocalDatabase localDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_donor);


        ((Spinner) findViewById(R.id.bloodgroupSpinnerAtToolbar)).setAdapter(ArrayAdapter.createFromResource(this, R.array.bloodGroup,
                android.R.layout.simple_list_item_1));

        donorListView = (ListView) findViewById(R.id.donorListView);
        totalDonorTV = (TextView) findViewById(R.id.totalDonor);
        localDatabase = new LocalDatabase(this);
        if (localDatabase.getAdmin()) {
            registerForContextMenu(donorListView);
        }
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
                            arrayLength = donorArray.length();
                            for (int i = 0; i < arrayLength; i++) {
                                JSONObject singleDonor = donorArray.getJSONObject(i);
                                String donorName = singleDonor.getString("Name");
                                String contact = singleDonor.getString("Contact");
                                String lastDonate = singleDonor.getString("LastDonate");
                                donorArrayList.add(new Donor(donorName, contact, lastDonate));
                            }
                            donorListView.setAdapter(new DonorAdapter(AllDonorActivity.this, donorArrayList));
                            totalDonorTV.setText("Total (" + arrayLength + ")");
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


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo) menuInfo;

//        ViewGroup vg = (ViewGroup) v;
//        View children = vg.getChildAt(info.position);
//        TextView child = (TextView) children.findViewById(R.id.name);
//
//        name = child.getText().toString();
//        menu.setHeaderTitle(name);

        getMenuInflater().inflate(R.menu.context_menu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.detail: {
                ToastMessage("Details");
                //             localDatabase.addCurrentProfile(name);
//                startActivity(new Intent(NavigationDrawerActivity.this, ProfileDetailsActivity.class)
//                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            }
            case R.id.edit:
                ToastMessage("Edit");
                break;
            case R.id.delete: {
                new AlertDialog.Builder(this).setMessage("Are You Sure to Delete it ??")
                        .setCancelable(false)
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                // new MyDeleteHelperThread().execute(name);
                            }
                        })
                        .show();
                break;
            }

        }
        return super.onContextItemSelected(item);
    }

    private void ToastMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
