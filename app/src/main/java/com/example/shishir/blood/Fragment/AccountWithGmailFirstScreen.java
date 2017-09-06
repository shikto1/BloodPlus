package com.example.shishir.blood.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shishir.blood.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AccountWithGmailFirstScreen extends Fragment implements View.OnClickListener {


    EditText nameEt, emailEt, passEt, conPassEt;
    Button createAcBtn;
    String nameStr, emailStr, passStr, conPassStr;
    FragmentManager fragmentManager;

    FirebaseAuth auth;
    DatabaseReference databaseReference;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_with_gmail_first_screen, container, false);
        auth = FirebaseAuth.getInstance();
        findViewById(view);
        return view;
    }

    private void findViewById(View view) {

        nameEt = (EditText) view.findViewById(R.id.nameEtRegisterWithG);
        emailEt = (EditText) view.findViewById(R.id.emailEtRegisterWithG);
        passEt = (EditText) view.findViewById(R.id.passwordEtRegisterWithG);
        conPassEt = (EditText) view.findViewById(R.id.cPassRegWithG);
        fragmentManager = getActivity().getSupportFragmentManager();

        createAcBtn = (Button) view.findViewById(R.id.createAcUsingEmailBtn);
        createAcBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        nameStr = nameEt.getText().toString();
        emailStr = emailEt.getText().toString();
        passStr = passEt.getText().toString();
        conPassStr = conPassEt.getText().toString();

        if (nameStr.length() == 0) {
            ToastMessage("Enter your name please !");
        } else if (emailStr.length() == 0) {
            ToastMessage("Enter your email");
        } else if (!emailStr.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
            ToastMessage("Enter a valid email");
        } else if (passStr.length() == 0) {
            ToastMessage("Enter Password !");
        } else if (!passStr.equals(conPassStr)) {
            ToastMessage("Password did not match");
        } else {
            Bundle bundle = new Bundle();
            bundle.putString("name", nameStr);
            bundle.putString("email", emailStr);
            bundle.putString("pass", passStr);
            AccountWithEmailSecondScreen secondF = new AccountWithEmailSecondScreen();
            secondF.setArguments(bundle);
            fragmentManager.beginTransaction().replace(R.id.parentLayoutRegisterLogin, secondF)
                    .commit();
            // registerUser(emailStr, passStr);

        }
    }


    private void registerUser(String email, String password) {

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    ToastMessage("Authentication Failed !");
                } else {
                    saveUserToFireBaseDatabase(task.getResult().getUser());
                }

            }
        });
    }

    private void saveUserToFireBaseDatabase(FirebaseUser user) {

        String uiID = user.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Donors").child(uiID);

        //For Single Value Input.............
        // databaseReference.child('name').setValue(name).addOnCompletionListener.............................

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("name", nameStr);
        //    map.put("blood", bloodStr);
        databaseReference.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    ToastMessage("Successfully Registered !");
                    //   startActivity(new Intent(CreateNewAccouontActivity.this, AfterLoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    // finish();
                }
            }
        });

    }

    private void ToastMessage(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
}
