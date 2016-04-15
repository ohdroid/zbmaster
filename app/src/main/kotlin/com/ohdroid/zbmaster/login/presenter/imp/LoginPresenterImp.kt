package com.ohdroid.zbmaster.login.presenter.imp

import android.content.Context
import cn.bmob.v3.listener.SaveListener
import com.ohdroid.zbmaster.application.data.DataManager
import com.ohdroid.zbmaster.login.data.LoginManager
import com.ohdroid.zbmaster.login.model.AccountInfo
import com.ohdroid.zbmaster.login.presenter.LoginPresenter
import com.ohdroid.zbmaster.login.view.LoginView
import com.ohdroid.zbmaster.login.view.RegisterView

/**
 * Created by ohdroid on 2016/2/25.
 */
class LoginPresenterImp constructor(var context: Context, val dataManager: DataManager) : LoginPresenter {


    var loginView: LoginView? = null
    var registerView: RegisterView? = null
    override fun detachView() {
        this.loginView = null;
    }

    override fun attachView(view: LoginView) {
        this.loginView = view
    }

    override fun attachRegisterView(registerView: RegisterView?) {
        this.registerView = registerView
    }


    override fun register(accountInfo: AccountInfo) {
        dataManager.loginManger.regist(accountInfo, object : LoginManager.LoginListener {
            override fun onSuccess() {
                registerView?.registerSuccess()
            }

            override fun onFailed(state: Int, msg: String) {
                registerView?.registerFailed(state, msg)
            }

        })
    }

    override fun login(accountInfo: AccountInfo) {
        println("==============loginmanager====>${dataManager}")

        dataManager.loginManger.login(accountInfo, object : SaveListener() {
            override fun onFailure(p0: Int, p1: String?) {
                loginView?.loginFailed("$p0-------login failed----------$p1")
            }

            override fun onSuccess() {
                loginView?.loginSuccess()
            }
        })
    }
}