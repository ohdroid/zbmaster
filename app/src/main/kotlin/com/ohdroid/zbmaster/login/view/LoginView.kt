package com.ohdroid.zbmaster.login.view

import android.app.Activity
import android.support.v4.app.Fragment
import com.ohdroid.zbmaster.application.BaseView

/**
 * Created by ohdroid on 2016/3/5.
 */
interface LoginView : BaseView {
    /**
     * 显示登录进度
     */
    fun showProgress()

    /**
     * 登录成功
     */
    fun loginSuccess()

    /**
     * 登录失败
     */
    fun loginFailed(errorMessage: String)

    /**
     * 由于QQ登录使用所以这里返回Fragment
     */
    fun getCurrentFragment(): Fragment
}