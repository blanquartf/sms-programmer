package fr.blanquartf.smsprogrammer.smsList;

import com.onegravity.contactpicker.contact.Contact;
import com.onegravity.contactpicker.core.ContactImpl;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import fr.blanquartf.smsprogrammer.data.ContactWrapper;
import fr.blanquartf.smsprogrammer.data.Sms;
import fr.blanquartf.smsprogrammer.data.SmsDao;
import fr.blanquartf.smsprogrammer.utils.SmsUtils;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by blanquartf on 03/01/2018.
 */

public class SmsListPresenter implements SmsListContract.Presenter {
    @Inject
    SmsUtils smsUtils;
    private SmsListContract.View view;
    @Inject
    SmsDao dao;
    private Sms smsToSend;

    public SmsListPresenter(SmsListContract.View view) {
        this.view = view;
    }

    @Override
    public void start() {
        EventBus.getDefault().register(this);
        EventBus.getDefault().post(new SmsLoadEvent());
    }

    @Override
    public void stop() {
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loadSmsList(SmsLoadEvent event)
    {
       Single.fromCallable(() -> dao.selectAll())
               .subscribeOn(Schedulers.newThread())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(smsList ->view.showSmsList(smsList));
    }

    @Override
    public void deleteSms(final Sms sms) {
        smsUtils.cancelSmsSend(sms);
        Single.fromCallable(() -> {dao.removeSms(sms);return true;})
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> EventBus.getDefault().post(new SmsLoadEvent()));
    }

    @Override
    public void programSms() {
        if (view.hasPermissions())
        {
            view.showDatePicker();
        }
        else
        {
            view.requestPermissions();
        }
    }

    @Override
    public void onContactsSelected(List<Contact> contacts) {
        List<ContactWrapper> contactWrapperList = new ArrayList<ContactWrapper>();
        for (Contact contact : contacts)
        {
            contactWrapperList.add(new ContactWrapper((ContactImpl)contact));
        }
        smsToSend.setContactList(contactWrapperList);
        view.launchMessageContentChooser(smsToSend);
    }

    @Override
    public void onSmsLongClicked(Sms sms) {
        view.showDeleteConfirmationDialog(sms);
    }

    @Override
    public void onDateTimeSet(Calendar dateForSms) {
        smsToSend =new Sms();
        smsToSend.setDate(dateForSms);
        view.chooseContact();
    }
}
