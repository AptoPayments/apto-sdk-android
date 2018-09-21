package com.shiftpayments.link.sdk.ui.utils;

import android.content.Context;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by adrian on 20/11/2017.
 */

public class DateUtil {

    public DateUtil() {
        mLocale = Locale.US;
    }

    public DateUtil(Locale locale) {
        mLocale = locale;
    }

    public static final String BIRTHDATE_DATE_FORMAT = "MM-dd-yyyy";
    private static final String ISO8601_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
    private static Locale mLocale;

    public static String formatISO8601Timestamp(String timestamp, String format) {
        Calendar calendar = GregorianCalendar.getInstance();
        String s = timestamp.replace("Z", "+00:00");
        Date date;
        try {
            s = s.substring(0, 22) + s.substring(23);  // to get rid of the ":"
            date = new SimpleDateFormat(ISO8601_DATE_FORMAT, mLocale).parse(s);
        } catch (IndexOutOfBoundsException e) {
            return "";
        } catch (ParseException e) {
            return "";
        }

        calendar.setTime(date);
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, mLocale);
        return dateFormat.format(calendar.getTime());
    }

    public Date getDateFromString(String dateString, String format) {
        if(dateString != null) {
            SimpleDateFormat birthdayFormat = new SimpleDateFormat(format, mLocale);
            try {
                return birthdayFormat.parse(dateString);
            } catch (ParseException e) {
                return null;
            }
        }
        return null;
    }

    public static String getSimpleTransactionDate(String timestamp) {
        Date date = new Date(Long.parseLong(timestamp) * 1000L);
        SimpleDateFormat expectedFormat = new SimpleDateFormat("MMM yyyy", Locale.US);
        return expectedFormat.format(date);
    }

    public static String getMonthFromTimeStamp(String timestamp) {
        Date date = new Date(Long.parseLong(timestamp)* 1000L);
        SimpleDateFormat expectedFormat = new SimpleDateFormat("MMM", Locale.US);
        return expectedFormat.format(date);
    }

    public static String getYearFromTimeStamp(String timestamp) {
        Date date = new Date(Long.parseLong(timestamp) * 1000L);
        SimpleDateFormat expectedFormat = new SimpleDateFormat("yyyy", Locale.US);
        return expectedFormat.format(date);
    }

    public static String getFormattedTransactionDate(String timestamp) {
        Date date = new Date(Long.parseLong(timestamp) * 1000L);
        SimpleDateFormat expectedFormat = new SimpleDateFormat("EEE, MMM dd 'at' hh:mm a", Locale.US);
        return expectedFormat.format(date);
    }

    public static String getLocaleFormattedDate(String timestamp, Context context) {
        Date date = new Date(Long.parseLong(timestamp) * 1000L);
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT, Locale.US);
        return dateFormat.format(date);
    }

    public SimpleDateFormat getBirthdayFormat() {
        return new SimpleDateFormat(BIRTHDATE_DATE_FORMAT, mLocale);
    }
}
