package com.ohdroid.zbmaster.application

import android.app.Application
import cn.bmob.v3.Bmob
import com.ohdroid.zbmaster.BuildConfig

/**
 * Created by ohdroid on 2016/2/24.
 *
 * 全局设置:
 * 1.导入Bmob后台
 */
class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Bmob.initialize(this, BuildConfig.BMOB_APP_KEY);
    }
}
