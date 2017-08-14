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

import com.example.shishir.blood.Database.LocalDatabase;
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
        ImageButton callBtn, msgBtn;
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
            holder.msgBtn = (ImageButton) convertView.findViewById(R.id.msgBtn);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.nameTv.setText(donorList.get(position).getDonorName());
        holder.lastDonateTv.setText(DateCalculator.calculateInterval(donorList.get(position).getLastDonationDate()));

        holder.callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = donorList.get(position).getContactNumber();
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + phone));
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                context.startActivity(callIntent);
            }
        });
        holder.msgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalDatabase localDatabase=new LocalDatabase(context);
                String phone = donorList.get(position).getContactNumber();
                Intent smsIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phone));
                smsIntent.putExtra("sms_body","Need "+ localDatabase.getSelectedBloodGrop()+" Blood at "+
                        localDatabase.getSelectedLocation()+"...");
                context.startActivity(smsIntent);
            }
        });
        return convertView;
    }

}
