package com.example.shishir.blood.Fragment;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shishir.blood.Database.LocalDatabase;
import com.example.shishir.blood.R;


public class PasswordRecoverFragment extends DialogFragment {
    Button cancel, submit;
    EditText numberEt;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_password_recover, null);
        cancel = (Button) view.findViewById(R.id.cancelBtninDialog);
        submit = (Button) view.findViewById(R.id.submitButton);
        numberEt = (EditText) view.findViewById(R.id.recoveryNumber);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String recoveryNumber = numberEt.getText().toString();
                LocalDatabase ld = new LocalDatabase(getActivity());
                if (recoveryNumber.isEmpty()) {
                    Toast.makeText(getActivity(), "You did not entered any number", Toast.LENGTH_SHORT).show();
                } else if (ld.isPhoneNumberMatched(recoveryNumber)) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(recoveryNumber, null, "Your Password: "+ld.getPassword(), null, null);
                    Toast.makeText(getActivity(), "Please wait to get your Password", Toast.LENGTH_SHORT).show();
                }
                //   Toast.makeText(getActivity(), "PhoneNumber Matched", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getActivity(), "Your Number is Incorrect", Toast.LENGTH_SHORT).show();

                dismiss();
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false).setView(view);
        Dialog dialog = builder.create();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

}

