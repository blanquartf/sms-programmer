package fr.blanquartf.smsprogrammer.data;

import android.provider.ContactsContract;

import com.onegravity.contactpicker.core.ContactImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by blanquartf on 02/01/2018.
 */

public class ContactWrapper {

    /**
     * This map contains the fields that the user can choose to add to his message.
     * The key is the fieldName and is rendered via the strings.xml file.
     * The value is the method name that has to be called on a ContactWrapper object to get the corresponding field for the phone contact
     */
    public static final Map<String,String> SUPPORTED_FIELDS = new HashMap<String,String>()
    {
        {
            put("contactFirstName","getContactFirstName");
            put("contactLastName","getContactLastName");
            put("contactMobilePhoneNumber","getContactMobilePhoneNumber");
            put("contactDisplayName","getContactDisplayName");
        }
    };

    /**
     * The real contact object
     */
    private ContactImpl contact;

    public ContactWrapper(ContactImpl contact)
    {
        this.contact=contact;
    }
    public String getContactFirstName()
    {
        return contact.getFirstName();
    }
    public String getContactLastName()
    {
        return contact.getLastName();
    }
    public String getContactDisplayName(){return contact.getDisplayName();};
    public String getContactMobilePhoneNumber()
    {
        return contact.getPhone(ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
    }
}
