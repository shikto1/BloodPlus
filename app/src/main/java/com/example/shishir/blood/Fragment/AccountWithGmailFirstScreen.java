package com.example.shishir.blood.Fragment;


import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shishir.blood.R;

public class AccountWithGmailFirstScreen extends Fragment implements View.OnClickListener {


    TextInputLayout nameTextInLay, emailTextInLay, passTextInLay, conPassTextInLay;
    EditText nameEt, emailEt, passEt, conPassEt;
    Button createAcBtn;
    String nameStr, emailStr, passStr, conPassStr;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_with_gmail_first_screen, container, false);
        findViewById(view);
        return view;
    }

    private void findViewById(View view) {

        nameTextInLay = (TextInputLayout) view.findViewById(R.id.textINLayName);
        emailTextInLay = (TextInputLayout) view.findViewById(R.id.textINLayEmail);
        passTextInLay = (TextInputLayout) view.findViewById(R.id.textINLayPass);
        conPassTextInLay = (TextInputLayout) view.findViewById(R.id.textINLayConPass);

        nameEt = (EditText) view.findViewById(R.id.nameEtRegisterWithG);
        emailEt = (EditText) view.findViewById(R.id.emailEtRegisterWithG);
        passEt = (EditText) view.findViewById(R.id.passwordEtRegisterWithG);
        conPassEt = (EditText) view.findViewById(R.id.cPassRegWithG);

        createAcBtn = (Button) view.findViewById(R.id.createAcUsingEmailBtn);
        createAcBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        nameStr = nameEt.getText().toString();
        emailStr = emailEt.getText().toString();

        conPassStr = conPassEt.getText().toString();

        if (TextUtils.isEmpty(nameStr)) {
            nameTextInLay.setErrorEnabled(true);
            nameTextInLay.setError("Required !");
        } else {
            nameTextInLay.setErrorEnabled(false);
        }
        if (emailIsValid(emailStr)) {
            passStr = passEt.getText().toString();
            if (passwordIsValid(passStr)) {
                conPassStr = conPassEt.getText().toString();
                if (passStr.equals(conPassStr)) {
                    Toast.makeText(getActivity(), "Password did not match", Toast.LENGTH_SHORT).show();
                } else {
                    createNewAccount(emailStr, passStr);
                }

            }
        }

    }

    private boolean passwordIsValid(String passStr) {
        if (TextUtils.isEmpty(passStr)) {
            passTextInLay.setError("Required Password !");
            return false;
        } else if (passStr.length() < 5) {
            passTextInLay.setError("Need at least 5 character !");
            return false;
        } else {
            passTextInLay.setErrorEnabled(false);
        }
        return true;
    }

    private boolean emailIsValid(String emailStr) {
        if (TextUtils.isEmpty(emailStr)) {
            emailTextInLay.setError("Required !");
            return false;
        } else if (!emailStr.matches("^[a-zA-Z0-9#_~!$&'()*+,;=:.\\\"(),:;<>@\\\\[\\\\]\\\\\\\\]+@[a-zA-Z0-9-]+(\\\\.[a-zA-Z0-9-]+)*$")) {
            emailTextInLay.setError("Email is not valid");
            return false;
        } else {
            emailTextInLay.setErrorEnabled(false);
        }

        return true;
    }

    private void createNewAccount(String email, String password) {

    }
}
