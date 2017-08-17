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
import android.widget.Toast;

import com.example.shishir.blood.Donor;
import com.example.shishir.blood.ExtraClass.Constants;
import com.example.shishir.blood.ExtraClass.DateCalculator;
import com.example.shishir.blood.R;

import java.util.ArrayList;

/**
 * Created by Shishir on 7/15/2017.
 */

public class AdminAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Donor> adminList;
    PopupMenu popupMenu;

    public AdminAdapter(Context context, ArrayList<Donor> adminList) {
        this.context = context;
        this.adminList = adminList;
    }

    @Override
    public int getCount() {
        return adminList.size();
    }

    @Override
    public Object getItem(int position) {
        return adminList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    static class ViewHolder {
        TextView nameTv, contactTv, locationTv;
        ImageButton settingBtn;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.single_admin, null);
            holder = new ViewHolder();
            holder.nameTv = (TextView) convertView.findViewById(R.id.adminName);
            holder.contactTv = (TextView) convertView.findViewById(R.id.contactAtAdmin);
            holder.locationTv = (TextView) convertView.findViewById(R.id.locationTvAtAdmin);
            holder.settingBtn = (ImageButton) convertView.findViewById(R.id.settingBtn);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.nameTv.setText(adminList.get(position).getDonorName() + " (" + adminList.get(position).getBloodGroup() + ")");
        holder.contactTv.setText("Contact: " + adminList.get(position).getContactNumber());
        holder.locationTv.setText("Location: " + adminList.get(position).getLocation());

        holder.settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Intent clickIntent = new Intent(Constants.POPUP_MENU_CLICKED_ACTION);
                final Bundle bundle = new Bundle();
                PopupMenu popupMenu = new PopupMenu(context, v);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu_for_admin, popupMenu.getMenu());

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
