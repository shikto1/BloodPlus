package com.example.shishir.blood.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.shishir.blood.Database.DonorTableManager;
import com.example.shishir.blood.Database.LocalDatabase;
import com.example.shishir.blood.Donor;
import com.example.shishir.blood.DonorAdapter;
import com.example.shishir.blood.R;

import java.util.ArrayList;


public class SearchListFragment extends Fragment {
    ListView donorListView;
    DonorAdapter adapter;
    LocalDatabase localDatabase;
    ArrayList<Donor> donorList;
    ActionBar actionBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_list, container, false);
        Bundle b = getArguments();
        donorList = (ArrayList<Donor>) b.getSerializable("list");
        finDViewByID(view);
        return view;
    }

    private void finDViewByID(View view) {
        donorListView = (ListView) view.findViewById(R.id.donorListViewAtSearchDonoFragment);
        localDatabase = new LocalDatabase(getActivity());
        actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle(localDatabase.getSelectedBloodGrop() + "  at  " + localDatabase.getSelectedLocation());

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        if (!donorList.isEmpty()) {
            adapter = new DonorAdapter(getActivity(), donorList);
            donorListView.setAdapter(adapter);
        }
        super.onActivityCreated(savedInstanceState);
    }
}
