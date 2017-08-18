package com.example.shishir.blood.Fragment;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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
                if (donationDateStr.length() < 3) {
                    Toast.makeText(getActivity(), "Enter Donation Date ", Toast.LENGTH_SHORT).show();
                } else {
                    updateDonationDate(donationDateStr);
                    dismiss();
                }

            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false).setView(view);
        Dialog dialog = builder.create();
        dialog.setTitle("Enter Donation Date");
      //  dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        return dialog;
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

    public void updateDonationDate(final String donationDate) {
        progressDialog.setMessage("Updating donation date...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, Constants.URL_UPDATE_DONATION_DATE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
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
                map.put("lastDonate", donationDate);
                map.put("name", nameStr);
                map.put("blood", bloodStr);
                map.put("contact", contactStr);
                return map;
            }
        };
        MySingleton.getInstance(getActivity()).addToRequestQueue(request);
    }
}
