package fr.blanquartf.smsprogrammer.smsContent;

import fr.blanquartf.smsprogrammer.BasePresenter;
import fr.blanquartf.smsprogrammer.BaseView;
import fr.blanquartf.smsprogrammer.smsList.SmsListContract;

/**
 * Created by blanquartf on 03/01/2018.
 */

public interface SmsContentContract {
    interface View extends BaseView<SmsListContract.Presenter> {

        String getSmsContent();

        void goToSmsList();

        void addTextToContent(String format);
    }

    interface Presenter extends BasePresenter{

        void programSmsSend();

        void addPatternToField(String item);
    }
}
