package com.ohdroid.zbmaster.di;

import android.content.Context;
import android.media.AudioManager;

import com.ohdroid.zbmaster.di.exannotation.ForApplication;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ohdroid on 2016/2/27.
 */
@Singleton
@Component(modules = AppModule.class)
public interface ApplicationComponent {

    @ForApplication
    Context getApplicationContext();//提供application 且需要@ForApplication过滤

    AudioManager getAudioManager();

}
