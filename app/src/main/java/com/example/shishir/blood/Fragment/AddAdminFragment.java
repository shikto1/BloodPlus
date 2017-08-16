package com.example.shishir.blood.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
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
public class AddAdminFragment extends Fragment implements View.OnClickListener {

    private ListView adminListView;
    private Button addAdminBtn;

    String[] donorName = {"Shikto", "Shishir", "Fahmida", "Nipa", "Afrin", "Silvia", "Ashraful"};


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
        addAdminBtn = (Button) view.findViewById(R.id.addAdminBtn);
        addAdminBtn.setOnClickListener(this);

        ArrayAdapter adapter = new ArrayAdapter(getActivity(),R.layout.single_for_ad_admin_list, donorName);
        adminListView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {

        String selected = "";
        SparseBooleanArray checkArray = adminListView.getCheckedItemPositions();
        int size = checkArray.size();
        for (int i = 0; i < size; i++) {
            int position = checkArray.keyAt(i);
            selected += donorName[position] + "\n";
        }
        Toast.makeText(getActivity(), selected, Toast.LENGTH_SHORT).show();
    }
}
