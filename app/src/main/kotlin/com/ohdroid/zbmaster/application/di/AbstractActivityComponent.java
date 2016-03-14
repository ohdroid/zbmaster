package com.ohdroid.zbmaster.application.di;

import android.app.Activity;

import com.ohdroid.zbmaster.application.di.exannotation.PerActivity;

import dagger.Component;

/**
 * Created by ohdroid on 2016/2/28.
 * activity 级抽象的共有的入口
 */
@PerActivity
@Component(dependencies = ApplicationModule.class, modules = PerActivityModule.class)
public interface AbstractActivityComponent {

    @PerActivity
    Activity activity();

}
