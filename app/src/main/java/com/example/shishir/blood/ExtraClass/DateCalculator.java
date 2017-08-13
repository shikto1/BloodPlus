package com.example.shishir.blood.ExtraClass;

import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

/**
 * Created by Shishir on 8/14/2017.
 */

public class DateCalculator {
    public static String calculateInterval(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
        LocalDate lastDonationDate = LocalDate.parse(dateStr, formatter);

        LocalDate currentDate=new LocalDate();
        Period period = new Period(lastDonationDate, currentDate, PeriodType.yearMonthDay());
        int day = period.getDays();
        int month = period.getMonths();
        int year = period.getYears();
        if (year == 0 && month == 0)
            return day + " days";
        else if (year == 0)
            return month + " months " + day + " days";
        else if(year>16){
            return year+" yr "+month+" mn";
        }
        else
            return year + " yr " + month + " mn " + day + " days";
    }
}
