package fr.blanquartf.smsprogrammer.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

/**
 * Created by blanquartf on 23/11/2017.
 */

@Database(entities = {Sms.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class SmsDatabase extends RoomDatabase {
    private static SmsDatabase instance;
    public static SmsDatabase getSmsDatabase(Context context)
    {
        if (instance==null)
        {
            instance=Room.databaseBuilder(context,
                    SmsDatabase.class, "smsDatabase").build();
        }
        return instance;
    }
    public abstract SmsDao smsDao();
}
