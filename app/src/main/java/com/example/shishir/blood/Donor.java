package com.example.shishir.blood;

import java.io.Serializable;

/**
 * Created by Shishir on 7/14/2017.
 */

public class Donor implements Serializable {
    private String donorName;
    private String gender;
    private String bloodGroup;
    private String location;
    private String birthDate;
    private String contactNumber;
    private String lastDonationDate;
    private String numberOfDonation;

    public Donor(String name,String contact){
        this.donorName=name;
        this.contactNumber=contact;
    }

    public Donor(String donorName, String gender, String bloodGroup, String location, String birthDate, String contactNumber, String lastDonationDate) {
        this.donorName = donorName;
        this.gender = gender;
        this.bloodGroup = bloodGroup;
        this.location = location;
        this.birthDate = birthDate;
        this.contactNumber = contactNumber;
        this.lastDonationDate = lastDonationDate;
    }

    public Donor(String donorName, String bloodGroup, String location, String contactNumber, String birthDate, String lastDonationDate) {
        this.donorName = donorName;
        this.bloodGroup = bloodGroup;
        this.location = location;
        this.birthDate = birthDate;
        this.contactNumber = contactNumber;
        this.lastDonationDate = lastDonationDate;
    }

    public Donor(String donorName, String contactNumber, String lastDonationDate) {
        this.donorName = donorName;
        this.contactNumber = contactNumber;
        this.lastDonationDate = lastDonationDate;
    }


    public String getDonorName() {
        return donorName;
    }

    public String getGender() {
        return gender;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public String getLocation() {
        return location;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getLastDonationDate() {
        return lastDonationDate;
    }
}
