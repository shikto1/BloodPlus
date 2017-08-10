package com.example.shishir.blood.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.shishir.blood.R;

public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        String msg=getIntent().getExtras().getString("dataM");
        ((TextView)findViewById(R.id.TV)).setText(msg);
    }
}
