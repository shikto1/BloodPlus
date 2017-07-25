package com.example.shishir.blood;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

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
        TextView nameTv, bloodGroupTv;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.single_donor, null);
            holder = new ViewHolder();
            holder.nameTv = (TextView) convertView.findViewById(R.id.nameTvAtsingleDonor);
            holder.bloodGroupTv = (TextView) convertView.findViewById(R.id.bloodGroupAtSingleDonor);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //  Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.shikto);
        //  Bitmap circularBitmap = ImageConverter.getRoundedCornerBitmap(bitmap, 100);


        // holder.circularImageView.setImageBitmap(circularBitmap);
        holder.nameTv.setText(donorList.get(position).getDonorName());
      //  holder.bloodGroupTv.setText(donorList.get(position).getBloodGroup());

//        if(imagePath!=null){
//            Drawable d = (Drawable) Drawable.createFromPath(imagePath);
//            holder.circularImageView.setImageDrawable(d);
//        }
//        String age = new DateCalculator().calculatedAge(donorList.get(position).getBirthYear());
//        holder.ageTv.setText(age);
        return convertView;
    }

}
