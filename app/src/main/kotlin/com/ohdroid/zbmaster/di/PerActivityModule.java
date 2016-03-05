package com.ohdroid.zbmaster.di;

import android.app.Activity;

import com.ohdroid.zbmaster.di.exannotation.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ohdroid on 2016/3/5.
 */
@Module
public class PerActivityModule {
    protected Activity activity;

    public PerActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    public Activity provideContext() {
        return activity;
    }


}
