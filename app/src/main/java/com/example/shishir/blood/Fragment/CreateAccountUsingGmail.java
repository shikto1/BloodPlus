package com.example.shishir.blood.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shishir.blood.Fragment.LoginWithFacebookOrMailFragment;
import com.example.shishir.blood.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;


public class CreateAccountUsingGmail extends Fragment implements View.OnClickListener {

    FragmentManager fragmentManager;
    Button registerWIthEmail, loginHereBtnAtFBG;

    TextView loginStatus;

    //For Facebook Login...................
    LoginButton registerWithFb;
    CallbackManager callbackManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_accouot_using_gmail, container, false);
        findViewById(view);
        return view;
    }

    private void findViewById(View view) {

        registerWIthEmail = (Button) view.findViewById(R.id.registerWithEmailBtn);
        loginHereBtnAtFBG = (Button) view.findViewById(R.id.loginHereFbEmail);
        loginStatus = (TextView) view.findViewById(R.id.loginStatus);
        fragmentManager = getActivity().getSupportFragmentManager();


        registerWIthEmail.setOnClickListener(this);
        loginHereBtnAtFBG.setOnClickListener(this);


        //Facebook Login SetUP..........................
        callbackManager = CallbackManager.Factory.create();
        registerWithFb = (LoginButton) view.findViewById(R.id.registerUsingFbBtn);
        registerWithFb.setFragment(this);
        registerWithFb.setReadPermissions("email", "public_profile");

        registerWithFb.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                handleFacebookAccessToken(loginResult.getAccessToken());

            }

            @Override
            public void onCancel() {
                loginStatus.setText("Cancelled");

            }

            @Override
            public void onError(FacebookException error) {

            }
        });


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
        loginStatus.setText("Name: " + first_name + " " + last_name + "\nGender: " + gender
                + "\nEmail: " + email + "\n" + "id: " + id);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.registerWithEmailBtn: {
                fragmentManager.beginTransaction()
                        .replace(R.id.parentLayoutRegisterLogin, new AccountWithGmailFirstScreen())
                        .addToBackStack("s")
                        .commit();
                break;
            }
            case R.id.loginHereFbEmail: {
                fragmentManager.beginTransaction().replace(R.id.parentLayoutRegisterLogin, new LoginWithFacebookOrMailFragment())
                        .commit();
                break;
            }
        }
    }

    private void ToastMessage(String ss) {
        Toast.makeText(getActivity(), ss, Toast.LENGTH_SHORT).show();
    }
}
