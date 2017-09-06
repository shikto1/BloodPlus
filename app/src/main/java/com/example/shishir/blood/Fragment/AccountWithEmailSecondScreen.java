package com.example.shishir.blood.Fragment;


import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shishir.blood.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class AccountWithEmailSecondScreen extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    EditText contactEt;
    TextView dateOfBirthTv, lastDonationDTv, bloodTv;
    AutoCompleteTextView locality, subLocality;
    Spinner bloodGSpinner;
    Button completeBtn;
    Calendar calendar = Calendar.getInstance();
    int dateFlag = 0;
    InputMethodManager inputMethodManager;
    String nameStr, emailStr, passStr;
    String contactStr, bloodGroupStr, localityStr, subLocalityStr, dateOfBirthStr, lastDonationDateStr, regDateStr;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_with_email_second_screen, container, false);
        Bundle b = getArguments();
        nameStr = b.getString("name");
        emailStr = b.getString("email");
        passStr = b.getString("pass");

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
        locality = (AutoCompleteTextView) view.findViewById(R.id.localityAtRegisterLogin);
        subLocality = (AutoCompleteTextView) view.findViewById(R.id.subLocalityAtRegisterLogin);
        inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        locality.setThreshold(2);

        bloodGSpinner.setAdapter(ArrayAdapter.createFromResource(getActivity(), R.array.bloodGroup, android.R.layout.simple_list_item_1));
        locality.setAdapter(ArrayAdapter.createFromResource(getActivity(), R.array.locationArray, android.R.layout.simple_list_item_1));


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
                saveInfoToDatabase();
                break;
            }
        }
    }

    private void saveInfoToDatabase() {

        contactStr = contactEt.getText().toString();
        localityStr = locality.getText().toString();
        subLocalityStr = subLocality.getText().toString();
        dateOfBirthStr = dateOfBirthTv.getText().toString();

        if (contactStr.length() == 0) {
            ToastMessage("Enter Your contact number");

        } else if (contactStr.length() < 11) {
            ToastMessage("Invalid Contact Number");

        } else if (contactStr.charAt(0) != '0' && contactStr.charAt(1) != 1) {
            ToastMessage("Invalid Contact Number");

        } else if (bloodGroupStr == null) {
            ToastMessage("Choose Your blood Group");

        } else if (localityStr.length() == 0) {
            ToastMessage("Select your location");

        } else if (subLocalityStr.length() == 0) {
            ToastMessage("Enter your sublocality");

        } else if (dateOfBirthStr.isEmpty()) {
            ToastMessage("Enter Your Date of birth");

        } else {
            lastDonationDateStr = lastDonationDTv.getText().toString();
            regDateStr = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
            registerUser();
        }
    }

    @Override
    public void onResume() {
        ToastMessage(nameStr + "\n" + emailStr + "\n" + passStr);
        super.onResume();
    }

    private void registerUser() {

        ToastMessage(contactStr + "\n" + bloodGroupStr + "\n" + localityStr + "\n" + subLocalityStr + "\n" + dateOfBirthStr + "\n" + lastDonationDateStr);
    }

    private void ToastMessage(String s) {
        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        if (dateFlag == 1)
            dateOfBirthTv.setText(String.format("%02d", dayOfMonth) + "/" + String.format("%02d", (month + 1)) + "/" + year);
        else
            lastDonationDTv.setText(String.format("%02d", dayOfMonth) + "/" + String.format("%02d", (month + 1)) + "/" + year);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        bloodGSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                inputMethodManager.hideSoftInputFromWindow(bloodGSpinner.getWindowToken(), 0);
                bloodGroupStr = parent.getItemAtPosition(position).toString();
                bloodTv.setText(bloodGroupStr);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        locality.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                inputMethodManager.hideSoftInputFromWindow(locality.getWindowToken(), 0);
            }
        });
        super.onActivityCreated(savedInstanceState);
    }

}
