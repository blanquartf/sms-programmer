package fr.blanquartf.smsprogrammer.smsList;

import android.app.Activity;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

/**
 * Created by blanquartf on 02/01/2018.
 */

public class DateTimePicker implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    private final DateTimePickerListener listener;

    public interface DateTimePickerListener{
        void onDateTimeSet(Calendar date);
    }

    private Calendar date;
    private Activity activity;


    public  DateTimePicker(Activity activity,DateTimePickerListener listener)
    {
        this.activity=activity;
        this.listener=listener;
    }

    public void show()
    {
        date = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                date.get(Calendar.YEAR),
                date.get(Calendar.MONTH),
                date.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setMinDate(date);
        dpd.show(activity.getFragmentManager(), "Datepickerdialog");
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {

        date.set(Calendar.HOUR_OF_DAY,hourOfDay);
        date.set(Calendar.MINUTE,minute);
        date.set(Calendar.SECOND,0);
        listener.onDateTimeSet(date);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        date.set(Calendar.YEAR,year);
        date.set(Calendar.MONTH,monthOfYear);
        date.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        TimePickerDialog dpd = TimePickerDialog.newInstance(
                this,
                date.get(Calendar.HOUR_OF_DAY),
                date.get(Calendar.MINUTE),
                true
        );
        dpd.show(activity.getFragmentManager(), "Datepickerdialog");
    }

}
