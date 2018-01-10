package fr.blanquartf.smsprogrammer;

import android.app.Application;

import fr.blanquartf.smsprogrammer.dagger.AppComponent;
import fr.blanquartf.smsprogrammer.dagger.DaggerAppComponent;
import fr.blanquartf.smsprogrammer.dagger.SmsModule;

/**
 * Created by blanquartf on 04/01/2018.
 */

public class SmsProgrammerApplication extends Application {
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder()
                .smsModule(new SmsModule(getApplicationContext()))
                .build();

        // If a Dagger 2 component does not have any constructor arguments for any of its modules,
        // then we can use .create() as a shortcut instead:
        //  mNetComponent = com.codepath.dagger.components.DaggerNetComponent.create();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
