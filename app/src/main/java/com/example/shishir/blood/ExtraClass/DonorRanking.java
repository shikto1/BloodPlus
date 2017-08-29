package com.example.shishir.blood.ExtraClass;

/**
 * Created by Shishir on 8/29/2017.
 */

public class DonorRanking {
    private String donorName;
    private String bloodG;
    private String numberOfDonation;

    public String getDonorName() {
        return donorName;
    }

    public String getNumberOfDonation() {
        return numberOfDonation;
    }

    public String getBloodG() {
        return bloodG;
    }

    public DonorRanking(String donorName, String bloodG, String numberOfDonation) {
        this.donorName = donorName;
        this.bloodG = bloodG;
        this.numberOfDonation = numberOfDonation;

    }
}
