package com.example.shishir.blood.Activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.shishir.blood.Network;
import com.example.shishir.blood.R;


public class FirstActivity extends Activity implements View.OnClickListener {

    Button searchDonorBtn, loginBtn, registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        findViewById();
    }

    private void findViewById() {
        searchDonorBtn = (Button) findViewById(R.id.searchDonorButton);
        loginBtn = (Button) findViewById(R.id.loginButtonAtFirstAc);
        registerBtn = (Button) findViewById(R.id.registerButton);

        searchDonorBtn.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.searchDonorButton: {
                if (Network.isNetAvailable(this))
                    startActivity(new Intent(this, SearchDonorActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                else {
                    Network.showInternetAlertDialog(this);
                }
                break;
            }
            case R.id.loginButtonAtFirstAc: {
                Intent intent = new Intent(this, RegisterLoginActivity.class);
                intent.putExtra("rr", "login");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            }
            case R.id.registerButton: {
                Intent intent = new Intent(this, RegisterLoginActivity.class);
                intent.putExtra("rr", "reg");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;

            }
        }

    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setMessage("Sure to exit ???")
                .setCancelable(false)
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).show();
    }

    @Override
    protected void onResume() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        super.onResume();
    }
}


