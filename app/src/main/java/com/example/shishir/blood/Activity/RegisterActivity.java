package com.example.shishir.blood.Activity;


import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.shishir.blood.Database.DonorTableManager;
import com.example.shishir.blood.Donor;
import com.example.shishir.blood.ExtraClass.Constants;
import com.example.shishir.blood.ExtraClass.MySingleton;
import com.example.shishir.blood.R;

import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    EditText fullNameEt, contactNumber;
    RadioGroup genderGroup;
    TextView birthDate, lastDonationDate;
    Spinner bloodGroup;
    TextView asBloodSpinner;
    AutoCompleteTextView location;
    String gender, bloodGroupStr, locationStr;
    Button submitBtn;
    Calendar calendar;
    int dateFlag;
    InputMethodManager inputMethodManager;
    DonorTableManager donorTableManager;
    boolean fTime;
    String nameStr, contactStr, birthStr, donationDateStr, regSTR;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fTime = true;
        findViewById();

        genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.maleRadioBtn)
                    gender = "Male";
                if (checkedId == R.id.femaleRadioBtn)
                    gender = "Female";
            }
        });

    }

    private void findViewById() {
        fullNameEt = (EditText) findViewById(R.id.fullNameEtAtBasicInfo);
        contactNumber = (EditText) findViewById(R.id.contactNumberEt);

        genderGroup = (RadioGroup) findViewById(R.id.genderGroupAtBasicInfo);

        birthDate = (TextView) findViewById(R.id.dateOfBirthAtBasicInfo);
        lastDonationDate = (TextView) findViewById(R.id.lastDonationDateAtBasicInfo);
        asBloodSpinner = (TextView) findViewById(R.id.asBloodSpinnerInBasicInfo);


        bloodGroup = (Spinner) findViewById(R.id.bloodS);
        location = (AutoCompleteTextView) findViewById(R.id.autoCompleTextViewForLocationInBasicInfo);
        location.setThreshold(2);
        submitBtn = (Button) findViewById(R.id.submitBtnAtBasicInfo);
        calendar = Calendar.getInstance();
        donorTableManager = new DonorTableManager(this);
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        bloodGroup.setAdapter(ArrayAdapter.createFromResource(this, R.array.bloodGroup, android.R.layout.simple_list_item_1));
        location.setAdapter(ArrayAdapter.createFromResource(this, R.array.locationArray, android.R.layout.simple_list_item_1));

        birthDate.setOnClickListener(this);
        lastDonationDate.setOnClickListener(this);
        asBloodSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bloodGroup.performClick();
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameStr = fullNameEt.getText().toString();
                contactStr = contactNumber.getText().toString();
                birthStr = birthDate.getText().toString();
                donationDateStr = lastDonationDate.getText().toString();

                registerUser();

                //   Donor donor = new Donor(nameStr, gender, bloodGroupStr, locationStr, birthStr, contactNStr, donationDateStr);
//                if (donorTableManager.addDonor(donor))
//                    ToastMessage("Successful");
//                else
//                    ToastMessage("Something Wrong !");
            }
        });

    }

    private void registerUser() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_REGISTER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ToastMessage(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("gender", gender);
                map.put("name", nameStr);
                map.put("blood", bloodGroupStr);
                map.put("location", locationStr);
                map.put("birthDate", birthStr);
                map.put("contact", contactStr);
                map.put("donationDate", donationDateStr);
                map.put("registrationDate", regSTR);
                return map;
            }
        };

        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void ToastMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        if (dateFlag == 1)
            birthDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
        else
            lastDonationDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.dateOfBirthAtBasicInfo)
            dateFlag = 1;
        else
            dateFlag = 2;
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.DAY_OF_MONTH);
        datePickerDialog.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
        datePickerDialog.show();

    }

    @Override
    protected void onStart() {
        bloodGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (fTime) {
                    fTime = false;
                } else {
                    bloodGroupStr = parent.getItemAtPosition(position).toString();
                    asBloodSpinner.setText(bloodGroupStr);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        location.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                locationStr = parent.getItemAtPosition(position).toString();
                inputMethodManager.hideSoftInputFromWindow(location.getWindowToken(), 0);
            }
        });
        super.onStart();
    }
}

