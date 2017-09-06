package com.example.shishir.blood.Fragment;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.shishir.blood.R;

import java.util.Calendar;


public class AccountWithEmailSecondScreen extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    EditText contactEt;
    TextView dateOfBirthTv, lastDonationDTv, bloodTv;
    Spinner bloodGSpinner;
    Button completeBtn;
    Calendar calendar = Calendar.getInstance();
    int dateFlag = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_with_email_second_screen, container, false);
        findViewById(view);
        return view;
    }

    private void findViewById(View view) {

        contactEt = (EditText) view.findViewById(R.id.contactAtRegisterLogin);
        dateOfBirthTv = (TextView) view.findViewById(R.id.dateOfBirthAtRegisterLogin);
        lastDonationDTv = (TextView) view.findViewById(R.id.lastDonationDAtRegisterLogin);
        bloodTv = (TextView) view.findViewById(R.id.bloodGAtRegisterLogin);
        bloodGSpinner = (Spinner) view.findViewById(R.id.bloodSpinnerAtRegisterLogin);
        completeBtn = (Button) view.findViewById(R.id.completeRegisterBtn);

        dateOfBirthTv.setOnClickListener(this);
        lastDonationDTv.setOnClickListener(this);
        bloodTv.setOnClickListener(this);
        completeBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.dateOfBirthAtRegisterLogin: {
                dateFlag = 1;
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.DAY_OF_MONTH);
                datePickerDialog.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
                datePickerDialog.show();
                break;
            }
            case R.id.lastDonationDAtRegisterLogin: {
                dateFlag = 2;
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.DAY_OF_MONTH);
                datePickerDialog.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
                datePickerDialog.show();
                break;
            }
            case R.id.bloodGAtRegisterLogin: {
                bloodGSpinner.performClick();
                break;
            }
            case R.id.completeRegisterBtn: {
                break;
            }
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }
}
