package com.ohdroid.zbmaster.di

import android.content.Context
import android.media.AudioManager
import com.ohdroid.zbmaster.di.exannotation.ForApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by ohdroid on 2016/2/27.
 */
@Module
class AppModule(val context: Context) {

    @ForApplication
    @Provides
    @Singleton
    fun provideContext(): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideAudioManager(): AudioManager {
        return context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    }
}