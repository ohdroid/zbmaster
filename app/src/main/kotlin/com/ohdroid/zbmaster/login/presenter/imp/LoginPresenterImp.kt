package com.ohdroid.zbmaster.login.presenter.imp

import android.content.Context
import com.ohdroid.zbmaster.application.data.DataManager
import com.ohdroid.zbmaster.login.data.LoginManager
import com.ohdroid.zbmaster.login.model.AccountInfo
import com.ohdroid.zbmaster.login.presenter.LoginPresenter
import com.ohdroid.zbmaster.login.view.LoginView

/**
 * Created by ohdroid on 2016/2/25.
 */
class LoginPresenterImp constructor(var context: Context, val dataManager: DataManager) : LoginPresenter {

    var loginView: LoginView? = null

    override fun detachView() {
        this.loginView = null;
    }

    override fun attachView(view: LoginView) {
        this.loginView = view
    }

    override fun register(accountInfo: AccountInfo) {
        dataManager.loginManger.regist(accountInfo, object : LoginManager.LoginListener {
            override fun onSuccess() {
                loginView?.registerSuccess()
            }

            override fun onFailed(msg: String) {
                loginView?.registerFailed(msg)
            }

        })
    }

    override fun login(accountInfo: AccountInfo) {
        dataManager.loginManger.login(accountInfo.userName, accountInfo.password, object : LoginManager.LoginListener {
            override fun onFailed(msg: String) {
                loginView?.loginFailed(msg)
            }

            override fun onSuccess() {
                loginView?.loginSuccess()
                println("user info========>"+dataManager.loginManger.accountManager?.getUserAccount().toString())
            }

        })
    }
}