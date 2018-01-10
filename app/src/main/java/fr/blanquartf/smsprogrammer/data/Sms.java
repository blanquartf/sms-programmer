package fr.blanquartf.smsprogrammer.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.List;

import fr.blanquartf.smsprogrammer.utils.Constants;

/**
 * Created by blanquartf on 28/12/2017.
 */

/**
 * This class represents an sms.
 * It will be stored into the user phone, waiting to be sent when the scheduled hour has been reached.
 */
@Entity
public class Sms implements Parcelable{
    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "smsContent")
    private String smsContent;

    @ColumnInfo(name = "contactList")
    private List<ContactWrapper> contactList;

    @ColumnInfo(name = "date")
    private Calendar date;

    public Sms() {}

    //region Parcelable constructor and methods

    protected Sms(Parcel in) {
        id = in.readLong();
        smsContent = in.readString();
        contactList = Converters.convertContactListStringToListFormat(in.readString());
        date = Converters.fromCalendarString(in.readString());
    }

    public static final Creator<Sms> CREATOR = new Creator<Sms>() {
        @Override
        public Sms createFromParcel(Parcel in) {
            return new Sms(in);
        }

        @Override
        public Sms[] newArray(int size) {
            return new Sms[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(smsContent);
        parcel.writeString(Converters.convertContactListToStringFormat(contactList));
        parcel.writeString(Converters.fromCalendar(date));
    }

    //endregion

    //region getters and setters
    public Calendar getDate() {return date;}

    public void setDate(Calendar date) {
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public List<ContactWrapper> getContactList() {
        return contactList;
    }

    public void setContactList(List<ContactWrapper> contactList) {
        this.contactList = contactList;
    }

    public String getSmsContent() {
        return smsContent;
    }

    public void setSmsContent(String smsContent) {
        this.smsContent = smsContent;
    }

    public void setId(long id) {
        this.id = id;
    }

    //endregion

    /**
     * This method generates the customized message for a specified contact
     * @param contact the contact
     * @return the customized message for the specified contact
     */
    public String getMessageForContact(ContactWrapper contact)
    {
        String smsContent = this.smsContent;
        for (String replacementVariable : ContactWrapper.SUPPORTED_FIELDS.keySet())
        {
            try {
                Method m = contact.getClass().getMethod(ContactWrapper.SUPPORTED_FIELDS.get(replacementVariable));
                smsContent=smsContent.replace(String.format(Constants.PATTERN_FOR_REPLACEMENT,replacementVariable), (String) m.invoke(contact));
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return smsContent;
    }

}
