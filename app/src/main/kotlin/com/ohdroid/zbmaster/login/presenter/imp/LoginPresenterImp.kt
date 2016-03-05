package com.ohdroid.zbmaster.login.presenter.imp

import android.app.Activity
import android.content.Context
import android.widget.Toast
import com.ohdroid.zbmaster.application.BaseView
import com.ohdroid.zbmaster.di.exannotation.PerActivity
import com.ohdroid.zbmaster.login.model.AccountInfo
import com.ohdroid.zbmaster.login.presenter.LoginPresenter
import com.ohdroid.zbmaster.login.view.LoginActivity
import com.ohdroid.zbmaster.login.view.LoginView
import javax.inject.Inject

/**
 * Created by ohdroid on 2016/2/25.
 */
class LoginPresenterImp constructor(var context: Context) : LoginPresenter {

    var loginView: LoginView? = null

    override fun detachView() {
        this.loginView = null;
    }

    override fun attachView(view: LoginView) {
        this.loginView = view
    }

    override fun register(accountInfo: AccountInfo) {
    }

    override fun login(accountInfo: AccountInfo) {

    }

    //    @Inject
    //    lateinit var context: LoginActivity
    //
    //    override fun addContext(context: Context) {
    //        this.context = context
    //    }
    //    @Inject
    //    @PerActivity
    //    lateinit var context: Activity;

    //    override fun qqLogin() {
    //        Toast.makeText(context, "qqLogin..", Toast.LENGTH_SHORT).show()
    //    }
    //
    //    override fun weChatLogin() {
    //        Toast.makeText(context, "weChatLogin..", Toast.LENGTH_SHORT).show()
    //    }
}