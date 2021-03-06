package com.example.shishir.blood.Activity;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
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
import com.example.shishir.blood.Network;
import com.example.shishir.blood.R;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    EditText fullNameEt, contactNumber, emailEt;
    TextView birthDate, lastDonationDate;
    Spinner bloodGroup;
    TextView asBloodSpinner;
    AutoCompleteTextView location, subLocality;
    String bloodGroupStr, locationStr;
    Button submitBtn;
    Calendar calendar;
    int dateFlag;
    InputMethodManager inputMethodManager;
    DonorTableManager donorTableManager;
    boolean fTime;
    ProgressDialog progressDialog;
    String nameStr, contactStr, birthStr, donationDateStr, regSTR, emailStr, subLocalityStr;
    boolean regSuccess = false;
    String intentStr = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fTime = true;

        intentStr = getIntent().getStringExtra("rr");
        findViewById();

    }

    private void findViewById() {
        fullNameEt = (EditText) findViewById(R.id.fullNameEtAtBasicInfo);
        contactNumber = (EditText) findViewById(R.id.contactNumberEt);
        emailEt = (EditText) findViewById(R.id.emailEt);

        birthDate = (TextView) findViewById(R.id.dateOfBirthAtBasicInfo);
        lastDonationDate = (TextView) findViewById(R.id.lastDonationDateAtBasicInfo);
        asBloodSpinner = (TextView) findViewById(R.id.asBloodSpinnerInBasicInfo);


        bloodGroup = (Spinner) findViewById(R.id.bloodS);
        location = (AutoCompleteTextView) findViewById(R.id.autoCompleTextViewForLocationInBasicInfo);
        subLocality = (AutoCompleteTextView) findViewById(R.id.subLocality);

        location.setThreshold(2);
        //    submitBtn = (Button) findViewById(R.id.submitBtnAtBasicInfo);
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

//        submitBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                submit();
//
//            }
//
//        });

    }

    private void submit() {
        if (Network.isNetAvailable(RegisterActivity.this)) {
            nameStr = fullNameEt.getText().toString();
            birthStr = birthDate.getText().toString();
            contactStr = contactNumber.getText().toString();
            locationStr = location.getText().toString();
            emailStr = emailEt.getText().toString();
            subLocalityStr = subLocality.getText().toString();

            if (nameStr.length() == 0) {
                ToastMessage("Enter your name please");

            } else if (bloodGroupStr == null) {
                ToastMessage("Choose Your blood Group");

            } else if (locationStr.length() == 0) {
                ToastMessage("Select your location");

            } else if (subLocalityStr.length() == 0) {
                ToastMessage("Enter your sublocality");

            } else if (birthStr.isEmpty()) {
                ToastMessage("Enter Your Date of birth");

            } else if (contactStr.length() == 0) {
                ToastMessage("Enter Your contact number");

            } else if (contactStr.length() < 11) {
                ToastMessage("Invalid Contact Number");

            } else if (contactStr.charAt(0) != '0' && contactStr.charAt(1) != 1) {
                ToastMessage("Invalid Contact Number");

            } else if (emailStr.length() == 0) {
                ToastMessage("Enter your email address");

            } else if (emailStr.length() > 0 && !emailStr.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
                ToastMessage("Email is not valid");

            } else {
                donationDateStr = lastDonationDate.getText().toString();
                regSTR = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
                registerUser();
            }


        } else {
            Network.showInternetAlertDialog(RegisterActivity.this);
        }
    }

    private void registerUser() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_REGISTER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                if (response.equals("success")) {
                    regSuccess = true;
                    alertDialog("Welcome !", "Successfully Registered");
                } else if (response.equals("exists")) {
                    alertDialog("Sorry !", "This user already exists");
                } else
                    alertDialog("Sorry !", "Registration Failed, Try again");
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
                map.put("gender", "");
                map.put("name", nameStr);
                map.put("blood", bloodGroupStr);
                map.put("location", locationStr);
                map.put("birthDate", birthStr);
                map.put("contact", contactStr);
                map.put("donationDate", donationDateStr);
                map.put("registrationDate", regSTR);
                map.put("sLocality", subLocalityStr);
                map.put("email", emailStr);
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
            birthDate.setText(String.format("%02d", dayOfMonth) + "/" + String.format("%02d", (month + 1)) + "/" + year);
        else
            lastDonationDate.setText(String.format("%02d", dayOfMonth) + "/" + String.format("%02d", (month + 1)) + "/" + year);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.dateOfBirthAtBasicInfo) {
            inputMethodManager.hideSoftInputFromWindow(location.getWindowToken(), 0);
            dateFlag = 1;
        } else
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
                inputMethodManager.hideSoftInputFromWindow(location.getWindowToken(), 0);
                bloodGroupStr = parent.getItemAtPosition(position).toString();
                asBloodSpinner.setText(bloodGroupStr);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        location.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //locationStr = parent.getItemAtPosition(position).toString();
                inputMethodManager.hideSoftInputFromWindow(location.getWindowToken(), 0);
            }
        });
        super.onStart();
    }

    public void alertDialog(String title, String msg) {
        new AlertDialog.Builder(this).setTitle(title).setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (regSuccess) {
                            if (intentStr.equals("reg")) {
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

                            }
                            if (intentStr.equals("fab")) {
                                finish();
                                startActivity(getIntent());
                            }
                        }

                    }
                }).show();
    }

    @Override
    protected void onResume() {
        boolean netAvailable = Network.isNetAvailable(this);
        if (!netAvailable) {
            Network.showInternetAlertDialog(this);
        }
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_admin_second_fragment, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        submit();
        return super.onOptionsItemSelected(item);
    }
}

