package fr.blanquartf.smsprogrammer.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by blanquartf on 23/11/2017.
 */

@Dao
public interface SmsDao {

    @Query("SELECT * FROM sms WHERE id = :id ")
    public Sms selectById(long id);

    @Query("SELECT * FROM sms")
    public List<Sms> selectAll();

    @Insert
    public long addSms(Sms sms);

    @Delete
    public void removeSms(Sms sms);
}
