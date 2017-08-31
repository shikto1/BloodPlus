package com.example.shishir.blood.Fragment;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.shishir.blood.ExtraClass.Constants;
import com.example.shishir.blood.ExtraClass.MyDialog;
import com.example.shishir.blood.ExtraClass.MySingleton;
import com.example.shishir.blood.Network;
import com.example.shishir.blood.R;

import java.util.HashMap;
import java.util.Map;

public class PushNotificationFragment extends Fragment implements View.OnClickListener {

    EditText msgTitle, msgDetails;
    Button sendBtn;
    ProgressDialog progressDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_push_notification, container, false);
        findViewById(view);
        return view;
    }

    private void findViewById(View view) {
        msgTitle = (EditText) view.findViewById(R.id.messageTitleEt);
        msgDetails = (EditText) view.findViewById(R.id.messageDetailsEt);
        sendBtn = (Button) view.findViewById(R.id.sendNotificationBtn);

        sendBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String title = msgTitle.getText().toString();
        String details = msgDetails.getText().toString();

        if (title.length() == 0) {
            ToastMessage("Title filed is empty !");
        } else if (details.length() == 0) {
            ToastMessage("Details field is empty !");
        } else {
            sendNotification(title, details);
        }
    }

    private void sendNotification(final String title, final String details) {

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Sending...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_PUSH_NOTIFICATION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                new AlertDialog.Builder(getActivity()).setMessage("Message Sent Successfully").setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("title", title);
                map.put("details", details);
                return map;
            }
        };

        MySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }

    public void ToastMessage(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
}
