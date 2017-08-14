package com.example.shishir.blood.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shishir.blood.Donor;
import com.example.shishir.blood.ExtraClass.DateCalculator;
import com.example.shishir.blood.R;

import java.util.ArrayList;

/**
 * Created by Shishir on 7/15/2017.
 */

public class DonorAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Donor> donorList;

    public DonorAdapter(Context context, ArrayList<Donor> donorList) {
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
        TextView nameTv, lastDonateTv;
        ImageButton callBtn;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.single_donor, null);
            holder = new ViewHolder();
            holder.nameTv = (TextView) convertView.findViewById(R.id.nameTvAtsingleDonor);
            holder.lastDonateTv = (TextView) convertView.findViewById(R.id.lastDonatedatListView);
            holder.callBtn = (ImageButton) convertView.findViewById(R.id.callImageBtn);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.nameTv.setText(donorList.get(position).getDonorName());
        holder.lastDonateTv.setText(DateCalculator.calculateInterval(donorList.get(position).getLastDonationDate()));

        holder.callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, donorList.get(position).getContactNumber(), Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }
}
