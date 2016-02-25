package com.ohdroid.zbmaster.login.view

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import com.ohdroid.zbmaster.R
import com.ohdroid.zbmaster.base.view.BaseActivity
import com.ohdroid.zbmaster.login.presenter.LoginPresenter
import com.ohdroid.zbmaster.login.presenter.imp.LoginPresenterImp

/**
 * Created by ohdroid on 2016/2/25.
 */
class LoginActivity : BaseActivity() {

    override fun getContext(): Context = this@LoginActivity

    val btnQQLogin by lazy { findViewById(R.id.btn_qq_login) }
    val btnWeChatLogin by lazy { findViewById(R.id.btn_wechat_login) }
    lateinit var presenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("info", "===========onCreate=========");
        setContentView(R.layout.login_layout)

        initViews()

        initPresenter();
    }

    fun initViews() {
        btnQQLogin.setOnClickListener(mBtnOnClickListener)
        btnWeChatLogin.setOnClickListener(mBtnOnClickListener)
    }

    fun initPresenter() {
        //TODO inject by Dagger2
        presenter = LoginPresenterImp()
        presenter.addContext(this@LoginActivity)
    }

    //this view btn onClickListener
    var mBtnOnClickListener: View.OnClickListener = View.OnClickListener { view ->

        when (view.id) {
            R.id.btn_qq_login -> presenter.qqLogin()
            R.id.btn_wechat_login -> presenter.weChatLogin()
        }

    }
}
