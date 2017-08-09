package com.example.shishir.blood.Activity;

import android.app.DatePickerDialog;
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

import com.example.shishir.blood.Database.LocalDatabase;
import com.example.shishir.blood.R;

import java.util.Calendar;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    EditText phoneEt;
    CheckBox loggedIn;
    TextView birthDate, registerHereBtn;
    Button loginBtn;
    boolean loggedInCheckBox = false;
    LocalDatabase localDatabase;
    Calendar calendar = Calendar.getInstance();

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
                String contactStr = phoneEt.getText().toString();
//                if (contactStr.isEmpty() || contactStr.length() == 0) {
//                    ToastMessage("Enter Contact Number");
//                    break;
//                }
                //            if (contactStr.length() > 0 && contactStr.length() < 11) {
//                    ToastMessage("Contact Number Incorrect !");
//                    break;
//                }
                String birthStr = birthDate.getText().toString();
//                if (birthStr.length() == 0 || birthStr.isEmpty()) {
//                    ToastMessage("Enter BirthDate !");
//                    break;
//                }
                ToastMessage(contactStr + " \n" + birthStr + "\n" + loggedInCheckBox);
                localDatabase.setLoggedIn(loggedInCheckBox);
                startActivity(new Intent(this, NavigationActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                if (loggedInCheckBox)
                    finish();

                // Here i have to check weather the user is a admin or not.......................................
                break;
            }
            case R.id.registerHereButtonAtLoginPage: {
                startActivity(new Intent(this, RegisterActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            }
        }

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        birthDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
    }

    private void ToastMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}

