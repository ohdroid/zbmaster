package com.ohdroid.zbmaster.login.presenter.imp

import android.content.Context
import com.ohdroid.zbmaster.login.model.AccountInfo
import com.ohdroid.zbmaster.login.presenter.LoginPresenter
import com.ohdroid.zbmaster.login.view.LoginView

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
        println(accountInfo.password)
    }

    override fun login(accountInfo: AccountInfo) {
        println(accountInfo.userId)
    }
}