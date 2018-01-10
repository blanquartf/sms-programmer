package fr.blanquartf.smsprogrammer.smsContent;

import javax.inject.Inject;

import fr.blanquartf.smsprogrammer.data.Sms;
import fr.blanquartf.smsprogrammer.data.SmsDao;
import fr.blanquartf.smsprogrammer.utils.Constants;
import fr.blanquartf.smsprogrammer.utils.SmsUtils;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by blanquartf on 03/01/2018.
 */

public class MessageContentPresenter implements SmsContentContract.Presenter{
    @Inject
    SmsUtils smsUtils;
    @Inject
    SmsDao dao;
    private Sms sms;
    private SmsContentContract.View view;

    public MessageContentPresenter(SmsContentContract.View view, Sms sms) {
        this.view=view;
        this.sms=sms;
    }

    @Override
    public void programSmsSend() {
        sms.setSmsContent(view.getSmsContent());
        Single<Sms> observable = Single.fromCallable(() -> {
            sms.setId(dao.addSms(sms));
            return sms;
        });
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(sms -> {
                        smsUtils.programSmsSend(sms);
                        view.goToSmsList();
                    });
    }

    @Override
    public void addPatternToField(String item) {
        view.addTextToContent(String.format(Constants.PATTERN_FOR_REPLACEMENT,item));
    }
}
