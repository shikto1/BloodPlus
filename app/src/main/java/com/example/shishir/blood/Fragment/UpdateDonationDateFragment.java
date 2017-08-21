package com.example.shishir.blood.Fragment;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.shishir.blood.Database.LocalDatabase;
import com.example.shishir.blood.ExtraClass.Constants;
import com.example.shishir.blood.ExtraClass.MySingleton;
import com.example.shishir.blood.Network;
import com.example.shishir.blood.R;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateDonationDateFragment extends DialogFragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    Button cancelBtn, saveBtn;
    TextView donationDate;
    AutoCompleteTextView hospitalName;
    Calendar calendar = Calendar.getInstance();
    ProgressDialog progressDialog;
    String nameStr, bloodStr, contactStr;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_update_donation_date, null);
        cancelBtn = (Button) view.findViewById(R.id.cancelBtnToUpdateDD);
        saveBtn = (Button) view.findViewById(R.id.saveBtnToUpdateDD);
        donationDate = (TextView) view.findViewById(R.id.newDonationDate);
        hospitalName = (AutoCompleteTextView) view.findViewById(R.id.autoTXTViewAtUpdatedD);
        progressDialog = new ProgressDialog(getActivity());
        donationDate.setOnClickListener(this);

        ///Receiving Details of the donor whose donation date is updating............................................
        Bundle b = getArguments();
        nameStr = b.getString("name");
        bloodStr = b.getString("blood");
        contactStr = b.getString("contact");

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String donationDateStr = donationDate.getText().toString();
                String hospitalStr = hospitalName.getText().toString();
                if (donationDateStr.length() < 3) {
                    ToastMessage("Enter donation date");
                } else if (hospitalStr.length() == 0) {
                    ToastMessage("Enter hospital name");
                } else {
                    if (Network.isNetAvailable(getActivity())) {
                        updateDDateAtDonorTable(donationDateStr);
                        insertInfoAtActivitiesTable(donationDateStr,hospitalStr);
                        getDialog().hide();
                    } else {
                        Network.showInternetAlertDialog(getActivity());
                    }
                }

            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false).setView(view);
        Dialog dialog = builder.create();
        dialog.setTitle("Update Donation Date");
        //  dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        return dialog;
    }

    private void insertInfoAtActivitiesTable(final String donationDate, final String hospital) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_INSERT_INFO_FROM_ACTIVITY_TABLE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("name", nameStr);
                map.put("blood", bloodStr);
                map.put("contact", contactStr);
                map.put("donationDate", donationDate);
                map.put("hospital", hospital);
                return map;
            }
        };

        MySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }

    @Override
    public void onClick(View v) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.DAY_OF_MONTH);
        datePickerDialog.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        donationDate.setText(String.format("%02d", dayOfMonth) + "/" + String.format("%02d", (month + 1)) + "/" + year);

    }

    public void updateDDateAtDonorTable(final String donationDate) {
        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Updating donation date...");
        pDialog.setCancelable(false);
        pDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, Constants.URL_UPDATE_DONATION_DATE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pDialog.dismiss();
                Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
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
                map.put("lastDonate", donationDate);
               // map.put("name", nameStr);
                //map.put("blood", bloodStr);
                map.put("contact", contactStr);
                return map;
            }
        };
        MySingleton.getInstance(getActivity()).addToRequestQueue(request);
    }

    @Override
    public void onStart() {
        hospitalName.setAdapter(ArrayAdapter.createFromResource(getActivity(), R.array.hospitalAray, android.R.layout.simple_list_item_1));

        hospitalName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InputMethodManager inputMethodManager= (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(hospitalName.getWindowToken(), 0);
            }
        });
        super.onStart();
    }

    public void ToastMessage(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
}
