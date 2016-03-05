package com.ohdroid.zbmaster.login.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.ohdroid.zbmaster.R
import com.ohdroid.zbmaster.login.model.AccountInfo
import com.ohdroid.zbmaster.login.presenter.LoginPresenter
import javax.inject.Inject

/**
 * Created by ohdroid on 2016/3/5.
 */
class LoginFragment : JLoginFragment(), LoginView, View.OnClickListener {


    var loginBtn: Button? = null
    var registerBtn: Button? = null

    lateinit var loginPresenter: LoginPresenter;

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        component.inject(this)
        loginPresenter = component.loginPresenter()

        loginPresenter.attachView(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.login_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginBtn = view?.findViewById(R.id.btn_qq_login) as Button
        registerBtn = view?.findViewById(R.id.btn_wechat_login) as Button

        loginBtn?.setOnClickListener(this)
        registerBtn?.setOnClickListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        loginPresenter.attachView(this)
    }

    override fun onClick(btn: View?) {
        when (btn?.id) {
            R.id.btn_qq_login -> loginPresenter.login(AccountInfo(110, "232", 1, "null"))
            R.id.btn_wechat_login -> loginPresenter.register(AccountInfo(110, "232", 1, "null"))
        }
    }

    //===============================响应行为相关==============================
    override fun showProgress() {
    }

    override fun loginSuccess() {
    }

    override fun loginFailed(errorMessage: String) {
    }

    override fun registerSuccess() {
    }

    override fun registerFailed(errorMessage: String) {
    }

}