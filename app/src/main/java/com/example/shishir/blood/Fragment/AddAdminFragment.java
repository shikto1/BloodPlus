package com.example.shishir.blood.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.shishir.blood.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddAdminFragment extends Fragment {

    private ListView adminListView;

    String[] donorName = {"Shikto", "Shishir", "Fahmida", "Nipa", "Afrin", "Silvia", "Ashraful"};


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_admin, container, false);
        findViewById(view);
        return view;
    }

    private void findViewById(View view) {
        adminListView = (ListView) view.findViewById(R.id.addAdminListView);

        ArrayAdapter adapter = new ArrayAdapter(getActivity(), R.layout.single_for_ad_admin_list, donorName);
        adminListView.setAdapter(adapter);
    }

//    @Override
//    public void onClick(View v) {
//
//        String selected = "";
//        SparseBooleanArray checkArray = adminListView.getCheckedItemPositions();
//        int size = checkArray.size();
//        for (int i = 0; i < size; i++) {
//            int position = checkArray.keyAt(i);
//            selected += donorName[position] + "\n";
//        }
//        Toast.makeText(getActivity(), selected, Toast.LENGTH_SHORT).show();
//    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.add_admin_second_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(getActivity(), "Check Icon", Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }
}
