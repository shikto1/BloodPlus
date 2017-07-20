package com.example.shishir.blood.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.shishir.blood.Donor;

import java.util.ArrayList;

/**
 * Created by Shishir on 7/15/2017.
 */

public class DonorTableManager {

    private DatabaseHelper helper;
    private SQLiteDatabase database;
    private Donor donor;


    public DonorTableManager(Context context) {
        helper = new DatabaseHelper(context);
    }

    private void open() {
        database = helper.getWritableDatabase();

    }

    private void close() {
        helper.close();
    }


    public boolean addDonor(Donor donor) {

        this.open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COL_DONOR_NAME, donor.getDonorName());
        contentValues.put(DatabaseHelper.COL_GENDER, donor.getGender());
        contentValues.put(DatabaseHelper.COL_BLOOD_GROUP, donor.getBloodGroup());
        contentValues.put(DatabaseHelper.COL_LOCATION, donor.getLocation());
        contentValues.put(DatabaseHelper.COL_BIRTHDATE, donor.getBirthDate());
        contentValues.put(DatabaseHelper.COL_LASTDONATE, donor.getLastDonationDate());
        contentValues.put(DatabaseHelper.COL_CONTACT_NUMBER, donor.getContactNumber());


        long inserted = database.insert(DatabaseHelper.TABLE_DONOR, null, contentValues);
        this.close();

        return inserted != -1;
    }


    public Donor getDonorDetails(String donorName, String contactNumber) {

        this.open();
        Cursor cursor = database.query(DatabaseHelper.TABLE_DONOR, null, DatabaseHelper.COL_DONOR_NAME + "=? AND " + DatabaseHelper.COL_CONTACT_NUMBER + " =?",
                new String[]{donorName, contactNumber}, null, null, null);

        cursor.moveToFirst();


        String birthDateStr = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_BIRTHDATE));
        String bloodGroup = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_BLOOD_GROUP));
        String lastDonteStr = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_LASTDONATE));
        String locaitonStr = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_LOCATION));
        String gednerStr = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_GENDER));
        String contactN = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_CONTACT_NUMBER));

        Donor donor = new Donor(donorName, gednerStr, bloodGroup, locaitonStr, birthDateStr, contactN, lastDonteStr); //I am passing age as in relation field
        this.close();

        return donor;
    }

    public ArrayList<Donor> getAllDonor() {

        this.open();
        ArrayList<Donor> donorList = new ArrayList<Donor>();
        Cursor cursor = database.query(DatabaseHelper.TABLE_DONOR, null, null, null, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();

            for (int i = 0; i < cursor.getCount(); i++) {

                String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_DONOR_NAME));
                String bloodG = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_BLOOD_GROUP));
                String contactN = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_CONTACT_NUMBER));
                String lastDonate = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_LASTDONATE));
                String location = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_LOCATION));
                String birthdate = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_BIRTHDATE));

                donor = new Donor(name, bloodG, location, contactN, birthdate, lastDonate);
                donorList.add(donor);
                cursor.moveToNext();
            }

        }
        this.close();
        return donorList;
    }


    public ArrayList<Donor> getAllDonorSearched(String bloodGroup, String location) {

        this.open();
        ArrayList<Donor> donorList = new ArrayList<Donor>();
        Cursor cursor = database.query(DatabaseHelper.TABLE_DONOR, new String[]{DatabaseHelper.COL_DONOR_NAME, DatabaseHelper.COL_CONTACT_NUMBER, DatabaseHelper.COL_LASTDONATE},
                DatabaseHelper.COL_BLOOD_GROUP + " =? AND " + DatabaseHelper.COL_LOCATION + " = ?", new String[]{bloodGroup, location}, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();

            for (int i = 0; i < cursor.getCount(); i++) {

                String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_DONOR_NAME));
                String contactN = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_CONTACT_NUMBER));
                String lastDonate = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_LASTDONATE));

                donor = new Donor(name, contactN, lastDonate);
                donorList.add(donor);
                cursor.moveToNext();
            }

        }
        this.close();
        return donorList;
    }

//    public boolean updateProfileImage(String profileName, String imagePath) {
//
//        this.open();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(DatabaseHelper.COL_BIRTH_MONTH, imagePath);
//
//        int updated = database.update(DatabaseHelper.TABLE_PROFILE, contentValues, DatabaseHelper.COL_NAME + " = ?", new String[]{profileName});
//        this.close();
//        if (updated > 0) {
//            return true;
//        } else return false;
//    }

//    public boolean updateProfile(Profile profile, String currentProfile) {
//
//        this.open();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(DatabaseHelper.COL_NAME, profile.getName());
//        contentValues.put(DatabaseHelper.COL_HEIGHT, profile.getHeight());
//        contentValues.put(DatabaseHelper.COL_WEIGHT, profile.getWeight());
//        contentValues.put(DatabaseHelper.COL_MAJOR_DIESEASE, profile.getMajorDisease());
//
//        int updated = database.update(DatabaseHelper.TABLE_PROFILE, contentValues, DatabaseHelper.COL_NAME + " = ?", new String[]{currentProfile});
//        this.close();
//        if (updated > 0) {
//            return true;
//        } else return false;
//    }
//
//    public boolean deleteDonor(int id) {
//        this.open();
//        int deleted = database.delete(DatabaseHelper.TABLE_DONOR, DatabaseHelper.COL_ID + "= " + id, null);
//        this.close();
//        if (deleted > 0) {
//            return true;
//        } else return false;
//
//    }
}
