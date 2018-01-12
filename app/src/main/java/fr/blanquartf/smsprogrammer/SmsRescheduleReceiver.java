package fr.blanquartf.smsprogrammer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import java.util.List;

import fr.blanquartf.smsprogrammer.data.Sms;
import fr.blanquartf.smsprogrammer.data.SmsDao;
import fr.blanquartf.smsprogrammer.data.SmsDatabase;
import fr.blanquartf.smsprogrammer.utils.SmsUtils;

/**
 * Created by blanquartf on 12/01/2018.
 */

public class SmsRescheduleReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        goAsync();
        AsyncTask.execute(() -> {
            SmsDao dao = SmsDatabase.getSmsDatabase(context).smsDao();
            List<Sms> smsList = dao.selectAll();
            SmsUtils smsUtils = new SmsUtils(context);
            for (Sms sms : smsList)
            {
                smsUtils.programSmsSend(sms);
            }
        });
    }
}
