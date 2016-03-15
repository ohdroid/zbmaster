package com.ohdroid.zbmaster.login.view

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.ohdroid.zbmaster.R
import com.ohdroid.zbmaster.application.ex.showToast
import com.ohdroid.zbmaster.login.model.AccountInfo
import com.ohdroid.zbmaster.login.presenter.LoginPresenter
import org.jetbrains.anko.support.v4.find

/**
 * Created by ohdroid on 2016/3/5.
 */
class LoginFragment : JLoginFragment(), LoginView, View.OnClickListener {


    val loginBtn: Button by lazy {
        find<Button>(R.id.btn_login)
    }
    val registerBtn: Button by lazy {
        find<Button>(R.id.btn_register)
    }
    val userName: EditText by lazy { find<EditText>(R.id.et_name) }
    val userPassword: EditText  by lazy { find<EditText>(R.id.et_password) }

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

        loginBtn.setOnClickListener(this)
        registerBtn.setOnClickListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        loginPresenter.attachView(this)
    }

    override fun onClick(btn: View?) {
        when (btn?.id) {
            R.id.btn_login -> login()
        }
    }

    fun login() {
        if (TextUtils.isEmpty(userName.text.toString())) {
            println("user name empty")
            return
        }

        if (TextUtils.isEmpty(userPassword.text.toString())) {
            println("password empty")
            return
        }

        loginPresenter.login(AccountInfo(userName.text.toString(), userPassword.text.toString()))
    }

    //===============================响应行为相关==============================
    override fun showProgress() {
    }

    override fun loginSuccess() {
        showToast("login success");
    }

    override fun loginFailed(errorMessage: String) {
    }

    override fun registerSuccess() {
        println("regist success")
    }

    override fun registerFailed(errorMessage: String) {
        println("regist faile" + errorMessage)
    }

}