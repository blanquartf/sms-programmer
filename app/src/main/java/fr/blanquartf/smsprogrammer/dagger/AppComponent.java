package fr.blanquartf.smsprogrammer.dagger;

import javax.inject.Singleton;

import dagger.Component;
import fr.blanquartf.smsprogrammer.smsContent.MessageContentPresenter;
import fr.blanquartf.smsprogrammer.smsList.SmsListPresenter;

/**
 * Created by blanquartf on 04/01/2018.
 */

@Singleton
@Component(modules = {SmsModule.class})
public interface AppComponent {
    void inject(SmsListPresenter mainActivity);
    void inject(MessageContentPresenter mainActivity);
}
