package com.example.shishir.blood.Fragment;


import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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

import com.example.shishir.blood.Database.DonorTableManager;
import com.example.shishir.blood.Donor;
import com.example.shishir.blood.R;

import java.util.Calendar;

import fr.ganfra.materialspinner.MaterialSpinner;

public class BasicInfoFragment extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basic_info, container, false);
        fTime = true;
        findViewById(view);

        genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.maleRadioBtn)
                    gender = "Male";
                if (checkedId == R.id.femaleRadioBtn)
                    gender = "Female";
            }
        });

        return view;
    }

    private void findViewById(View view) {
        fullNameEt = (EditText) view.findViewById(R.id.fullNameEtAtBasicInfo);
        contactNumber = (EditText) view.findViewById(R.id.contactNumberEt);

        genderGroup = (RadioGroup) view.findViewById(R.id.genderGroupAtBasicInfo);

        birthDate = (TextView) view.findViewById(R.id.dateOfBirthAtBasicInfo);
        lastDonationDate = (TextView) view.findViewById(R.id.lastDonationDateAtBasicInfo);
        asBloodSpinner = (TextView) view.findViewById(R.id.asBloodSpinnerInBasicInfo);


        bloodGroup = (Spinner) view.findViewById(R.id.bloodS);
        location = (AutoCompleteTextView) view.findViewById(R.id.autoCompleTextViewForLocationInBasicInfo);
        location.setThreshold(2);
        submitBtn = (Button) view.findViewById(R.id.submitBtnAtBasicInfo);
        calendar = Calendar.getInstance();
        donorTableManager = new DonorTableManager(getActivity());
        inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        bloodGroup.setAdapter(ArrayAdapter.createFromResource(getActivity(), R.array.bloodGroup, android.R.layout.simple_list_item_1));
        location.setAdapter(ArrayAdapter.createFromResource(getActivity(), R.array.locationArray, android.R.layout.simple_list_item_1));

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
                String nameStr = fullNameEt.getText().toString();
                String contactNStr = contactNumber.getText().toString();
                String birthStr = birthDate.getText().toString();
                String donationDateStr = lastDonationDate.getText().toString();

                Donor donor = new Donor(nameStr, gender, bloodGroupStr, locationStr, birthStr, contactNStr, donationDateStr);
                if (donorTableManager.addDonor(donor))
                    Toast.makeText(getActivity(), "Successful", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getActivity(), "Somethig Wrong !", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.dateOfBirthAtBasicInfo)
            dateFlag = 1;
        else
            dateFlag = 2;
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.DAY_OF_MONTH);
        datePickerDialog.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        if (dateFlag == 1)
            birthDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
        else
            lastDonationDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
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
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        inputMethodManager.hideSoftInputFromWindow(fullNameEt.getWindowToken(), 0);
        super.onResume();
    }
}
