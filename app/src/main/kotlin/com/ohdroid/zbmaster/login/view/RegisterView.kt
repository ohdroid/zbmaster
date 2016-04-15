package com.ohdroid.zbmaster.login.view

import com.ohdroid.zbmaster.application.BaseView

/**
 * Created by ohdroid on 2016/4/15.
 */
interface RegisterView : BaseView {
    /**
     *注册成功
     */
    fun registerSuccess()

    /**
     * 注册失败
     */
    fun registerFailed(state: Int, errorMessage: String)

}