package com.example.shishir.blood.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.shishir.blood.Donor;
import com.example.shishir.blood.ExtraClass.Constants;
import com.example.shishir.blood.ExtraClass.DonorRanking;
import com.example.shishir.blood.R;

import java.util.ArrayList;

/**
 * Created by Shishir on 8/29/2017.
 */

public class DonorRankingAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<DonorRanking> donorList;
    PopupMenu popupMenu;
    static int donorRankNumber;

    public DonorRankingAdapter(Context context, ArrayList<DonorRanking> donorList) {
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
        TextView donorRank, nameTv, nDonation;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.single_layout_for_donor_ranking, null);
            holder = new ViewHolder();

            holder.donorRank = (TextView) convertView.findViewById(R.id.donorRank);
            holder.nDonation = (TextView) convertView.findViewById(R.id.donationAtDonorRanking);
            holder.nDonation = (TextView) convertView.findViewById(R.id.donorNameAtDonorRanking);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.donorRank.setText("" + (donorRankNumber++));
        holder.nameTv.setText(donorList.get(position).getDonorName() + " (" + donorList.get(position).getBloodG() + ")");
        holder.nDonation.setText("Contact: " + donorList.get(position).getNumberOfDonation());

        return convertView;
    }
}
