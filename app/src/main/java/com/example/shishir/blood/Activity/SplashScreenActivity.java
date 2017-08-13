package com.example.shishir.blood.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.shishir.blood.Database.LocalDatabase;
import com.example.shishir.blood.R;

public class SplashScreenActivity extends AppCompatActivity {

    Thread thread;
    LocalDatabase localDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        localDatabase = new LocalDatabase(this);


        thread = new Thread() {
            @Override
            public void run() {
                try {
                    thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    if (localDatabase.getLoggedIn()) {
                        startActivity(new Intent(SplashScreenActivity.this, NavigationActivity.class));
                    } else {
                        startActivity(new Intent(SplashScreenActivity.this, FirstActivity.class));
                    }
                    finish();
                }
            }
        };
        thread.start();
    }
}
