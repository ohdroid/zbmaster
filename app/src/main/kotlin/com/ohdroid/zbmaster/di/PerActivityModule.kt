package com.ohdroid.zbmaster.di

import android.app.Activity
import com.ohdroid.zbmaster.di.exannotation.PerActivity
import dagger.Module
import dagger.Provides

/**
 * Created by ohdroid on 2016/2/28.
 */
@Module
class PerActivityModule(val activity: Activity) {

    @Provides
    @PerActivity
    fun provideActivity(): Activity {
        return activity
    }

}