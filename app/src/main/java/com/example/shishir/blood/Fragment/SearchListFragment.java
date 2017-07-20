package com.example.shishir.blood.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.shishir.blood.Database.DonorTableManager;
import com.example.shishir.blood.Database.LocalDatabase;
import com.example.shishir.blood.DonorAdapter;
import com.example.shishir.blood.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchListFragment extends Fragment {
    ListView donorListView;
    DonorAdapter adapter;
    LocalDatabase localDatabase;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_list, container, false);
        finDViewByID(view);
        return view;
    }

    private void finDViewByID(View view) {
        donorListView = (ListView) view.findViewById(R.id.donorListViewAtSearchDonoFragment);
        localDatabase = new LocalDatabase(getActivity());
        adapter = new DonorAdapter(getActivity(), new DonorTableManager(getActivity()).getAllDonorSearched
                (localDatabase.getSelectedBloodGrop(), localDatabase.getSelectedLocation()));


        donorListView.setAdapter(adapter);
    }

}
