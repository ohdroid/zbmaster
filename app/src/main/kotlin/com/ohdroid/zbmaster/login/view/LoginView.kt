package com.ohdroid.zbmaster.login.view

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
     * 注册成功
     */
    fun registerSuccess()

    /**
     * 注册失败
     * @param errorMessage 错误原因
     */
    fun registerFailed(errorMessage: String)
}