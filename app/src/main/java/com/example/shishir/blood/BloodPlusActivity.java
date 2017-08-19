package com.example.shishir.blood;

/**
 * Created by Shishir on 8/20/2017.
 */

public class BloodPlusActivity {
    private String name;
    private String blood;
    private String hospital;
    private String contact;
    private String donationDate;

    public String getName() {
        return name;
    }

    public String getBlood() {
        return blood;
    }

    public String getHospital() {
        return hospital;
    }

    public String getContact() {
        return contact;
    }
    public String getDonationDate(){
        return donationDate;
    }

    public BloodPlusActivity(String name, String blood, String hospital, String contact,String donationDate) {
        this.name = name;
        this.blood = blood;
        this.hospital = hospital;
        this.contact = contact;
        this.donationDate=donationDate;

    }

}
