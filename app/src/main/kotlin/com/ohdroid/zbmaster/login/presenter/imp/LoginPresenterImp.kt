package com.ohdroid.zbmaster.login.presenter.imp

import android.content.Context
import android.widget.Toast
import com.ohdroid.zbmaster.login.presenter.LoginPresenter

/**
 * Created by ohdroid on 2016/2/25.
 */
class LoginPresenterImp() : LoginPresenter {
    lateinit var context: Context

    override fun addContext(context: Context) {
        this.context = context
    }

    override fun qqLogin() {
        Toast.makeText(context, "qqLogin", Toast.LENGTH_SHORT).show()
    }

    override fun weChatLogin() {
        Toast.makeText(context, "weChatLogin", Toast.LENGTH_SHORT).show()
    }
}