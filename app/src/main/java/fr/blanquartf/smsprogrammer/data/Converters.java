package fr.blanquartf.smsprogrammer.data;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import fr.blanquartf.smsprogrammer.utils.Constants;

/**
 * Created by blanquartf on 10/01/2018.
 */

public class Converters {

    /**
     * Convert a contact list from String format to List format
     * @param contactList the contact list in string format
     * @return the contact list in List format
     */
    @TypeConverter
    public static List<ContactWrapper> convertContactListStringToListFormat(String contactList) {
        Gson gson = new Gson();
        ArrayList<ContactWrapper> contacts = new ArrayList<ContactWrapper>();
        try {
            JSONArray array = new JSONArray(contactList);
            for (int i=0;i<array.length();i++)
            {
                contacts.add(gson.fromJson(array.getString(i),ContactWrapper.class));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return contacts;
    }

    /**
     * Convert a contact list from List format to String format
     * @param contactList the contact list to convert
     * @return the contact list in String format
     */
    @TypeConverter
    public static String convertContactListToStringFormat(List<ContactWrapper> contactList) {
        return new Gson().toJson(contactList, new ArrayList<ContactWrapper>().getClass());
    }

    /**
     * Convert a date from Calendar format to String format
     * @param date the date from Calendar format to convert
     * @return the date converted to String format
     */

    @TypeConverter
    public static String fromCalendar(Calendar date) {
        return Constants.DATE_FORMAT.format(date.getTime());
    }

    /**
     * Convert a date from String format to Calendar format
     * @param calendarString the date from String format to convert
     * @return the date converted to Calendar format
     */
    @TypeConverter
    public static Calendar fromCalendarString(String calendarString) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(Constants.DATE_FORMAT.parse(calendarString));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar;
    }
}
