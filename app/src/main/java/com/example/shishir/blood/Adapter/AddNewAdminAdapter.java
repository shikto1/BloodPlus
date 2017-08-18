package com.example.shishir.blood.Adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shishir.blood.Activity.AllDonorActivity;
import com.example.shishir.blood.Database.LocalDatabase;
import com.example.shishir.blood.Donor;
import com.example.shishir.blood.ExtraClass.Constants;
import com.example.shishir.blood.ExtraClass.DateCalculator;
import com.example.shishir.blood.R;

import java.util.ArrayList;

/**
 * Created by Shishir on 7/15/2017.
 */

public class AddNewAdminAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Donor> donorList;

    public AddNewAdminAdapter(Context context, ArrayList<Donor> donorList) {
        this.context = context;
        this.donorList = donorList;
    }

    @Override
    public int getCount() {
        return donorList.size();
    }

    @Override
    public Object getItem(int position) {
        return donorList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    static class ViewHolder {
        TextView nameTv, contactTV;
        CheckBox checkBox;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.single_donor_for_add_as_admin, null);
            holder = new ViewHolder();
            holder.nameTv = (TextView) convertView.findViewById(R.id.donorNameAtAddNewAdmin);
            holder.contactTV = (TextView) convertView.findViewById(R.id.contactOfDonorAtAddNewAdmin);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkboxForAddNewAdmin);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.nameTv.setText(donorList.get(position).getDonorName() + " (" + donorList.get(position).getBloodGroup() + ")");
        holder.contactTV.setText("Contact: " + donorList.get(position).getContactNumber());

//        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked)
//                    Toast.makeText(context,"Checked",Toast.LENGTH_SHORT).show();
//                else
//                    Toast.makeText(context," Un checked",Toast.LENGTH_SHORT).show();
//            }
//        });
        return convertView;
    }
}
