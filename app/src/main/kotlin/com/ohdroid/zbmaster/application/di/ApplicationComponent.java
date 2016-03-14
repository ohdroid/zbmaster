package com.ohdroid.zbmaster.application.di;

import android.content.Context;

import com.ohdroid.zbmaster.application.data.DataManager;
import com.ohdroid.zbmaster.application.di.exannotation.ForApplication;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ohdroid on 2016/2/27.
 */
@Singleton
//@Component(modules = AppModule.class)
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    @ForApplication
    Context getApplicationContext();//提供application 且需要@ForApplication过滤

//    AudioManager getAudioManager();

    DataManager dataManger();

}
