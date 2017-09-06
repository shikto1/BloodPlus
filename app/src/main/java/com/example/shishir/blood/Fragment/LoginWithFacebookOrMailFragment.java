package com.example.shishir.blood.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shishir.blood.Activity.NavigationActivity;
import com.example.shishir.blood.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginWithFacebookOrMailFragment extends Fragment implements View.OnClickListener {
    // faceobook Login........
    LoginButton loginWithFacebookBtn;
    CallbackManager callbackManager;


    Button loginWithEmailBtn, registerHBtn, forgotPassBtn;
    EditText emailEt, passEt;
    TextView loginResult;
    FragmentManager fragmentManager;
    ProgressDialog progressDialog;
    FirebaseAuth auth;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_with_facebook_gmail, container, false);
        callbackManager = CallbackManager.Factory.create();
        findViewById(view);
        return view;
    }

    private void findViewById(View view) {
        loginWithFacebookBtn = (LoginButton) view.findViewById(R.id.loginWithFacebookBtn);
        loginWithFacebookBtn.setFragment(this);
        loginWithFacebookBtn.setReadPermissions("email", "public_profile");
        loginWithFacebookBtn.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                handleFacebookAccessToken(loginResult.getAccessToken());

            }

            @Override
            public void onCancel() {
                loginResult.setText("Cancelled");

            }

            @Override
            public void onError(FacebookException error) {

            }
        });


        loginWithEmailBtn = (Button) view.findViewById(R.id.loginWithEmailBtn);
        registerHBtn = (Button) view.findViewById(R.id.registerHereBtnAtFacebookGmail);
        forgotPassBtn = (Button) view.findViewById(R.id.forgotPasswordBtnAtFG);
        loginResult = (TextView) view.findViewById(R.id.loginResult);
        fragmentManager = getActivity().getSupportFragmentManager();

        emailEt = (EditText) view.findViewById(R.id.emailEtAtLoginWithEmail);
        passEt = (EditText) view.findViewById(R.id.passwordETFAtLoginWithEmail);

        auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(getActivity());


        loginWithFacebookBtn.setOnClickListener(this);
        loginWithEmailBtn.setOnClickListener(this);
        registerHBtn.setOnClickListener(this);
        forgotPassBtn.setOnClickListener(this);
    }

    private void handleFacebookAccessToken(AccessToken accessToken) {

        String userID = accessToken.getUserId();
        Toast.makeText(getActivity(), userID, Toast.LENGTH_SHORT).show();

        GraphRequest graphRequest = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                displayUserInfo(object);
            }
        });
        Bundle prm = new Bundle();
        prm.putString("fields", "first_name, last_name, email, id, gender");
        graphRequest.setParameters(prm);
        graphRequest.executeAsync();

    }

    private void displayUserInfo(JSONObject object) {
        String first_name = "", last_name = "", email = "", id = "", gender = "", birthday = "";
        try {
            first_name = object.getString("first_name");
            last_name = object.getString("last_name");
            email = object.getString("email");
            id = object.getString("id");
            gender = object.getString("gender");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        loginResult.setText("Name: " + first_name + " " + last_name + "\nGender: " + gender
                + "\nEmail: " + email + "\n" + "id: " + id);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.loginWithEmailBtn: {
                doSignIn();
                break;
            }
            case R.id.registerHereBtnAtFacebookGmail: {
                fragmentManager.beginTransaction().replace(R.id.parentLayoutRegisterLogin, new CreateAccountUsingGmail())
                        .addToBackStack("ss")
                        .commit();
                break;
            }
            case R.id.forgotPasswordBtnAtFG: {

            }
        }
    }

    private void doSignIn() {
        String emailSt = emailEt.getText().toString();
        String passStr = passEt.getText().toString();
        if (TextUtils.isEmpty(emailSt)) {
            ToastMessage("Enter Email !");
        } else if (!emailSt.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]")) {
            ToastMessage("Enter a valid Email !");
        } else if (TextUtils.isEmpty(passStr)) {
            ToastMessage("Enter Password !");
        } else {
            progressDialog.setMessage("Registering...");
            progressDialog.show();
            auth.signInWithEmailAndPassword(emailSt, passStr)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if (!task.isSuccessful()) {
                                ToastMessage("Sign in with Email failed !");
                            } else {
                                startActivity(new Intent(getActivity(), NavigationActivity.class)
                                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                            }
                        }
                    });
        }
    }

    private void ToastMessage(String s) {
        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
