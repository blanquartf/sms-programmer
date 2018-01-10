package fr.blanquartf.smsprogrammer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.telephony.SmsManager;

import org.greenrobot.eventbus.EventBus;

import fr.blanquartf.smsprogrammer.data.ContactWrapper;
import fr.blanquartf.smsprogrammer.data.Sms;
import fr.blanquartf.smsprogrammer.data.SmsDao;
import fr.blanquartf.smsprogrammer.data.SmsDatabase;
import fr.blanquartf.smsprogrammer.smsList.SmsLoadEvent;

/**
 * Created by blanquartf on 28/12/2017.
 */

public class SmsSenderReceiver extends BroadcastReceiver {
    public static final String SMS_ID="SMS_ID";
    public static final String ACTION_SEND_SMS= "fr.blanquartf.smsprogrammer.ACTION_SEND_SMS";
    @Override
    public void onReceive(final Context context, Intent intent) {
        goAsync();
        final long id = intent.getLongExtra(SMS_ID, -1);
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                SmsDao dao = SmsDatabase.getSmsDatabase(context).smsDao();
                Sms sms = dao.selectById(id);
                SmsManager smsManager = SmsManager.getDefault();
                for(ContactWrapper contact : sms.getContactList())
                {
                    smsManager.sendTextMessage(contact.getContactMobilePhoneNumber(), null, sms.getMessageForContact(contact), null, null);
                }
                dao.removeSms(sms);
                EventBus.getDefault().post(new SmsLoadEvent());
            }
        });

    }
}
