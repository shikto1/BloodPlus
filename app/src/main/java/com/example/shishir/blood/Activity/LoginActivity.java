package com.example.shishir.blood.Activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    EditText phoneEt;
    CheckBox loggedIn;
    TextView birthDate, registerHereBtn;
    Button loginBtn;
    boolean loggedInCheckBox = false;
    LocalDatabase localDatabase;
    Calendar calendar = Calendar.getInstance();
    String contactStr, birthStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById();
    }

    private void findViewById() {

        phoneEt = (EditText) findViewById(R.id.phoneNumberEtAtLoginPage);
        birthDate = (TextView) findViewById(R.id.birthDateAtLoginPage);
        loggedIn = (CheckBox) findViewById(R.id.loggedInCheckBoxAtLoginPage);

        loggedIn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                loggedInCheckBox = isChecked;
            }
        });

        loginBtn = (Button) findViewById(R.id.loginButtonAtLoginPage);
        registerHereBtn = (TextView) findViewById(R.id.registerHereButtonAtLoginPage);
        localDatabase = new LocalDatabase(this);


        loginBtn.setOnClickListener(this);
        birthDate.setOnClickListener(this);
        registerHereBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        switch (id) {
            case R.id.birthDateAtLoginPage: {

                DatePickerDialog datePickerDialog = new DatePickerDialog(this, this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.DAY_OF_MONTH);
                datePickerDialog.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
                datePickerDialog.show();
                break;
            }
            case R.id.loginButtonAtLoginPage: {
                if (Network.isNetAvailable(LoginActivity.this)) {
                    contactStr = phoneEt.getText().toString();
                    birthStr = birthDate.getText().toString();
                    if (contactStr.length() == 0) {
                        ToastMessage("Enter Your contact number");
                    } else if (contactStr.length() < 11) {
                        ToastMessage("Invalid Contact Number");
                    } else if (contactStr.charAt(0) != '0' && contactStr.charAt(1) != 1) {
                        ToastMessage("Invalid Contact Number");
                    } else if (birthStr.isEmpty()) {
                        ToastMessage("Enter Your Date of birth");
                    } else {
                        localDatabase.setLoggedIn(loggedInCheckBox);
                        login();
                        startActivity(new Intent(this, NavigationActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    }
                } else {
                    Network.showInternetAlertDialog(LoginActivity.this);
                    break;
                }


            }
            case R.id.registerHereButtonAtLoginPage: {
                startActivity(new Intent(this, RegisterActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            }
        }

    }

    private void login() {
        final ProgressDialog pD=new ProgressDialog(this);
        pD.setMessage("Logging in...");
        pD.setCancelable(false);
        pD.show();

        StringRequest request = new StringRequest(Request.Method.POST, Constants.URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pD.dismiss();
                if(!response.equals("fail")){
                    try {
                        JSONObject jsonOb=new JSONObject(response);
                        String name=jsonOb.getString("Name");
                        String blood=jsonOb.getString("Blood");
                        String admin=jsonOb.getString("Admin");

                        if(admin.equals("1")){
                            new LocalDatabase(LoginActivity.this).setAdmin(1);
                        }
                        startActivity(new Intent(LoginActivity.this,NavigationActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pD.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("birthDate", birthStr);
                map.put("contact", contactStr);
                return map;
            }
        };
        MySingleton.getInstance(this).addToRequestQueue(request);
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        birthDate.setText(String.format("%02d", dayOfMonth) + "/" + String.format("%02d", (month + 1)) + "/" + year);
    }

    private void ToastMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}

