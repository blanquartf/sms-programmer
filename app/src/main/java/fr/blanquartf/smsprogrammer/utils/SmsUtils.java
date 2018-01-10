package fr.blanquartf.smsprogrammer.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import org.greenrobot.eventbus.EventBus;

import fr.blanquartf.smsprogrammer.SmsSenderReceiver;
import fr.blanquartf.smsprogrammer.data.Sms;
import fr.blanquartf.smsprogrammer.smsList.SmsLoadEvent;

/**
 * Created by blanquartf on 03/01/2018.
 */

public class SmsUtils {

    private final Context context;

    public SmsUtils(Context context)
    {
        this.context=context;
    }
    private PendingIntent getIntentForSms( Sms sms)
    {
        Intent intentToFire = new Intent(context, SmsSenderReceiver.class);
        intentToFire.setAction(SmsSenderReceiver.ACTION_SEND_SMS);
        intentToFire.putExtra(SmsSenderReceiver.SMS_ID,sms.getId());

        PendingIntent alarmIntent = PendingIntent.getBroadcast(context,
                0, intentToFire, PendingIntent.FLAG_UPDATE_CURRENT);
        return alarmIntent;
    }

    public void programSmsSend(Sms sms)
    {
        PendingIntent alarmIntent = getIntentForSms(sms);
        AlarmManager alarmManager = (AlarmManager)context.
                getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, sms.getDate().getTimeInMillis(),alarmIntent);
        EventBus.getDefault().post(new SmsLoadEvent());
    }

    public void cancelSmsSend(Sms sms)
    {
        PendingIntent alarmIntent = getIntentForSms(sms);
        AlarmManager alarmManager = (AlarmManager)context.
                getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(alarmIntent);
    }
}
