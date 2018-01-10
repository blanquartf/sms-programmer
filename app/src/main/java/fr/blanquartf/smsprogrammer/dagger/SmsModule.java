package fr.blanquartf.smsprogrammer.dagger;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import fr.blanquartf.smsprogrammer.data.SmsDao;
import fr.blanquartf.smsprogrammer.data.SmsDatabase;
import fr.blanquartf.smsprogrammer.utils.SmsUtils;

/**
 * Created by blanquartf on 04/01/2018.
 */

@Module
public class SmsModule {

    private final Context context;

    public SmsModule(Context context)
    {
        this.context=context;
    }
    @Provides
    @Singleton
    SmsDao provideSmsDao(SmsDatabase smsDatabase) {
        return smsDatabase.smsDao();
    }

    @Provides
    @Singleton
    SmsDatabase provideSmsDatabase()
    {
        return SmsDatabase.getSmsDatabase(context);
    }

    @Provides
    @Singleton
    SmsUtils provideSmsUtils()
    {
        return new SmsUtils(context);
    }
}
