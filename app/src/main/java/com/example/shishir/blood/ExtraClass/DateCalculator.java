package com.example.shishir.blood.ExtraClass;

import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Shishir on 8/14/2017.
 */

public class DateCalculator {
    public static String calculateInterval(String dateStr) {
        if (dateStr.isEmpty() || dateStr.length() < 5) {
            return "Not available";
        } else {
            DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
            LocalDate lastDonationDate = LocalDate.parse(dateStr, formatter);

            LocalDate currentDate = new LocalDate();
            Period period = new Period(lastDonationDate, currentDate, PeriodType.yearMonthDay());
            int day = period.getDays();
            int month = period.getMonths();
            int year = period.getYears();
            if (year > 0) {
                return year + " year " + month + " months " + day + " days ago";
            } else
                return month + " months " + day + " days ago";
        }
    }

    public static String formatDate(String date) {
        DateFormat ff = new SimpleDateFormat("dd/MM/yyyy");  //MM =month number but MMM=month name.............
        Date dd = null;
        try {
            dd = ff.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat fff = new SimpleDateFormat("MMM dd, yyyy");
        String fDate = fff.format(dd);
        return fDate;
    }
}
