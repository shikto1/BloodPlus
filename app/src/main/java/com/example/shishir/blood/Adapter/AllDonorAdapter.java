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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shishir.blood.Database.LocalDatabase;
import com.example.shishir.blood.Donor;
import com.example.shishir.blood.ExtraClass.Constants;
import com.example.shishir.blood.ExtraClass.DateCalculator;
import com.example.shishir.blood.R;

import java.util.ArrayList;

/**
 * Created by Shishir on 7/15/2017.
 */

public class AllDonorAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Donor> donorList;
    PopupMenu popupMenu;

    public AllDonorAdapter(Context context, ArrayList<Donor> donorList) {
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
        TextView nameTv, lastDonateTv, locationTV;
        ImageButton settingBtn;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.single_donor_for_all_donor, null);
            holder = new ViewHolder();
            holder.nameTv = (TextView) convertView.findViewById(R.id.donorNameAtAllDonor);
            holder.lastDonateTv = (TextView) convertView.findViewById(R.id.lastDonateAtAllDonor);
            holder.locationTV = (TextView) convertView.findViewById(R.id.locationAtAllDonor);
            holder.settingBtn = (ImageButton) convertView.findViewById(R.id.menuImageBtnAtAllDonor);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.nameTv.setText(donorList.get(position).getDonorName() + " (" + donorList.get(position).getBloodGroup() + ")");
        holder.lastDonateTv.setText("Last Donated:" + DateCalculator.calculateInterval(donorList.get(position).getLastDonationDate()));
        holder.locationTV.setText("Location: " + donorList.get(position).getLocation());
        holder.settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent clickIntent = new Intent(Constants.POPUP_MENU_CLICKED_ACTION);
                final Bundle bundle = new Bundle();
                PopupMenu popupMenu = new PopupMenu(context, v);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu_for_all_donor, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        bundle.putInt("itemID", item.getItemId());
                        bundle.putInt("position", position);
                        clickIntent.putExtra("event", bundle);
                        context.sendBroadcast(clickIntent);
                        return true;
                    }
                });

                popupMenu.show();
            }
        });
        return convertView;
    }


}
