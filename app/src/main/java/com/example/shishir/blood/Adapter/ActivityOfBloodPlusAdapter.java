package com.example.shishir.blood.Adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shishir.blood.BloodPlusActivity;
import com.example.shishir.blood.Database.LocalDatabase;
import com.example.shishir.blood.Donor;
import com.example.shishir.blood.ExtraClass.DateCalculator;
import com.example.shishir.blood.Fragment.ActivityOfBloodPlus;
import com.example.shishir.blood.R;

import java.util.ArrayList;

/**
 * Created by Shishir on 7/15/2017.
 */

public class ActivityOfBloodPlusAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<BloodPlusActivity> activityList;

    public ActivityOfBloodPlusAdapter(Context context, ArrayList<BloodPlusActivity> activityList) {
        this.context = context;
        this.activityList = activityList;
    }

    @Override
    public int getCount() {
        return activityList.size();
    }

    @Override
    public Object getItem(int position) {
        return activityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    static class ViewHolder {
        TextView detailsTv;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.single_layout_for_blood_plus_activity, null);
            holder = new ViewHolder();
            holder.detailsTv = (TextView) convertView.findViewById(R.id.detailsForActivty);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String name = activityList.get(position).getName();
        String hospitalName = activityList.get(position).getHospital();
        String dDate=activityList.get(position).getDonationDate();
        String blood=activityList.get(position).getBlood();

        holder.detailsTv.setText(name+" ("+blood+") donated blood at "+hospitalName+" on "+DateCalculator.formatDate(dDate));

        return convertView;
    }

}
