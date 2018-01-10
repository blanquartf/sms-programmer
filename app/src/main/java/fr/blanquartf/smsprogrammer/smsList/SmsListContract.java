package fr.blanquartf.smsprogrammer.smsList;

import com.onegravity.contactpicker.contact.Contact;

import java.util.List;

import fr.blanquartf.smsprogrammer.BasePresenter;
import fr.blanquartf.smsprogrammer.BaseView;
import fr.blanquartf.smsprogrammer.data.Sms;

/**
 * Created by blanquartf on 03/01/2018.
 */

public interface SmsListContract {

    interface View extends BaseView<Presenter> {
        void showSmsList(List<Sms> sms);

        void showDatePicker();
        void chooseContact();

        void launchMessageContentChooser(Sms smsToSend);

        boolean hasPermissions();

        void requestPermissions();

        void showDeleteConfirmationDialog(Sms sms);
    }

    interface Presenter extends BasePresenter,DateTimePicker.DateTimePickerListener {
        void start();
        void stop();
        void deleteSms(Sms sms);

        void programSms();

        void onContactsSelected(List<Contact> contacts);

        void onSmsLongClicked(Sms sms);
    }
}
