package me.ledge.link.sdk.ui.utils;

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

    private static final String ISO8601_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

    public static String formatISO8601Timestamp(String timestamp, String format) {
        Calendar calendar = GregorianCalendar.getInstance();
        String s = timestamp.replace("Z", "+00:00");
        Date date = null;
        try {
            s = s.substring(0, 22) + s.substring(23);  // to get rid of the ":"
            date = new SimpleDateFormat(ISO8601_DATE_FORMAT, Locale.US).parse(s);
        } catch (IndexOutOfBoundsException e) {
            return "";
        } catch (ParseException e) {
            return "";
        }

        calendar.setTime(date);
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
        return dateFormat.format(calendar.getTime());
    }
}
