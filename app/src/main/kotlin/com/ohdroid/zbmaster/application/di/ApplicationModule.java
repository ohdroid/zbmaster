package com.ohdroid.zbmaster.application.di;

import android.content.Context;

import com.ohdroid.zbmaster.application.di.exannotation.ForApplication;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ohdroid on 2016/2/27.
 * application 级服务
 * 由于Kotlin 与 java 的static的问题我还没有好的解决方式，目前使用java写module
 */
@Module
public class ApplicationModule {
    private Context context;

    public ApplicationModule(Context context) {
        this.context = context;
    }

    @Provides
    @ForApplication
    public Context provideContext() {
        return context;
    }

}
