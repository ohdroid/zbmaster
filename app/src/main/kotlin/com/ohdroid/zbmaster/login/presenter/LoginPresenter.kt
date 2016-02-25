package com.ohdroid.zbmaster.login.presenter

import android.content.Context

/**
 * Created by ohdroid on 2016/2/25.
 */
interface LoginPresenter {

    fun addContext(context: Context)

    fun qqLogin()

    fun weChatLogin()
}
