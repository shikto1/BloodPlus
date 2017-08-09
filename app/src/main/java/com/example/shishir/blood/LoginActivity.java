package com.example.shishir.blood;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.shishir.blood.Fragment.PasswordRecoverFragment;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    TextInputLayout phoneL, passL;
    EditText phoneEt, passEt;
    CheckBox loggedIn;
    TextView clickMeBtn, registerHereBtn;
    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById();
    }

    private void findViewById() {
       // phoneL = (TextInputLayout) findViewById(R.id.phoneNumberInputLayoutAtLoginPage);
        //passL = (TextInputLayout) findViewById(R.id.passwordInLaoutAtLognPage);

      //  phoneEt = (EditText) findViewById(R.id.phoneNumberEtAtLoginPage);
        //passEt = (EditText) findViewById(R.id.passwordEtAtLoginPage);

        loggedIn = (CheckBox) findViewById(R.id.loggedInCheckBoxAtLoginPage);

      //  clickMeBtn = (TextView) findViewById(R.id.clickMeButtonAtLoginPage);
      //  loginBtn = (Button) findViewById(R.id.loginButton);
        registerHereBtn = (TextView) findViewById(R.id.registerHereButtonAtLoginPage);

        clickMeBtn.setOnClickListener(this);
        //loginBtn.setOnClickListener(this);
        registerHereBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        switch (id) {
//            case R.id.clickMeButtonAtLoginPage: {
//                new PasswordRecoverFragment().show(getSupportFragmentManager(),"");
//                break;
//            }
//            case R.id.loginButton: {
//                startActivity(new Intent(this,LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
//                break;
//            }
            case R.id.registerHereButtonAtLoginPage: {
                startActivity(new Intent(this, RegisterActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            }
        }

    }
}
