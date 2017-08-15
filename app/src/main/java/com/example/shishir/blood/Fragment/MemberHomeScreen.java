package com.example.shishir.blood.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.shishir.blood.Activity.SearchDonorActivity;
import com.example.shishir.blood.R;

public class MemberHomeScreen extends Fragment {

    Button searchBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_member_home_screen, container, false);

        findViewById(view);
        return view;
    }

    private void findViewById(View view) {
        searchBtn= (Button) view.findViewById(R.id.searchBtnAtMemberHome);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SearchDonorActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
    }
}
